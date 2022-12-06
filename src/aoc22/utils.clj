(ns aoc22.utils
  (:require
   [clojure.java.io :as io]))

(defn read-lines [filename]
  (with-open [reader (io/reader filename)]
    (doall (line-seq reader))))
