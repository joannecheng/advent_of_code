(ns puzzle02
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input-str
  "1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc")

(defn lines [input-str]
  (-> input-str
      (str/split #"\n")))

(defn parse-line [line]
  (let [[_ min-count max-count c passw] (re-find #"(\d+)-(\d+)\s(\w):\s(\w+)" line)]
    {:min-count (read-string min-count)
     :max-count (read-string max-count)
     :char c
     :password passw}))

(defn xor [cond1 cond2]
  (cond
    (and cond2 (not cond1)) true
    (and cond1 (not cond2)) true
    :else false))

(defn valid-pass? [{:keys [min-count max-count char password]}]
  (let [char-count (-> (re-pattern char)
                       (re-seq password)
                       count)]
    (and (>= char-count min-count) (<= char-count max-count))))

(defn valid-pass2? [{:keys [min-count max-count char password]}]
  (let [pos1 (str (get password (dec min-count)))
        pos2 (str (get password (dec max-count)))]
    (xor (= pos1 char) (= pos2 char))))

(def input-file-str
  (slurp
   (io/resource "day2.txt")))

(->> input-file-str
     lines
     (map parse-line)
     (filter valid-pass?)
     count)
;; => {:min-count 2, :max-count 4, :char "r", :password "prrmspx"}
;; => 467

(->> input-file-str
     lines
     (map parse-line)
     (filter valid-pass2?)
     count)
;; => 441
