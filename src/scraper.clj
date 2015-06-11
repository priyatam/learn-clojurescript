(ns scraper
  (:require [net.cgrand.enlive-html :as html]))

(def ^:dynamic *base-url* "http://www.public-domain-poetry.com/a-h-laidlaw/printpoem/weep-not-for-him-22324")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn title []
  (map html/text (html/select (fetch-url *base-url*) [:font.t0])))

(defn author []
  (map html/text (html/select (fetch-url *base-url*) [:font.t1 :a])))

(defn poem []
  (map html/text (html/select (fetch-url *base-url*) [:font.t3a])))


(title)
(author)
(poem)
