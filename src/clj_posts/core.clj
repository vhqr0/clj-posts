(ns clj-posts.core)

(defn ->id
  []
  (random-uuid))

(defn ->date
  ([]
   (java.util.Date.))
  ([timestamp]
   (java.util.Date. timestamp)))

(def root-id #uuid "cceb21ce-cd9b-41f8-9e7a-97b3f381952b")
(def root-date (->date 0))
