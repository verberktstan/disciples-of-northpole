(ns aoc22.day-six
  (:require [aoc22.utils :as u]))

(def ^:private packet 4) ; Length of start-of-packet marker
(def ^:private message 14) ; Length of start-of-message marker

(defn- scan-for-marker [n]
  (fn [line] (->> line (partition n 1) (map (comp #{n} count set)))))

(defn- lock-signal [n]
  (comp (map (partial take-while nil?)) (map count) (map (partial + n))))

(defn- scan [n]
  #(-> (comp (map (scan-for-marker n)) (lock-signal n))
       (u/read-lines "resources/day-six-input.txt")
       first))

(def -main (u/wrap-main {:part-one (scan packet) :part-two (scan message)}))
