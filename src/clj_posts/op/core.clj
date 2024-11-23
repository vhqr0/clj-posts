(ns clj-posts.op.core
  (:require [clojure.spec.alpha :as s]))

(defn op-throw
  ([reason]
   (op-throw reason nil))
  ([reason opts]
   (throw (ex-info "Operation failed" (merge {:reason reason} opts)))))

(defmulti op-spec :op/type)

(s/def :op/op (s/multi-spec op-spec :op/type))

(defmulti op-effects (fn [_ctx op] (:op/type op)))
