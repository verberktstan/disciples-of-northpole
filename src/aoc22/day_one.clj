(ns aoc22.day-one
  (:require
   [clojure.edn :as edn]
   [aoc22.utils :refer [read-lines]]))

(defn- sum-calories-per-elf [lines]
  (->> lines
       (map edn/read-string)
       (partition-by nil?)
       (keep (partial reduce +))))

(def read-and-sum (comp sum-calories-per-elf read-lines))

(defn sum-max-calories
  ([calory-sums]
   (reduce max calory-sums))
  ([calory-sums n]
   (->> calory-sums
        (sort >)
        (take n)
        (reduce +))))

(comment
  ;; Part one
  ;; Return the highest number of calories carried by a single elf.
  (-> "resources/day-one-input.txt" read-and-sum sum-max-calories)

  ;; Part two
  ;; Return the sum of the 3 highest number of calories.
  (-> "resources/day-one-input.txt" read-and-sum (sum-max-calories 3))
  )
