package mm.events;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private static final int HELLO_ID = 1;
    Button notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerNotifyButton();

    }

    private void registerNotifyButton() {
        notify = (Button) findViewById(R.id.notifyButton);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
            }
        });
    }

    public void createNotification(){
        // this
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.drawable.caution;
        CharSequence tickerText = "Hello"; // ticker-text
        long when = System.currentTimeMillis();
        Context context = getApplicationContext();
        CharSequence contentTitle = "Hello";
        CharSequence contentText = "Hello";
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification(icon, tickerText, when);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

// and this

        mNotificationManager.notify(HELLO_ID, notification);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
