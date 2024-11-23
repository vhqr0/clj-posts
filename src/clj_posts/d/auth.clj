(ns clj-posts.d.auth
  (:require [clojure.spec.alpha :as s]))

(s/def :auth/id uuid?)
(s/def :auth/user :user/id)
(s/def :auth/create-at inst?)
(s/def :auth/code string?)
(s/def :auth/password :user/password)

(defn ->code
  []
  (str (random-uuid)))
