(ns aoc22.day-one
  (:require
   [clojure.edn :as edn]
   [aoc22.utils :as u]))

(defn- sum-calories-per-elf [lines]
  (->> lines
       (map edn/read-string)
       (partition-by nil?)
       (keep (partial reduce +))))

(def ^:private read-and-sum (comp sum-calories-per-elf u/read-lines))

(defn- sum-max-calories
  ([calory-sums]
   (reduce max calory-sums))
  ([calory-sums n]
   (->> calory-sums
        (sort >)
        (take n)
        (reduce +))))

(def -main
  (u/wrap-main
    {:part-one #(-> "resources/day-one-input.txt" read-and-sum sum-max-calories)
     :part-two #(-> "resources/day-one-input.txt" read-and-sum (sum-max-calories 3))}))
