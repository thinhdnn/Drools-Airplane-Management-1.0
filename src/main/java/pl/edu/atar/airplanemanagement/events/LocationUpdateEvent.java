package pl.edu.atar.airplanemanagement.events;

public class LocationUpdateEvent {
    private String airplaneId;
    private double latitude;
    private double longitude;
    private long timestamp;

    public LocationUpdateEvent(String airplaneId, double latitude, double longitude, long timestamp) {
        this.airplaneId = airplaneId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }
}