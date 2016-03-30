(ns tic-tac-toejure.core)

(def space ":___")

(defn print-it [message]
  (println message))

(defn prompt [message]
  (print-it message)
  (read-line))

(defn print-board []
  (print-it space))

(defn -main [& args]
  (print-board))
