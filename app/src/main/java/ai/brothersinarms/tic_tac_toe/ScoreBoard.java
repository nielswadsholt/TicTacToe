package ai.brothersinarms.tic_tac_toe;

import android.util.Log;

import java.util.ArrayList;
import java.util.Hashtable;

class ScoreBoard {
    enum LineState { X, O, EMPTY, BLOCKED }

    class Line {
        LineState state;
        int count;

        Line() {
            this.state = LineState.EMPTY;
        }
    }

    private Hashtable<String, Line> board;
    private int dim;

    ScoreBoard(int dim) {
        this.dim = dim;
        board = new Hashtable<>();

        for (int i = 0; i < this.dim; i++) {
            board.put("R" + i, new Line());
            board.put("C" + i, new Line());
        }
        board.put("D1", new Line());
        board.put("D2", new Line());
    }

    boolean Update(int fieldIdx, LineState state) {
        boolean won = false;

        int row = fieldIdx / dim;
        int col = fieldIdx % dim;

        ArrayList<String> lineKeys = new ArrayList<>();
        lineKeys.add("R" + row);
        lineKeys.add("C" + col);

        if (row == col) { lineKeys.add("D1"); }
        if (row + col + 1 == dim) { lineKeys.add("D2"); }

        for (String key : lineKeys) {
            Line line = board.get(key);
            Log.d("scoreboard", line.state.name() + ": " + line.count);
            if (line.state == LineState.EMPTY) {
                line.state = state;
                line.count++;
            }
            else if (line.state == state) {
                line.count++;
            }
            else {
                line.state = LineState.BLOCKED;
            }

            if (line.count >= dim) {
                won = true;
            }

        }

        return won;
    }
}
