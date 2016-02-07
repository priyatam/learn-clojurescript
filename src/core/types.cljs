(ns ^{:doc "Core types, identity, and equality "}
  core)

;; Basic Types ;;

(def a-number 42)
(def anotehr-number 3.1445)
(def a-string "Hello World")
(def a-regex #"\d{3}-\d{3}-\d{4}")
(def truthy true)
(def falsy false)
(def scary nil)
(def a-keyword :think)

;; Equality ;;

(def a ["red" "blue" "green"])
(def b ["red" "blue" "green"])

(= a b)

(= 1 "1") ;; ClojureScript equality is based on value
(= {} {})

;; Arithmetic

(+ 9 (* 10 5) (/ 1 2))

;; Regex

