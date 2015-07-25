(ns thinking.functions)

(defn foofn [str]
  (println "hello " str))

(defn total
  ([price qty]
   (* price qty))
  ([price]
   (* price 10)))

;;;;;;;;;;
;; apply

(apply str ["hello" " " "world"])

(max 1 2 3)

(apply max [1 2 3])

;;;;;;;;;
;; partial

(def hundred-times
  (partial * 100))

(defn total-price [price & qty]
  (apply * price qty))

(total-price 10 2 3)
