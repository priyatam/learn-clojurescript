(ns functions.core)


;; Basics

(defn foo [str]
  (println "hello " str))

(defn total
  ([price qty]
   (* price qty))
  ([price]
   (* price 10)))

(def ^:dynamic *evalme* 10)


;; Apply

(apply str ["hello" " " "world"])

(max 1 2 3)

(apply max [1 2 3])


;; Dynamic Binding

(binding [*evalme* 20]
  (str "hello" *evalme*))

(str "hello" *evalme*)
