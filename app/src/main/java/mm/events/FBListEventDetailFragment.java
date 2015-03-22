package mm.events;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import mm.events.backend.FacebookAPI;
import mm.events.domain.FBEvent;
import mm.events.domain.FBEventDetails;
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

            FacebookAPI api = FacebookAPI.getInstance(getActivity());
            FBEvent event = api.getEvent(eventDetails.getId());

            ((LogoView) rootView.findViewById(R.id.event_details_status)).setLogo(getStatus(event));

        }

        return rootView;
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
}
