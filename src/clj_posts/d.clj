(ns clj-posts.d
  (:require [datomic.client.api :as d]
            [clj-posts.d.user :as user]
            [clj-posts.d.post :as post]))

(def schema
  (->> (concat
        user/schema
        post/schema)
       vec))

(def seeds
  [(assoc user/root-user :db/id -1)
   (assoc post/root-post :db/id -2)])

(defn connect
  ([]
   (connect nil))
  ([{:keys [client-config db-name]
     :or {client-config {:system "clj-posts" :server-type :datomic-local}
          db-name "clj-posts"}}]
   (let [client (d/client client-config)]
     (d/create-database client {:db-name db-name})
     (let [conn (d/connect client {:db-name db-name})]
       (d/transact conn {:tx-data schema})
       (d/transact conn {:tx-data seeds})
       conn))))
