package mm.events.backend;

import java.text.DateFormat;
import java.text.ParseException;
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
                   String location, String startTime,
                   String endTime, String timezone,
                   RSVPStatus status) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startTime = toUtilDate(startTime);
        this.endTime = toUtilDate(endTime);
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

    private static Date toUtilDate(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {e.printStackTrace(); return null;}
    }
}
