(ns sets
  (require [clojure.set :refer :all]))

(defrecord Supplier [number name status city])
(defrecord Part [number name colour weight city])
(defrecord Shipment [supplier part quantity])

(def suppliers
  #{(Supplier. "S1" "Smith" 20 "London")
    (Supplier. "S2" "Jones" 10 "Paris")
    (Supplier. "S3" "Blake" 30 "Paris")})
(def parts
  #{(Part. "P1" "Nut" "Red" 12.0 "London")
    (Part. "P2" "Bolt" "Green" 17.0 "Paris")
    (Part. "P3" "Screw" "Blue" 17.0 "Oslo")})
(def shipments
  #{(Shipment. "S1" "P1" 300)
    (Shipment. "S2" "P2" 200)
    (Shipment. "S3" "P3" 400)})

(rename parts {:number :id, :city :location})
(select #(= (:name %) "Smith") suppliers)

(project suppliers [:city])

(join parts shipments {:number :part})

(project
 (join
  (select #(= (:city %) "Paris")
          suppliers) shipments {:number :supplier})
 [:name])
