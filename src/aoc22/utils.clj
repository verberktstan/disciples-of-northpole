(ns aoc22.utils
  (:require
   [clojure.java.io :as io]))

(def first-key (comp key first))

(defn read-lines [filename]
  (with-open [reader (io/reader filename)]
    (doall (line-seq reader))))

(defn key= [x]
  (comp #{x} key))

(defn val= [x]
  (comp #{x} val))
