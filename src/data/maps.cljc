(ns data.maps)

(def a-map {:one 1
            :two 2})

(merge
 {:five 1 :two 22}
 {:four 4}
 {:one 1 :two 2 :three 3})

(def data
  {:headers {"Content-Type" "text/html"}
   :body {:text {:html "markup"}
          :images "/img/path-to-img.jpg"}})

(assoc-in data [:body :text] "Hello Planet" )

(def matrix [[1 2 3 4 5]
             ["a" "b" "c" "d" "e"]
             ["hello" "thanks" "hola" "namaste" "hi"]])
