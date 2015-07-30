(ns thinking.async
  (:refer-clojure
   :exclude [map reduce into partition partition-by take merge])
  #?(:clj
     (:require [clojure.core.async :refer [go <! chan close! put!] :as async]))
  #?(:cljs
     (:require [cljs.core.async :refer [<! >! chan close! put!] :as async]))
  #?(:cljs
     (:require-macros [cljs.core.async.macros :refer [go]])))

(def publisher (chan))

(def subscribers (atom {}))

(def publication
  (pub publisher #(:topic %)))

(defn subscribe [channel topic]
  (sub publication topic (chan)))

(defn unsubscribe
  [channel topic]
  (unsub-all channel topic))

(comment 
  (defn notify [channel]
    (go-loop []
      (println channel ": " (<! channel))
      (recur))))

(defn publish [topic data]
  (go (>! publisher (assoc {:topic topic} data))))

