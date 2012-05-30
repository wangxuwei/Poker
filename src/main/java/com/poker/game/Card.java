package com.poker.game;

/**
 * A generic com.poker.game card in a deck (without jokers).
 * <p/>
 * Its value is determined first by rank, then by suit.
 *
 * @author woofgl
 */
public class Card implements Comparable<Card> {

    public static final int NO_OF_RANKS = CardRank.values().length;

    public static final int NO_OF_SUITS = CardSuit.values().length;

    /**
     * The rank.
     */
    private final CardRank rank;

    /**
     * The suit.
     */
    private final CardSuit suit;

    public Card(CardRank rank, CardSuit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Constructor based on rank and suit.
     *
     * @param rank The rank.
     * @param suit The suit.
     * @throws IllegalArgumentException If the rank or suit is invalid.
     */
    public Card(int rank, int suit) {
        if (rank < 0 || rank > CardRank.values().length - 1) {
            throw new IllegalArgumentException("Invalid rank");
        }
        if (suit < 0 || suit > CardSuit.values().length - 1) {
            throw new IllegalArgumentException("Invalid suit");
        }
        this.rank = CardRank.values()[rank];
        this.suit = CardSuit.values()[suit];

    }

    /**
     * Constructor based on a string representing a card.
     * <p/>
     * The string must consist of a rank character and a suit character, in that
     * order.
     *
     * @param s The string representation of the card, e.g. "As", "Td", "7h".
     * @return The card.
     * @throws IllegalArgumentException If the card string is null or of invalid length, or the rank
     *                                  or suit could not be parsed.
     */
    public Card(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Null string or of invalid length");
        }
        s = s.trim();
        if (s.length() != 2) {
            throw new IllegalArgumentException("Empty string or invalid length");
        }

        // Parse the rank character.
        String rankSymbol = s.substring(0, 1);
        String suitSymbol = s.substring(1);
        this.rank = CardRank.symbol(rankSymbol);
        this.suit = CardSuit.symbol(suitSymbol);

    }

    /**
     * Returns the suit.
     *
     * @return The suit.
     */
    public int getSuitValue() {
        return suit.ordinal();
    }

    /**
     * Returns the rank.
     *
     * @return The rank.
     */
    public int getRankValue() {
        return rank.ordinal();
    }

    public CardRank getRank() {
        return rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    /*
    * (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return (rank.ordinal() * CardSuit.values().length + suit.ordinal());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            return ((Card) obj).hashCode() == hashCode();
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Card card) {
        int thisValue = hashCode();
        int otherValue = card.hashCode();
        if (thisValue < otherValue) {
            return -1;
        } else if (thisValue > otherValue) {
            return 1;
        } else {
            return 0;
        }
    }

    /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
    @Override
    public String toString() {
        return rank.getSymbol() + suit.getSymbol();
    }

}
