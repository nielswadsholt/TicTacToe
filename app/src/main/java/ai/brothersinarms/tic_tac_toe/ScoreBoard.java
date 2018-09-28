package ai.brothersinarms.tic_tac_toe;

import android.util.Log;
import java.util.ArrayList;
import java.util.Hashtable;

class ScoreBoard {
    enum LineState { X, O, EMPTY, BLOCKED }

    class LineScore {
        LineState state;
        int score;

        LineScore() {
            this.state = LineState.EMPTY;
        }
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
            LineScore lineScore = board.get(key);
            Log.d("scoreboard", lineScore.state.name() + ": " + lineScore.score);
            if (lineScore.state == LineState.EMPTY) {
                lineScore.state = state;
                lineScore.score++;
            }
            else if (lineScore.state == state) {
                lineScore.score++;
            }
            else {
                lineScore.state = LineState.BLOCKED;
            }

            if (lineScore.score >= dim) { won = true; }
        }

        return won;
    }
}
