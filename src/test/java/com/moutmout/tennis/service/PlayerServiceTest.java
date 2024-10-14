package com.moutmout.tennis.service;

import com.moutmout.tennis.Player;
import com.moutmout.tennis.data.PlayerEntityList;
import com.moutmout.tennis.repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void shouldReturnPlayersRanking(){
        //given
        Mockito.when(playerRepository.findAll()).thenReturn(PlayerEntityList.ALL);
        //when
        List<Player> allPlayers = playerService.getAllPlayers();
        //then
        Assertions.assertThat(allPlayers)
                .extracting("lastName")
                .containsExactly("Nadal", "Djokovic", "Federer", "Murray");
    }

    @Test
    public void shouldRetrievePlayer(){
        String playerToRetrieve = "Nadal";
        //given
        Mockito.when(playerRepository.findOneByLastNameIgnoreCase(playerToRetrieve)).thenReturn(Optional.ofNullable(PlayerEntityList.RAFAEL_NADAL));
        //when
        Player retrievedPlayer = playerService.getByLastName(playerToRetrieve);
        //then
        Assertions.assertThat(retrievedPlayer.lastName()).isEqualTo("Nadal");
        Assertions.assertThat(retrievedPlayer.firstName()).isEqualTo("Rafael");
        Assertions.assertThat(retrievedPlayer.rank().position()).isEqualTo(1);
    }

    @Test
    public void shouldFailToRetrievePlayer_WhenPlayerDoesNotExist() {
        // Given
        String unknownPlayer = "doe";
        Mockito.when(playerRepository.findOneByLastNameIgnoreCase(unknownPlayer)).thenReturn(Optional.empty());

        // When // Then
        Exception exception = assertThrows(PlayerNotFoundException.class, () -> {
            playerService.getByLastName(unknownPlayer);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("Le joueur " + unknownPlayer + " n'a pas été trouvé");
    }
}
