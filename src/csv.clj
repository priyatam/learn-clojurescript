(ns csv
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [clojure.pprint :as pp]
            [clojure.walk :as walk]
            [clojure.data.csv :as csv]
            [cheshire.core :as json])
  (:import (java.sql Timestamp)
           (java.util Date UUID)))

(defn parse-csv [str-in]
  (csv/read-csv str-in :separator \tab))

(defn read-csv [data-in]
  (with-open [f (io/reader (io/resource data-in))]
    (doall (parse-csv f))))

(defn parse-json [data-in]
  (->> data-in
       (map #(nth % 4)) ;; 5th item is json data
       (apply str)
       (json/parse-string)
       walk/keywordize-keys))

(defn to-data [res-in]
  (with-open [res (io/reader (io/resource res-in))]
    (->> res
         (parse-csv)
         (parse-json))))
