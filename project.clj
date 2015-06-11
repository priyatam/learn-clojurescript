(defproject thinking-clojure "0.1.0"
  :description "Thinking Clojure, a learning series"
  :url "https://github.com/priyatam/thinking-clojure"
  :scm {:name "git"
        :url "https://github.com/priyatam/thinking-clojure"}
  :min-lein-version "2.5.0"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ["-Xms1g" "-server"]
  :global-vars {*warn-on-reflection* false *assert* false}
  :dependencies [[org.clojure/clojure "1.7.0-RC1"]])
