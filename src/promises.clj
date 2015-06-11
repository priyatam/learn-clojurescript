(ns promises)

(def f
  (future (Thread/sleep 10000) (println "done") 100))

;; @f

(future-cancel f)
(future-cancelled? f)
(future-done? f)

;; Create a promise
(def p (promise))

;; Check if was delivered/realized
(realized? p)

;; Delivering the promise
(deliver p 42)

;; Check again if it was delivered
(realized? p)

;; Deref to see what has been delivered
;;@p
;;(deref p)


(let
    [sum (future (apply + (range 1e7)))]
    (println "Started...")
    (println "Done: " @sum))
