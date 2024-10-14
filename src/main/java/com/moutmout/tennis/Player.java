package com.moutmout.tennis;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record Player(
        @NotBlank(message = "Le prénom ne doit pas être vide") String firstName,
        @NotBlank(message = "Le nom de famille ne doit pas être vide") String lastName,
        @NotNull(message = "La date est obligatoire")
        @PastOrPresent(message = "La date de naissance doit être présente") LocalDate birthDate,
        @Valid Rank rank) {
}
