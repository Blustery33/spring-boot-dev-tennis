package com.moutmout.tennis.service;

import com.moutmout.tennis.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.moutmout.tennis.PlayerList;
import com.moutmout.tennis.PlayerToSave;
import com.moutmout.tennis.Rank;
import com.moutmout.tennis.entity.PlayerEntity;

public class RankingCalculator {

    private final List<PlayerEntity> currentPlayersRanking;

    public RankingCalculator(List<PlayerEntity> currentPlayersRanking) {
        this.currentPlayersRanking = currentPlayersRanking;
    }

    public List<PlayerEntity> getNewPlayersRanking() {

        currentPlayersRanking.sort((player1, player2) -> Integer.compare(player2.getPoints(), player1.getPoints()));

        List<PlayerEntity> updatedPlayers = new ArrayList<>();

        for (int i = 0; i < currentPlayersRanking.size(); i++) {
            PlayerEntity updatedPlayer = currentPlayersRanking.get(i);
            updatedPlayer.setRank(i+1);
            updatedPlayers.add(updatedPlayer);
        }

            return updatedPlayers;
    }
}
