package ai.brothersinarms.tic_tac_toe;

import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

class ScoreBoard {
    static final int X = Game.X;
    static final int O = Game.O;
    static final int EMPTY = Game.EMPTY;
    static final int BLOCKED = 2;

    @IntDef({X, O, EMPTY, BLOCKED})
    @interface LineState{}

    class LineStatus {
        @LineState int state;
        int count;
    }

    private Hashtable<String, LineStatus> board;
    private int dim;

    ScoreBoard(int dim) {
        this.dim = dim;
        board = new Hashtable<>();

        for (int i = 0; i < this.dim; i++) {
            board.put("R" + i, new LineStatus());
            board.put("C" + i, new LineStatus());
        }
        board.put("D1", new LineStatus());
        board.put("D2", new LineStatus());
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
            LineStatus lineStatus = board.get(key);
            if (lineStatus.state == EMPTY) {
                lineStatus.state = state;
                lineStatus.count++;
            }
            else if (lineStatus.state == state) {
                lineStatus.count++;
            }
            else {
                lineStatus.state = BLOCKED;
            }

            if (lineStatus.count >= dim) { won = true; }
        }

        return won;
    }

    int GetScore() {
        int score = 0;

        for (Map.Entry<String, LineStatus> entry : board.entrySet()) {
            LineStatus ls = entry.getValue();

            if (!(ls.state == EMPTY || ls.state == BLOCKED)) {
                if (ls.count == dim) {
                    return Integer.MAX_VALUE * ls.state;
                }
                else {
                    score += Math.pow(4, ls.count) * ls.state;
                }
            }
        }

        return score;
    }
}
