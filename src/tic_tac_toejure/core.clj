(ns tic-tac-toejure.core
  (:require [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.ai :refer :all]))

(def build-board
  (vec (repeat 9 "")))

(def human-player
  {:marker "X" :move-getter get-player-move})

(def computer-player
  {:marker "O" :move-getter random-move})

(defn as-int [input]
  (read-string input))

(defn place-marker [board position marker]
  (assoc board (as-int position) marker))

(defn take-turn [board player]
  (def move ((get player :move-getter)))
  (def marker (get player :marker))
  (place-marker board move marker))

; Game loop

(defn play [board players]
  (print-board board)
  (def next-board (take-turn board (first players)))
  (recur next-board (reverse players)))

(defn -main [& args]
  (def players (vector human-player computer-player))
  (play build-board players))
