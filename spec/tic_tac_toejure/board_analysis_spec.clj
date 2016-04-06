(ns tic-tac-toejure.board_analysis_spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.board_analysis :refer :all]))

(def test-markers (vector "X" "O"))

(describe "in?"
  (it "returns true if matches"
    (should= true
      (in? [[0 1 2] [3 4 5]] [0 1 2])))

  (it "returns false for no matches"
    (should= false
      (in? [[0 1 2] [3 4 5]] [0 1 4]))))

(let [main-collection [[0 1 2] [3 4 5]]]
  (describe "check-for-all-win-conditions"
    (it "returns true if matches"
      (should= true
        (check-for-all-win-conditions main-collection [0 1 2 9 8])))

    (it "returns false if no matches"
      (should= false
        (check-for-all-win-conditions main-collection [0 9])))))

(describe "check for this win condition"
  (it "returns true if win condition satisfied"
    (should= true
      (check-this-win-condition [0 1 2] [0 1 2 3 4 5])))

  (it "returns false if condititon not satisfied"
    (should= false
      (check-this-win-condition [0 1 2] [0 1 3 4 5]))))

(describe "get-winner"
  (it "returns winner's marker"
    (let [test-board ["O" "O" "O" "" "" "" "" "" ""]]
      (should= "O"
        (get-winner test-board test-markers))))

  (it "returns false if no winner"
    (should= false
      (get-winner (vec (repeat 9 "")) test-markers))))

(describe "get-all-spaces-for"
  (it "gets all spaces for given marker"
    (let [new-board ["O" "O" "O" "" "" "" "" "" ""]]
      (should= [0 1 2]
        (get-all-spaces-for new-board "O")))))

(describe "game-stats :game-over"
  (it "false if board contans blanks"
    (let [almost-full ["" "x" "x" ""]]
      (let [game-state (analyze-game-state almost-full test-markers)]
        (should= false
          (game-state :game-over)))))

  (it "true if board contains no blanks"
    (let [full ["x" "x" "x" "x"]]
      (let [game-state (analyze-game-state full test-markers)]
        (should= true
          (game-state :game-over)))))

  (it "true if a player has won"
    (let [o-won ["O" "O" "O" "" "" "" "" "" ""]]
      (let [game-state (analyze-game-state o-won test-markers)]
        (should= true
          (game-state :game-over))))))

(describe "game-over"
  (it "true if winner"
    (should= true
      (game-over? (repeat 9 "O") test-markers)))

  (it "false if no winner"
    (should= false
      (game-over? (repeat 9 "") test-markers))))
