package ai.brothersinarms.tic_tac_toe;

import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.Hashtable;

class ScoreBoard {
    static final int X = Game.X;
    static final int O = Game.O;
    static final int EMPTY = Game.EMPTY;
    static final int BLOCKED = 2;

    @IntDef({X, O, EMPTY, BLOCKED})
    @interface LineState{}

    class LineScore {
        @LineState int state;
        int score;
    }

    private Hashtable<String, LineScore> board;
    private int dim;

    ScoreBoard(int dim) {
        this.dim = dim;
        board = new Hashtable<>();

        for (int i = 0; i < this.dim; i++) {
            board.put("R" + i, new LineScore());
            board.put("C" + i, new LineScore());
        }
        board.put("D1", new LineScore());
        board.put("D2", new LineScore());
    }

    boolean Update(int fieldIdx, int state) {
        boolean won = false;

        int row = fieldIdx / dim;
        int col = fieldIdx % dim;

        ArrayList<String> lineKeys = new ArrayList<>();
        lineKeys.add("R" + row);
        lineKeys.add("C" + col);

        if (row == col) { lineKeys.add("D1"); }
        if (row + col + 1 == dim) { lineKeys.add("D2"); }

        for (String key : lineKeys) {
            LineScore lineScore = board.get(key);
            if (lineScore.state == EMPTY) {
                lineScore.state = state;
                lineScore.score++;
            }
            else if (lineScore.state == state) {
                lineScore.score++;
            }
            else {
                lineScore.state = BLOCKED;
            }

            if (lineScore.score >= dim) { won = true; }
        }

        return won;
    }
}
