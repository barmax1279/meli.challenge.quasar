package com.meli.challenge.quasar.entities;

public class Position {
    private final double X;
    private final double Y;

    public Position(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    public double getX() {
        return this.X;
    }

    public double getY() {
        return this.Y;
    }
}