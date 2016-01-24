(ns data.atoms)


;; basics -----

(def an-atom (atom {:k1 "val"}))

(comment @an-atom
         (reset! an-atom {:k2 "val2"})
         @an-atom
         (swap! an-atom assoc :k3 "val3")
         @an-atom)

(def validatable-state
  (atom 0 :validator
        (fn [new-val]
          (== 0 new-val))))

(comment
  (swap! validatable-state inc))

(def app-state (atom nil))

;; undo/redo implementation with atoms ---

(def history
  (atom [@app-state]))

(add-watch
 app-state :history
 (fn [_ _ old new]
   #_(println old new history)
   (when-not (= (last @history) new)
     (swap! history conj new))))

(defn undo! []
  (when (> (count @history) 1)
    (swap! history pop)
    (reset! app-state (last @history))))
