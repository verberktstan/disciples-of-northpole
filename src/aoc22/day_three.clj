(ns aoc22.day-three
  (:require [aoc22.utils :as u]
            [clojure.set :as set]))

(defn split-ruchsack
  "Returns a vector with to sequences of items in the ruchsack, one for each compartiment."
  [ruchsack]
  (let [compartiment-size (-> ruchsack count (/ 2) int)]
    (split-at compartiment-size ruchsack)))

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
  (->> "resources/day-three-input.txt"
       u/read-lines
       (map split-ruchsack)
       (map find-shared-item)
       (map priority)
       (reduce +))
  )
