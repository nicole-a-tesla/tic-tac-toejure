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
  (Integer/parseInt input))

(defn is-valid-position? [user-input]
  (re-matches #"[0-8]" user-input))

(defn is-position-available? [board user-input]
  (= "" (get board (as-int user-input))))

(defn input-is-valid? [board user-input]
  (and (is-valid-position? user-input) (is-position-available? board user-input)))

(defn get-valid-move
  ([board move-getter]
    (get-valid-move board move-getter (move-getter)))
  ([board move-getter move]
    (if (input-is-valid? board move)
      (as-int move)
      (recur board move-getter (move-getter)))))

(defn place-marker [board position marker]
  (assoc board position marker))

(defn take-turn [board player]
  (def move (get-valid-move board (:move-getter player)))
  (place-marker board move (:marker player)))

(defn play [board players]
  (print-board board)
  (def next-board (take-turn board (first players)))
  (recur next-board (reverse players)))

(defn -main [& args]
  (def players (vector human-player computer-player))
  (play build-board players))
