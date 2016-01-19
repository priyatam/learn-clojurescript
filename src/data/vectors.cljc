(ns data.vectors)

(def my-vec (vector 1 2 3))

(let [[first-element & the-rest] my-vec]
  (str "first=" first-element " - " the-rest))
