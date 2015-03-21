package mm.events;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import mm.events.backend.FBEvent;
import mm.events.backend.FacebookAPI;
import mm.events.backend.FacebookAPIImpl;

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

    private FBEvent event;

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
            FacebookAPI api = new FacebookAPIImpl(getActivity());
            event = api.getEvent(getArguments().getString(ARG_ITEM_ID));
        }
        Log.e("all day", ""+event.isAllDay());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fblistevent_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (event != null) {
            ((TextView) rootView.findViewById(R.id.fblistevent_detail)).setText(event.getName());
        }

        return rootView;
    }
}
