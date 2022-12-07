(ns aoc22.day-four
  (:require [aoc22.utils :as u]
            [clojure.edn :as edn]
            [clojure.set :as set]))

(defn- fully-contained? [[r1 r2]]
  (let [sets (map set [r1 r2])]
    (or (apply set/subset? sets)
        (apply set/superset? sets)
        nil)))

(defn- range-inc-end [start end]
  (range start (inc end)))

(defn- ->range [s]
  (->> s
       (re-find #"(\d+)\-(\d+)")
       rest
       (map edn/read-string)
       (apply range-inc-end)))

(defn- split-pair [s]
  (->> s
       (re-matches #"(\d+\-\d+),(\d+\-\d+)")
       rest))

(defn- part-one []
  (->> "resources/day-four-input.txt"
       u/read-lines
       (map split-pair)
       (map (partial mapv ->range))
       (keep fully-contained?)
       count))

(def -main (u/wrap-main {:part-one part-one}))
