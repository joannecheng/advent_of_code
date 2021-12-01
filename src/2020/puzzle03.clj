(ns puzzle03
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn tree? [char]
  (= char \#))

(def field
  (-> (io/resource "day03.txt")
      slurp
      (str/split #"\n")))

(defn get-cell [field x y]
  (let [row (nth field y)]
    (nth (cycle row) x)))

(defn traverse [field right down]
  (loop [new-x (+ 0 right)
         new-y (+ 0 down)
         tree-count 0]
    (let [new-tree-count (if (tree? (get-cell field new-x new-y))
                       (inc tree-count)
                       tree-count)]
      (if (= new-y (dec (count field)))
        new-tree-count
        (recur (+ right new-x) (+ down new-y) new-tree-count)))))

(*
 (traverse field 1 1)
 (traverse field 3 1)
 (traverse field 5 1)
 (traverse field 7 1)
 (traverse field 1 2))

