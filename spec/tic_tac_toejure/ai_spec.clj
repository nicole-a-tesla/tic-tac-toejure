(ns tic-tac-toejure.ai-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.ai :refer :all]))

(describe "Ai"
  (describe "random-move"
    (it "returns a number"
      (should= java.lang.Integer (type (random-move))))
    (it "returns numbers between 0 and 9"
      (def randos (repeatedly 100 #(rand-int 9)))
      (should= true (empty? (filter #(> % 8) randos))))
    )
)

(run-specs)
