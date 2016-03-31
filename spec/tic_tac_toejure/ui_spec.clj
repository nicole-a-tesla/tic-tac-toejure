(ns tic-tac-toejure.ui-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.ui :refer :all]))

(def empty-board (vec (repeat 9 "")))

(def empty-board-output-str (str empty-space-view empty-space-view empty-space-view "\n"
                                 empty-space-view empty-space-view empty-space-view "\n"
                                 empty-space-view empty-space-view empty-space-view "\n"))

(describe "Ui"
  (around [it]
    (with-out-str (it)))

  (describe "test prompt"
    (it "tests the input of prompt"
      (should= "X"
        (with-in-str "X"
          (prompt "X or O?"))))

    (it "tests the output of prompt"
      (should= "X or O?\n"
       (with-out-str (with-in-str "O"
        (prompt "X or O?"))))))

  (describe "test print-board"
    (it "prints board to stdout"
      (should= empty-board-output-str
        (with-out-str (print-board empty-board)))))

  (describe "prompt user for position choice"
    (it "test gets user input"
      (with-redefs [prompt (fn [& _] "0")]
        (should= "0"
          (get-player-move)))))

  (describe "build-board-view"
    (it "builds board representation for empty board"
      (should= (repeat 9 ":___")
      (build-view empty-board))))

)

(run-specs)
