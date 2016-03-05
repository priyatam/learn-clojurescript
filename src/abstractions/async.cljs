(ns async.core
  (:require 'core)
  (:require-macros
   [cljs.core.async.macros :refer [alt! go go-loop]])
  (:require
   [cljs.core.match :refer-macros [match]]
   [clojure.string :as string]
   [cljs.core.async :as async :refer [<! >! chan]]))

;; Rob Pike's famous "hello world" of CSP -----

(enable-console-print!)

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
    (go (>! c (<! (fastest query web1 web2))))
    (go (>! c (<! (fastest query image1 image2))))
    (go (>! c (<! (fastest query video1 video2))))
    (go (loop [i 0 ret []]
          (if (= i 3)
            ret
            (recur (inc i) (conj ret (alt! [c t] ([v] v)))))))))

(go
  (println (<! (google "clojure"))))
