(ns clj-posts.d.utils
  (:require [datomic.client.api :as d]))

(defn d-eids
  [conn & where]
  (let [query `[:find ?eid :where ~@where]]
    (->> (d/db conn) (d/q query))))

(defn d-eid
  [conn & where]
  (->> (apply d-eids conn where) first))

(defn d-empty?
  [conn where]
  (->> (d-eids conn where) empty?))
