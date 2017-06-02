(ns tictactoe.core
  (:gen-class))

(def winning-positions [[0 1 2] [3 4 5] [6 7 8] [0 4 8] [2 4 6] [0 3 6] [1 4 7] [2 5 8]])
(def board [["O" " " "O"] ["X" "X" "O"] [" " " " " "]])

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

(get-cell board 2 2)

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

(defn opponent
  [player]
  (if (= player "O")
    "X"
    "O"))

(opponent "X")

 ;; (defn mini-max
 ;;  [board player-value]
 ;;  (if (not= (has-empty-cells? board) nil)
 ;;    (map #(mini-max % (opponent player-value)) (for [x (range 3)]
 ;;           (for [y (range 3)](println (mark-cell board x y player-value)))))))

(defn draw?
  [board]
  (and (= (has-empty-cells? board) nil) (or  (= (win? (flatten board) "X") nil)) (= (win? (flatten board) "O") nil)))

(draw? board)

(defn game-over?
  [board]
  (or (win? (flatten board) "X") (win? (flatten board) "O") (= (has-empty-cells? board) nil)))

(game-over? board)

(defn calculate-score
  [board player-value depth]
  (if (draw? board)
    0
    (let [isWinner (win? (flatten board) player-value)]
      (if (= isWinner true)
        (- 10 depth)
        (- depth 10)))))

(calculate-score board "O" 2)

(quot 7 3)
(rem 7 3)

(def board-2 [["O" " " "O"] ["X" "X" "O"] [" " " " "X"]])


(defn max-scores
  [board player-value player-chance depth]
  (if (game-over? board)
    (calculate-score board player-value depth)
    (let [spots (available-spots board)]
      (apply max (map #(max-scores (mark-cell board (quot % 3) (rem % 3) player-chance) player-value (opponent player-chance) (inc depth)) spots)))))

(max-scores board-2 "X" "O" 0)

(defn mini-max
  [board player-value]
  (if (= (has-empty-cells? board) true)
    (let [spots (available-spots board)
          scores (map #(max-scores (mark-cell board (quot % 3) (rem % 3) player-value) player-value (opponent player-value) 0) spots)]
      1))

(mini-max board-2 "X")

(defn available-spots
   [board]
  (map first (filter #(= " " (second %)) (map-indexed vector (flatten board)))))

(available-spots board)

;(min-max board 0 true)
;(move board)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
