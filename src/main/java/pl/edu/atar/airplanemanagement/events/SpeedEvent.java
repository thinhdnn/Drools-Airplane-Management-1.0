package pl.edu.atar.airplanemanagement.events;

public class SpeedEvent {
    private String airplaneId;
    private double speed;

    public SpeedEvent(String airplaneId, double speed) {
        this.airplaneId = airplaneId;
        this.speed = speed;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public double getSpeed() {
        return speed;
    }
}