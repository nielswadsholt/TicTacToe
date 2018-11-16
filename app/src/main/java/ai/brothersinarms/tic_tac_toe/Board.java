package ai.brothersinarms.tic_tac_toe;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

class Board {

    private int dim;
    private int[][] board;
    private Set<Integer> vacant;

    Board(int dim) {
        this.dim = dim;
        this.board = new int[dim][dim];

        vacant = new TreeSet<>();
        for (int i = 0; i < Math.pow(dim, 2); i++) {
            vacant.add(i);
        }
    }

    int getDim() {
        return dim;
    }

    public boolean move(int fieldIdx, @Game.FieldValue int player) {
        int row = fieldIdx / dim;
        int col = fieldIdx % dim;

        if (board[row][col] == 0) {
            board[row][col] = player;
            vacant.remove(fieldIdx);

            return true;
        }

        return false;
    }

    int getRandomEmpty() {
        int rnd = new Random().nextInt(dim * dim);
        Integer v = ((TreeSet<Integer>)vacant).ceiling(rnd);

        if (v == null) {
            v = ((TreeSet<Integer>)vacant).floor(rnd);
        }

        return v == null ? -1 : v;
    }

    boolean isFull() {
        return vacant.isEmpty();
    }

    Set<Integer> getVacantClone() { return new TreeSet<>(vacant); }
}
