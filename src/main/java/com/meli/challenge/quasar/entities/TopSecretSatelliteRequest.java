package com.meli.challenge.quasar.entities;

public class TopSecretSatelliteRequest {

    private final String name;
    private final float distance;
    private final String[] message;

    public TopSecretSatelliteRequest(String name, float distance, String[] message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
    }

    public float getDistance() {
        return this.distance;
    }

    public String getName() {
        return this.name;
    }

    public String[] getMessage() {
        return this.message;
    }

}
