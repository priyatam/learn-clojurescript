(ns macros)

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

(read-string "#(+ 1 2)")

(eval (read-string "'(1 2 3)"))

(eval #(+ 1 2))

(eval #(+ 1 2))

(read-string "(if true 1 2)")

(defn ignore-last [fn-call]
  (butlast fn-call))

#_(ignore-last (+ 1 2 3))
