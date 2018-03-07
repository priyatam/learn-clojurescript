(ns abstractions.async
  (:refer-clojure
   :exclude [map reduce into partition partition-by take merge])
  #?(:clj
     (:require [clojure.core.async :as async :refer [alt! go go-loop <! <!! >! >!!]]))
  #?(:cljs
     (:require [cljs.core.async :as async :refer [<! >!]]))
  #?(:cljs
     (:require-macros [cljs.core.async.macros :refer [go go-loop alt!]])))

;; Simple pub sub system

(def publisher (async/chan))

(def subscribers (atom {}))

(def publication
  (async/pub publisher #(:topic %)))

(defn subscribe [channel topic]
  (async/sub publication topic (async/chan)))

(defn unsubscribe [channel topic]
  (async/unsub-all channel topic))

(defn notify [channel]
  (go-loop []
    (println channel ": " (<! channel))
    (recur)))

(defn publish [topic data]
  (go
    (>! publisher (assoc {:topic topic} data))))

;; Rob Pike's "hello world" of CSP (google search) -----

(defn fake-search [kind]
  (fn [c query]
    (go
     (<! (async/timeout (rand-int 100)))
     (>! c [kind query]))))

(def web1 (fake-search :web1))
(def web2 (fake-search :web2))
(def image1 (fake-search :image1))
(def image2 (fake-search :image2))
(def video1 (fake-search :video1))
(def video2 (fake-search :video2))

(defn fastest [query & replicas]
  (let [c (async/chan)]
    (doseq [replica replicas]
      (replica c query))
    c))

(defn google [query]
  (let [c (async/chan)
        t (async/timeout 80)]
    (go
      (>! c (<! (fastest query web1 web2))))
    (go
      (>! c (<! (fastest query image1 image2))))
    (go
      (>! c (<! (fastest query video1 video2))))
    (go-loop [i 0, ret []]
      (if (= i 3)
        ret
        (recur (inc i)
               (conj ret (alt! [c t] ([v] v))))))))

(go
  (<! (google "clojure")))
