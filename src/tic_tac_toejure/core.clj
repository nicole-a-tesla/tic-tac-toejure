(ns tic-tac-toejure.core
  (:require [tic-tac-toejure.ui :refer :all]))

(def build-board
  (vec (repeat 9 "")))

(defn as-int [input]
  (read-string input))

(defn place-marker [board position marker]
  (assoc board (as-int position) marker))

; Game loop

(defn play [board]
  (print-board board)
  (def move (get-player-move))
  (def next-board (place-marker board move "X"))
  (if-not (= move "q")
    (recur next-board)))

(defn -main [& args]
  (play build-board))
