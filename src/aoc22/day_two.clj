(ns aoc22.day-two
  (:require [aoc22.utils :refer [read-lines]]
            [clojure.string :as str]))

(def parse-opponents-play {"A" :rock
                           "B" :paper
                           "C" :scissors})

(def parse-my-play {"X" :rock
                    "Y" :paper
                    "Z" :scissors})

(def beats {:rock     :scissors
            :paper    :rock
            :scissors :paper})

(def score-for-my-play {:rock 1 :paper 2 :scissors 3})

(defn parse-plays [s]
  (let [[opponent me] (str/split s #" ")
        play          (parse-my-play me)
        op-play       (parse-opponents-play opponent)]
    {:my-play           play
     :opponents-play    op-play
     :score-for-my-play (score-for-my-play play)
     :i-won?            (= (beats play) op-play)}))

(comment
  (map parse-plays (read-lines "resources/day-two-input.txt"))
  )
