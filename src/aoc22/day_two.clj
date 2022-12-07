(ns aoc22.day-two
  (:require [aoc22.utils :as u]
            [clojure.string :as str]))

(def ^:private parse-play
  {"A" :rock "B" :paper "C" :scissors "X" :lose "Y" :draw "Z" :win})

(def ^:private beats? {:rock :scissors, :paper :rock, :scissors :paper})

(def ^:private shape-score {:rock 1 :paper 2 :scissors 3})

(defn- play-that-beats [opponents-play]
  (->> beats?
       (filter (u/val= opponents-play))
       u/first-key))

(defn- play-that-loses-from [opponents-play]
  (->> beats?
       (remove (u/val= opponents-play))
       (remove (u/key= opponents-play))
       u/first-key))

(defn- determine-play [i-should opponents-play]
  (condp = i-should
    :draw opponents-play
    :win  (play-that-beats opponents-play)
    :lose (play-that-loses-from opponents-play)))

(defn- result [{:keys [my-play opponents-play]}]
  (cond
    (= my-play opponents-play)          :draw
    (= (beats? my-play) opponents-play) :win
    :else                               :loss))

(def ^:private round-score {:loss 0 :draw 3 :win 6})

(defn- parse-plays [s]
  (let [[opponent me]  (str/split s #" ")
        i-should       (parse-play me)
        opponents-play (parse-play opponent)]
    {:my-play        (determine-play i-should opponents-play)
     :opponents-play opponents-play}))

(defn- calculate-score [{:keys [my-play] :as plays}]
  (+ (shape-score my-play) (-> plays result round-score)))

(defn- total-score-for-strategy-guide []
  (->> "resources/day-two-input.txt"
       (u/read-lines (comp (map parse-plays) (map calculate-score)))
       (reduce +)))

(def -main (u/wrap-main {:part-two total-score-for-strategy-guide}))
