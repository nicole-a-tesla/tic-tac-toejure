(ns tic-tac-toejure.ui)

(def get-player-move-message "Select your next move...")

(def empty-space-view ":___")


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
