(set-env!
  :source-paths #{"src"}
  :dependencies '[[me.raynes/conch "0.8.0"]
                  [compojure "1.3.4"]])

(task-options!
  pom {:project 'thinking-clojure
       :version "0.1.0"}
  jar {:manifest {"Foo" "bar"}})

(deftask build
  "Build my project."
  []
  (comp (pom) (jar) (install)))
