(ns puzzle06
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.set :as set]))

(def input (slurp (io/resource "day06.txt")))


(defn count-group [group]
  (-> group
      (str/replace #"\n" "")
      distinct
      count))

(defn count-group2 [group]
  (->> (str/split group #"\n")
       (map set)
       (apply set/intersection)
       count))

(->>
 (map count-group2 (str/split input #"\n\n"))
 (apply +))

(->>
 (map count-group (str/split input #"\n\n"))
 (apply +))
