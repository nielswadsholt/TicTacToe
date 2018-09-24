package ai.brothersinarms.tic_tac_toe;

import android.util.Log;
import java.util.HashSet;
import java.util.Set;

public class Game {
    enum Player {
        O (1),
        X (2);

        int id;
        int imgId;

        Player(int id) {
            this.id = id;

            if (id == 1) {
                this.imgId = R.drawable.player_o;
            }
            else {
                this.imgId = R.drawable.player_x;
            }
        }

        Player getOpponent() {
            if (this.id == 1) return Player.X;
            else return Player.O;
        }
    }

    private int dim;
    private int[][] board;
    private Set<Integer> vacant;

    Game(int dim){
        this.dim = dim;
        this.board = new int[dim][dim];
        Log.d("board", Integer.toString(this.board[0][0]));

        vacant = new HashSet<>();

        for (int i = 0; i < Math.pow(dim, 2); i++) {
            vacant.add(i);
        }
    }

    public boolean Move(Player player, int fieldIdx) {
        int row = fieldIdx / dim;
        int col = fieldIdx % dim;

        if (board[row][col] == 0){
            board[row][col] = player.id;
            vacant.remove(fieldIdx);
            Log.d("vacant", Integer.toString(vacant.size()));

            return true;
        }

        return false;
    }

//    public boolean Won(int fieldIdx) {
//        int row = fieldIdx / dim;
//        int col = fieldIdx % dim;
//
//        for (int i = 0; i < dim; i++) {
//            if (board[row][i])
//        }
//
//        return true;
//    }
}
