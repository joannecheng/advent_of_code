(ns advent.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.core.reducers :as r]))

(def input
  (let [input-as-str (-> (slurp (io/resource "day03.txt"))
                         (str/split #"\n"))]
    input-as-str))


(def test-input
  ["00100"
   "11110"
   "10110"
   "10111"
   "10101"
   "01111"
   "00111"
   "11100"
   "10000"
   "11001"
   "00010"
   "01010"])

(defn find-counts [digit row acc]
  (let [row (reverse row)]
    (reduce-kv #(do
                  (cond-> %1
                    (= %3 digit) (update %2 inc)))
               acc
               (vec row))))


(defn find-common-bits [input]
  (let [init (vec (repeat (count (first input)) 0))
        ones (reduce #(find-counts \1 %2 %1) init input)
        zeros (reduce #(find-counts \0 %2 %1) init input)]
    [zeros ones]))

;; Part 1
(find-common-bits test-input)

(reduce-kv
 (fn [acc pos [zero one]] (if (> zero one)
                            acc
                            (+ acc (Math/pow 2 pos))))
 0
 (apply mapv vector (find-common-bits test-input)))

(reduce-kv
 (fn [acc pos [zero one]] (if (> zero one)
                            acc
                            (+ acc (Math/pow 2 pos))))
 0
 (apply mapv vector (find-common-bits input)))


(reduce-kv
 (fn [acc pos [zero one]] (if (> zero one)
                            (+ acc (Math/pow 2 pos))
                            acc))
 0
 (apply mapv vector (find-common-bits input)))
;; =>

(* 844.0  3251.0)

;; Part 2

(defn count-zeros [res]
  (count (filter #(= % \0) res)))

(defn count-ones [res]
  (count (filter #(= % \1) res)))

(defn bit-at-pos [input pos]
  (map #(nth (reverse %1) pos) input))


(def pos 0)
(def res (bit-at-pos test-input pos))
res

(defn oxygen [input pos]
  (let [res (bit-at-pos input pos)]

    (prn
     (> (count-zeros res) (count-ones res)))
    (cond
      (> (count-zeros res) (count-ones res))
      (do (prn "count zeros is more")
          (filter #(= \0 (nth % pos)) input))

      (= (count-zeros res) (count-ones res))
      (filter #(= \1 (nth  % pos)) input)

      :else
      (filter #(= \1 (nth  % pos)) input))))

(defn co2 [input pos]
  (let [res (bit-at-pos input pos)]
    (cond
      (< (count-zeros res) (count-ones res))
      (filter #(= \0 (nth  % pos)) input)

      (= (count-zeros res) (count-ones res))
      (filter #(= \0 (nth  % pos)) input)

      :else
      (filter #(= \1 (nth  % pos)) input))))

(oxygen test-input 0)
(co2 test-input 2)
(count-zeros
 (bit-at-pos test-input 0))

(filter #(= \0 (nth % 0)) (first test-input))

(count-ones
 (bit-at-pos test-input 0))

(loop [input test-input
       pos 0]
  (let [oxy (oxygen input pos)]
    (prn oxy)
    (if (or (= pos (dec (count (first input)))) (= (count oxy) 1))
      oxy
      (recur oxy (inc pos)))))


(def limit (dec (count (first input))))

(loop [input input
       pos 0]
  (let [c (co2 input pos)]
    (if (or (>= pos limit) (= (count c) 1))
      c
      (recur c (inc pos)))))

(dec (count (first input)))

(*
 1586
 2980)