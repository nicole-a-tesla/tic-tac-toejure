(ns tic-tac-toejure.core
  (:require [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.ai :refer :all]))
(def build-board
  (vec (repeat 9 "")))

(def human-player
  {:marker "X" :move-getter get-player-move :name "Human Player"})

(def computer-player
  {:marker "O" :move-getter random-move :name "Computer Player"})

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

(defn get-all-spaces-for [board marker]
  (def indexed-sublists (map-indexed vector board))
  (def marker-only (filter #(= marker (% 1)) indexed-sublists))
  (def final (map #(get % 0) marker-only))
  final)

(def wins [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]])

(defn in? [collection item]
  (some #(= item %) collection))

(defn check-this-win-condition [win-condition player-spots]
  (every? #(in? player-spots %) win-condition))

(defn search-for-wins [wins player-spots]
  (some true? (map #(check-this-win-condition % player-spots) wins)))

(defn get-winner [board players]
  (if (empty? players)
    nil
    (do
      (def marker (:marker (first players)))
      (def player-spots (get-all-spaces-for board marker))
      (if (search-for-wins wins player-spots)
        marker
        (recur board (rest players))))))

(defn get-game-stats [board players]
  (def winner (get-winner board players))
  (def board-is-full (not-any? #(= "" %) board))
  (hash-map :winner winner, :game-over (or (not= winner nil) board-is-full)))

(defn play [board players]
  (print-board board)
  (def game-stats (get-game-stats board players))
  (if (:game-over game-stats)
    (print-it "Game Over!")
    (do
      (def next-board (take-turn board (first players)))
      (recur next-board (reverse players)))))

(defn -main [& args]
  (def players (vector human-player computer-player))
  (play build-board players))
