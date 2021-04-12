package com.meli.challenge.quasar.entities;

public class Distance {
    private final String satelliteName;
    private final Position position;
    private final float distance;

    public Distance(String satelliteName, Position position, float distance) {
        this.satelliteName = satelliteName;
        this.position = position;
        this.distance = distance;
    }

    public String getSatelliteName() {
        return this.satelliteName;
    }

    public Position getPosition() {
        return this.position;
    }

    public float getDistance() {
        return this.distance;
    }
}
