package org.fortunerise.api.v1.models;

import java.util.ArrayList;
import java.util.List;

public class RouletteModel {
    private int gameId;
    private int player_id;
    private List<BetModel> bets;

    // Constructor
    public RouletteModel(int gameId, int player) {
        this.gameId = gameId;
        this.player_id = player_id;
        this.bets = new ArrayList<>(); // Initialize empty bet list
    }

    // Add a new bet to the game
    public void placeBet(BetModel bet) {
        bets.add(bet);
    }

    // Getters and Setters
    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return player_id;
    }

    public List<BetModel> getBets() {
        return bets;
    }
}
