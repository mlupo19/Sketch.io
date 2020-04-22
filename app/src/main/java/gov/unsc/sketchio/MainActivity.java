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
        String[] ipParts = ipAddressView.getText().toString().split("\\.");
        if (ipParts.length == 4) {
            byte[] bytes = new byte[4];
            for (int i = 0; i < ipParts.length; i++) {
                bytes[i] = Byte.parseByte(ipParts[i]);
            }
            try {
                ipAddress = InetAddress.getByAddress(bytes);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        if (!nameView.getText().toString().equals(""))
            name = nameView.getText().toString();
        Intent i = new Intent(this, PlayActivity.class);
        i.putExtra("ipAddress", ipAddress);
        i.putExtra("name", name);

        startActivity(i);
    }

}
