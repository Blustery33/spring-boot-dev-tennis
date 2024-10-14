package com.moutmout.tennis.controller;

import com.moutmout.tennis.Error;
import com.moutmout.tennis.Player;
import com.moutmout.tennis.PlayerToSave;
import com.moutmout.tennis.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Tennis Players API")
@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @Operation(summary = "Finds players", description = "Finds players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players list", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Player.class)))
            }),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")
    })
    @GetMapping
    public List<Player> list(){
        return playerService.getAllPlayers();
    }


    @Operation(summary = "Find player by lastName", description = "Find player by lastName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players list", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))
            }),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")
    })
    @GetMapping("/{lastName}")
    public Player getByLastName(@PathVariable("lastName") String lastName){
        return playerService.getByLastName(lastName);
    }

    @Operation(summary = "Create a new player", description = "Create a new player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create a new player", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerToSave.class))
            }),
            @ApiResponse(responseCode = "400", description = "Player with specified last name already exist.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")
    })
    @PostMapping
    public Player createPlayer(@RequestBody @Valid PlayerToSave playerToSave){
        return playerService.create(playerToSave);
    }

    @Operation(summary = "Update a player", description = "Update a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update a player", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerToSave.class))
            }),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")
    })
    @PutMapping
    public Player updatePlayer(@RequestBody @Valid PlayerToSave playerToSave){
        return playerService.update(playerToSave);
    }


    @Operation(summary = "Delete a player by LastName", description = "Delete a player by LastName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player has been deleted"),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")

    })

    @DeleteMapping("/{lastName}")
    public void deletePlayerByLastName(@PathVariable("lastName") String lastName){
        playerService.delete(lastName);
    }
}
