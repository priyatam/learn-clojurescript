(ns thinking.protocols
  (:require
   [clojure.edn :as edn]))

;; Protocols let you describe behavior and implement them separately

(defprotocol Selector
  (-select [s m]))

(defn select [m selectors-coll]
  (reduce conj {} (map #(-select % m) selectors-coll)))

;; A protocol can also extend other protocols and types, like so

(extend-protocol Selector
  clojure.lang.Keyword
  (-select [k m]
    (find m k))
  clojure.lang.APersistentMap
  (-select [sm m]
    (into {}
          (for [[k s] sm]
            [k (select (get m k) s)]))))

;; For example

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
                      :address {:house "42"
                                :street "Moon St."
                                :city "Atlanta"
                                :state "GA"
                                :zip 76509
                                :mobile "+188888888"}}})

(comment
  (select my-map
          [:name :email {:address [:city :state]
                         :spouse [:name {:address [:city :state]}]}]))
