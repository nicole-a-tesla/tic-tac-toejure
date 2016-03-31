(ns tic-tac-toejure.core)

; Constants & defs

(def get-player-move-message "Select your next move...")

(def empty-space-view ":___")

(def build-board
  (vec (repeat 9 "")))

; Ui

(defn print-it [message]
  (println (apply str message)))

(defn prompt [message]
  (print-it message)
  (read-line))

(defn get-player-move []
  (prompt get-player-move-message)
)

(defn state-to-view-mapper [space-contents]
  (if (clojure.string/blank? space-contents)
    empty-space-view
    (str ": " space-contents " ")))

(defn build-view [row]
  (map state-to-view-mapper row))

(defn print-board
  ([board]
  (print-board board (partition 3 board)))
  ([board rows]
    (print-it (build-view(first rows)))
    (if-not (empty? (rest rows))
      (recur board (rest rows)))
    )
)

; move logic

(defn as-int [input]
  (read-string input))

(defn place-marker [board position marker]
  (assoc board (as-int position) marker))

; Game loop

(defn play [board]
  (print-board board)
  (def move (get-player-move))
  (def next-board (place-marker board move "X"))
  (if-not (= move "q")
    (recur next-board)))

(defn -main [& args]
  (play build-board))
