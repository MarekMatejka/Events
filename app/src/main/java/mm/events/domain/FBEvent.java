package mm.events.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FBEvent implements Comparable{

    String id;
    String name;
    String location;
    Date startTime;
    Date endTime;
    String timezone;
    RSVPStatus status;

    public FBEvent(String id, String name,
                   String location, Date startTime,
                   Date endTime, String timezone,
                   RSVPStatus status) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timezone = timezone;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getTimezone() {
        return timezone;
    }

    public RSVPStatus getStatus() {
        return status;
    }

    public void setStatus(RSVPStatus status) {
        this.status = status;
    }

    public boolean isAllDay() {
        return startTime.getHours() == 0;
    }

    public String getFormattedStartDate() {
        SimpleDateFormat format;
        if (isAllDay())
            format = new SimpleDateFormat("dd.MM.yyyy");
        else
            format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return format.format(startTime);
    }

    @Override
    public String toString() {
        return "FBEvent{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", status=" + status +
                '}';
    }

    public int compareTo(FBEvent other) {
        return other.getStartTime().compareTo(startTime);
    }

    @Override
    public int compareTo(Object another) {
        return compareTo((FBEvent)another);
    }
}
