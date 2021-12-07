(ns advent.day07
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def test-input
  #_(slurp (io/resource "day06.txt"))
  (-> "16,1,2,0,4,2,7,1,2,14"
      (str/split #",")
      (#(map read-string %))))

(def input
  #_(slurp (io/resource "day06.txt"))
  (-> #_"16,1,2,0,4,2,7,1,2,14"
      (slurp (io/resource "day07.txt"))
      (str/split #",")
      (#(map read-string %))))

(defn mode [input]
  (-> (sort-by (comp - val) (frequencies input))
      first
      first))

(defn median [input]
  (let [sorted (sort input)]
    (nth
     sorted
     (/ (count input) 2))))

(defn mean [input]
  (Math/ceil
   (/
    (reduce + input)
    (count input))))

(defn calc-distance [input num]
  (reduce
   (fn [acc x] (+ acc (Math/abs (- num x))))
   0
   input))

(defn consecutive-sum [n]
  (Math/floor (* (/ n 2) (inc n))))

(defn calc-distance-2 [input num]
  (reduce
   (fn [acc x]
     (+ acc (consecutive-sum (Math/abs (- num x)))))
   0
   input))

(defn part1 [input]
  (min (calc-distance input (mode input))
       (calc-distance input (median input))))

(defn part2 [input]
  ;; Brute force
  #_(apply min (map (partial calc-distance-2 test-input) (range 0 10)))
  #_(apply min (map (partial calc-distance-2 input) (range 0 1000)))

  ;; Calculating the mean and flat out guessing ¯\_(ツ)_/¯
  (let [m (mean input)
        to-test (range (dec m) (inc m))]
    (apply min (map (partial calc-distance-2 input) to-test))))

(time
 (part2 input))
;; => 1.00148777E8

;; Brute force: "Elapsed time: 2469.565993 msecs"
;; Mean guessing: "Elapsed time: 5.37026 msecs"

