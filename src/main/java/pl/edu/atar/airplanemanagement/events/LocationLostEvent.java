package pl.edu.atar.airplanemanagement.events;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class LocationLostEvent {
    private Date timestamp;

    public LocationLostEvent() {
    }

    public LocationLostEvent(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}