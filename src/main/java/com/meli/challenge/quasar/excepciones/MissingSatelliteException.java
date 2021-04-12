package com.meli.challenge.quasar.excepciones;

public class MissingSatelliteException extends Exception {
    public MissingSatelliteException(String satelliteName) {super(satelliteName);

    }
}
