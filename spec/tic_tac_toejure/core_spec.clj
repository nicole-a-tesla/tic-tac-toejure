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
      (should= nil
        (is-valid-position? "beep")))

    (it "does not match if number not on board"
      (should= nil
        (is-valid-position? "9")))

    (it "does not match floats"
      (should= nil
        (is-valid-position? "2.3")))

    (it "returns input if int between 0 and 8"
      (should= "4"
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
      (with-redefs [is-valid-position? (fn [_] "truthy string")]
        (with-redefs [is-position-available? (fn [& _] true)]
          (should= true
            (input-is-valid? build-board "something valid like 8")))))

    (it "return nil (falsey) for invalid but available position "
      (with-redefs [is-valid-position? (fn [_] nil)]
        (with-redefs [is-position-available? (fn [& _] true)]
          (should-be-nil
            (input-is-valid? build-board "random words like tune yards")))))

    (it "returns nil (falsey) for invalid and unavailable position"
     (with-redefs [is-valid-position? (fn [_] nil)]
       (with-redefs [is-position-available? (fn [& _] false)]
          (should-be-nil
            (input-is-valid? build-board "soumething out of bounds like 9")))))

    (it "returns false for positions that are taken"
      (with-redefs [is-valid-position? (fn [_] "truthy string")]
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
    (def almost-full ["" "x" "x" ""])
    (def stats (get-game-stats almost-full test-players))
    (should= false
      (:game-over stats)))

  (it "true if board contains no blanks"
    (def full ["x" "x" "x" "x"])
    (def stats (get-game-stats full test-players))
    (should= true
      (:game-over stats)))

  (it "true if a player has won"
    (def o-won ["O" "O" "O" "" "" "" "" "" ""])
    (def stats (get-game-stats o-won test-players))
    (should= true
      (:game-over stats))))

(describe "get-all-spaces-for"
  (it "gets all spaces for given marker"
    (def new-board ["O" "O" "O" "" "" "" "" "" ""])
      (should= [0 1 2]
        (get-all-spaces-for new-board "O"))))

(describe "get-winner"
  (it "returns winner's marker"
    (def test-board ["O" "O" "O" "" "" "" "" "" ""])
    (should= "O"
      (get-winner test-board test-players)))

  (it "returns nil if no winner"
    (should-be-nil
      (get-winner (vec (repeat 9 "")) test-players))))

(describe "Game End Conditions"
  (it "game ends if board is full"
    (should= true
      (.contains (with-out-str
        (play (vec (repeat 9 "X")) test-players)) "Game Over!\n")))

  (it "ends when a player has won"
    (let [board ["O" "O" "O" "" "" "" "" "" ""]]
    (should= true
      (.contains (with-out-str
        (play board test-players)) "Game Over!\n"))))
  )

(describe "in?"
  (it "returns true if matches"
    (should= true
      (in? [[0 1 2] [3 4 5]] [0 1 2])))
  (it "returns nil for no matches"
    (should-be-nil
      (in? [[0 1 2] [3 4 5]] [0 1 4]))))

(describe "search-for-wins"
  (def main-collection [[0 1 2] [3 4 5]])
  (it "returns true if matches"
    (should= true
      (search-for-wins main-collection [0 1 2 9 8])))

  (it "returns nil if no matches"
    (should-be-nil
      (search-for-wins main-collection [0 9]))))

(describe "check for this win condition"
  (it "returns true if win condition satisfied"
    (should= true
      (check-this-win-condition [0 1 2] [0 1 2 3 4 5])))
  (it "returns false if condiiton not satisfied"
    (should= false
      (check-this-win-condition [0 1 2] [0 1 3 4 5]))))
