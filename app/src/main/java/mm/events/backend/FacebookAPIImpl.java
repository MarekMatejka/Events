package mm.events.backend;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FacebookAPIImpl implements FacebookAPI {

    private Map<String, FBEvent> events;
    private Context context;

    public FacebookAPIImpl(Context context) {
        this.context = context;
        this.events = readEvents();
    }

    @Override
    public List<FBEvent> getAllEventsForUser() {
        List<FBEvent> fbEvents = new LinkedList<>(events.values());
        Collections.sort(fbEvents);
        return fbEvents;
    }

    @Override
    public void RSVPtoEvent(String eventID, RSVPStatus status) {
        events.get(eventID).setStatus(status);
    }

    @Override
    public FBEvent getEvent(String id) {
        return events.get(id);
    }

    private Map<String,FBEvent> readEvents() {
        return parseEvents(loadJSONFromAsset());
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("events.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {ex.printStackTrace(); return null;}
        return json;
    }

    private Map<String, FBEvent> parseEvents(String json) {
        Map<String,FBEvent> fbEvents = new HashMap<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject event = array.getJSONObject(i);
                FBEvent fbEvent = new FBEvent(event.getString("id"), event.getString("name"),
                        event.optString("location"), event.optString("start_time"),
                        event.optString("end_time"), event.optString("timezone"),
                        convertToStatus(event.getString("rsvp_status")));
                fbEvents.put(fbEvent.getId(), fbEvent);
            }
        }catch (JSONException e){e.printStackTrace();}
        return fbEvents;
    }

    private RSVPStatus convertToStatus(String status) {
        switch (status) {
            case "attending": return RSVPStatus.GOING;
            case "unsure": return RSVPStatus.UNSURE;
            case "declined": return RSVPStatus.DECLINED;
            case "maybe": return RSVPStatus.MAYBE;
            default: return RSVPStatus.UNKNOWN;
        }
    }
}
