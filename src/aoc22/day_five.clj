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

(defn- move-crates [crates {:keys [move from to]}]
  (let [crates-to-move (take move (get crates from))]
    (-> crates
        (update to seq)
        (update from (partial drop move))
        (update to (partial concat crates-to-move)))))

(defn- move [mover]
  (if (#{:crate-mover-9000} mover)
    (fn move-9000 [crates {:keys [move] :as instruction}]
      (reduce move-crate crates (repeat move instruction)))
    (fn move-9001 [crates instruction]
      (move-crates crates instruction))))

(defn- read-all [filename]
  (-> filename u/read-lines split-lines parse-setup parse-instructions))

(defn- crate-mover-9000 [{:keys [setup instructions]}]
  (reduce (move :crate-mover-9000) setup instructions))

(defn- crate-mover-9001 [{:keys [setup instructions]}]
  (reduce (move :crate-mover-9001) setup instructions))

(defn- top-crates [crates]
  (->> crates (sort-by key) (map (comp first val)) (apply str)))

(def -main
  (u/wrap-main {:part-one #(-> "resources/day-five-input.txt"
                               read-all
                               crate-mover-9000
                               top-crates)
                :part-two #(-> "resources/day-five-input.txt"
                               read-all
                               crate-mover-9001
                               top-crates)}))
