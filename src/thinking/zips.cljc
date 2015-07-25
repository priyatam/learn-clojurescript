(ns thinking.zips
  (:require
   [clojure.zip :as z]))

;; example from clojure source: zip.cljn

(def zip1 [1 '(a b c) 2])

(def root-loc (seq-zip (seq zip1)))

(def data '[[a * b] + [c * d]])
(def dz (z/vector-zip data))

(z/right (z/own (z/right (z/right (z/down dz)))))
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
