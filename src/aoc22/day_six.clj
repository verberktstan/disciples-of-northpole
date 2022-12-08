(ns aoc22.day-six
  (:require [aoc22.utils :as u]))

(def ^:private marker-length 4)

(defn- scan-for-marker-packet [line]
  (->> line (partition marker-length 1) (map (comp #{marker-length} count set))))

(def ^:private lock-signal
  (comp
    (map (partial take-while nil?))
    (map count)
    (map (partial + marker-length))))

(def -main
  (u/wrap-main
    {:part-one #(-> (comp (map scan-for-marker-packet) lock-signal)
                    (u/read-lines "resources/day-six-input")
                    first)}))
