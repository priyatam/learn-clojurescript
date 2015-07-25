(ns thinking.closures)

(def foo
  (let [counter (atom 0)]
    (fn []
      (do (swap! counter inc) @counter))))

(def ^:dynamic *evalme* 10)

;; Dynamic Binding

(binding [*evalme* 20]
  (str "hello" *evalme*))

(str "hello" *evalme*)
