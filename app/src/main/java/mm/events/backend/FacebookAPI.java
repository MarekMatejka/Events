package mm.events.backend;

import android.content.Context;

import java.util.List;
import java.util.Map;

import mm.events.domain.FBEvent;
import mm.events.domain.FBEventDetails;
import mm.events.domain.RSVPStatus;

public abstract class FacebookAPI {

    protected OnDataChangeListener listener;
    protected static Context context;
    protected Map<String, FBEvent> events;
    private static FacebookAPI api = null;

    public static FacebookAPI getInstance(Context c) {
        context = c;
        if (api == null) {
            api = new FacebookAPIFile();
        }
        return api;
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.listener = listener;
    }

    public abstract List<FBEvent> getAllEventsForUser();
    public abstract void RSVPtoEvent(String eventID, RSVPStatus status);
    public abstract FBEvent getEvent(String eventID);
    public abstract FBEventDetails getEventDetails(String eventID);
    public abstract FBEvent getNewEventForUser();

    public interface OnDataChangeListener {
        public void onDataChange();
    }
}
