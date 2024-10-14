package com.moutmout.tennis.service;

import com.moutmout.tennis.Player;
import com.moutmout.tennis.PlayerList;
import com.moutmout.tennis.PlayerToSave;
import com.moutmout.tennis.Rank;
import com.moutmout.tennis.entity.PlayerEntity;
import com.moutmout.tennis.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll().stream()
                .map(playerEntity -> new Player(playerEntity.getFirstName(), playerEntity.getLastName(), playerEntity.getBirthDate(),
                        new Rank(playerEntity.getRank(), playerEntity.getPoints())))
                .sorted(Comparator.comparing(player -> player.rank().position())).collect(Collectors.toList());
    }

    public Player getByLastName(String lastName){

        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName);
        if(player.isEmpty()) {
            throw new PlayerNotFoundException(lastName);
        }

        PlayerEntity playerEntity = player.get();
        return new Player(playerEntity.getFirstName(), playerEntity.getLastName(), playerEntity.getBirthDate(),
                new Rank(playerEntity.getRank(), playerEntity.getPoints()));
    }

    public Player create(PlayerToSave playerToSave){
        Optional<PlayerEntity> playerToCreate = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if(playerToCreate.isPresent()){
            throw new PlayerAlreadyExistsException(playerToSave.lastName());
        }

        PlayerEntity playerEntity = new PlayerEntity(
                playerToSave.lastName(),
                playerToSave.firstName(),
                playerToSave.birthDate(),
                playerToSave.points(),
                99999999
        );

        playerRepository.save(playerEntity);

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedPlayers);

        return getByLastName(playerEntity.getLastName());
    }

    public Player update(PlayerToSave playerToSave){
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if(player.isEmpty()){
            throw new PlayerNotFoundException(playerToSave.lastName());
        }

        player.get().setFirstName(playerToSave.firstName());
        player.get().setBirthDate(playerToSave.birthDate());
        player.get().setPoints(playerToSave.points());
        playerRepository.save(player.get());

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedPlayers);

        return getByLastName(playerToSave.lastName());
    }

    public void delete(String lastName){
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName);
        if(player.isEmpty()){
            throw new PlayerNotFoundException(lastName);
        }
        playerRepository.delete(player.get());

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedPlayers);
    }
}
