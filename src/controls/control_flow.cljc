(ns thinking.control-flow
  (:require [clojure.string :as str]))

;;;;;;;;
;; Simple Loops

(defn countdown [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 10))
        (println  "countdown: " n))
      (recur (dec n)))))

;; (countdown 100)

;;;;;;;;;;;;;;;;;;;;;
;; List Comprehensions

(defn prime? [n]
  (not-any? zero? (map #(rem n %) (range 2 n))))

(for [x (range 3 17 2) :when (prime? x)
      y (range 3 17 2) :when (prime? y)]
  [x y])

;; Recursion

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
    (if (> start-num (Math/sqrt num)) true
        (if (= (rem num start-num) 0) false
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

;; (find-needle "*" "hay|stack")

(defn mustache-template [tpl env]
  (loop [tpl tpl
         env env]
    (cond (empty? env) tpl
          :else        (let [[key value] (first env)]
                         (recur
                          (try
                            (str/replace tpl
                                         (re-pattern (str "\\{\\{" (name key) "\\}\\}"))
                                         value)
                            (catch Exception e tpl))
                          (rest env))))))
