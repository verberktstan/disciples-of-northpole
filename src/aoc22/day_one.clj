(ns aoc22.day-one
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]))

(defn read-lines [filename]
  (with-open [reader (io/reader filename)]
    (doall (line-seq reader))))

(def empty-string? #{""})
(def empty-line? #{'("")})

(defn max-calories [coll]
  (->> coll
       (partition-by empty-string?)
       (remove empty-line?)
       (map (partial map edn/read-string)) ; Read the string, returning ints in this case.
       (map (partial reduce +)) ; Sum the calories per elf
       (reduce max))) ; Find the maximum calories a single elf carries.

(comment
  (-> "resources/day-one-input.txt" read-lines max-calories)
  )
