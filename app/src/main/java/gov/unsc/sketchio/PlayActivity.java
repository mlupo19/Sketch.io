package gov.unsc.sketchio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PlayActivity extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        name = getIntent().getExtras().getString("name");

        connect();
    }

    private void connect() {

    }
}
