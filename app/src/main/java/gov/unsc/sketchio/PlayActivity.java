package gov.unsc.sketchio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class PlayActivity extends AppCompatActivity {

    private String name;
    private CanvasView cv;
    private TextView word;
    private TextView chat;
    private EditText editChat;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private volatile boolean connected;

    private Thread networkThread;
    private boolean toSend = false;
    private volatile String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        cv = findViewById(R.id.canvasView);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        name = getIntent().getExtras().getString("name");
        word = findViewById(R.id.wordTextView);
        chat = findViewById(R.id.textViewChat);
        editChat = findViewById(R.id.editTextChat);
        editChat.setOnKeyListener((v, kc, e) -> {
            if (e.getAction() == KeyEvent.ACTION_DOWN && kc == KeyEvent.KEYCODE_ENTER) {
                String message = editChat.getEditableText().toString();
                editChat.setText("");

                chat(message);

                return true;
            }
            return false;
        });
        networkThread = new Thread(() -> {
            connect((InetAddress) getIntent().getSerializableExtra("ipAddress"));
            while (connected) {
                if (toSend) {
                    try {
                        dos.writeUTF(message);
                        dos.flush();
                        dos.flush();
                        System.out.println("Sent message! " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    toSend = false;
                }
            }
        }, "NetworkThread");
        networkThread.start();

        Thread readThread = new Thread(() -> {
            while (!connected);
            while (connected) {
                try {
                    printToChat(dis.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Read thread done");
        }, "ReadThread");
        readThread.start();
    }

    private void chat(String message) {
        this.message = message;
        toSend = true;
    }


    private void connect(InetAddress server) {
        try {
            socket = new Socket(server.getHostAddress(), Util.SERVER_PORT);

            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(name);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        connected = true;
    }

    private void printToChat(String s) {
        runOnUiThread(() -> chat.append(s + "\n"));
    }
}
