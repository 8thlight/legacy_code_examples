(ns game.core)

  (def board (atom ["0" "1" "2" "3" "4" "5" "6" "7" "8"]))

  (defn game-over []
   (or (= (get @board 0) (get @board 1) (get @board 2))
    (= (get @board 3) (get @board 4) (get @board 5))
    (= (get @board 6) (get @board 7) (get @board 8))
    (= (get @board 0) (get @board 3) (get @board 6))
    (= (get @board 1) (get @board 4) (get @board 7))
    (= (get @board 2) (get @board 5) (get @board 8))
    (= (get @board 0) (get @board 4) (get @board 8))
    (= (get @board 2) (get @board 4) (get @board 6))))

  (defn tie []
   (and (not (= (get @board 0) "0"))
    (not (= (get @board 1) "1"))
    (not (= (get @board 2) "2"))
    (not (= (get @board 3) "3"))
    (not (= (get @board 4) "4"))
    (not (= (get @board 5) "5"))
    (not (= (get @board 6) "6"))
    (not (= (get @board 7) "7"))
    (not (= (get @board 8) "8"))))

  (defn print-board []
   (println (str " " (get @board 0) " | " (get @board 1) " | " (get @board 2) "\n===+===+===\n " (get @board 3) " | " (get @board 4) " | " (get @board 5) "\n===+===+===\n " (get @board 6) " | " (get @board 7) " | " (get @board 8) "\n")))

(def best-move (atom nil))

  (defn get-computer-move []
   (if (= "4" (get @board 4))
    (reset! board (assoc @board 4 "O"))
    (do
     (let [container []
      available-spaces (vec (filter identity (for [s @board]
            (do
             (if (and (not (= s "X")) (not (= s "O")))
              (conj container s))))))
      results (vec (for [as available-spaces]
          (do
           (reset! board (assoc @board (Integer. (first as)) "O"))
           (if (game-over)
            (do
             (reset! best-move (first as))
             (reset! board (assoc @board (Integer. (first as)) (Integer. (first as)))))
            (do
             (reset! board (assoc @board (Integer. (first as)) "X"))
             (if (game-over)
              (do
               (reset! best-move (Integer. (first as)))
               (reset! board (assoc @board (Integer. (first as)) (Integer. (first as)))))
              (reset! board (assoc @board (Integer. (first as)) (Integer. (first as))))))))))]
      (if @best-move
       (reset! board (assoc @board @best-move "O"))
       (reset! board (assoc @board (Integer. (first (get available-spaces (rand-int (count available-spaces))))) "O")))
      results))))

(def spot (atom nil))

  (defn get-human-spot []
   (reset! spot nil)
   (while (not @spot)
    (do
     (reset! spot (Integer. (read-line)))
     (if (and (not (= (get board @spot) "X")) (not (= (get board @spot) "O")))
      (reset! board (assoc @board @spot "X"))
      (reset! spot nil)))))

  (defn game []
   (if (or (not (game-over)) (not (tie)))
    (do
     (get-human-spot)
     (print-board)
     (if (or (not (game-over)) (not (tie)))
      (do
       (get-computer-move)
       (print-board)
       (game))
      (do
       (println "Game over")
       (System/exit 0)))
     (do
      (println "Game over")
      (System/exit 0)))))

  (defn start-game []
   (print-board)
   (println "Enter [0-8]:")
   (game))

  (defn -main []
   (start-game))
