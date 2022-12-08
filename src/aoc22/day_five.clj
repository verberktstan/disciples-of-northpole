(ns aoc22.day-five
  (:require [aoc22.utils :as u]
            [clojure.string :as str]))

;; Parsing crates
(def ^:private crate? (comp (set (range 65 91)) int))

(defn- parse-crate [characters]
  (first (filter crate? (set characters))))

(defn- parse-setup-line [s]
  (let [sections (partition-all 4 s)]
    (->> (map parse-crate sections)
         (zipmap (range 1 10)))))

;; Parsing instructions
(defn- parse-int [s]
  (try
    (Integer/parseInt s)
    (catch NumberFormatException _)))

(defn- parse-instruction-line [s]
  (->> (str/split s #" ")
       (keep parse-int)
       (zipmap [:move :from :to])))

;; Split lines into crate setup and instruction lines
(defn- split-lines [lines]
  {:setup (take-while (complement #{""}) lines)
   :instructions (drop-while #(not (str/starts-with? % "move")) lines)})

(defn- parse-setup [m]
  (-> m
      (update :setup (partial map parse-setup-line))
      (update :setup (partial apply merge-with str))))

(defn- parse-instructions [m]
  (update m :instructions (partial map parse-instruction-line)))
;; Moving of the crates
(defn- move-crate [crates {:keys [from to]}]
  (let [crate (first (get crates from))]
    (-> crates
        (update to seq)
        (update from rest)
        (update to conj crate))))

(defn- move [crates {:keys [move] :as instruction}]
  (reduce move-crate crates (repeat move instruction)))

(defn- move-all [{:keys [setup instructions]}]
  (reduce move setup instructions))

(comment
  (-> (u/read-lines "resources/day-five-input.txt")
      split-lines
      parse-setup
      parse-instructions
      move-all)

  )
