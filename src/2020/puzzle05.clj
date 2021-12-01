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

(defn parse-code [seat-str]
  (loop [seat-str seat-str
         input (range (Math/pow 2 (count seat-str)))]
    (let [cur-str (first seat-str)]
      (cond
        (#{\L \F} cur-str) (recur (rest seat-str) (take-lower-half input))
        (#{\R \B} cur-str) (recur (rest seat-str) (take-upper-half input))
        :else (first input)))))

(defn parse-code-binary [seat-str]
  (->> seat-str
       (map-indexed
        (fn [i cur]
          (if (#{\R \B} cur)
            (Math/pow 2 (- (dec (count seat-str)) i))
            0)))
       (reduce +)))

(defn decode-row-seat-code [row-seat-code]
  (let [row-code (subs row-seat-code 0 7)
        seat-code (subs row-seat-code 7)]
    (int
     (+
      (* 8 (parse-code-binary row-code))
      (parse-code-binary seat-code)))))

(def seats
  (map
   decode-row-seat-code
   (str/split-lines input)))

(apply max seats)

(set/difference
 (set (range 68 965))
 (set seats))
