(ns aoc22.day-one
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]))

(defn- read-lines [filename]
  (with-open [reader (io/reader filename)]
    (doall (line-seq reader))))

(defn- sum-calories-per-elf [lines]
  (->> lines
       (map edn/read-string)
       (partition-by nil?)
       (keep (partial reduce +))))

(def read-and-sum (comp sum-calories-per-elf read-lines))

(defn max-calories
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
  (-> "resources/day-one-input.txt" read-and-sum max-calories)

  ;; Part two
  ;; Return the sum of the 3 highest number of calories.
  (-> "resources/day-one-input.txt" read-and-sum (max-calories 3))
  )
