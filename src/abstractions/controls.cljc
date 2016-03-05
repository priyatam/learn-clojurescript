(ns abstractions.controls
  (:require [clojure.string :as str]))


;; Loops -----

(defn countdown [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 10))
        (str  "countdown: " n))
      (recur (dec n)))))

(comment
 (countdown 100))

;; Iterators -----

;; All datastructures support uniform iterations.

(def colorsv ["red" "orange" "green"])

(doseq [color colorsv]
  color)

(def data
  {:one 1
   :two 2
   :three 3})

(doseq [[k v] data]
  (str "key" k)
  (str "value" v))

;; List Comprehensions -----

(defn prime? [n]
  (not-any? zero? (map #(rem n %) (range 2 n))))

(for [x (range 3 17 2) :when (prime? x)
      y (range 3 100 2) :when (prime? y)]
  [x y])


(let [state true]
  (if state
    "yay"
    "nay"))

;; Recursion -----

;; Loop binds initial values once, then binds values from each recursion call

(def factorial
  (fn [n]
    (loop [cnt n acc 1]
      (if (zero? cnt)
        acc
        (recur (dec cnt) (* acc cnt))))))

(defn prime?
  [num]
  (loop [start-num 2]
    (if (> start-num (Math/sqrt num))
      true
      (if (= (rem num start-num) 0)
        false
        (recur (inc start-num))))))

(defn find-needle [needle haystack]
  (loop [needle needle
         maybe-here haystack
         not-here '()]
    (let [needle? (first maybe-here)]
      (if (or (= (str needle?) (str needle))
              (empty? maybe-here))
        [needle? maybe-here not-here]
        (recur needle
               (rest maybe-here)
               (concat not-here (list (first maybe-here))))))))

(comment
 (find-needle "*" "hay|stack"))

(let [res {:status "200"}]
  (cond
    (= (:status res) "200") (str "response successed")
    (= (:status res) "500") (str "response failed!")
    (= (:status res) "400")  (str "seriously, check your url")
    :else "forget it, start your server"))

(defn mustache-template [tpl env]
  (loop [tpl tpl
         env env]
    (cond
      (empty? env) tpl
      :else (let [[key value] (first env)]
              (recur
               (try
                 (str/replace tpl
                              (re-pattern (str "\\{\\{" (name key) "\\}\\}"))
                              value)
                 (catch Exception e tpl))
               (rest env))))))
