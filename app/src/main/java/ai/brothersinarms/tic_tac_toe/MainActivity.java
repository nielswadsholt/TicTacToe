package ai.brothersinarms.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int dim = 3;
    private Game game;
    private Game.Player player = Game.Player.X;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG && (dim < 3 || dim > 5)) {
            throw new AssertionError("Dimension must be between 3 and 5");
        }

        game = new Game(dim);

        GridLayout grid = findViewById(R.id.board);
        grid.setOnClickListener(this);
        grid.setColumnCount(dim);

        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                ImageView field = new ImageView(this);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row, 1f),
                        GridLayout.spec(col, 1f));
                params.height = 0;
                params.width = 0;
                field.setLayoutParams(params);

                field.setScaleType(ImageView.ScaleType.FIT_XY);
                field.setImageResource(R.drawable.blank);
                field.setId(row * dim + col);
                grid.addView(field);
            }
        }

        int childCount = grid.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView field = (ImageView) grid.getChildAt(i);
            field.setOnClickListener(this);
        }

//        Log.d("opponent", player.getOpponent().name());
    }

    @Override
    public void onClick(View v) {
        ImageView field = (ImageView) v;
        int fieldId = field.getId();

        if (game.Move(player, fieldId)) {
            field.setImageResource(player.imgId);
            Log.d("field", Integer.toString(fieldId));
        }
        else {
            Log.d("field", "This field is already taken");
        }

        if (game.Won(fieldId)) {
            Log.d("won", "WINNER: HUMAN PLAYER!");
        }
        else {

            // Simple random computer player
            int opponentMoveId = game.getVacant();
            if (opponentMoveId >= 0) {
                game.Move(player.getOpponent(), opponentMoveId);
                ImageView opponentField = findViewById(opponentMoveId);
                opponentField.setImageResource(player.getOpponent().imgId);
            }

            if (opponentMoveId >= 0 && game.Won(opponentMoveId)) {
                Log.d("won", "WINNER: AI PLAYER!");
            }
        }
    }
}
