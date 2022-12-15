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

(defn- all-parent-dirs
  "Returns a list of the paths of all parent directories."
  [path]
  (reduce (fn [m n] (->> path (take n) vec (conj m))) nil (range 1 (-> path count inc))))

(defn- add-size-to-path [file-size]
  (fn [m path]
    (update m path + file-size)))

(defn- follow-the-rabbit
  "Returns a (flat) map with summed filesizes for every path."
  [{:keys [current-path] :as result}
   {:keys [command cmd-arg sub-dir file-size]}]
  (cond
    (and command (#{"/"} cmd-arg))  (assoc result :current-path ["/"])
    (and command (#{".."} cmd-arg)) (update result :current-path (comp vec (partial drop-last)))
    (#{"cd"} command)               (update result :current-path conj cmd-arg)
    sub-dir                         (update result (concat current-path [sub-dir]) #(or % 0))
    file-size                       (reduce (add-size-to-path file-size) result (all-parent-dirs current-path))
    :else                           result))

(defn- paths-and-sizes
  "Returns a map containing every map's path associated with their size."
  []
  (->> (u/read-lines (map parse-line) "resources/day-seven-input.txt")
       (reduce follow-the-rabbit {["/"] 0})
       (filter (comp number? val))
       (into {})))

(defn- sum-sub100k
  "Returns the sum of all sub 100k size directories."
  [dirs-with-size]
  (->> dirs-with-size
       (remove (comp (partial <= 100000) val))
       vals
       (reduce +)))

(defn- required-space-to-be-deleted [used-space]
  (let [total-size     70000000
        space-required 30000000
        free-space     (- total-size used-space)]
    (when (> space-required free-space)
      (- space-required free-space))))

(defn- smallest-viable-dir [paths-and-sizes]
  (let [threshold (-> paths-and-sizes (get '("/")) required-space-to-be-deleted)]
    (reduce-kv
      (fn [m path size]
        (if (>= size threshold)
          (cond-> m
            (< size (:size m) ) (assoc :size size :path path))
          m))
      {:size ##Inf}
      paths-and-sizes)))

(def -main (u/wrap-main {:part-one #(sum-sub100k (paths-and-sizes))
                         :part-two #(:size (smallest-viable-dir (paths-and-sizes)))}))
