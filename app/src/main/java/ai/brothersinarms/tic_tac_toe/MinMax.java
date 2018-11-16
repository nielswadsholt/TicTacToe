package ai.brothersinarms.tic_tac_toe;

import java.util.Set;
import java.util.TreeSet;

class MinMax extends AI {

    private int maxDepth = 3;
    private Set<Integer> vacant = new TreeSet<>();
    private Set<Integer> visited = new TreeSet<>();
    private ScoreBoard scoreBoard;

    MinMax(Game game)
    {
        super(game);

        if (game.getBoard().getDim() > 10) {
            maxDepth = 1;
        }
        else if (game.getBoard().getDim() > 3) {
            maxDepth = 2;
        }
    }

    @Override
    int GetMove(int player) {
        vacant = game.getBoard().getVacantClone();
        scoreBoard = game.GetScoreBoardClone();

        int[] result = MM(maxDepth, player);

        return result[0];
    }

    private int[] MM(int searchDepth, int player) {
        int bestScore = -IntMax() * player;
        int bestMove = -1;

        if (searchDepth == 0 || visited.size() == vacant.size()) {
            bestScore = scoreBoard.GetScore();
        }
        else {
            for (int move : vacant) {
                if (!visited.contains(move)) {
                    int score;

                    visited.add(move);
                    boolean won = scoreBoard.Update(move, player);

                    if (won)
                    {
                        score = IntMax() * player;
                    }
                    else {
                        score = MM(searchDepth - 1, -1 * player)[1];
                    }

                    visited.remove(move);
                    scoreBoard.Revert(move, player);

                    if (score * player > bestScore * player) {
                        bestScore = score;
                        bestMove = move;

                        if (bestScore * player == IntMax() * player) {
                            break;
                        }
                    }
                }
            }
        }

        return new int[] {bestMove, bestScore};
    }

    private int IntMax() {
        return 2000000000;
    }
}
