(ns recursion
  (:require [clojure.string :as str]))

;; Loop binds initial values once, then binds values from each recursion call

(def factorial
  (fn [n]
    (loop [cnt n acc 1]
       (if (zero? cnt)
            acc
          (recur (dec cnt) (* acc cnt))))))

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
