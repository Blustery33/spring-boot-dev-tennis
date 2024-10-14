package com.moutmout.tennis.service;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String lastName) {
        super("Le joueur " + lastName + " n'a pas été trouvé");
    }
}

