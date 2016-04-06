(ns tic-tac-toejure.ai-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.ai :refer :all])
  (:use [tic-tac-toejure.board_analysis :only [get-winner, game-over?]]))

(def empty-board (vec (repeat 9 "")))

(describe "Random Ai"
  (describe "random-move"
    (it "returns a string"
      (should= java.lang.String (type (random-move))))

    (it "returns a string that can be converted to an integer"
      (should-not-throw
        (Integer/parseInt (random-move))))))

(describe "Minimax Ai"

    (describe "score"
      (it "returns 10 for boards where own marker wins"
        (with-redefs [get-winner (fn [& _] "O")]
          (should= 10
            (score empty-board "O" "X"))))

      (it "returns -10 for boards where opponent marker wins"
        (with-redefs [get-winner (fn [& _] "O")]
          (should= -10
            (score empty-board "X" "O"))))

      (it "returns 0 for boards where no one wins"
        (with-redefs [get-winner (fn [& _] false)]
          (should= 0
            (score empty-board "X" "O")))))

  (describe "Minimax Move"
    (it "if game is stalemate, returns score of current board"
        (should= 0
          (minimax-move ["X" "X" "O" "O" "O" "X" "X" "X" "O"] "O" "X")))

    (it "opponent has won, returns score of current board"
        (should= -10
          (minimax-move (repeat 9 "X") "O" "X")))

    (it "computer has won, returns score of current board"
        (should= 10
          (minimax-move (repeat 9 "O") "O" "X")))

    (it "picks winning position if available"
      (let [board (into (vec (repeat 7 "")) (vec (repeat 2 "O")))]
        (should= 2
          (minimax-move board "O" "X"))))

  )
)
