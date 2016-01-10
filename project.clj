(defproject learn-clojurescript "0.3.0"
  :description "Learn Isomorphic Clojurescript, a practical learning series on the REPL"
  :url "https://github.com/priyatam/thinking-clojurescript"
  :scm {:name "git" :url "https://github.com/priyatam/learn-clojurescript"}
  :min-lein-version "2.5.0"
  :license {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ["-Xmx512g" "-server"]
  :global-vars {*warn-on-reflection* false *assert* false}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.2.374"]
                 [com.cognitect/transit-cljs "0.8.237"]
                 [com.cognitect/transit-clj "0.8.285"]
                 [com.stuartsierra/component "0.3.1"]
                 [garden "1.3.0"]
                 [hipo "0.5.2"]]
  :profiles {:dev {:dependencies [[priyatam/replify "0.2.2"]]}}
  :plugins [[lein-marginalia "0.8.0"]])
