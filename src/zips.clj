(ns zips
  (:use [clojure.zip]))

;;;;;;;;;
;; Zip

;; example from clojure source: zip.cljn

(def zip1 [1 '(a b c) 2])

(def root-loc (seq-zip (seq zip1)))

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
  (loop [loc (seq-zip (seq original))]
    (if (end? loc)
      (root loc)
      (recur (next
                (do (println (node loc))
                    loc))))))
