(ns tic-tac-toejure.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.core :refer :all]))

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
        (prompt "X or O?")))))
  )

  (describe "test print-board"
    (it "prints board to stdout"

      (should= (str space space space "\n"
                    space space space "\n"
                    space space space "\n")
        (with-out-str (print-board))))
  )

  ; (describe "print board with marker"
  ;   (it "prints board with marker after placed")
  ; )
)
