(defproject learn-clojurescript "0.3.2-SNAPSHOT"
  :description "Learn Isomorphic Clojurescript on the REPL"
  :url "https://github.com/priyatam/learn-clojurescript"
  :scm {:name "git" :url "https://github.com/priyatam/learn-clojurescript"}
  :min-lein-version "2.8.0"
  :license {:name "MIT License" :url "https://opensource.org/licenses/MIT"}
  :jvm-opts ["-Xmx256m" "-server"]
  :global-vars {*warn-on-reflection* false *assert* false}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [org.clojure/core.async "0.4.490"]
                 [org.clojure/core.match "0.3.0-alpha5"]
                 [cljs-http "0.1.45"]
                 [garden "1.3.6"]]
  :cljsbuild {:builds [{:id "demos"
                        :source-paths ["demos"]
                        :compiler {:optimizations :whitespace
                                   :pretty-print false
                                   :output-to "out/demos.js"}}]}
  :profiles {:dev  {:dependencies [[facjure/replify "0.4.1"]
                                   [garden "1.3.4"]]}}
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-garden "0.2.8"]]
  :main replify)
