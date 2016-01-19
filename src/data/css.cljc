(ns data.css
  (:require
   [garden.core :refer [css]]))

(css [:body {:font-size "16px"}])

(defn clearfix [clazz]
  [clazz {:*zoom 1}
   [:&:before :&:after {:display "table"
                        :content " "
                        :line-height 0}]
   [:&:after {:clear "both"}]])
