package mm.events;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

import mm.events.backend.FacebookAPI;
import mm.events.domain.FBEvent;
import mm.events.domain.FBEventDetails;
import mm.events.domain.RSVPStatus;
import mm.events.views.LogoRadioButton;
import mm.events.views.LogoView;

/**
 * A fragment representing a single FBListEvent detail screen.
 * This fragment is either contained in a {@link FBListEventListActivity}
 * in two-pane mode (on tablets) or a {@link FBListEventDetailActivity}
 * on handsets.
 */
public class FBListEventDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private FBEventDetails eventDetails;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FBListEventDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            FacebookAPI api = FacebookAPI.getInstance(getActivity());
            eventDetails = api.getEventDetails(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fblistevent_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (eventDetails != null) {
            ((TextView) rootView.findViewById(R.id.event_details_name)).setText(eventDetails.getName());
            ((TextView) rootView.findViewById(R.id.event_details_organiser)).setText(eventDetails.getOwnerName());
            ((TextView) rootView.findViewById(R.id.event_details_location)).setText(eventDetails.getLocation());
            ((TextView) rootView.findViewById(R.id.event_details_time)).setText(getEventTime());
            ((TextView) rootView.findViewById(R.id.event_details_description)).setText(eventDetails.getDescription());

            final FacebookAPI api = FacebookAPI.getInstance(getActivity());

            final LogoRadioButton accept = (LogoRadioButton)rootView.findViewById(R.id.event_details_accept);
            final LogoRadioButton maybe = (LogoRadioButton)rootView.findViewById(R.id.event_details_maybe);
            final LogoRadioButton reject = (LogoRadioButton)rootView.findViewById(R.id.event_details_decline);

            final RadioGroup group = (RadioGroup)rootView.findViewById(R.id.group);

            final FBEvent event = api.getEvent(eventDetails.getId());

            setStatus(accept, maybe, reject, group, event);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api.RSVPtoEvent(eventDetails.getId(), RSVPStatus.GOING);
                    setStatus(accept, maybe, reject, group, event);
                }
            });

            maybe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api.RSVPtoEvent(eventDetails.getId(), RSVPStatus.MAYBE);
                    setStatus(accept, maybe, reject, group, event);
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api.RSVPtoEvent(eventDetails.getId(), RSVPStatus.DECLINED);
                    setStatus(accept, maybe, reject, group, event);
                }
            });

            LogoView calendar = (LogoView)rootView.findViewById(R.id.event_details_calendar);
            calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createCalendarEvent(event);
                }
            });
        }

        return rootView;
    }

    private void setStatus(LogoRadioButton accept, LogoRadioButton maybe, LogoRadioButton reject, RadioGroup group, FBEvent event) {
        switch (event.getStatus()) {
            case GOING: accept.setChecked(true); break;
            case MAYBE: maybe.setChecked(true); break;
            case DECLINED: reject.setChecked(true); break;
            default: group.clearCheck();
        }
    }

    private String getEventTime() {
        if (eventDetails.getEndTime() != null && !eventDetails.getEndTime().equals(""))
            return eventDetails.getFormattedStartDate() + " - " + eventDetails.getFormattedEndDate();
        else
            return eventDetails.getFormattedStartDate();
    }

    private String getStatus(FBEvent event) {
        switch (event.getStatus()) {
            case GOING: return "f00c";
            case DECLINED: return "f00d";
            default: return "f128";
        }
    }

    private void createCalendarEvent(FBEvent event) {
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, event.getName());
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, event.getLocation());

        // Start of event details
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(event.getStartTime());

        Calendar endCal = Calendar.getInstance();
        if (event.getEndTime() != null) {
            endCal.setTime(event.getEndTime());
        }
        else {
            endCal.setTimeInMillis(event.getStartTime().getTime()+(60*60*1000));
        }

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCal.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endCal.getTimeInMillis());

        startActivity(calIntent);
    }
}
