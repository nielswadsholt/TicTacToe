package ai.brothersinarms.tic_tac_toe;

class MonteCarlo extends AI {
    MonteCarlo(Game game) { super(game); }

    // TODO: Implement MonteCarlo
    @Override
    public int GetMove(Game.Player player) {
        return game.getVacant();
    }
}
