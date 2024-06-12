package pl.edu.atar.airplanemanagement.events;

public class MaintenanceAlertEvent {
    private String airplaneId;

    public MaintenanceAlertEvent(String airplaneId) {
        this.airplaneId = airplaneId;
    }

    public String getAirplaneId() {
        return airplaneId;
    }
}