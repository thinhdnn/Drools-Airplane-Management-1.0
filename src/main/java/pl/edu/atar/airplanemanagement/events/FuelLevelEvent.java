package pl.edu.atar.airplanemanagement.events;

public class FuelLevelEvent {
    private String airplaneId;
    private double fuelLevel;

    public FuelLevelEvent(String airplaneId, double fuelLevel) {
        this.airplaneId = airplaneId;
        this.fuelLevel = fuelLevel;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }
}