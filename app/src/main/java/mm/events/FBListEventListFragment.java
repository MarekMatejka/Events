package mm.events;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import mm.events.adapter.EventsListAdapter;
import mm.events.domain.FBEvent;
import mm.events.backend.FacebookAPI;

/**
 * A list fragment representing a list of FBListEvents. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link FBListEventDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class FBListEventListFragment extends ListFragment implements FacebookAPI.OnDataChangeListener{

    private List<FBEvent> events;

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    @Override
    public void onDataChange() {
        this.getListView().invalidateViews();
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FBListEventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookAPI api = FacebookAPI.getInstance(getActivity());
        api.setOnDataChangeListener(this);
        events = api.getAllEventsForUser();
        // TODO: replace with a real list adapter.
        setListAdapter(new EventsListAdapter(getActivity(), events));

        newEvent();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(events.get(position).getId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public void newEvent() {
        CountDownTimer cdt = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                FacebookAPI api = FacebookAPI.getInstance(getActivity());
                FBEvent newEventForUser = api.getNewEventForUser();
                createNotification(newEventForUser);
            }
        }.start();
    }

    public void createNotification(FBEvent event) {
        Log.e("main id", event.getId());

        Context context = getActivity();

        //going
        Intent intentGoing = new Intent(context, NotifyActivityHandler.class);
        intentGoing.putExtra("Accept", event.getId().toString());
//        intentGoing.putExtra("action", ""+event.getId().toString());
//        intentGoing.putExtra("action", "going");
//        intentGoing.putExtra("id", event.getId().toString());

        //maybe
        Intent intentMaybe = new Intent(context, NotifyActivityHandler.class);
        intentMaybe.putExtra("Maybe", event.getId().toString());
//        intentMaybe.putExtra("id", event.getId());
        //reject
        Intent intentReject = new Intent(context, NotifyActivityHandler.class);
        intentReject.putExtra("Decline", event.getId().toString());
//        intentReject.putExtra("id", event.getId());

       /* // 1 intent:
        Intent i1 = new Intent(this, NotifyActivityHandler.class);
        //i1.putExtra("action", "going");
        PendingIntent p1 = PendingIntent.getActivity(this, 0, i1, 0);*/

        // open activity
        Intent intentContent = new Intent(context, FBListEventListActivity.class);
        intentContent.putExtra("action", "content");

        PendingIntent pIntentGoing = PendingIntent.getActivity(context, 0, intentGoing, 0);
        PendingIntent pIntentReject = PendingIntent.getActivity(context, 1, intentReject, 0);
        PendingIntent pIntentMaybe = PendingIntent.getActivity(context, 2, intentMaybe, 0);
        PendingIntent pIntentContent = PendingIntent.getActivity(context, 3, intentContent, 0);

        // Build notification
        // Actions are just fake
        Notification notification = new Notification.Builder(context)
                .setContentTitle(event.getName())
                .setContentText(event.getFormattedStartDate() + " @ " + event.getLocation())
                .setSmallIcon(R.drawable.fb_icon)
                .setPriority(1)
                .setContentIntent(pIntentContent)
                .addAction(0, "Accept", pIntentGoing)
                .addAction(0, "Maybe", pIntentMaybe)
                .addAction(0, "Decline", pIntentReject)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // hide the notification after it is selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(1, notification);
    }
}
