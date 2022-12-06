(ns aoc22.day-four
  (:require [aoc22.utils :as u]
            [clojure.edn :as edn]))

(defn range-inc-end [start end]
  (range start (inc end)))

(defn ->range [s]
  (->> s
       (re-find #"(\d)\-(\d)")
       rest
       (map edn/read-string)
       (apply range-inc-end)))

(defn split-pair
  [s]
  (->> s
       (re-matches #"(\d\-\d),(\d\-\d)")
       rest))

(comment
 (->> "resources/day-four-input.txt"
      u/read-lines
      (map split-pair)
      (map (partial mapv ->range)))
  )
