package mm.events.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FBEventDetails {

    String id;
    String name;
    String description;
    Date startTime;
    Date endTime;
    boolean isAllDay;
    String ownerName;
    String location;
    FBEventVenue venue;

    public FBEventDetails(String id, String name, String description,
                          Date startTime, Date endTime, boolean isAllDay,
                          String ownerName, String location, FBEventVenue venue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAllDay = isAllDay;
        this.ownerName = ownerName;
        this.location = location;
        this.venue = venue;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getLocation() {
        return location;
    }

    public FBEventVenue getVenue() {
        return venue;
    }

    public String getFormattedStartDate() {
        SimpleDateFormat format;
        if (isAllDay())
            format = new SimpleDateFormat("dd.MM.yyyy");
        else
            format = new SimpleDateFormat("dd.MM.yyyy hh:mm a");
        return format.format(startTime);
    }

    public String getFormattedEndDate() {
        SimpleDateFormat format;
        if (isAllDay())
            format = new SimpleDateFormat("dd.MM.yyyy");
        else
            format = new SimpleDateFormat("dd.MM.yyyy hh:mm a");
        return format.format(endTime);
    }
}
