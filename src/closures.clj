(ns closures)

(let [x [1 2]]
  (prn (conj x :a))
  (prn (conj x :b)))

(defn present
  [gift]
  (fn [] gift))

(def green-box (present "clockwork beetle"))

(def red-box (present "plush tiger"))

(red-box)

(defmacro delay
  "Takes a body of expressions and yields a Delay object that will
  invoke the body only the first time it is forced (with force or deref/@), and
  will cache the result and return it on all subsequent force
  calls. See also - realized?"
  {:added "1.0"}
  [& body]
    (list 'new 'clojure.lang.Delay (list* `^{:once true} fn* [] body)))

(def tomorrow
  (delay (prn "Exec Tomorrow") (+ 1 2)))

(def fu
  (future (prn "hi") (+ 1 2)))

(dotimes [i 6]
  (future (prn i)))
