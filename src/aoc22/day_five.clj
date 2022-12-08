(ns aoc22.day-five
  (:require [aoc22.utils :as u]
            [clojure.string :as str]))

(def ^:private crate? (comp (set (range 65 91)) int))

(defn- parse-crate [characters]
  (first (filter crate? (set characters))))

(defn- parse-setup-line [s]
  (let [sections (partition-all 4 s)]
    (->> (map parse-crate sections)
         (zipmap (range 1 10)))))

(defn- parse-int [s]
  (try
    (Integer/parseInt s)
    (catch NumberFormatException _)))

(defn- parse-instruction-line [s]
  (->> (str/split s #" ")
       (keep parse-int)
       (zipmap [:move :from :to])))

(defn- split-lines [lines]
  {:setup (take-while (complement #{""}) lines)
   :instructions (drop-while #(not (str/starts-with? % "move")) lines)})

(comment
  (-> (u/read-lines "resources/day-five-input.txt")
      split-lines
      (update :setup (partial map parse-setup-line))
      (update :setup (partial apply merge-with str))
      (update :instructions (partial map parse-instruction-line)))
  )
