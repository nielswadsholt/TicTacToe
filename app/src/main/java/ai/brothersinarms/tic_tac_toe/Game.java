package ai.brothersinarms.tic_tac_toe;

import android.support.annotation.IntDef;
import android.util.Log;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

class Game {

    static final int X = -1;
    static final int O = 1;
    static final int EMPTY = 0;

    @IntDef({X, O, EMPTY})
    @interface FieldValue {}

    private int dim;
    private int[][] board;
    private ScoreBoard scoreBoard;
    private Set<Integer> vacant;
    boolean won = false;
    private AI ai = new RandomPlayer(this);

    Game(int dim){
        this.dim = dim;
        this.board = new int[dim][dim];
        this.scoreBoard = new ScoreBoard(dim);

        vacant = new TreeSet<>();
        for (int i = 0; i < Math.pow(dim, 2); i++) {
            vacant.add(i);
        }
    }

    boolean Move(int fieldIdx, @FieldValue int fieldValue) {
        int row = fieldIdx / dim;
        int col = fieldIdx % dim;

        if (board[row][col] == 0){
            board[row][col] = fieldValue;
            vacant.remove(fieldIdx);
            won = scoreBoard.Update(fieldIdx, fieldValue);
//            Log.d("field", "idx: " + fieldIdx + " val: " + fieldValue);
            Log.d("field", "score: " + scoreBoard.GetScore());

            return true;
        }

        return false;
    }

    int GetRandomEmpty() {
        int rnd = new Random().nextInt(dim * dim);
        Integer v = ((TreeSet<Integer>)vacant).ceiling(rnd);

        if (v == null) {
            v = ((TreeSet<Integer>)vacant).floor(rnd);
        }

        return v == null ? -1 : v;
    }

    int GetMove(@FieldValue int fieldValue)
    {
        return ai.GetMove(fieldValue);
    }

    boolean IsFull() {
        return vacant.isEmpty();
    }
}
