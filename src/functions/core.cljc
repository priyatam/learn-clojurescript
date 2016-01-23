(ns functions.core)


;; Apply

(apply str ["hello" " " "world"])

(max 1 2 3)

(apply max [1 2 3])


;; Regular Functions

(defn foo [str]
  (println "hello " str))

(defn total
  ([price qty]
   (* price qty))
  ([price]
   (* price 10)))


;; Dynamic Binding

(def ^:dynamic *evalme* 10)

(binding [*evalme* 20]
  (str "hello" *evalme*))

(str "hello" *evalme*)
