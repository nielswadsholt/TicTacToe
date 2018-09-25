package ai.brothersinarms.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast toast = Toast.makeText(this, R.string.welcome, Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}
