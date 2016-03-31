(ns tic-tac-toejure.core
  (:require [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.ai :refer :all]))

(def build-board
  (vec (repeat 9 "")))

(defn as-int [input]
  (read-string input))

(defn place-marker [board position marker]
  (assoc board (as-int position) marker))

; Game loop

(defn play [board]
  (print-board board)
  (def human-move (get-player-move))
  (def intermediate-board (place-marker board human-move "X"))
  (print-board intermediate-board)
  (def next-board (place-marker intermediate-board (random-move) "O"))
  (if-not (= human-move "q")
    (recur next-board)))

(defn -main [& args]
  (play build-board))
