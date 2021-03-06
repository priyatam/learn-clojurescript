(ns functions.higher-order
  (:require [clojure.zip :as z]))

;; higher order functions

(defn present [gift]
  (fn [] gift))

(def green-box (present "clockwork beetle"))
(def red-box (present "plush tiger"))

(comment "repl"
         (red-box))

;; Map

(map inc [1 2 3 4 5])

;; map can be used with multiple collections that're passed to the mapping fn in parallel:
(map + [1 2 3] [4 5 6])

;; When map is passed more than one collection, the mapping function will
;; be applied until one of the collections runs out:
(map + [1 2 3] (iterate inc 1))

;; map is often used with closures
(map #(str "Hello " % "!" ) ["Ford" "Jose" "Patel"])

;; Pull "columns" out of a collection of collections.
(map vector [:a :b :c] {:one 1 :two 2 :three 3} [:g :h :i])

(apply map vector [[:a :b :c]
                   {:one 1 :two 2 :three 3}
                   [:g :h :i]])

(map #(vector (first %) (let [crzy (fn [e]
                                     (* e 10))]
                          (* (crzy 10) (second %))))
     {:a 1 :b 2 :c 3})

;; applies fn (list) to first element of each collection as (fn 1 a 10)
;; applies fn to second ...
;; ignores unmatched columns

(map list [1 2 3] '(a b c) [10 6])

(def d1 [:a :b :c])
(#(map list % (range)) d1)

;; Q: return a collection with the max within each collection
;; [[1 2 3][4 5 6][7 8 9]]

;; You can use map and apply together to drill one level deep in a collection of
;; colls, in this case returning a collection of the max of each nested coll

(map #(apply max %)
     [[1 2 3][4 5 6][7 8 9]])

;; Filter

(filter even? (range 10))

(filter #(= (count %) 1)
        ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""])

;; When coll is a map, pred is called with key/value pairs.
(filter #(> (second %) 100)
        {:a 1
         :b 2
         :c 101
         :d 102
         :e -1})

;;(into {} *1)

(filter #(if (odd? %) %) (range 10))

;; Use a hash-map as a function to translate values in a collection from the
;; given key to the associated value; then use (filter identity... to remove the nils
(map 
 (filter even?
         (map
          {2 "two" 3 "three"} [1 2 3 4 5])))


;; Reduce

(reduce + [1 2 3 4 5])

;; converting a vector to a set
(reduce conj #{} [:a :b :c])

;; Word frequency example
(def text
  "Clojure is a concise, powerful, and performant general-purpose programming
  language that runs on the JVM, CLR, Node.js, and modern mobile and desktop web
  browsers. It's based on Lisp, the most powerful programming language in the
  world.")

(defn word-frequency [s]
  (reduce #(assoc %1 %2 (inc (%1 %2 0)))
          {}
          (re-seq #"\w+" s)))


;; Build your own ring Middleware pattern

;; request
(def request
  {:server-port "8080"
   :content-type :text
   :remote-addr "http://localhost:8080"})

;; handler
(defn my-ip [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (:remote-addr request)})

;; middleware
(defn wrap-content-type [handler content-type]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "Content-Type"] content-type))))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/edn"}
   :body (pr-str data)})

(def app
  (wrap-content-type my-ip "text/html"))

(defn serve [request]
  (app request))


;; mapcat

(mapcat reverse [[3 2 1 0] [6 5 4] [9 8 7]])

;; Let's play with Maps

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


;; Useful higher order functions from std library

;; reverse

(reverse [1 2 3])

;; remove (opp of filter)

(remove #(if (odd? %) %) (range 10))



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
