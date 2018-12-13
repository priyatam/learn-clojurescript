(ns data.maps)

(def a-map {:one 1
            :two 2})

(def b-map {:one 100
            :two 200})

(merge
 {:five 1 :two 22}
 {:four 4}
 {:one 1 :two 2 :three 3})

(def data
  {:headers {:content-type {:name "text"
                            :type :html}}
   :body {:text {:html "markup"}
          :images "/img/path-to-img.jpg"}
   :auth (first a-map)})

(def data2
  {:headers {:content-type {:name "plain"
                            :type :plain}}
   :body {:text {:html "markdown"}
          :images "/img/path-to-imark.jpg"}
   :auth (first b-map)})

(assoc-in data [:body :text] "Hello Planet" )
(assoc-in data2 [:headers :content-type :ip] "http://123131dfgdfdf")

(def matrix [[1 2 3 4 5]
             ["a" "b" "c" "d" "e"]
             ["hello" "thanks" "hola" "namaste" "hi"]])

(:type (:content-type (:headers data)))

(-> (:headers data)
    :content-type
    :type)

(map #(-> % :headers :content-type :type)
     (list data data2))

(keys data)
(vals data)
