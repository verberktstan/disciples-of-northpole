(ns aoc22.day-four
  (:require [aoc22.utils :as u]
            [clojure.edn :as edn]
            [clojure.set :as set]))

(defn- fully-contained? [sets]
  (or (apply set/subset? sets)
      (apply set/superset? sets)
      nil))

(defn- with-overlap? [sets]
  (seq (apply set/intersection sets)))

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

(defn- count-ranges [keep-fn]
  (fn count-ranges* []
    (->> "resources/day-four-input.txt"
         u/read-lines
         (map split-pair)
         (map (partial mapv ->range))
         (map (partial map set))
         (keep keep-fn)
         count)))

(def -main (u/wrap-main {:part-one (count-ranges fully-contained?)
                         :part-two (count-ranges with-overlap?)}))
