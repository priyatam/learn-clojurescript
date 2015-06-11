(ns probability
  (:require [clojure.set :refer :all]))

(defn fact [n]
  (reduce * (range 1N (inc (bigint n)))))

(defn nPr [n r]
  ( / (fact n) (fact (- n r))))

(defn nCr [n r]
  ( / (fact n) (* (fact r) (fact (- n r)))))

(defn prob [pop, m, n]
  (if (>= 0 n) 1
    (* (double (/ m pop)) (prob (- pop 1) (- m 1) (- n 1)))))

;; RUN

(nPr 6 3)
(nCr 6 3)

(prob 100 10 5)

(comment
  matrix - 10 * 10 = 100 (n)
  assume 5% are starbucks = 5 (r)
  each box can have a store or not
  total stores - 100
  total paths)
