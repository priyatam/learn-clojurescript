(ns functions.compose)


(def process-strings
  (comp keyword #(.toLowerCase %) name))

(comment
  (map process-strings '(a B W)))

