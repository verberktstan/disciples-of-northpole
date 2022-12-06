(ns aoc22.day-two
  (:require [aoc22.utils :refer [read-lines]]
            [clojure.string :as str]))

(def parse-play
  {"A" :rock "B" :paper "C" :scissors "X" :lose "Y" :draw "Z" :win})

(def beats? {:rock :scissors, :paper :rock, :scissors :paper})

(def shape-score {:rock 1 :paper 2 :scissors 3})

(defn determine-play [i-should opponents-play]
  (condp = i-should
    :draw opponents-play
    :win  (-> (filter (comp #{opponents-play} val) beats?)
              first
              key)
    :lose (->> (remove (comp #{opponents-play} val) beats?)
               (remove (comp #{opponents-play} key))
               first
               key)))

(defn result [{:keys [my-play opponents-play]}]
  (cond
    (= my-play opponents-play)          :draw
    (= (beats? my-play) opponents-play) :win
    :else                               :loss))

(def round-score {:loss 0 :draw 3 :win 6})

(defn parse-plays [s]
  (let [[opponent me]  (str/split s #" ")
        i-should       (parse-play me)
        opponents-play (parse-play opponent)]
    {:my-play        (determine-play i-should opponents-play)
     :opponents-play opponents-play}))

(defn calculate-score [{:keys [my-play] :as plays}]
  (+ (shape-score my-play) (-> plays result round-score)))

(comment
  ;; Part two
  ;; Return the sum of the scores for all the rounds
  (->> "resources/day-two-input.txt"
       read-lines
       (map parse-plays)
       (map calculate-score)
       (reduce +))
  )
