(ns functions.partials)


(def hundred-times
  (partial * 100))

(defn total-price [price & qty]
  (apply * price qty))

(total-price 10 2 3)
