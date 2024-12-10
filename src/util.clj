^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(ns util
  (:require
   [clojure.string :as str]))

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(defn read-day-input
  [day & {:keys [sample] :or {sample false}}]
  (let [suffix (if sample "-sample" "")]
    (slurp (format "inputs/day%02d%s.in" day suffix))))

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(defn string-list->int-list
  [s & {:keys [neg?]
        :or {neg? true}}]
  (mapv parse-long
        (re-seq (if neg? #"-?\d+" #"\d+") s)))

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(defn parse-line
  [line & [fn-parse]]
  (let [f (case fn-parse
            :ints string-list->int-list)]
    (f line)))

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(defn parse-lines
  [lines & [fn-parse]]
  (mapv #(parse-line % fn-parse)
        (str/split lines #"\n")))

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(defn transpose
  [matrix]
  (apply mapv vector matrix))
