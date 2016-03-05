(ns data.lists)

(list 'a 'b 'c)

'(a b c)

(let [m {:1 1 :2 2 :3 3}]
  (map list (keys m) (vals m)))

(list* 1 [2 3])

(list? '(1 2 3))
(list? 0)
(list? {})
(list? [])
