^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(ns day01
  (:require
   util
   [clojure.string :as str]))

;; ## Input & Parsing

(def input (util/read-day-input 1 :sample true))

;; We should see this input as 2 vertical seqs of ints.

;; Nothing doable besides splitting by lines (sep: `\n`).
(str/split-lines input)

;; We get a seq of strings, each corresponding to a line of the input.
;; Each line contains 2 numbers (with some other noise we must prune).

;; Using the first element as example.
;; Let's transform `"3   4"` -> `[3, 4]`.
(str/split "3   4" #"")

;; Get rid of the noise:
(remove str/blank? (str/split "3   4" #" "))

;; Also for more than single digit ints:
(remove str/blank? (str/split "13   4" #" "))

;; String -> Int
(map parse-long
     (remove str/blank? (str/split "3   4" #" ")))

;; Define a function that does this:
^{:nextjournal.clerk/visibility {:result :hide}}
(defn parse-input
  [s]
  (map parse-long
       (remove str/blank? (str/split s #" "))))

;; Parse input:

(def input-parsed (->> (str/split-lines input)
                       (map parse-input)))

;; At this point we notice that each head of the subseq correpond to
;; the elements of the first seq column imagined earlier. The tails
;; (second elements) are the elements of the second seq column.

;; To make this restructuring one can simply transpose this seq
;; of seq (matrix). In Clojure one can transpose a matrix via:

^{:nextjournal.clerk/visibility {:result :hide}}
(defn transpose
  [matrix]
  (apply map vector matrix))

;; Apply to the input:
(def input-parsed (transpose input-parsed))

;; This is what we're going to use from now on.

;; ## Part 1

;; Sort the 2 seqs:
(def input-parsed (map #(sort %) input-parsed))

;; We now need to compare the distance between each element of two seqs.

;; Let's define a function that returns the distance of two numbers:

^{:nextjournal.clerk/visibility {:result :hide}}
(defn distance
  [x y]
  (abs (- x y)))

;; Test it out:

;; **NOTE**: `assert` returns `nil` if the assertion is true.

(assert (= (distance -7 7) 14))

;; Now let's take advantage that `map` can take several `coll`s:

;; e.g:
(map + [1 2 3] [4 5 6])

;; and...
(map distance '(1 2 3) '(4 1 1))

;; If the above was confusing, this is pretty much the following:
(map (fn [x y] (distance x y)) '(1 2 3) '(4 1 1))

;; Now apply to our input:
(def distances
  (map distance (first input-parsed) (second input-parsed)))

;; **NOTE**: The following can also be done using destructuring
;; (there are exactly 2 elements)
(def distances
  (let [[seq1 seq2] input-parsed]
    (map distance seq1 seq2)))

;; We can do the above because we are sure the input contains exactly 2 seqs.
;; Now we only need sum all those values. We're looking for the sum
;; of an int list.

;; Define a function that accomplishes that:
^{:nextjournal.clerk/visibility {:result :hide}}
(defn sum
  [coll]
  (reduce + coll))

;; We should get an `11`...
(def part1 (sum distances))

;; ### My Input

;; Now test this out with my personal input:
(def input (util/read-day-input 1 :sample false))

(def part1
  (let [[seq1, seq2]
        (->> (str/split-lines input)
             (map parse-input)
             transpose
             (map #(sort %)))
        distances (map distance seq1 seq2)]
    (sum distances)))
