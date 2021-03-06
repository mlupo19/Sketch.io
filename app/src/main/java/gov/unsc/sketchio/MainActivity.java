package gov.unsc.sketchio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private InetAddress ipAddress;
    private EditText nameView;
    private EditText ipAddressView;
    private String name = "Banana";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ipAddress = InetAddress.getByAddress(new byte[]{0,0,0,0});
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        nameView = findViewById(R.id.editTextName);
        nameView.setOnFocusChangeListener((l, k) -> {
            if (k)
                nameView.setText("");
        });
        ipAddressView = findViewById(R.id.editTextIP);
    }

    public void playClick(View v) {
        try {
            ipAddress = InetAddress.getByName(ipAddressView.getText().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (!nameView.getText().toString().equals(""))
            name = nameView.getText().toString();
        Intent i = new Intent(this, PlayActivity.class);
        i.putExtra("ipAddress", ipAddress);
        i.putExtra("name", name);

        startActivity(i);
    }

}
