package mm.events;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import mm.events.backend.FacebookAPI;
import mm.events.domain.FBEvent;


public class MainActivity extends ActionBarActivity {

    Button notify, not2, calButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerNotifyButton2();
    }

    private void registerNotifyButton2() {
        notify = (Button) findViewById(R.id.notbutton2);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEvent();
            }
        });
    }

    // another method
    public void createNotification(FBEvent event) {
        //going
        Intent intentGoing = new Intent(this, NotifyActivityHandler.class);
        intentGoing.putExtra("action", "going");

        //maybe
        Intent intentMaybe = new Intent(this, NotifyActivityHandler.class);
        intentMaybe.putExtra("action", "maybe");
        //reject
        Intent intentReject = new Intent(this, NotifyActivityHandler.class);
        intentReject.putExtra("action", "reject");

       /* // 1 intent:
        Intent i1 = new Intent(this, NotifyActivityHandler.class);
        //i1.putExtra("action", "going");
        PendingIntent p1 = PendingIntent.getActivity(this, 0, i1, 0);*/

        // open activity
        Intent intentContent = new Intent(this, FBListEventListActivity.class);
        intentContent.putExtra("action", "content");
        PendingIntent pIntentGoing = PendingIntent.getActivity(this, 0, intentGoing, 0);
        PendingIntent pIntentReject = PendingIntent.getActivity(this, 1, intentReject, 0);
        PendingIntent pIntentMaybe = PendingIntent.getActivity(this, 2, intentMaybe, 0);
        PendingIntent pIntentContent = PendingIntent.getActivity(this, 3, intentContent, 0);

        // Build notification
        // Actions are just fake
        Notification notification = new Notification.Builder(this)
                .setContentTitle(event.getName())
                .setContentText(event.getFormattedStartDate() + " @ " + event.getLocation())
                .setSmallIcon(R.drawable.fb_icon)
                .setPriority(1)
                .setContentIntent(pIntentContent)
                .addAction(0, "Accept", pIntentGoing)
                .addAction(0, "Maybe", pIntentMaybe)
                .addAction(0, "Decline", pIntentReject)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // hide the notification after it is selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(1, notification);
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

    public void showEvents(View v) {
        Intent intent = new Intent(this, FBListEventListActivity.class);
        startActivity(intent);
        onStop();
    }

    public void newEvent() {
        CountDownTimer cdt = new CountDownTimer(5, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                FacebookAPI api = FacebookAPI.getInstance(getApplicationContext());
                FBEvent newEventForUser = api.getNewEventForUser();
                createNotification(newEventForUser);
            }
        }.start();
    }
}
