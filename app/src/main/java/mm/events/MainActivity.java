package mm.events;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends Activity {

    private static final int HELLO_ID = 1;
    Button notify, not2, calButton;
    // TODO comment or delete following when correct calls to the createCalanderEvent done
    String event_title, event_location, event_description;
    boolean event_all_day = false;
    Calendar event_end_time;
    int event_begin_year, event_begin_month, event_begin_day, event_begin_hrs, event_begin_min;
    int event_end_year, event_end_month, event_end_day, event_end_hrs, event_end_min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO comment or delete following when correct calls to the createCalanderEvent done
        event_title = "Facebook Hackathon";
        event_location = "10 Brock Street, NW1 3FG London, United Kingdom";
        event_description = "Move fast and break things!";
        event_all_day = false;
        event_begin_year = 2014;
        event_begin_month = 3;
        event_begin_day = 21;
        event_begin_hrs = 12;
        event_begin_min = 13;
        event_end_year = 2014;
        event_end_month = 3;
        event_end_day = 22;
        event_end_hrs = 13;
        event_end_min = 0;

        registerNotifyButton();
        registerNotifyButton2();
        registerCalenderDummy();
    }

    private void createCalanderEvent(String title, String location, String description, boolean all_day, int begin_year, int begin_month, int begin_day, int begin_hrs, int begin_min, int end_year, int end_month, int end_day, int end_hrs, int end_min) {
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, title);
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, description);


        // NOTE: calender month seems to start from 0 so 0 wil be january, 1 wil be feb... 11 wil be december
        // But the day and year are correct and from 1
        // format>>GregorianCalendar(year, month, day)
//        GregorianCalendar calDate = new GregorianCalendar(2012, 1, 15);
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, all_day);

        // Start of event details
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(begin_year, begin_month, begin_day, begin_hrs, begin_min);

        // End of event details
        Calendar endCal = Calendar.getInstance();
        endCal.set(end_year, end_month, end_day, end_hrs, end_min);
//        endCal.set(year, mnth, day, hrs, min);

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCal.getTimeInMillis());
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endCal.getTimeInMillis());
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());

        startActivity(calIntent);

    }

    private void registerCalenderDummy() {
        calButton = (Button) findViewById(R.id.cButton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createCalanderEvent(event_title, event_location, event_description, event_all_day, event_begin_year, event_end_month, event_begin_day, event_begin_hrs, event_begin_min, event_end_year, event_end_month, event_end_day, event_end_hrs, event_end_min);
            }
        });
    }

    private void registerNotifyButton() {
        not2 = (Button) findViewById(R.id.notifyButton);
        not2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createNotification();
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

/*
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
    }*/

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
