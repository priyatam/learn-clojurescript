(ns thinking.jsons
  (:require
   [cognitect.transit :as t]))

#?(:cljs
   (defn roundtrip [x]
     (let [w (t/writer :json)
           r (t/reader :json)]
       (t/read r (t/write w x)))))

#?(:cljs
   (defn test-roundtrip []
     (let [list1 [:red :green :blue]
           list2 [:apple :pear :grape]
           data  {(t/integer 1) list1
                  (t/integer 2) list2}
           data' (roundtrip data)]
       (assert (= data data')))))

