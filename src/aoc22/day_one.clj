(ns aoc22.day-one
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]))

(defn read-lines [filename]
  (with-open [reader (io/reader filename)]
    (doall (line-seq reader))))

(defn max-calories [lines]
  (->> lines
       (map edn/read-string)
       (partition-by nil?)
       (keep (partial reduce +))
       (reduce max)))

(comment
  (-> "resources/day-one-input.txt" read-lines max-calories)
  )
