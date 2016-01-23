(ns abstractions.components
  (:require [com.stuartsierra.component :as component]))

(defn connect [host port]
  ;; TODO
  )

(defrecord APIServer [host port connection]
  ;; Implement the Lifecycle protocol
  component/Lifecycle

  (start [component]
    (println "Starting API Server")
    ;; In the 'start' method, initialize this component
    ;; and start it running. For example, connect to an
    ;; api server, create a token, or initialize shared
    ;; state.
    (let [conn (connect host port)]
      ;; Return an updated version of the component with
      ;; the run-time state assoc'd in.
      (assoc component :connection conn)))

  (stop [component]
    (println "Stopping API server")
    ;; In the 'stop' method, shut down the running
    ;; component and release any external resources it has
    ;; acquired.
    (.close connection)
    ;; Return the component, optionally modified. Remember that if you
    ;; dissoc one of a record's base fields, you get a plain map.
    (assoc component :connection nil)))
