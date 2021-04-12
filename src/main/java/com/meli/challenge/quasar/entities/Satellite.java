package com.meli.challenge.quasar.entities;

public class Satellite {

    private final String name;
    private final Position location;


    public Satellite(String name, Position location) {
        this.name = name;
        this.location = location;
    }


    public String getName() {
        return name;
    }


    public Position getLocation() {
        return location;
    }
}