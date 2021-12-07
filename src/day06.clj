(ns advent.day06
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (slurp (io/resource "day06.txt")))

(def prepared-input
  (->> input
       (#(str/split % #","))
       (mapv read-string)))

(defn one-day [input-list]
  (reduce-kv
   (fn [acc i fish]
     (cond
       (= fish 0) (-> acc (assoc i 6) (conj 8))
       :else (update acc i dec)))
   input-list
   input-list))

(defn part1 [input days]
  (-> (reduce
       (fn [acc _]
         (one-day acc))
       input
       (range 0 days))
      count))
(part1 prepared-input 80)

(defn new-day [input _]
  (cond-> input
    (get input 0) (assoc 9 (get input 0))
    (get input 0) (update 7 #(+ (get % 7 0) (get input 0)))
    :always (->
             (dissoc 0)
             (update-keys dec))))

;; Part 2
(->> (reduce
      new-day
      (frequencies prepared-input)
      (range 0 256))
     (map last)
     (reduce +))

