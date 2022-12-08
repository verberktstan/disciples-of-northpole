(ns aoc22.day-seven
  (:require [aoc22.utils :as u]
            [clojure.string :as str]))

(defn- parse-line [s]
  (let [[a b c] (str/split s #" ")
        command (when (#{"$"} a) b)]
    {:command   command
     :cmd-arg   (when command c)
     :sub-dir   (when (#{"dir"} a) b)
     :file-size (when-let [fs (u/parse-int a)] fs)}))

(def conv (comp vec conj))
(def drop-lastv (comp vec (partial drop-last)))
(def takev (comp vec take))

(defn- all-parent-dirs [path]
  (reduce (fn [m n] (conj m (takev n path))) nil (range 1 (-> path count inc))))

(defn- follow-the-rabbit [{:keys [current-path] :as result} {:keys [command cmd-arg sub-dir file-size]}]
  (cond
    (and command (#{"/"} cmd-arg))  (assoc result :current-path ["/"])
    (and command (#{".."} cmd-arg)) (update result :current-path drop-lastv)
    (#{"cd"} command)               (update result :current-path conj cmd-arg)
    sub-dir                         (update result (conv current-path sub-dir) #(or % 0))
    file-size                       (reduce (fn [m path] (update m path + file-size)) result (all-parent-dirs current-path))
    :else                           result))

(comment
  (u/read-lines "resources/day-seven-input.txt")
  (u/read-lines (map parse-line) "resources/day-seven-input.txt")

  (->> (u/read-lines (map parse-line) "resources/day-seven-input.txt")
       (reduce follow-the-rabbit {["/"] 0})
       (filter (comp number? val))
       (remove (comp (partial <= 100000) val))
       vals
       (reduce +))

  )
