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

(def ^:private range-regex #"(\d+)\-(\d+)")

(defn- ->range [s]
  (->> (u/regex-groups range-regex s)
       (map edn/read-string)
       (apply range-inc-end)))

(def ^:private pair-regex #"(\d+\-\d+),(\d+\-\d+)")

(defn- count-ranges [keep-fn]
  (fn count-ranges* []
    (->> "resources/day-four-input.txt"
         u/read-lines
         (map (partial u/regex-groups pair-regex))
         (map (partial mapv ->range))
         (map (partial map set))
         (keep keep-fn)
         count)))

(def -main (u/wrap-main {:part-one (count-ranges fully-contained?)
                         :part-two (count-ranges with-overlap?)}))
