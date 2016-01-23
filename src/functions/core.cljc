(ns ^{:doc "All about functions and their scope"}
    functions.core)


;; Apply: the ultimate function!

(apply str ["hello" " " "world"])

(max 1 2 3)

(apply max [1 2 3])

(map (partial apply +) [[1 2] [3 4]])

;; Regular Functions

(defn foo [str]
  (println "hello " str))

;; Multi Arity

(defn total
  ([price qty]
   (* price qty))
  ([price]
   (* price 10)))

(defn greeting
  ([] (greeting "Hello" "world"))
  ([name] (greeting "Hello" name))
  ([salutation name] (str salutation ", " name "!")))

;; Variadic

(defn average
  ([x] x)
  ([x y] (/ (+ x y) 2))
  ([x y & extra] (/ (reduce + (+ x y) extra)
                    (+ 2 (count extra)))))

;; Destructuring

(defn printer [& {name :name
                  [hole1 hole2] :scores}]
  {"name" name "hole1" hole1 "hole2" hole2})

(comment
  (printer :name "Joey" :scores [42 18]))
