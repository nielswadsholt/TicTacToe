package ai.brothersinarms.tic_tac_toe;

import android.util.Log;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

        vacant = new HashSet<>();
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
        int size = vacant.size();

        if (size > 0) {
            int item = new Random().nextInt(size);
            int i = 0;

            for(Integer idx : vacant)
            {
                if (i == item)
                    return idx;
                i++;
            }
        }

        return -1;
    }

    boolean CheckWin(int fieldIdx) {
        int row = fieldIdx / dim;
        int col = fieldIdx % dim;
        boolean rowWin = true;
        boolean colWin = true;
        boolean diagonal1Win = true;
        boolean diagonal2Win = true;

        for (int i = 0; i < dim; i++) {
            if (board[row][i] != board[row][col]) rowWin = false;
            if (board[i][col] != board[row][col]) colWin = false;
            if (board[i][i] != board[row][col]) diagonal1Win = false;
            if (board[i][dim-1-i] != board[row][col]) diagonal2Win = false;
        }

        won = rowWin || colWin || diagonal1Win || diagonal2Win;
        return won;
    }
}
