package com.poker.game;

public enum CardSuit {
	DIAMONDS("d"),
    CLUBS("c"),
	HEARTS("h"),
	SPADES("s");
    private String symbol;

    private CardSuit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static CardSuit symbol(String symbol) {
        for (CardSuit suit : CardSuit.values()) {
            if (suit.getSymbol().equals(symbol)) {
                return suit;
            }
        }

        throw new IllegalArgumentException("Unknown suit: " + symbol);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CardSuit");
        sb.append("{symbol='").append(symbol).append('\'');
        sb.append('}');
        return sb.toString();
    }
}