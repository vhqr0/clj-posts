(ns clj-posts.d.post
  (:require [clojure.spec.alpha :as s]
            [clj-posts.d.core :as core]))

;;; spec

(s/def :post/id uuid?)
(s/def :post/parent uuid?)
(s/def :post/author uuid?)
(s/def :post/create-at inst?)
(s/def :post/name string?)

(s/def :post/keyword keyword?)
(s/def :post/keywords (s/coll-of :post/keyword))
(s/def :post/content-type #{:txt :html :md :org :tex})
(s/def :post/content string?)

(s/def :cp/post (s/keys :req [:post/id :post/parent :post/author :post/create-at :post/name]
                        :opt [:post/keywords :post/content-type :post/content]))

(def root-post
  "Root post."
  #:post{:id core/root-id :parent core/root-id :author core/root-id :create-at core/root-date :name "root"})

(def schema
  [{:db/ident       :post/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity}
   {:db/ident       :post/parent
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one}
   {:db/ident       :post/author
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one}
   {:db/ident       :post/create-at
    :db/valueType   :db.type/instant
    :db/cardinality :db.cardinality/one}
   {:db/ident       :post/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident       :post/keywords
    :db/valueType   :db.type/keyword
    :db/cardinality :db.cardinality/many}
   {:db/ident       :post/content-type
    :db/valueType   :db.type/keyword
    :db/cardinality :db.cardinality/one}
   {:db/ident       :post/content
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}])
