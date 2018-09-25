package ai.brothersinarms.tic_tac_toe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private static int dim = 3;
    private Game game;
    private Game.Player player = Game.Player.X;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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
                field.setImageResource(R.drawable.ttt_blank);
                field.setId(row * dim + col);
                grid.addView(field);
            }
        }

        for (int i = 0; i < grid.getChildCount(); i++) {
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

            if (game.CheckWin(fieldId)) {
                DeclareWinner(getString(R.string.win_human));
            }
            else {
                // Simple random computer player
                int opponentMoveId = game.getVacant();
                if (opponentMoveId >= 0) {
                    game.Move(player.getOpponent(), opponentMoveId);
                    ImageView opponentField = findViewById(opponentMoveId);
                    opponentField.setImageResource(player.getOpponent().imgId);

                    if (game.CheckWin(opponentMoveId)) {
                        DeclareWinner(getString(R.string.win_computer));
                    }
                }
            }
        }
        else {
            Log.d("field", "This field is already taken");
        }

        if (!game.won && game.getVacant() == -1) {
            DeclareWinner("It's a draw!");
        }
    }

    public void DeclareWinner(CharSequence message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NewGame();
            }
        });
        alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                NewGame();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.setCancelable(false);
//        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void NewGame() {
        this.recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                NewGame();
                return true;
            case R.id.board_3x3:
                dim = 3;
                NewGame();
                return true;
            case R.id.board_4x4:
                dim = 4;
                NewGame();
                return true;
            case R.id.board_5x5:
                dim = 5;
                NewGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}