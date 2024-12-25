package org.fortunerise.dtos;

import java.util.ArrayList;
import java.util.List;

public class RouletteDto {
    private int gameId;
    private int player_id;
    private List<BetDto> bets;

    // Constructor
    public RouletteDto(int gameId, int player) {
        this.gameId = gameId;
        this.player_id = player_id;
        this.bets = new ArrayList<>(); // Initialize empty bet list
    }

    // Add a new bet to the game
    public void placeBet(BetDto bet) {
        bets.add(bet);
    }

    // Getters and Setters
    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return player_id;
    }

    public List<BetDto> getBets() {
        return bets;
    }
}
