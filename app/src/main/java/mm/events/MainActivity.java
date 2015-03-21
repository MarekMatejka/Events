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
    Button notify, not2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerNotifyButton();
        registerNotifyButton2();

    }

    private void registerNotifyButton() {
        not2 = (Button) findViewById(R.id.notifyButton);
        not2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
//                createNotification2();
            }
        });
    }

    private void registerNotifyButton2() {
        notify = (Button) findViewById(R.id.notbutton2);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createNotification();
                createNotification2();
            }
        });
    }


    public void createNotification() {
        // this
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.drawable.fb_icon;
        CharSequence tickerText = "Event Invite"; // ticker-text
        long when = System.currentTimeMillis();
        Context context = getApplicationContext();
        CharSequence contentTitle = "fb"; // title
        CharSequence contentText = "Event invite to fb hackathon";
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification(icon, tickerText, when);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

// and this

        mNotificationManager.notify(HELLO_ID, notification);
    }

    // another method
    public void createNotification2(/*View view*/) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Event invite") //title
                .setContentText("Date")
                .setSmallIcon(R.drawable.fb_icon)
                .setPriority(1)
                .setContentIntent(pIntent)
                .addAction(0, "Accept", pIntent)
                .addAction(0, "Maybe", pIntent)
//              .addAction(R.drawable.fb_icon, "More", pIntent)
                .addAction(0, "Decline", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(1, noti);

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
