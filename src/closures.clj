(ns closures)

(def foo
  (let [counter (atom 0)]
    (fn []
      (do (swap! counter inc) @counter))))
