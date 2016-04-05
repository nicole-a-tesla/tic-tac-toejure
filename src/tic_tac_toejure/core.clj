(ns tic-tac-toejure.core
  (:require [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.ai :refer :all]
            [tic-tac-toejure.board_analysis :refer :all]))

(def build-board
  (vec (repeat 9 "")))

(def human-player
  {:marker "X" :move-getter get-player-move :name "Human Player"})

(defn computer-player [strategy]
  {:marker "O" :move-getter strategy :name "Computer Player"})

(defn as-int [input]
  (Integer/parseInt input))

(defn is-valid-position? [user-input]
  (boolean (re-matches #"[0-8]" user-input)))

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
  (let [move (get-valid-move board (player :move-getter))]
    (place-marker board move (player :marker))))

(defn analyze-game-state [board players]
  (let [winner (get-winner board players)]
    (let [board-is-full (not-any? #(= "" %) board)]
      (hash-map :winner winner, :game-over (or (boolean winner) board-is-full)))))

(defn play [board players]
  (print-board board)
  (let [game-state (analyze-game-state board (map :marker players))]
    (if (game-state :game-over)
      (announce-game-over (game-state :winner))
      (let [next-board (take-turn board (first players))]
        (recur next-board (reverse players))))))

(defn -main [& args]
  (let [players (vector human-player (computer-player random-move))]
    (play build-board players)))
