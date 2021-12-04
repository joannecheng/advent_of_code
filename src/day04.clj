(ns advent.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def row-l 5)

(defn parse-boards [boards-str]
  "FIXME: OH MY GOD WHAT EVEN IS THIS"
  (->> boards-str
       (partition (inc row-l))
       (map #(str/join " " %))
       (map #(str/split % #" "))
       (map #(filter not-empty %))
       (mapv #(mapv read-string %))))

(defn input [input-str]
  (let [input-as-str (-> input-str
                         (str/split #"\n"))
        test-nums (-> (first input-as-str)
                      (str/split #","))
        test-nums (mapv read-string test-nums)]

    [test-nums (parse-boards (drop 2 input-as-str))]))

(input (slurp (io/resource "day04.txt")))

(def test-input
  [7 4 9 5 11 17 23 2 0 14 21 24 10 16 13 6 15 25 12 22 18 20 8 19 3 26 1])

(def boards
  [[22 13 17 11 0
    8 2 23 4 24
    21 9 14 16 7
    6 10 3 18 5
    1 12 20 15 19]

   [3 15 0 2 22
    9 18 13 17 5
    19 8 7 25 23
    20 11 10 24 4
    14 21 16 12 6]

   [14 21 17 24 4
    10 16 15 9 19
    18 8 23 26 20
    22 11 13 6 5
    2 0 12 3 7]])


(def init-board-matches (vec (repeat (count boards) [])))

(defn winning-row [board-match]
  (filter
   (fn [row-num]
     (= row-l (->> board-match
                   (filter #(= row-num (second %)))
                   set
                   count)))
   (range 0 row-l)))

(defn winning-col [board-match]
  (filter
   (fn [col-num]
     (= row-l (count (filter #(= col-num (first %)) board-match))))
   (range 0 row-l)))

(defn winning-boards
  ([board-matches]
   (winning-boards board-matches []))

  ([board-matches boards-to-skip]
   (->> board-matches
        (keep-indexed (fn [idx b]
                        (when
                         (and
                          (or (not-empty (winning-row b))
                              (not-empty (winning-col b)))
                          (not (some #(= % idx) boards-to-skip)))
                          idx))))))

(defn find-match
  "Takes a board, input num
   Returns location of match as [r c]"
  [board num]
  (let [index (.indexOf board num)]
    (if (= index -1)
      []
      [(mod index row-l) (int (/ index row-l))])))

(defn update-boards [boards board-matches num]
  (reduce-kv
   (fn [acc idx curr-board]
     (update-in acc [idx] conj (find-match curr-board num)))
   board-matches
   boards))

(defn number-at-pos [board pos]
  (nth board (+ (* (second pos) row-l) (first pos))))

(defn board-product [board board-match]
  (let [every-pos (for [x (range 0 5)
                        y (range 0 5)]
                    [x y])]
    (reduce
     #(if (some #{%2} board-match)
        %1
        (+ %1 (number-at-pos board %2)))
     0
     every-pos)))


(defn calculate-winning-board
  ([boards board-matches index last-num]
   (let [board (nth boards index)
         matches (filter not-empty (nth board-matches index))]
     (*
      (board-product board matches)
      last-num))))

(defn part2* [test-input boards]
  (loop [test-input test-input
         board-matches init-board-matches
         boards-to-skip []
         last-won nil]
    (let [updated-bm (update-boards boards board-matches (first test-input))
          winning (winning-boards updated-bm boards-to-skip)
          won? (seq winning)]
      (if (= 1 (count test-input))
        last-won
        (recur (rest test-input) updated-bm
               (cond-> boards-to-skip won? (concat winning))
               (if won?
                 (calculate-winning-board boards updated-bm (first winning) (first test-input))
                 last-won))))))

(defn part1* [test-input boards]
  (loop [test-input test-input
         board-matches init-board-matches]
    (let [updated-bm (update-boards boards board-matches (first test-input))
          winning (winning-boards updated-bm)]
      (if (or (some? winning) (= 1 (count test-input)))
        (calculate-winning-board boards updated-bm winning (first test-input))
        (recur (rest test-input) updated-bm)))))

(defn part1 []
  (let [[test-input boards] (input (slurp (io/resource "day04.txt")))]
    (part1* test-input boards)))

(defn part2 []
  (let [[test-input boards] (input (slurp (io/resource "day04.txt")))]
    (part2* test-input boards)))

(part1)
(part2)
(part1* test-input boards)
(part2* test-input boards)