(ns thinking.automata)

(defn FSM []
  {:states {}
   :transitions {}
   ;; if we add neighbors to fsm we can use the graph package to analyze it
   :neighbors (fn  [fsm state] (map (fn [[event transition]] (transition :target))
                                    ((fsm :transitions) (state :name))))})

(defn State [name entry-action exit-action]
  {:name name :entry-action entry-action :exit-action exit-action})

(defn add-state [fsm state]
  "Returns fsm"
  (assoc fsm :states (assoc (fsm :states) (state :name) state)))

(defn Transition [target-state action]
  {:target target-state :action action})

(defn- state-transition-table [fsm state]
  "Returns transition table for state"
  (or ((fsm :transitions) (state :name)) {}))

(defn add-transition [fsm state event-name transition]
  "Add transition to state under event-name, return fsm"
  (let [new-state-transition-table (assoc (state-transition-table fsm state) event-name transition)
        new-transition-table (assoc (fsm :transitions) (state :name) new-state-transition-table)]
    (assoc fsm :transitions new-transition-table)))

(defn Context [initial-state init-action data]
  (if init-action (init-action {:current-state initial-state} data)
      {:current-state initial-state}))

(defn- dispatch [fsm state context message]
  "Returns context"
  (let [transition (or ((state-transition-table fsm state) (message :event)) {})]
    (if (empty? transition)
      context
      (let [context-exit ((or (state :exit-action) (fn [c m] c)) context message)
            context-trans ((or (transition :action)
                               (fn [c m] c))
                           (assoc context-exit :current-state (transition :target)) message)]
        ((or ((transition :target) :entry-action) (fn [c m] c))
         context-trans message)))))

(defn send-message [fsm context message]
  (dispatch fsm (context :current-state) context message))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def state0 (State "state0" nil nil))
(def state1 (State "state1" nil nil))

(defn print-action [c e]
  (print " trans to state " ((c :current-state) :name))
  c)

(def trans1 (Transition state1 print-action))
(def trans0 (Transition state0 print-action))

(def fsm (FSM))
(def fsm (add-state fsm state0))
(def fsm (add-state fsm state1))
(def fsm (add-transition fsm state0 "go" trans1))
(def fsm (add-transition fsm state1 "go" trans0))

(def message {:event "go"})

(def context (Context state0 nil nil))

(def state2 (State "state2" nil nil))
(fsm :states)

(def fsm (add-state fsm state2))
(def trans2 (Transition state2 print-action))
(def fsm (add-transition fsm state0 "go2" trans2))

(def context (send-message fsm context message))
(def context (send-message fsm context message))
(def context (send-message fsm context {:event "go2"}))
