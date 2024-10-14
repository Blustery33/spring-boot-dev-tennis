package com.moutmout.tennis;

import jakarta.validation.constraints.NotBlank;

public record UserCredentials(
        @NotBlank(message = "Le nom de compte est requis") String login,
        @NotBlank(message = "Le mot de passe est requis") String password) {
}
