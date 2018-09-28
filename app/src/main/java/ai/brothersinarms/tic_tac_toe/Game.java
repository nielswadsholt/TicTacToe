package ai.brothersinarms.tic_tac_toe;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import ai.brothersinarms.tic_tac_toe.ScoreBoard.LineState;

class Game {
    enum Player {
        O (1),
        X (2);

        int id;
        int imgId;

        Player(int id) {
            this.id = id;

            if (id == 1) {
                this.imgId = R.drawable.ttt_o;
            }
            else {
                this.imgId = R.drawable.ttt_x;
            }
        }

        Player getOpponent() {
            if (this == Player.O)
                return Player.X;
            else
                return Player.O;
        }
    }

    private int dim;
    private int[][] board;
    private ScoreBoard scoreBoard;
    private Set<Integer> vacant;
    boolean won = false;
    AI ai = new RandomPlayer(this);

    Game(int dim){
        this.dim = dim;
        this.board = new int[dim][dim];
        this.scoreBoard = new ScoreBoard(dim);

        vacant = new TreeSet<>();
        for (int i = 0; i < Math.pow(dim, 2); i++) {
            vacant.add(i);
        }
    }

    boolean Move(Player player, int fieldIdx) {
        int row = fieldIdx / dim;
        int col = fieldIdx % dim;

        if (board[row][col] == 0){
            board[row][col] = player.id;
            vacant.remove(fieldIdx);

            LineState lineState = player == Player.X ? LineState.X : LineState.O;
            won = scoreBoard.Update(fieldIdx, lineState);

            return true;
        }

        return false;
    }

    int getVacant() {
        int rnd = new Random().nextInt(dim * dim);
        Integer v = ((TreeSet<Integer>)vacant).ceiling(rnd);

        if (v == null) {
            v = ((TreeSet<Integer>)vacant).floor(rnd);
        }

        return v == null ? -1 : v;
    }
}
