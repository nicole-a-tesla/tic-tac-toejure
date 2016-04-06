(ns tic-tac-toejure.ai
  (:require [tic-tac-toejure.board_analysis :refer :all]))


(defn place-marker [board position marker]
  (assoc board position marker))

(defn random-move []
  (str (rand-int 9)))

(defn calculate-points [winning-marker own-marker]
  (if (= winning-marker own-marker) 10 -10))

(defn score [board own-marker opponent-marker]
  (let [winning-marker (get-winner board (vector own-marker opponent-marker))]
    (if (false? winning-marker)
      0
      (calculate-points winning-marker own-marker))))

(defn minimax-move [board own-marker opponent-marker]
  (if (game-over? board (vector own-marker opponent-marker))
    (score board own-marker opponent-marker)
    (let [empty-positions (get-all-spaces-for board "")]
      (into {} (for [position empty-positions
                    :let [new-board (place-marker board position own-marker)
                          new-scores (hash-map position (minimax-move new-board opponent-marker own-marker))]]
                          new-scores)))))
