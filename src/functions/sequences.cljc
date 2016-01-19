(ns functions.sequences
  (:require [clojure.zip :as z]))

(def data
  {:headers {"Content-Type" "text/html"}
   :body {:text {:html "markup"}
          :images "/img/path-to-img.jpg"}})

(assoc-in data [:body :text] "Hello Planet" )

(def matrix [[1 2 3 4 5]
             ["a" "b" "c" "d" "e"]
             ["hello" "thanks" "hola" "namaste" "hi"]])

;; reverse

(reverse [1 2 3])

;; remove (opp of filter)

(remove #(if (odd? %) %) (range 10))

;; comp

(defn reverse-and-take-rest [numbers]
  (rest (reverse numbers)))

(reverse-and-take-rest [1 2 3 4 5])

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


;; Playing with Maps

(defn map-over-map
  "Given a function like (fn [k v] ...) returns a new map with each entry mapped
   by it."
  [f m]
  (when m
    (persistent! (reduce-kv (fn [out-m k v]
                              (apply assoc! out-m (f k v)))
                            (transient (empty m))
                            m))))

(defn map-values
  "Apply a function on all values of a map and return the corresponding map (all
   keys untouched)"
  [f m]
  (when m
    (persistent! (reduce-kv (fn [out-m k v]
                              (assoc! out-m k (f v)))
                            (transient (empty m))
                            m))))

(defn map-keys
  "Apply a function on all keys of a map and return the corresponding map (all
   values untouched)"
  [f m]
  (when m
    (persistent! (reduce-kv (fn [out-m k v]
                              (assoc! out-m (f k) v))
                            (transient (empty m))
                            m))))

(defn map-values
  "Apply a function on all values of a map and return the corresponding map (all
   keys untouched)"
  [f m]
  (when m
    (persistent! (reduce-kv (fn [out-m k v]
                              (assoc! out-m k (f v)))
                            (transient (empty m))
                            m))))

(defn invert-map
  "Inverts a map: key becomes value, value becomes key"
  [m]
  (zipmap (vals m) (keys m)))

;;; Zips

;; example from clojure source: zip.clj

(def zip1 [1 '(a b c) 2])

(def root-loc (z/seq-zip (seq zip1)))

(def data '[[a * b] + [c * d]])
(def dz (z/vector-zip data))

(z/lefts (z/right (z/down (z/right (z/right (z/down dz))))))
(z/rights (z/right (z/down (z/right (z/right (z/down dz))))))
(z/up (z/up (z/right (z/down (z/right (z/right (z/down dz)))))))
(z/path (z/right (z/down (z/right (z/right (z/down dz))))))

(-> dz z/down z/right z/right z/down z/right (z/replace '/) z/root)
(-> dz z/next z/next (z/edit str) z/next z/next z/next (z/replace '/) z/root)
(-> dz z/next z/next z/next z/next z/next z/next z/next z/next z/next z/remove
    (z/insert-right 'e) z/root)
(-> dz z/next z/next z/next z/next z/next z/next z/next z/next z/next z/remove z/up
    (z/append-child 'e) z/root)

(z/end? (-> dz z/next z/next z/next z/next z/next z/next z/next z/next z/next z/remove z/next))

(-> dz z/next z/remove z/next z/remove z/root)

(loop [loc dz]
  (if (z/end? loc)
    (z/root loc)
    (recur (z/next (if (= '* (z/node loc))
                   (z/replace loc '/)
                   loc)))))

(loop [loc dz]
  (if (z/end? loc)
    (z/root loc)
    (recur (z/next (if (= '* (z/node loc))
                   (z/remove loc)
                   loc)))))

(defn print-tree [original]
  (loop [loc (z/seq-zip (seq original))]
    (if (z/end? loc)
      (z/root loc)
      (recur (z/next
                (do (println (z/node loc))
                    loc))))))
