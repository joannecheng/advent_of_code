(ns advent.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.core.reducers :as r]))

(def input
  (let [input-as-str (-> (slurp (io/resource "day01.txt"))
                         (str/split #"\n"))]
    (map read-string input-as-str)))

(def test-input
  [199
   200
   208
   210
   200
   207
   240
   269
   260
   263])

(partition 2 1 test-input)

(->> test-input
     (partition 2 1)
     (filter (fn [[a b]] (> b a)))
     count)

;; Part 1 without partition
(reduce
 (fn [[prev acc] curr]
   (if (> curr prev)
     [curr (inc acc)]
     [curr  acc]))
 [9999999 0]
 test-input)

;; Part 2 without partition
(reduce
 (fn [[prev-window acc] curr]
   (if (> curr prev)
     [curr (inc acc)]
     [curr  acc]))
 [9999999 0]
 test-input)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(->> (partition 3 1 test-input)
     vec)

(->> input
     (partition 3 1)
     (filter (fn [[a b]] (> b a)))
     count)


;; Part 2
(loop [in test-input
       increased-cnt 0]
  (let [numbers (take 4 in)
        increased? (fn [[a b c d]] (< (+ a b c) (+ b c d)))]
    (if (= (count in) 4)
      (cond-> increased-cnt (increased? numbers) inc)
      (recur (vec (rest in))
             (cond-> increased-cnt (increased? numbers) inc)))))
;; => 5

