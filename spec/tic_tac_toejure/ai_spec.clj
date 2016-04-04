(ns tic-tac-toejure.ai-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.ai :refer :all]))

(describe "Ai"
  (describe "random-move"
    (it "returns a string"
      (should= java.lang.String (type (random-move))))

    (it "returns numbers between 0 and 9"
      (def randos (repeatedly 100 #(rand-int 9)))
      (should= true (empty? (filter #(> % 8) randos)))))
)
