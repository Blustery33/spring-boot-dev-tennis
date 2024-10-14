package com.moutmout.tennis.service;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String lastName) {
        super("Le joueur " + lastName + " existe déjà !");
    }
    
}
