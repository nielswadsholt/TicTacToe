package ai.brothersinarms.tic_tac_toe;

import android.support.annotation.IntDef;
import android.util.Log;

class Game {

    static final int X = -1;
    static final int O = 1;
    static final int EMPTY = 0;

    @IntDef({X, O, EMPTY})
    @interface FieldValue {}

    private Board board;
    private ScoreBoard scoreBoard;
    boolean won = false;
    private AI ai;

    Game(int dim){
        this.board = new Board(dim);
        this.scoreBoard = new ScoreBoard(dim);
        ai = new MinMax(this);
    }

    public Board getBoard() {
        return board;
    }

    boolean Move(int fieldIdx, @FieldValue int player) {

        if (board.move(fieldIdx, player)) {
            won = scoreBoard.Update(fieldIdx, player);
            Log.d("field", "idx: " + fieldIdx + " val: " + player);
            Log.d("field", "score: " + scoreBoard.GetScore());

            return true;
        }

        return false;
    }

    int GetMove(@FieldValue int fieldValue)
    {
        return ai.GetMove(fieldValue);
    }

    ScoreBoard GetScoreBoardClone() {
        return scoreBoard.Clone();
    }

    void SwitchAI(AI ai) {
        ai.game = this;
        this.ai = ai;
    }
}
