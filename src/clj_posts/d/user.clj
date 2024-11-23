(ns clj-posts.d.user
  (:require [clojure.spec.alpha :as s]
            [clj-posts.d.core :as core]))

(s/def :user/id uuid?)
(s/def :user/create-at inst?)
(s/def :user/name string?)

(s/def :user/email string?)
(s/def :user/password string?)

(s/def :cp/user (s/keys :req [:user/id :user/create-at :user/name]
                        :opt [:user/email :user/password]))

(def root-user
  "Root user."
  #:user{:id core/root-id :create-at core/root-date :name "root"})

(def schema
  [{:db/ident       :user/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity}
   {:db/ident       :user/create-at
    :db/valueType   :db.type/instant
    :db/cardinality :db.cardinality/one}
   {:db/ident       :user/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident       :user/email
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident       :user/password
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}])
