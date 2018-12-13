(ns data.records)

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

