(ns seqs
  (:require [clojure.zip :as zip]))

(def data
  {:headers {"Content-Type" "text/html"}
   :body {:text {:html "markup"}
          :images "/img/path-to-img.jpg"}})

(assoc-in data [:body :text] "Hello Planet" )


(def matrix [[1 2 3 4 5]
             ["a" "b" "c" "d" "e"]
             ["hello" "thanks" "hola" "namaste" "hi"]])

;;;;;;;;;
;; Zip

;; example from clojure source: zip.clj

(def zip1 [1 '(a b c) 2])

(def root-loc (zip/seq-zip (seq zip1)))

(def data '[[a * b] + [c * d]])
(def dz (vector-zip data))

(right (down (right (right (down dz)))))
(lefts (right (down (right (right (down dz))))))
(rights (right (down (right (right (down dz))))))
(up (up (right (down (right (right (down dz)))))))
(path (right (down (right (right (down dz))))))

(-> dz down right right down right)
(-> dz down right right down right (replace '/) root)
(-> dz next next (edit str) next next next (replace '/) root)
(-> dz next next next next next next next next next remove root)
(-> dz next next next next next next next next next remove (insert-right 'e) root)
(-> dz next next next next next next next next next remove up (append-child 'e) root)

(end? (-> dz next next next next next next next next next remove next))

(-> dz next remove next remove root)

(loop [loc dz]
  (if (end? loc)
    (root loc)
    (recur (next (if (= '* (node loc))
                   (replace loc '/)
                   loc)))))

(loop [loc dz]
  (if (end? loc)
    (root loc)
    (recur (next (if (= '* (node loc))
                   (remove loc)
                   loc)))))

(defn print-tree [original]
  (loop [loc (zip/seq-zip (seq original))]
    (if (zip/end? loc)
      (zip/root loc)
      (recur (zip/next
                (do (println (zip/node loc))
                    loc))))))

;;;;;;;;;
;; pmap
;; Like map, except f is applied in parallel.

(defn long-running-job [n]
  (Thread/sleep 1000)
  (+ n 10))

(time (doall (map long-running-job (range 4))))

(time (doall (pmap long-running-job (range 4))))

;;;;;;;;;;;
;; reverse

(reverse [1 2 3])

;;;;;;;;;;;
;; remove (opp of filter)

(remove #(if (odd? %) %) (range 10))


;;;;;;;;;;;
;; comp

(defn reverse-and-take-rest [numbers]
  (rest (reverse numbers)))

(reverse-and-take-rest [1 2 3 4 5])

;; how about writing a generic function?
;; ((comp rest reverse) [1 2 3 4])

(defn square [x]
  (* x x))

(def compai (comp square first rest reverse))

(compai [1 2 3 4 5])

(map
 (comp - (partial + 3) (partial * 2))
 [1 2 3 4])

;; credit: http://commandercoriander.net/blog/2015/02/21/writing-clojures-comp-function-from-scratch/

;; first: try a recursive function
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

;; second: try a reducing fn
(defn red-comp [& all-fns]
  (reduce
   (fn [acc-fn curr-fn]
     (fn [a]
       (acc-fn (curr-fn a)))
     identity
     all-fns)))

(def red-comp (comp rest reverse))
(red-comp [1 2 3 4 5])
