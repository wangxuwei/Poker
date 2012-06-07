package com.poker.game;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 6/7/12
 * Time: 11:03 AM
 */
public interface Deck {
    void shuffle();

    void reset();

    Card deal();

    List<Card> deal(int noOfCards);

    Card deal(int rank, int suit);
}
