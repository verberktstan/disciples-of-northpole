(ns aoc22.day-one
  (:require
   [clojure.edn :as edn]
   [aoc22.utils :as u]))

(def ^:private input-file "resources/day-one-input.txt")

(defn- read-and-sum-calories [filename]
  (let [rasc (comp (map edn/read-string) (partition-by nil?) (keep (partial reduce +)))]
    (u/read-lines (partial into [] rasc) filename)))

(defn- sum-max-calories
  ([calory-sums] (reduce max calory-sums))
  ([calory-sums n] (->> calory-sums (sort >) (take n) (reduce +))))

(def -main
  (u/wrap-main
    {:part-one #(-> input-file read-and-sum-calories sum-max-calories)
     :part-two #(-> input-file read-and-sum-calories (sum-max-calories 3))}))
