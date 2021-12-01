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


(def users
  [{:first "John" :last "Parsons" :active? true}
   {:first "Amanda" :last "Clark" :active? true}
   {:first "Kevin" :last "Lawrence" :active? false}
   {:first "Caroline" :last "Mills" :active? true}
   {:first "Warren" :last "Smith" :active? false}])

(->> users
     (filter #(:active? %))
     (map #(str (:first %) " " (:last %))))

(def xf (comp
         (filter #(:active? %))
         (map #(str (:first %) " " (:last %)))))