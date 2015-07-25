(ns thinking.objects
  (:require
   [clojure.edn :as edn]))

;; Cljc doesn't support traditional OOP programming; however at its
;; heart, OO foundations exist: Interfaces, Polymorphism, and light-weight 'records'
;; for holding data together. Once you grasp these basics, you'll notice that
;; they play well with the standard built-in functions and sequences, and thus
;; provide the best of both worlds.

;; Records hold a map-like data together, giving them a structure. It implements
;; PersistentMap, and can be passed to any function that accepts maps.

(defrecord Person [name email address github-id events])

;; This is how you 'instantiate' it
(->Person "foo bar" "foobar@foobar.com" "100 Mission St, San Fracisco, CA, 94803" "foobar" "some event data")

;; What if we want to change the last attr to another record, for nested 'data'?
;; Create another record
(defrecord Events [^Integer eid attr val etime])

;; Change Person records structure
(defrecord Person [name email address github-id Events])

;; Try to instantiate it again
(->Person "foo bar" "foobar@foobar.com" "100 Mission St, San Fracisco, CA, 94803" "foobar"
          (->Events 100 :login "twitter" "1990-07-16T19:20:30.45+01:00"))

;; As mentioned, you can pass records to any function that accepts a map
;; Let's try to filer a Person

(def people
  (list
   (->Person "foo bar" "foobar@foobar.com" "100 Mission St, San Fracisco, CA, 94803" "foobar" "some event data for foo")
   (->Person "stu bar" "stu@foobar.com" "101 Mission St, San Fracisco, CA, 94803" "sbar" "some event data for stu")
   (->Person "rich bar" "rich@foobar.com" "102 Mission St, San Fracisco, CA, 94803" "rbar" "some event data for rich")))

(filter #(= "stu bar" (.name %)) people)

;; Go ahead, try other functions


;; Protocols let you describe behavior to existing types or new types.
;; They also extend other protocols and types. In order to understand what
;; this means, let's do an exercise.

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
(extend-protocol Selector
  clojure.lang.Keyword
  (-select [k m]
    (find m k))
  clojure.lang.APersistentMap
  (-select [sm m]
    (into {}
          (for [[k s] sm]
            [k (select (get m k) s)]))))

;; Let's try now
(select my-map
          [:name :email {:address [:city :state]
                         :spouse [:name {:address [:city :state]}]}])

