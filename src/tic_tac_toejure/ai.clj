(ns tic-tac-toejure.ai
  (:require [tic-tac-toejure.board_analysis :refer :all]))

(defn random-move []
  (str (rand-int 9)))

(defn calculate-points [winning-marker own-marker]
  (if (= winning-marker own-marker) 10 -10))

(defn score [board own-marker opponent-marker]
  (let [winning-marker (get-winner board (vector own-marker opponent-marker))]
    (if (false? winning-marker)
      0
      (calculate-points winning-marker own-marker))))


