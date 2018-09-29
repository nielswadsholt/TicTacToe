package ai.brothersinarms.tic_tac_toe;

abstract class AI {
    Game game;

    AI(Game game) { this.game = game; }

    abstract int GetMove(int player);
}
