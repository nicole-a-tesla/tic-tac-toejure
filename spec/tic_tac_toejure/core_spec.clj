(ns tic-tac-toejure.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.core :refer :all]))

(def test-players (vector human-player computer-player))

(describe "place-marker"
  (it "adds marker to space"
    (should= ["X" "" "" "" "" "" "" "" ""]
      (place-marker build-board 0 "X"))))

(describe "User Input Validations"

  (describe "is-valid-position"
    (it "does not match non-number strings"
      (should= false
        (is-valid-position? "beep")))

    (it "does not match if number not on board"
      (should= false
        (is-valid-position? "9")))

    (it "does not match floats"
      (should= false
        (is-valid-position? "2.3")))

    (it "returns true if int between 0 and 8"
      (should= true
        (is-valid-position? "4"))))

  (describe "is-position-available?"
    (it "returns true if position is available"
      (should= true
        (is-position-available? build-board "0")))

    (it "returns false if position is taken"
      (should= false
        (is-position-available? (vec (repeat 9 "x")) "0")))

    (it "returns false if position is not on board"
      (should= false
        (is-position-available? build-board "10"))))

  (describe "input-is-valid?"

    (it "returns true for if valid and available position"
      (with-redefs [is-valid-position? (fn [_] true)]
        (with-redefs [is-position-available? (fn [& _] true)]
          (should= true
            (input-is-valid? build-board "something valid like 8")))))

    (it "return false for invalid but available position "
      (with-redefs [is-valid-position? (fn [_] false)]
        (with-redefs [is-position-available? (fn [& _] true)]
          (should= false
            (input-is-valid? build-board "random words like tune yards")))))

    (it "returns false for invalid and unavailable position"
     (with-redefs [is-valid-position? (fn [_] false)]
       (with-redefs [is-position-available? (fn [& _] false)]
          (should= false
            (input-is-valid? build-board "soumething out of bounds like 9")))))

    (it "returns false for positions that are taken"
      (with-redefs [is-valid-position? (fn [_] true)]
        (with-redefs [is-position-available? (fn [& _] false)]
          (should= false
            (input-is-valid? build-board "valid index like 0 but for a full board")))))))

(describe "get-valid-move"
  (it "returns number when input validity check returns true"
    (with-redefs [input-is-valid? (fn [] true)])
      (should= 3
        (get-valid-move build-board (fn [] "3")))))

(describe "game-stats :game-over"
  (it "false if board contans blanks"
    (let [almost-full ["" "x" "x" ""]]
      (let [game-state (analyze-game-state almost-full test-players)]
        (should= false
          (game-state :game-over)))))

  (it "true if board contains no blanks"
    (let [full ["x" "x" "x" "x"]]
      (let [game-state (analyze-game-state full test-players)]
        (should= true
          (game-state :game-over)))))

  (it "true if a player has won"
    (let [o-won ["O" "O" "O" "" "" "" "" "" ""]]
      (let [game-state (analyze-game-state o-won test-players)]
        (should= true
          (game-state :game-over))))))

(describe "get-all-spaces-for"
  (it "gets all spaces for given marker"
    (let [new-board ["O" "O" "O" "" "" "" "" "" ""]]
      (should= [0 1 2]
        (get-all-spaces-for new-board "O")))))

(describe "get-winner"
  (it "returns winner's marker"
    (let [test-board ["O" "O" "O" "" "" "" "" "" ""]]
      (should= "O"
        (get-winner test-board test-players))))

  (it "returns false if no winner"
    (should= false
      (get-winner (vec (repeat 9 "")) test-players))))

(describe "Game End Conditions"
  (it "game ends if board is full"
    (should= true
      (.contains (with-out-str
        (play (vec (repeat 9 "X")) test-players)) "Game Over!")))

  (let [board ["O" "O" "O" "" "" "" "" "" ""]]
    (it "ends when a player has won"
      (should= true
        (.contains (with-out-str
          (play board test-players)) "Game Over!")))

    (it "announces winning player"
      (should= true
        (.contains (with-out-str
          (play board test-players)) "O wins!")))))

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
