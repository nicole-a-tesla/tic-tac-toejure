(ns tic-tac-toejure.core)

(def get-player-move-message "Select your next move...")

(def space ":___")

(def board
  (get (vector (repeat 9 space)) 0)
)

(defn print-it [message]
  (println (apply str message)))

(defn prompt [message]
  (print-it message)
  (read-line))

(defn print-board
  ([]
  (print-board (partition 3 board)))
  ([rows]
    (print-it (first rows))
    (if-not (empty? (rest rows))
      (print-board (rest rows)))
    )
)

(defn get-player-move []
  (prompt get-player-move-message)
)

(defn play []
  (print-board)
  (def move (get-player-move))
  (if-not (= move "q")
    (recur)))

(defn -main [& args]
  (play))
