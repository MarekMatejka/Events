package mm.events.backend;

import java.util.List;

public interface FacebookAPI {

    List<FBEvent> getAllEventsForUser();
    void RSVPtoEvent(String eventID, RSVPStatus status);

}
