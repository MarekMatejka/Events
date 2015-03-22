package mm.events;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import mm.events.backend.FacebookAPI;
import mm.events.domain.FBEvent;


public class MainActivity extends Activity {

    Button notify, not2, calButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerNotifyButton2();
        registerCalenderDummy();
    }

    private void createCalanderEvent(FBEvent event) {
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, event.getName());
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, event.getLocation());

        // Start of event details
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(event.getStartTime());

        // End of event details
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(event.getEndTime());

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCal.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endCal.getTimeInMillis());

        startActivity(calIntent);

    }

    private void registerCalenderDummy() {
        calButton = (Button) findViewById(R.id.cButton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookAPI api = FacebookAPI.getInstance(getApplicationContext());
                createCalanderEvent(api.getNewEventForUser());
            }
        });
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
        Intent intent = new Intent(this, NotifyActivityHandler.class);
//        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle(event.getName()) //title
                .setContentText(event.getFormattedStartDate() + " @ "+event.getLocation())
                .setSmallIcon(R.drawable.fb_icon)
                .setPriority(1)
                .setContentIntent(pIntent)
                .addAction(0, "Accept", pIntent)
                .addAction(0, "Maybe", pIntent)
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

    public void showEvents(View v) {
        Intent intent = new Intent(this, FBListEventListActivity.class);
        startActivity(intent);
        onStop();
    }

    public void newEvent() {
        CountDownTimer cdt = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                FacebookAPI api = FacebookAPI.getInstance(getApplicationContext());
                FBEvent newEventForUser = api.getNewEventForUser();
                createNotification(newEventForUser);
            }
        }.start();
    }
}
