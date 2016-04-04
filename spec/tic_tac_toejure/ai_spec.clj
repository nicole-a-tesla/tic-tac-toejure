(ns tic-tac-toejure.ai-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.ai :refer :all]))

(describe "Ai"
  (describe "random-move"
    (it "returns a string"
      (should= java.lang.String (type (random-move))))))
