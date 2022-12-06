(ns aoc22.day-two
  (:require [aoc22.utils :refer [read-lines]]
            [clojure.string :as str]))

(def opponents-play {"A" :rock
                     "B" :paper
                     "C" :scissors})

(def my-play {"X" :rock
              "Y" :paper
              "Z" :scissors})

(defn parse-plays [s]
  (let [[opponent me] (str/split s #" ")]
    {:my-play        (my-play me)
     :opponents-play (opponents-play opponent)}))

(comment
  (read-lines "resources/day-two-input.txt")
  (map parse-plays (read-lines "resources/day-two-input.txt"))
  )
