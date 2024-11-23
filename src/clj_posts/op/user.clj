(ns clj-posts.op.user
  (:require [clojure.spec.alpha :as s]
            [datomic.client.api :as d]
            [clj-posts.d.utils :as dutils]
            [clj-posts.d.core :as dcore]
            [clj-posts.op.core :as core]))

(defmethod core/op-spec :user/create-user [_]
  (s/keys :req [:user/name :user/email]))

(defmethod core/op-effects :user/create-user
  [{:keys [conn now]} {:user/keys [name email]}]
  (cond (dutils/d-empty? conn ['?eid :user/name name])   (core/op-throw :invalid-user-name)
        (dutils/d-empty? conn ['?eid :user/email email]) (core/op-throw :invalid-user-email)
        :else (let [id (dcore/->id)]
                {:db [{:user/id id :create-at now :user/name name :user/email email}
                      {:auth/id id :create-at now :auth/code ""}]
                 :email []})))

(defmethod core/op-spec :user/active-user [_]
  (s/keys :req [:auth/id :auth/code :user/password]))

(defmethod core/op-effects :user/active-user [{:keys [conn]} {:auth/keys [id code password]}]
  (let [auth-eid (dutils/d-eid conn ['?eid :auth/id id])
        auth-code (first (d/q [:find '?code :where [auth-eid :auth/code '?code]] (d/db conn)))]
    (if-not (= auth-code code)
      (core/op-throw :invalid-auth-code)
      (let [user-id (first (d/q [:find '?user :where [auth-eid :auth/user '?user]] (d/db conn)))
            user-eid (dutils/d-eid conn ['?eid :user/id user-id])]
        {:db [{:db/id user-eid :user/password password}]}))))
