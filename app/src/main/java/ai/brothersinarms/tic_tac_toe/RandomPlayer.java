package ai.brothersinarms.tic_tac_toe;

class RandomPlayer extends AI {

    RandomPlayer(Game game) {
        super(game);
    }

    @Override
    public int GetMove(int player) {
        return game.getVacant();
    }
}
