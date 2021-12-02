(ns advent.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.core.reducers :as r]))

(def input
  (let [input-as-str (-> (slurp (io/resource "day02.txt"))
                         (str/split #"\n"))]
    (map #(-> (str/split % #" ")
              (update 1 read-string)) input-as-str)))

(def test-input
  [["forward" 5]
   ["down" 5]
   ["forward" 8]
   ["up" 3]
   ["down" 8]
   ["forward" 2]])

(defn movement [[direction steps] m]
  (case direction
    "forward" (update m :h #(+ steps %))
    "up" (update m :v #(-  % steps))
    "down" (update m :v #(+ steps %))))

(defn movement2 [[direction steps] m]
  (case direction
    "forward" (-> (update m :h #(+ steps %))
                  (update :v #(+ (* steps (:a m)) %)))
    "up" (update m :a #(-  % steps))
    "down" (update m :a #(+ steps %))))

(defn final-prod [{:keys [h v]}]
  (* h v))

(def init {:h 0 :v 0 :a 0})

;; Part 1
(-> (reduce #(movement %2 %1) init input)
    final-prod)

;; Part 2
(-> (reduce #(movement2 %2 %1) init input)
    final-prod)

