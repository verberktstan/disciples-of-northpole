(ns aoc22.day-two
  (:require [aoc22.utils :refer [read-lines]]
            [clojure.string :as str]))

(def parse-play
  {"A" :rock "B" :paper "C" :scissors "X" :rock "Y" :paper "Z" :scissors})

(def beats? {:rock :scissors, :paper :rock, :scissors :paper})

(def shape-score {:rock 1 :paper 2 :scissors 3})

(defn result [{:keys [my-play opponents-play]}]
  (cond
    (= my-play opponents-play)          :draw
    (= (beats? my-play) opponents-play) :win
    :else                               :loss))

(def round-score {:loss 0 :draw 3 :win 6})

(defn parse-plays [s]
  (let [[opponent me] (str/split s #" ")]
    {:my-play        (parse-play me)
     :opponents-play (parse-play opponent)}))

(defn calculate-score [{:keys [my-play] :as plays}]
  (+ (shape-score my-play) (-> plays result round-score)))

(comment
  ;; Part one
  ;; Return the sum of the scores for all the rounds
  (->> "resources/day-two-input.txt"
       read-lines
       (map parse-plays)
       (map calculate-score)
       (reduce +))
  )
