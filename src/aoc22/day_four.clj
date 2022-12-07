(ns aoc22.day-four
  (:require [aoc22.utils :as u]
            [clojure.edn :as edn]
            [clojure.set :as set]))

(defn- fully-contained? [sets]
  (or (apply set/subset? sets) (apply set/superset? sets) nil))

(defn- with-overlap? [sets]
  (seq (apply set/intersection sets)))

(defn- range-inc-end [start end]
  (range start (inc end)))

(def ^:private range-regex #"(\d+)\-(\d+)")

(defn- ->range [s]
  (->> s (u/regex-groups range-regex) (map edn/read-string) (apply range-inc-end)))

(def ^:private pair-regex #"(\d+\-\d+),(\d+\-\d+)")

(def ^:private pair->range-sets
  (comp (partial map set) (partial mapv ->range) (partial u/regex-groups pair-regex)))

(defn- count-ranges [keep-fn]
  (fn count-ranges* []
    (->> "resources/day-four-input.txt"
         (u/read-lines (comp (map pair->range-sets) (keep keep-fn)))
         count)))

(def -main (u/wrap-main {:part-one (count-ranges fully-contained?)
                         :part-two (count-ranges with-overlap?)}))
