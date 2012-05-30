package com.poker.game;

public enum CardRank {
    DEUCE("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("T"),
	JACK("J"),
	QUEEN("Q"),
	KING("K"),
	ACE("A");
    private String symbol;

    private CardRank(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static CardRank symbol(String symbol){
        for (CardRank rank : CardRank.values()) {
            if (rank.getSymbol().equals(symbol)) {
                return rank;
            }
        }

        throw new IllegalArgumentException("Unknown rank: " + symbol);
    }


}
