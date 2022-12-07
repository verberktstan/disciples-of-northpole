(ns aoc22.day-three
  (:require [aoc22.utils :as u]
            [clojure.set :as set]))

(defn split-rucksack
  "Returns a vector with to sequences of items in the rucksack, one for each compartiment."
  [rucksack]
  (let [compartiment-size (-> rucksack count (/ 2) int)]
    (split-at compartiment-size rucksack)))

(defn find-shared-item
  "Returns the item that is present in both compartiments."
  [compartiments]
  (->> (map set compartiments)
       (apply set/intersection)
       first))

(defn priority
  "Returns the priority of the supplied character (it represents an item type).
  Relies on unicode integer conversion."
  [char]
  (let [i (int char)]
    (if (< i 97) (- i 38) (- i 96))))

(defn part-one []
  (->> "resources/day-three-input.txt"
       u/read-lines
       (map split-rucksack)
       (map find-shared-item)
       (map priority)
       (reduce +)))

(defn part-two []
  (->> "resources/day-three-input.txt"
       u/read-lines
       (partition 3)
       (map find-shared-item)
       (map priority)
       (reduce +)))

(def -main (u/wrap-main {:part-one part-one :part-two part-two}))
