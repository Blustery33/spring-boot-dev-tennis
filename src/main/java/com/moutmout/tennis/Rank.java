package com.moutmout.tennis;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record Rank(
        @Positive(message = "La position dans le classement ne peut pas être 0") int position,
        @PositiveOrZero(message = "les points doivent être supérieurs à zéro") int points) {
}
