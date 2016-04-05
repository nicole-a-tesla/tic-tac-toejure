(ns tic-tac-toejure.ai-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.ai :refer :all])
  (:use [tic-tac-toejure.board_analysis :only [get-winner]]))

(describe "Random Ai"
  (describe "random-move"
    (it "returns a string"
      (should= java.lang.String (type (random-move))))

    (it "returns a string that can be converted to an integer"
      (should-not-throw
        (Integer/parseInt (random-move))))))

(describe "Minimax Ai"

  (let [board (into (repeat 6 "") (repeat 3 "O"))]

    (describe "score"
      (it "returns 10 for boards where own marker wins"
        (with-redefs [get-winner (fn [& _] "O")]
          (should= 10
            (score board "O" "X"))))

      (it "returns -10 for boards where opponent marker wins"
        (with-redefs [get-winner (fn [& _] "O")]
          (should= -10
            (score board "X" "O"))))

      (it "returns 0 for boards where no one wins"
        (with-redefs [get-winner (fn [& _] false)]
          (should= 0
            (score (vec (repeat 9 "")) "X" "O"))))))
  )
