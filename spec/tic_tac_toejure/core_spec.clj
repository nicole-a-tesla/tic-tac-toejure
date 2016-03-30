(ns tic-tac-toejure.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.core :refer :all]))

(describe "placeholder test"
  (around [it]
    (with-out-str (it)))

  (it "tests the input of prompt"
    (should= "X"
    (with-in-str "X"
      (prompt "X or O?"))))

  (it "tests the output of prompt"
    (should= "X or O?\n"
    (with-out-str (with-in-str "O"
    (prompt "X or O?")))))
)
