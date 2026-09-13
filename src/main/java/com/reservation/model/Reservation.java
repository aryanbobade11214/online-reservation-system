package com.reservation.model;

import java.time.LocalDate;

public class Reservation {

    private int id;
    private String pnr;
    private String username;
    private int trainId;
    private String trainName;
    private String passengerName;
    private int age;
    private LocalDate journeyDate;

    // Constructor for booking a new reservation
    public Reservation(
            String pnr,
            String username,
            int trainId,
            String passengerName,
            int age,
            LocalDate journeyDate) {

        this.pnr = pnr;
        this.username = username;
        this.trainId = trainId;
        this.passengerName = passengerName;
        this.age = age;
        this.journeyDate = journeyDate;
    }

    // Constructor for retrieving reservation from database
    public Reservation(
            int id,
            String pnr,
            String username,
            int trainId,
            String passengerName,
            int age,
            LocalDate journeyDate) {

        this.id = id;
        this.pnr = pnr;
        this.username = username;
        this.trainId = trainId;
        this.passengerName = passengerName;
        this.age = age;
        this.journeyDate = journeyDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", pnr='" + pnr + '\'' +
                ", username='" + username + '\'' +
                ", trainId=" + trainId +
                ", trainName='" + trainName + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", age=" + age +
                ", journeyDate=" + journeyDate +
                '}';
    }
}