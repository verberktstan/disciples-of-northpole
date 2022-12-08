(ns aoc22.utils
  (:require
   [clojure.java.io :as io]))

(def first-key (comp key first))

(defn read-lines
  ([filename]
   (read-lines (map identity) filename))
  ([transduce-fn filename]
   (with-open [reader (io/reader filename)]
     (->> reader line-seq (into [] transduce-fn)))))

(defn key= [x]
  (comp #{x} key))

(defn val= [x]
  (comp #{x} val))

(defn wrap-main [props]
  (fn wrap-main* [part & _]
    (let [f (get props (keyword part))]
      (if f
        (println (f))
        (println "Can't run that part!")))))

(def regex-groups (comp rest re-matches))

(defn parse-int [s]
  (try (Integer/parseInt s) (catch NumberFormatException _)))
