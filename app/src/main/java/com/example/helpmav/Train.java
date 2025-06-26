package com.example.helpmav;

public class Train {
    private String trainId;           // Unique train identifier
    private String lineName;          // Train line name or number
    private double latitude;          // Current latitude
    private double longitude;         // Current longitude
    private String status;            // e.g. "On Time", "Delayed"
    private String nextStation;       // Name of next station
    private long timestamp;           // Timestamp of last update (epoch millis)
    private double speed;             // Current speed in km/h or m/s
    private double direction;         // Direction in degrees (0-360)
    private int occupancy;            // Number of passengers or occupancy percentage
    private String originStation;     // Starting station of this train
    private String destinationStation;// Final destination station
    private int delaySeconds;         // Delay time in seconds
    private boolean wheelchairAccessible; // Whether train is wheelchair accessible
    private String trainHeadSign;         // Type of train (express, local, etc.)

    private int arrivalTimeSeconds;   // Arrival time in seconds since midnight
    private int departureTimeSeconds; // Departure time in seconds since midnight

    // Constructor with new arrival/departure fields added
    public Train(String trainId, String lineName, double latitude, double longitude, String status,
                 String nextStation, long timestamp, double speed, double direction, int occupancy,
                 String originStation, String destinationStation, int delaySeconds,
                 boolean wheelchairAccessible, String trainHeadSign,
                 int arrivalTimeSeconds, int departureTimeSeconds) {
        this.trainId = trainId;
        this.lineName = lineName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.nextStation = nextStation;
        this.timestamp = timestamp;
        this.speed = speed;
        this.direction = direction;
        this.occupancy = occupancy;
        this.originStation = originStation;
        this.destinationStation = destinationStation;
        this.delaySeconds = delaySeconds;
        this.wheelchairAccessible = wheelchairAccessible;
        this.trainHeadSign = trainHeadSign;
        this.arrivalTimeSeconds = arrivalTimeSeconds;
        this.departureTimeSeconds = departureTimeSeconds;
    }

    // Getters and setters for all fields including new ones

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextStation() {
        return nextStation;
    }

    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getOriginStation() {
        return originStation;
    }

    public void setOriginStation(String originStation) {
        this.originStation = originStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public int getDelaySeconds() {
        return delaySeconds;
    }

    public void setDelaySeconds(int delaySeconds) {
        this.delaySeconds = delaySeconds;
    }

    public boolean isWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public void setWheelchairAccessible(boolean wheelchairAccessible) {
        this.wheelchairAccessible = wheelchairAccessible;
    }

    public String getTrainHeadSign() {
        return trainHeadSign;
    }

    public void setTrainHeadSign(String trainHeadSign) {
        this.trainHeadSign = trainHeadSign;
    }

    public int getArrivalTimeSeconds() {
        return arrivalTimeSeconds;
    }

    public void setArrivalTimeSeconds(int arrivalTimeSeconds) {
        this.arrivalTimeSeconds = arrivalTimeSeconds;
    }

    public int getDepartureTimeSeconds() {
        return departureTimeSeconds;
    }

    public void setDepartureTimeSeconds(int departureTimeSeconds) {
        this.departureTimeSeconds = departureTimeSeconds;
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainId='" + trainId + '\'' +
                ", lineName='" + lineName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", status='" + status + '\'' +
                ", nextStation='" + nextStation + '\'' +
                ", timestamp=" + timestamp +
                ", speed=" + speed +
                ", direction=" + direction +
                ", occupancy=" + occupancy +
                ", originStation='" + originStation + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", delaySeconds=" + delaySeconds +
                ", wheelchairAccessible=" + wheelchairAccessible +
                ", trainHeadSign='" + trainHeadSign + '\'' +
                ", arrivalTimeSeconds=" + arrivalTimeSeconds +
                ", departureTimeSeconds=" + departureTimeSeconds +
                '}';
    }
}
