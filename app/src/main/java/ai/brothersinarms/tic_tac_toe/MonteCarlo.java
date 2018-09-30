package ai.brothersinarms.tic_tac_toe;

class MonteCarlo extends AI {
    MonteCarlo(Game game) { super(game); }

    // TODO: Implement MonteCarlo
    @Override
    public int GetMove(int player) {
        return game.GetRandomEmpty();
    }
}
