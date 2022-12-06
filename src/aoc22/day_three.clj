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

(comment
  ;; Part one
  ;; Sum the priorities of all items that appear in both compartiments of each rucksack.
  (->> "resources/day-three-input.txt"
       u/read-lines
       (map split-rucksack)
       (map find-shared-item)
       (map priority)
       (reduce +))

  ;; Part two
  ;; In groups of 3 rucksacks, find the shared item between those 3, and sum their priority.
  (->> "resources/day-three-input.txt"
       u/read-lines
       (partition 3)
       (map find-shared-item)
       (map priority)
       (reduce +))
  )
