(ns ^{:doc "Core operators and Cljs/Js interop"}
  core)


(println "hello Clojurescript")

;; Basic Types

(def a-number 42)
(def anotehr-number 3.1445)
(def a-string "Hello World")
(def a-regex #"\d{3}-\d{3}-\d{4}")
(def truthy true)
(def falsy false)
(def scary nil)
(def a-keyword :think)

;; Arithmetic

(+ 9 (* 10 5) (/ 1 2))

;; Vars

(def doors js/window)

(def singularity (js/Date. "2044-01-01"))

;; Objects, Constructors, Properties

(def my-object
  (js-obj "a" 1 "b" true "c" nil))

(def js-object
  (js-obj :a 1
          :b [1 2 3]
          :c #{"d" true :e nil}))

(def js-object
  #js {:a 1 :b 2})

(def date (js/Date.))

(def my-array (js->clj (.-globalArray js/window)))

(def first-item (get my-array 0))

(.-length (array 1 2 3))

;; Methods

(.alert js/window "Hello, clojurescript!")

(.parse js/JSON "[1, 2, 3]")

;; Arrays

(array 1 2 3 4)

(js-obj "a" 1 "b" 2)

(js-keys (js-obj "a" 1 "b" 2))

(let [a (array "foo" "bar" "car")]
  (aget a 0)
  (aset a 2 "bus"))

(let [o (js-obj "somekey" (array "foo" "bar" "car"))]
  (aget o "somekey" 0)
  (aset o "somekey" 2 "bus"))

;; Convertions

(clj->js {:a 1 :b 2})

(let [o (clj->js {:a 1 :b 2})]
  (js->clj o)
  (js->clj o :keywordize-keys true))

;; Exporting Cljs functions to Js

(defn ^:export creat-chart []
  (let [ch (js/Chart.)]
    (. ch (PolarArea []))))

;; Evaluate Raw Js

(js* "alert('this is raw JS')")
