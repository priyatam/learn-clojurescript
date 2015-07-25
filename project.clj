(defproject thinking-clojurescript "0.2.0"
  :description "Thinking Clojurescript, a learning series"
  :url "https://github.com/priyatam/thinking-clojurescript"
  :scm {:name "git" :url "https://github.com/priyatam/thinking-clojurescript"}
  :min-lein-version "2.5.0"
  :license {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ["-Xms1g" "-server"]
  :global-vars {*warn-on-reflection* false *assert* false}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3367"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.cognitect/transit-cljs "0.8.220"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.10"]
                                  [figwheel "0.3.3"]
                                  [priyatam/replify "0.2.2"]]}
             :figwheel {:nrepl-port 7888}}
  :plugins [[cider/cider-nrepl "0.9.1"]
            [lein-marginalia "0.8.0"]
            [lein-figwheel "0.3.3"]])
