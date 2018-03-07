(ns abstractions.transducers
  #?(:cljs
     (:require-macros [cljs.core.async.macros :refer [go go-loop]]))
  (:require
   #?(:clj  [clojure.core.async :as async :refer [<! <!! >! >!! timeout chan]]
      :cljs [cljs.core.async    :as async :refer [<! >! timeout chan]])))

;; map, filter

(map inc (range 10))

(filter even? '(1 2 3 4 5 6 7 8 9 10))

;; write map and inc in terms of reduce

(defn map-inc-reducer [res in]
  (conj res (inc in)))

(reduce map-inc-reducer [] (range 10))

;; take out 'inc' and generize fn

(defn map-reducer [f]
  (fn [res in]
    (conj res (f in))))

(reduce (map-reducer #(* % %)) [] (range 10))

;; write a filter with predicate in terms of reduce

(defn filter-reducer [predicate]
  (fn [res in]
    (if (predicate in)
      (conj res in)
      res)))

(reduce (filter-reducer even?) [] '(1 2 3 4 5 6 7 8 9 10))

;; compose map-reducer and filter-reducer together

(reduce
  (filter-reducer even?)
  []
  (reduce
    (map-reducer inc)
    []
    (range 10)))

;; same as
(filter even? (map inc (range 10)))

;; what if, instead of conj, we let the user pass in whatever reducing function they want?
;; introducing: reducing function
;; result, input -> result

(defn mapping [f]
  (fn [reducing]
    (fn [res in]
      (reducing res (f in)))))

(defn filtering [predicate]
  (fn [reducing]
    (fn [res in]
      (if (predicate in)
        (reducing res in)
        res))))

(reduce
  ((filtering even?) conj)
  []
  (reduce
    ((mapping inc) conj)
    []
    (range 10)))

;; anything of the type: result, input -> result is a reducing function

((mapping inc) ((filtering even?) conj))

;; proof?

(reduce ((mapping inc) ((filtering even?) conj)) [] (range 10))

;; Since this looks like parens for the sake of parens, let's clean it up with 'comp'
;; a comp is takes a set of functions and returns a fn that is the composition of those fns.

(def steps
  (comp
    (mapping inc)
    (filtering even?)))

;; what's happening? we're composing reducing functions

(defn square [x] (* x x))

(defn sleep [x]
  (println x)
  (Thread/sleep 500)
  x)

(def steps
  (comp
   (filtering even?)
   (mapping sleep)
   (filtering #(< % 10))
   (mapping square)
   (mapping inc)))

(reduce (steps conj) [] (range 10))

;; filter and transform items in a channel.

(def my-chan (async/chan 1 steps))

;; Waiting for an item to print...
(async/take! my-chan println)

;; nothing printed to screen, since 3 is not even
(async/put! my-chan 3)

;; "17" printed to screen, since 4 is even and less than 10
(async/put! my-chan 4)


;; Next?
;; Transducers: (result, input -> result) -> (result, input -> result)
;; Transducers employ only one collection regardless of the no of transformations.
;; Both versions, however, require intermediate vectors, one each for map and filter.

(def xf (comp (filter odd?) (map inc)))

(transduce xf + (range 5))
