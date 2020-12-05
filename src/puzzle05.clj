(ns puzzle05
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input (slurp (io/resource "day05.txt")))

(defn take-upper-half [input]
  (let [c (count input)]
    (take-last (/ c 2) input )))

(defn take-lower-half [input]
  (let [c (count input)]
    (take (/ c 2) input)))

(defn parse-code [seat-str input]
  (loop [seat-str seat-str
         input input]
    (let [cur-str (first seat-str)]
      (cond
        (or (= cur-str \L)
            (= cur-str \F)) (recur (rest seat-str) (take-lower-half input))
        (or (= cur-str \R)
            (= cur-str \B)) (recur (rest seat-str) (take-upper-half input))
        :else (first input)))))

(defn decode-row-seat-code [row-seat-code]
  (let [row-code (subs row-seat-code 0 7)
        seat-code (subs row-seat-code 7)]
    (+
     (* 8 (parse-code row-code (range 128)))
     (parse-code seat-code (range 8)))))

(def seats
  (map
   decode-row-seat-code
   (str/split input #"\n")))

(set/difference
 (set (range 68 965))
 (set seats))
