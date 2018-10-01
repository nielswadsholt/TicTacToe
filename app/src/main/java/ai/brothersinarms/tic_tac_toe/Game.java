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
    private AI ai = new MinMax(this);

    Game(int dim){
        this.dim = dim;
        this.board = new int[dim][dim];
        this.scoreBoard = new ScoreBoard(dim);

        vacant = new TreeSet<>();
        for (int i = 0; i < Math.pow(dim, 2); i++) {
            vacant.add(i);
        }
    }

    boolean Move(int fieldIdx, @FieldValue int player) {
        int row = fieldIdx / dim;
        int col = fieldIdx % dim;

        if (board[row][col] == 0){
            board[row][col] = player;
            vacant.remove(fieldIdx);
            won = scoreBoard.Update(fieldIdx, player);
            Log.d("field", "idx: " + fieldIdx + " val: " + player);
            Log.d("field", "score: " + scoreBoard.GetScore());

            return true;
        }

        return false;
    }

    int GetDim() { return dim; }

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

    ScoreBoard GetScoreBoardClone() {
        return  scoreBoard.Clone();
    }

    Set<Integer> GetVacantClone() { return new TreeSet<>(vacant); }

    void SwitchAI(AI ai) {
        ai.game = this;
        this.ai = ai;
    }
}
