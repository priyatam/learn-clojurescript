(ns abstractions.protocols)

;; Protocols let you describe behavior to existing types or new types.
;; They also extend other protocols and types. In order to understand what
;; this means, let's do an exercise.


(defprotocol IFoo
  (foo [this])
  (bar [this]))

(deftype Foo1 []
  IFoo
  (foo [this] (println "Foo1"))
  (bar [this] (println "Bar1")))

(deftype Foo2 []
  IFoo
  (foo [this] (println "Foo2"))
  (bar [this] (println "Bar2")))

;; (foo (Foo1.))

;; (foo (Foo2.))

(deftype Foo [])
(deftype IncompatibleBar [])

(defprotocol ExpressYourself
  (foos-and-bars-in-harmony [this]))

(extend-type Foo
  ExpressYourself
  (foos-and-bars-in-harmony [this] "I am a foo!"))

(extend-type IncompatibleBar
  ExpressYourself
  (foos-and-bars-in-harmony [this] "I am an incompatible bar!"))

(defprotocol Sushi
  (wash-my-hands [this])
  (eat-sushi [this]))

(defn deal-with-sushi [eater]
  (wash-my-hands eater)
  (eat-sushi eater))

(deftype NeverEatenSushiBeforeAmerican []
  Sushi
  (wash-my-hands [this]
    (println "I would if I was actually going to eat that raw fish."))
  (eat-sushi [this]
    (println "Well, I guess I'll try the salmon")))

(deftype American []
  Sushi
  (wash-my-hands [this]
    (println "Er, where's the sink?"))
  (eat-sushi [this]
    (println "Hey, I'm hip, I'll use chopsticks!")))

(deftype Japanese []
  Sushi
  (wash-my-hands [this]
    (println "Oshibori for the win."))
  (eat-sushi [this]
    (println "Actually dude we just use our fingers.")))

;; (deal-with-sushi (NeverEatenSushiBeforeAmerican.))

;; (deal-with-sushi (American.))

;; (deal-with-sushi (Japanese.))

(deftype Dog []
  Sushi
  (wash-my-hands [this]
    (println "Not even sure why I implemented this function"))
  (eat-sushi [this]
    (println "Get food in mouth now")))

;; (deal-with-sushi (Dog.))


(defrecord Foo3 [field1 field2]
  IFoo
  (foo [this] (println "Foo3, field1: " field1))
  (bar [this] (println "Bar3, field2: " field2)))

(def foo3 (Foo3. 1 2))

(def quick-foo
  (reify
    IFoo
    (foo [this] (println "Quick, gimme a foo!"))))

;; (foo quick-foo)

;; Expression Problem

(defprotocol IFoo
  (foo [this]))

(deftype FooItAgain []
  IFoo
  (foo [this] (println "same old foo.")))

(defprotocol INewFoo
  (a-new-fn [this]))

(extend-type FooItAgain
  INewFoo
  (a-new-fn [this] (println "realized I needed this!")))

;; (foo (FooItAgain.))

;; (a-new-fn (FooItAgain.))

(deftype YetAnotherFoo []
  IFoo
  (foo [this] (println "yep.")))

(deftype FooMeToTheMoon []
  IFoo
  (foo [this] (println "...")))

(extend-protocol INewFoo
  YetAnotherFoo
  (a-new-fn [this] (println "need it here too."))

  FooMeToTheMoon
  (a-new-fn [this] (println "...and here.")))

;; (a-new-fn (YetAnotherFoo.))
;; (a-new-fn (FooMeToTheMoon.))

;; specify

(defprotocol IForwardsLister
  (list-forwards [this]))

(defprotocol IBackwardsLister
  (list-backwards [this]))

(defrecord DefaultForwardsLister [coll]
  IForwardsLister
  (list-forwards [this] (println "forwards: " coll)))

(defn make-string-lister [s]
  #?(:cljs
     (specify (DefaultForwardsLister. s)
       IBackwardsLister
       (list-backwards [this]
         (println "backwards: " (clojure.string/join "" (-> this first val reverse)))))))

(defn make-list-lister [l]
  #?(:cljs
     (specify (DefaultForwardsLister. l)
       IBackwardsLister
       (list-backwards [this]
         (println "backwards: " (clojure.string/join "" (-> this first val reverse)))))))

(def sl (make-string-lister "My string."))
(def ll (make-list-lister '(1 2 3 4)))

;; (list-forwards sl)
;; (list-forwards ll)
;; (list-backwards sl)
;; (list-backwards ll)


;; Often, I deal with large datasets, with nested maps and keywords
(def my-map {:name "John Doe"
             :email "john@doe.com"
             :address {:house "42"
                       :street "Moon St."
                       :city "San Francisco"
                       :state "CA"
                       :zip 76509
                       :mobile "+188888888"}
             :ssn "123456"
             :spouse {:name "Jane Doe"
                      :ssn "654321"
                      :relation :wife
                      :address {:house "40"
                                :street "Sun St."
                                :city "Atlanta"
                                :state "GA"
                                :zip 76509
                                :mobile "+188888888"}}})

;; Rather than struggling with iteration and maps on the nested structures, would'nt
;; it be nice to have something like this?
(comment
  (select my-map
          [:name :email {:address [:city :state]
                         :spouse [:name {:address [:city :state]}]}]))

;; We can implement this new feature with Protocols.
;; Let's describe a Protocol name and a function to select data from nested maps.
(defprotocol Selector
  (-select [k m]))

;; Ideally, we perform this operation over a collection. The pattern
;; here is to map this operation over a collection and sum its results
(defn select [m selectors-coll]
  (reduce conj {} (map #(-select % m) selectors-coll)))

;; A sample implementation can be based on the collection type
#?(:clj
   (extend-protocol Selector
     clojure.lang.Keyword
     (-select [k m]
       (find m k))
     clojure.lang.APersistentMap
     (-select [sm m]
       (into {}
             (for [[k s] sm]
               [k (select (get m k) s)])))))

;; Let's try now
(select my-map
          [:name :email {:address [:city :state]
                         :spouse [:name {:address [:city :state]}]}])
