(ns tic-tac-toejure.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.core :refer :all]))

(describe "place-marker"
  (it "adds marker to space"
    (should= ["X" "" "" "" "" "" "" "" ""]
      (place-marker build-board "0" "X")))
)

(run-specs)
