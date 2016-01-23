(ns functions.compose)


;; Basics

(def process-strings
  (comp keyword #(.toLowerCase %) name))

(comment
  (map process-strings '(a B W)))

(defn reverse-and-take-rest [numbers]
  (rest (reverse numbers)))

(reverse-and-take-rest [1 2 3 4 5])

(comment
  ((comp rest reverse) [1 2 3 4]))

(defn square [x]
  (* x x))

(def compai (comp square first rest reverse))

(compai [1 2 3 4 5])

(map
 (comp - (partial + 3) (partial * 2))
 [1 2 3 4])

;; Write your own Compose function

;; as a recursive function
(defn recur-comp [& all-fns]
  (loop [fns all-fns
         acc-fn identity]
    (if (empty? fns) acc-fn
        (recur
         (butlast fns)
         (fn [a]
           ((last fns) (acc-fn a)))))))

(def recur-comp (comp rest reverse))
(recur-comp [1 2 3 4 5])

;; as a reducing fn
(defn red-comp [& all-fns]
  (reduce
   (fn [acc-fn curr-fn]
     (fn [a]
       (acc-fn (curr-fn a)))
     identity
     all-fns)))

(def red-comp (comp rest reverse))

(comment
  (red-comp [1 2 3 4 5]))
