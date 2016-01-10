(ns thinking.control-flow)

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
