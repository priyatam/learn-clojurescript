(ns abstractions.macros)

(def foo "(println [1 2 3])")

(eval (read-string foo))

(defmacro delay
  [& body]
  (list 'new 'clojure.lang.Delay (list* `^{:once true} fn* [] body)))

(def tomorrow
  (delay (prn "Exec Tomorrow") (+ 1 2)))

(def fu
  (future (prn "hi") (+ 1 2)))

(dotimes [i 6] (future (prn i)))

;; FIXME
(defmacro ignore [body & handler]
  "Ingores a body that throws an exception by returning nil.
   If handler is passed, exc is returned to the handler."

  `(let [resp#  (try
                  ~body
                  (catch Exception e# e#))
         thrown-exc# (if (instance? Exception resp#)
                        true nil)
         success# (if (not thrown-exc#)
                    resp#)
         failure# (if thrown-exc#
                    (if (not (nil? ~handler))
                      (apply ~handler (.getMessage resp#))
                      nil)
                    nil)]
     (or success# failure#)))

(defmacro template
  [& body]
  `(let [es# (setup)]
     (binding [*out* s#]
       ~@body
       (str s#))))

(let [ex '(+ 1 2 3)]
  (cons '* (rest ex)))
