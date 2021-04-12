package com.meli.challenge.quasar.entities;

public class TopSecretResponse {

    private Position position;
    private String message;
    private String errorDescription;

    public TopSecretResponse(){}

    public TopSecretResponse(Position position, String message) {
        this.position = position;
        this.message = message;
    }

    public Position getPosition() {
        return this.position;
    }

    public String getMessage() {
        return this.message;
    }

    public void setErrorDescription(String errorDescription){
        this.errorDescription = errorDescription;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setMessage(String message){
        this.message = message;
    }

}
