(ns github
  (:require [clojure.pprint :as pp]
            [tentacles.users :as users]
            [tentacles.data :as data]
            [tentacles.repos :as repos])
  (:use [environ.core]))

;; Print users
(users/user "priyatam")

;; Login
(defn auth []
  (str (:gh-user env ) ":" (:gh-pass env)))

(users/me {:auth (auth)})

;; Followers
(map :login (users/followers (:gh-user env )))

;; Count Followers
(count (map :login (users/followers (:gh-user env ))))

;; Count Followers per page
(count (map :login (users/followers (:gh-user env ) {:per-page 5})))

;; My followers
(map :login (users/my-followers {:auth (auth)}))

;; My following
(map :login (users/my-following {:auth (auth)}))

;; Repos
(repos/user-repos "rhickey")


;; Show only forked repos
(filter :fork (repos/user-repos "stuarthalloway"))

;; Show my forked repos
(filter :fork (repos/repos {:auth (auth)}))

;; Watchers of a user's repo
(repos/watchers "priyatam" "learn-clojure-tools")

