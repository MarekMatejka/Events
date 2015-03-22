package mm.events.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mm.events.domain.FBEvent;
import mm.events.domain.FBEventDetails;
import mm.events.domain.FBEventVenue;
import mm.events.domain.RSVPStatus;

public class FacebookAPIFile extends FacebookAPI {

    public FacebookAPIFile() {
        events = readEvents();
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
        if (listener != null)
            listener.onDataChange();
    }

    @Override
    public FBEvent getEvent(String eventID) {
        return events.get(eventID);
    }

    @Override
    public FBEventDetails getEventDetails(String eventID) {
        return parseEventDetails(loadJSONFromAsset("event_details.json")).get(eventID);
    }

    @Override
    public FBEvent getNewEventForUser() {
        Random random = new Random();
        FBEvent event = new FBEvent(""+random.nextInt(123456), "Test Event", "Right here", new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 1234567890), "Europe/UK", RSVPStatus.UNSURE);
        events.put(event.getId(), event);

        if (listener != null)
            listener.onDataChange();

        return event;
    }

    private Map<String,FBEvent> readEvents() {
        return parseEvents(loadJSONFromAsset("events.json"));
    }

    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
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
                        event.optString("location"), toUtilDate(event.optString("start_time")),
                        toUtilDate(event.optString("end_time")), event.optString("timezone"),
                        convertToStatus(event.getString("rsvp_status")));
                fbEvents.put(fbEvent.getId(), fbEvent);
            }
        }catch (JSONException e){e.printStackTrace();}
        return fbEvents;
    }

    private Map<String, FBEventDetails> parseEventDetails(String json) {
        Map<String,FBEventDetails> fbEvents = new HashMap<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject details = array.getJSONObject(i);
                FBEventDetails fbDetails = new FBEventDetails(details.optString("id"), details.optString("name"),
                                                              details.optString("description"), toUtilDate(details.optString("start_time")),
                                                              toUtilDate(details.optString("end_time")), details.optBoolean("is_date_only"),
                                                              details.optJSONObject("owner").optString("name"), details.optString("location"),
                                                              toEventVenue(details.optJSONObject("venue")));


                fbEvents.put(fbDetails.getId(), fbDetails);
            }
        }catch (JSONException e){e.printStackTrace();}
        return fbEvents;
    }

    private FBEventVenue toEventVenue(JSONObject venue) {
        return new FBEventVenue(venue.optString("city"), venue.optString("country"),
                                venue.optString("street"), venue.optString("zip"));
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

    private static Date toUtilDate(String dateString) {
        DateFormat format = new SimpleDateFormat(dateString.contains("T") ? "yyyy-MM-dd'T'hh:mm:ss" : "yyyy-MM-dd");

        try {
            return format.parse(dateString);
        } catch (ParseException e) {e.printStackTrace(); return null;}
    }
}
