package com.reservation.model;

public class Train {

    private int id;
    private String trainName;
    private String source;
    private String destination;
    private int availableSeats;

    public Train() {
    }

    public Train(int id, String trainName, String source,
                 String destination, int availableSeats) {

        this.id = id;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.availableSeats = availableSeats;
    }

    public Train(String trainName, String source,
                 String destination, int availableSeats) {

        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.availableSeats = availableSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {

        return trainName + " - "
                + source + " to "
                + destination
                + " (Seats: "
                + availableSeats + ")";
    }
}