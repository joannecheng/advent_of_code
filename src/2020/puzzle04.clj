(ns puzzle04
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.spec.alpha :as s]))

(def fields
  #{:byr :iyr :eyr :hgt :hcl :ecl :pid :cid})
(def valid-missing-fields #{:cid})

;; missing cid is valid, anything else is not
;;byr (Birth Year) - four digits; at least 1920 and at most 2002.
;;iyr (Issue Year) - four digits; at least 2010 and at most 2020.
;;eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
;;hgt (Height) - a number followed by either cm or in:
;;If cm, the number must be at least 150 and at most 193.
;;If in, the number must be at least 59 and at most 76.
;;hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
;;ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
;;pid (Passport ID) - a nine-digit number, including leading zeroes.
;;cid (Country ID) - ignored, missing or not.

(s/def ::hgt-cm (s/and #(>= % 150) #(<= % 193)))
(s/def ::hgt-in (s/and #(>= % 59) #(<= % 76)))
(defn valid-hgt? [hgt]
  (let [[_ height unit] (re-find #"(\d+)(\w+)" hgt)]
    (cond
      (= unit "cm") (s/valid? ::hgt-cm (read-string height))
      (= unit "in") (s/valid? ::hgt-in (read-string height))
      :else false)))

(s/def ::byr (s/and #(>= (read-string %) 1920) #(<= (read-string %) 2002)))
(s/def ::iyr (s/and #(>= (read-string %) 2010) #(<= (read-string %) 2020)))
(s/def ::eyr (s/and #(>= (read-string %) 2020) #(<= (read-string %) 2030)))

(s/def ::hgt #(valid-hgt? %))

(s/def ::hcl (s/and string? #(re-matches #"\#([0-9]|[a-f]){6}" %)))

(s/def ::ecl #(contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} %))

(s/def ::pid #(re-matches #"[0-9]{9}" %))

(s/def ::passport (s/keys :req-un [::byr
                                   ::iyr
                                   ::eyr ::hgt ::hcl ::ecl
                                   ::pid]))

(defn parse-passport [passport-str]
  (->>
   (str/split passport-str #"\s+")
   (map #(-> (str/split % #":")
             (update 0 keyword)))
   flatten
   (apply hash-map)))

(defn valid-passport? [passport-str]
  (s/valid? ::passport (parse-passport passport-str)))

(->> (str/split (slurp (io/resource "day04.txt")) #"\n\n")
     (filter valid-passport?)
     count)
