(ns functions)

(defn total
  ([price qty]
    (* price qty))
  ([price]
   (* price 1)))
(total 2 3)
(total 2)

(defn total2 [price & qty]
    (apply * price qty))

(total2 10 2 3)

(defn countdown [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 100))
        (println  "countdown: " n))
      (recur (dec n)))))

(countdown 100)

(def  books {})

(some (fn [b] :books > 5) books)

(def ^:dynamic *evalme* 10)

(binding [*evalme* 10]
  (str "hello" *evalme*))
