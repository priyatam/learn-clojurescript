(ns seq)

(def data {:headers {"Content-Type" "text/html"} :body "Hello World" })

(assoc-in data [:status] 200 )

(def env
  (merge
   {:five 1 :two 22}
   {:four 4}
   {:one 1 :two 2 :three 3}))
