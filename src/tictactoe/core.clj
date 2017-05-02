(ns tictactoe.core
  (:gen-class))

(def board [[" " " " " "] [" " " " " "] [" " " " " "]])

(defn print-row
  [row]
  (str  "| " (clojure.string/join " | " row) " |"))

(defn print-board
  [board]
  (clojure.string/join "/n" (for [row board] (print-row row))))

(print-board board)

(defn get-square
  [board x y]
  {:pre [(<= x 2)
         (<= y 2)]}
  ((board x) y))

(get-square board 0 0)

(defn is-empty
  [board x y]
  (if (= (get-square board x y) " ")
    true false))

(is-empty board 0 0)

(defn mark-square
  [board x y v]
  {:pre [(<= x 2)
         (<= y 2)]}
  (when (is-empty board x y) (assoc-in board [x y] v)))

(mark-square board 0 0 "0")

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
