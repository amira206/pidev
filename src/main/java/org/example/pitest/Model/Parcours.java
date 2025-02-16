package org.example.pitest.Model;

import java.util.Objects;

public class Parcours {
    private int id;
    private String name;
    private String destination;
    private String pickup;
    private double latDest;
    private double lngDest;
    private double latPickup;
    private double lngPickup;
    private int distance;
    private int time;

    public Parcours(int id, String name, String pickup, String destination, double latDest, double lngDest, double latPickup, double lngPickup, int distance, int time) {
        this.id = id;
        this.name = name;
        this.pickup = pickup;
        this.destination = destination;
        this.latDest = latDest;
        this.lngDest = lngDest;
        this.latPickup = latPickup;
        this.lngPickup = lngPickup;
        this.distance = distance;
        this.time = time;
    }

    public Parcours(String name, int id, String destination, String pickup) {
        this.name = name;
        this.id = id;
        this.destination = destination;
        this.pickup = pickup;
    }

    public Parcours() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public double getLatDest() {
        return latDest;
    }

    public void setLatDest(double latDest) {
        this.latDest = latDest;
    }

    public double getLngDest() {
        return lngDest;
    }

    public void setLngDest(double lngDest) {
        this.lngDest = lngDest;
    }

    public double getLatPickup() {
        return latPickup;
    }

    public void setLatPickup(double latPickup) {
        this.latPickup = latPickup;
    }

    public double getLngPickup() {
        return lngPickup;
    }

    public void setLngPickup(double lngPickup) {
        this.lngPickup = lngPickup;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parcours parcours)) return false;
        return id == parcours.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Parcours{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", pickup='" + pickup + '\'' +
                ", latDest=" + latDest +
                ", lngDest=" + lngDest +
                ", latPickup=" + latPickup +
                ", lngPickup=" + lngPickup +
                ", distance=" + distance +
                ", time=" + time +
                '}';
    }
}
