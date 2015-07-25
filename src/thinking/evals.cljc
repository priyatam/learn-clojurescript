(ns thinking.evals)

(def foo "(println [1 2 3])")

(eval (read-string foo))
