(ns tictactoe.core
  (:gen-class))

(def winning-positions [[0 1 2] [3 4 5] [6 7 8] [0 4 8] [2 4 6] [0 3 6] [1 4 7] [2 5 8]])
(def board [[" " " " " "] [" " " " " "] [" " " " " "]])

(defn print-row
  [row]
  (str  "| " (clojure.string/join " | " row) " |"))

(defn print-board
  [board]
  (clojure.string/join "\n" (for [row board] (print-row row))))

(print-board board)

(defn get-cell
  [board x y]
  {:pre [(<= x 2)
         (<= y 2)]}
  ((board x) y))

(get-cell board 0 0)

(defn is-empty
  [board x y]
  (if (= (get-cell board x y) " ")
    true false))

(is-empty board 0 0)

(defn mark-cell
  [board x y v]
  {:pre [(<= x 2)
         (<= y 2)]}
  (when (is-empty board x y) (assoc-in board [x y] v)))

(mark-cell board 0 0 "O")

(defn has-empty-cells?
  [board]
  (some #(= " " %) (flatten board)))

(has-empty-cells? board)

(defn win?
  [board v]
  (some #(= v (nth board (get % 0)) (nth board (get % 1)) (nth board (get % 2))) winning-positions))

(win? (flatten board) "X")

(defn move
  [board]
  (if (has-empty-cells? board)
    (let [x (read-line)
          y (read-line)
          v (read-line)
          temp-board (mark-cell board (Integer/parseInt x) (Integer/parseInt y) v)]
      (if (win? (flatten temp-board) v)
        (println (str temp-board "\n" v " wins"))
        ((println temp-board) (move temp-board))))
    board))

;(move board)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
