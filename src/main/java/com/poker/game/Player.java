
package com.poker.game;

import java.util.List;
import java.util.Set;

/**
 * A Texas Hold'em player.
 * 
 * The player's actions are delegated to a PlayerClient, which can be either
 * human-controlled or AI-controlled (bot).
 * 
 * @author woofgl
 */
public class Player {
    
    /** Name. */
    private final String name;
    
    /** Client application responsible for the actual behavior. */
    //private final Client client;
    
    /** Hand of cards. */
    private final Hand hand;
    
    /** Current amount of cash. */
    private int cash;
    
    /** Whether the player has his hole cards being dealt. */
    private boolean hasCards;

    /** Current bet. */
    private int bet;
    
    /** Number of bets and raises in the current betting round. */
    private int raises;
    
    /** Pot when all-in. */
    private int allInPot;
    
    /** Last action performed. */
    private Action action;
    
    /** Last action's bet increment. */
    private int betIncrement;

    /**
     * Constructor.
     * 
     * @param name
     *            The player's name.
     * @param cash
     *            The player's starting amount of cash.
     */
    public Player(String name, int cash) {
        this.name = name;
        this.cash = cash;
        //this.client = client;

        hand = new Hand();

        resetHand();
    }
    

    /**
     * Prepares the player for another hand.
     */
    public void resetHand() {
        hand.removeAllCards();
        hasCards = false;
        resetBet();
    }
    
    /**
     * Resets the player's bet.
     */
    public void resetBet() {
        bet = 0;
        action = null;
        raises = 0;
        allInPot = 0;
        betIncrement = 0;
    }
    
    /**
     * Sets the hole cards.
     */
    public void setCards(List<Card> cards) {
        hand.removeAllCards();
        if (cards == null) {
            hasCards = false;
        } else {
            if (cards.size() == 2) {
                hand.addCards(cards);
                hasCards = true;
                System.out.format("[CHEAT] %s's cards:\t%s\n", name, hand);
            } else {
                throw new IllegalArgumentException("Invalid number of cards");
            }
        }
    }
    
	/**
	 * Returns whether the player has his hole cards dealt.
	 * 
	 * @return True if the hole cards are dealt, otherwise false.
	 */
    public boolean hasCards() {
        return hasCards;
    }
    
    /**
     * Returns the player's name.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the player's current amount of cash.
     *
     * @return The amount of cash.
     */
    public int getCash() {
        return cash;
    }
    
    /**
     * Returns whether the player is broke.
     *
     * @return True if the player is broke, otherwise false.
     */
    public boolean isBroke() {
        return (cash == 0);
    }
    
    /**
     * Returns the player's current bet.
     *
     * @return The current bet.
     */
    public int getBet() {
        return bet;
    }
    
    /**
     * Returns the number of raises the player has done in this betting round.
     * 
     * @return The number of raises.
     */
    public int getRaises() {
        return raises;
    }
    
    /**
     * Returns the player's action.
     *
     * @return  the action
     */
    public Action getAction() {
        return action;
    }
    
    /**
     * Returns the bet increment of the last action.
     * 
     * @return The bet increment.
     */
    public int getBetIncrement() {
        return betIncrement;
    }
    
    /**
     * Returns the player's hand of cards.
     *
     * @return The hand of cards.
     */
    public Hand getHand() {
        return hand;
    }
    
    /**
     * Returns the player's hole cards.
     *
     * @return The hole cards.
     */
    public Card[] getCards() {
        return hand.getCards();
    }
    
    /**
     * Posts the small blind.
     * 
     * @param blind
     *            The small blind.
     */
    public void postSmallBlind(int blind) {
        action = Action.SMALL_BLIND;
        cash -= blind;
        bet += blind;
    }
    
    /**
     * Posts the big blinds.
     * 
     * @param blind
     *            The big blind.
     */
    public void postBigBlind(int blind) {
        action = Action.BIG_BLIND;
        cash -= blind;
        bet += blind;
    }
    
    /**
     * Returns the part of the pot this player has a stake in when all-in.
     *  
     * @return The all-in pot.
     */
    public int getAllInPot() {
        return allInPot;
    }
    
    /**
     * Sets the part of the pot this player has a stake in when all-in.
     * 
     * @param allInPot
     *            The all-in pot.
     */
    public void setInAllPot(int allInPot) {
        this.allInPot = allInPot;
    }
    
	/**
	 * Asks the player to act and returns his selected action.
	 * 
	 * Determining the player's action is handled by the client application.
	 * 
	 * @param action
	 *            The allowed actions.
	 * @param minBet
	 *            The minimum bet.
	 * @param currentBet
	 *            The current bet.
	 * 
	 * @return The selected action.
	 */
    public Action act(Table table, Action action, int minBet, int currentBet) {
        //player do action, in web because it need push pattern, can't use pull pattern, it need implement in out side
        //action = client.act(actions);
        switch (action) {
            case CHECK:
                break;
            case CALL:
                betIncrement = currentBet - bet;
                if (betIncrement > cash) {
                    //TODO: All-in with partial Call.
                    betIncrement = cash;
                }
                cash -= betIncrement;
                bet += betIncrement;
                break;
            case BET:
                betIncrement = minBet;
                if (betIncrement >= cash) {
                    //TODO: All-in with partial Bet.
                    betIncrement = cash;
                }
                cash -= betIncrement;
                bet += betIncrement;
                raises++;
                break;
            case RAISE:
                currentBet += minBet;
                betIncrement = currentBet - bet;
                cash -= betIncrement;
                bet += betIncrement;
                raises++;
                break;
            case FOLD:
                hand.removeAllCards();
                break;
        }
        table.onAction(action, this);
        return action;
    }
    
    /**
     * Wins the pot.
     * 
     * @param pot
     *            The pot.
     */
    public void win(int pot) {
        cash += pot;
    }
    
    /**
     * Returns a clone of this player with only public information.
     * 
     * @return The cloned player.
     */
    public Player publicClone() {
        Player clone = new Player(name, cash);
        clone.hasCards = hasCards;
        clone.bet = bet;
        clone.raises = raises;
        clone.action = action;
        return clone;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }
    
}
