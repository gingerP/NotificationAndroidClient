package vinni.com.notification;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vinni.com.notification.handlers.Settings;
import vinni.com.notification.handlers.Calling;

public class Notification extends AppCompatActivity {

    private static final String TAG = "Notification";
    private static final int COLOR_SUCCESS = Color.parseColor("#5FBA7D");
    private static final int COLOR_FAILURE = Color.parseColor("#E64320");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnSubmit = (Button) findViewById(R.id.SUBMIT);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.ADDRESS);
                String inputText = input.getText().toString();
                Log.d(TAG, "Saving address: " + inputText);
                Settings.setIpAddress(inputText);
            }
        });

        Button btnCheckConnection = (Button) findViewById(R.id.CHECK_CONNECTION);
        btnCheckConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.ADDRESS);
                TextView status = (TextView) findViewById(R.id.STATUS);
                String inputText = input.getText().toString();
                status.setTextColor(Color.GRAY);
                status.setText("Request to " + inputText);
                Log.d(TAG, "Check connection for: " + inputText);
                Calling.sendNotification("Test");
                status.setText(Calling.checkConnection(inputText));
                status.setTextColor(COLOR_SUCCESS);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            // When the user center presses, let them pick a contact.
            //startActivityForResult();
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks
        // on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
