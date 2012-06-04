package com.poker.game;


import java.util.List;

public class TableState {
    private List<Player> activePlayers;
    private List<Player> players;

    private int dealerPosition;
    private int actorPosition;

    private List<Card> board;
    private int pot;

    private int bigBlind;
    private int smallBlind;
    private String message;

    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getDealerPosition() {
        return dealerPosition;
    }

    public int getActorPosition() {
        return actorPosition;
    }

    public List<Card> getBoard() {
        return board;
    }

    public int getPot() {
        return pot;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TableState(List<Player> activePlayers, List<Player> players, int dealerPosition,
                      int actorPosition, List<Card> board, int pot, int bigBlind, int smallBlind) {
        this.activePlayers = activePlayers;
        this.players = players;
        this.dealerPosition = dealerPosition;
        this.actorPosition = actorPosition;
        this.board = board;
        this.pot = pot;
        this.bigBlind = bigBlind;
        this.smallBlind = smallBlind;

    }
}
