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
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/data.csv "0.1.2"]
                 [org.clojure/core.cache "0.6.4"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [org.clojure/core.logic "0.8.10"]
                 [org.clojure/data.xml "0.0.8"]
                 [clj-time "0.9.0"]
                 [cheshire "5.5.0"]
                 [clj-http "1.1.2"]
                 [hiccup "1.0.5"]
                 [enlive "1.1.5"]
                 [tentacles "0.3.0"]
                 [me.raynes/conch "0.8.0"]]

  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.10"]]}}
  :plugins [[lein-cljfmt "0.1.10"]
            [lein-marginalia "0.8.0"]
            [jonase/eastwood "0.2.1"]])
