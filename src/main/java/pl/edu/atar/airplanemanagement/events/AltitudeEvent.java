package pl.edu.atar.airplanemanagement.events;

public class AltitudeEvent {
    private String airplaneId;
    private double altitude;

    public AltitudeEvent(String airplaneId, double altitude) {
        this.airplaneId = airplaneId;
        this.altitude = altitude;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public double getAltitude() {
        return altitude;
    }
}