(ns tic-tac-toejure.board_analysis)

(def wins [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]])

(defn in? [collection item]
  (boolean (some #(= item %) collection)))

(defn check-this-win-condition [win-condition player-spots]
  (every? #(in? player-spots %) win-condition))

(defn check-for-all-win-conditions [wins player-spots]
  (boolean (some true? (map #(check-this-win-condition % player-spots) wins))))

(defn get-all-spaces-for [board marker]
  (let [indexed-sublists (map-indexed vector board)]
    (let [marker-only (filter #(= marker (% 1)) indexed-sublists)]
      (map #(get % 0) marker-only))))

(defn get-winner [board markers]
  (if (empty? markers)
    false
    (let [marker (first markers)]
      (let [player-spots (get-all-spaces-for board marker)]
        (if (check-for-all-win-conditions wins player-spots)
          marker
          (recur board (rest markers)))))))

(defn board-is-full? [board]
  (not-any? #(= "" %) board))

(defn game-over? [board markers]
  (boolean (or (get-winner board markers) (board-is-full? board))))

(defn analyze-game-state [board markers]
  (hash-map :winner (get-winner board markers), :game-over (game-over? board markers)))

