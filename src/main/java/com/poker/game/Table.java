// This file is part of the 'texasholdem' project, an open source
// Texas Hold'em poker application written in Java.
//
// Copyright 2009 Oscar Stigter
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// This file is part of the 'com.poker.game' project, an open source
// Texas Hold'em poker application written in Java.
//
// Copyright 2009 Oscar Stigter
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.poker.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Limit Texas Hold'em poker table.
 * 
 * Controls the com.poker.game flow for a single poker table.
 * 
 * @author Oscar Stigter
 */
public class Table {
    
    /** The maximum number of bets or raises in a single hand per player. */
    private static final int MAX_RAISES = 4;
    
    /** The size of the big blind. */
    private final int bigBlind;
    
    /** The players at the table. */
    private final List<Player> players;
    
    /** The active players in the current hand. */
    private final List<Player> activePlayers;
    
    /** The deck of cards. */
    private final Deck deck;
    
    /** The community cards on the board. */
    private final List<Card> board;
    
    /** The current dealer position. */
    private int dealerPosition;

    /** The current dealer. */
    private Player dealer;

    /** The position of the acting player. */
    private int actorPosition;
    
    /** The acting player. */
    private Player actor;

    /** The minimum bet in the current hand. */
    private int minBet;
    
    /** The current bet in the current hand. */
    private int bet;
    
    /** The pot in the current hand. */
    private int pot;
    
    /** Whether the com.poker.game is over. */
    private boolean gameOver;
    
    /**
     * Constructor.
     * 
     * @param bigBlind
     *            The size of the big blind.
     */
    public Table(int bigBlind) {
        this.bigBlind = bigBlind;
        players = new ArrayList<Player>();
        activePlayers = new ArrayList<Player>();
        deck = new Deck();
        board = new ArrayList<Card>();
    }
    
    /**
     * Adds a player.
     * 
     * @param player
     *            The player.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    /**
     * Main com.poker.game loop.
     */
    public void start() {
        resetGame();
        while (!gameOver) {
            playHand();
        }
        notifyMessage("Game over.");
    }
    
    /**
     * Resets the com.poker.game.
     */
    private void resetGame() {
        dealerPosition = -1;
        actorPosition = -1;
        gameOver = false;
        for (Player player : players) {
            player.getClient().joinedTable(bigBlind, players);
        }
    }
    
    /**
     * Plays a single hand.
     */
    private void playHand() {
        resetHand();
        
        // Small blind.
        rotateActor();
        postSmallBlind();
        
        // Big blind.
        rotateActor();
        postBigBlind();
        
        // Pre-Flop.
        dealHoleCards();
        doBettingRound();
        
        // Flop.
        if (activePlayers.size() > 1) {
            bet = 0;
            dealCommunityCards("Flop", 3);
            doBettingRound();

            // Turn.
            if (activePlayers.size() > 1) {
                bet = 0;
                dealCommunityCards("Turn", 1);
                minBet = 2 * bigBlind;
                doBettingRound();

                // River.
                if (activePlayers.size() > 1) {
                    bet = 0;
                    dealCommunityCards("River", 1);
                    doBettingRound();

                    // Showdown.
                    if (activePlayers.size() > 1) {
                        bet = 0;
                        doShowdown();
                    }
                }
            }
        }
    }
    
    /**
     * Resets the com.poker.game for a new hand.
     */
    private void resetHand() {
        board.clear();
        bet = 0;
        pot = 0;
        notifyBoardUpdated();
        activePlayers.clear();
        for (Player player : players) {
            player.resetHand();
            if (!player.isBroke()) {
                activePlayers.add(player);
            }
        }
        dealerPosition = (dealerPosition + 1) % players.size();
        dealer = players.get(dealerPosition);
        deck.shuffle();
        actorPosition = dealerPosition;
        minBet = bigBlind;
        bet = minBet;
        for (Player player : players) {
            player.getClient().handStarted(dealer);
        }
        notifyPlayersUpdated(false);
        notifyMessage("New hand, %s is the dealer.", dealer);
    }

    /**
     * Rotates the position of the player in turn (the actor).
     */
    private void rotateActor() {
        if (activePlayers.size() > 0) {
            do {
                actorPosition = (actorPosition + 1) % players.size();
                actor = players.get(actorPosition);
            } while (!activePlayers.contains(actor));
            for (Player player : players) {
                player.getClient().actorRotated(actor);
            }
        } else {
            // Should never happen.
            throw new IllegalStateException("No active players left");
        }
    }
    
    /**
     * Posts the small blind.
     */
    private void postSmallBlind() {
        final int smallBlind = bigBlind / 2;
        actor.postSmallBlind(smallBlind);
        pot += smallBlind;
        notifyBoardUpdated();
        notifyPlayerActed();
    }
    
    /**
     * Posts the big blind.
     */
    private void postBigBlind() {
        actor.postBigBlind(bigBlind);
        pot += bigBlind;
        notifyBoardUpdated();
        notifyPlayerActed();
    }
    
    /**
     * Deals the Hole Cards.
     */
    private void dealHoleCards() {
        for (Player player : players) {
            player.setCards(deck.deal(2));
        }
        notifyPlayersUpdated(false);
        notifyMessage("%s deals the hole cards.", dealer);
    }
    
    /**
     * Deals a number of community cards.
     * 
     * @param phaseName
     *            The name of the phase.
     * @param noOfCards
     *            The number of cards to deal.
     */
    private void dealCommunityCards(String phaseName, int noOfCards) {
        for (int i = 0; i < noOfCards; i++) {
            board.add(deck.deal());
        }
        notifyPlayersUpdated(false);
        notifyMessage("%s deals the %s.", dealer, phaseName);
    }
    
    /**
     * Performs a betting round.
     */
    private void doBettingRound() {
        // Determine the number of active players.
        int playersToAct = activePlayers.size();
        // Determine the initial player and bet size.
        if (board.size() == 0) {
            // Pre-Flop; player left of big blind starts, bet is the big blind.
            bet = bigBlind;
        } else {
            // Otherwise, player left of dealer starts, no initial bet.
            actorPosition = dealerPosition;
            bet = 0;
        }
        notifyBoardUpdated();
        while (playersToAct > 0) {
            rotateActor();
            Set<Action> allowedActions = getAllowedActions(actor);
            Action action = actor.act(allowedActions, minBet, bet);
            if (!allowedActions.contains(action)) {
                String msg = String.format("Illegal action (%s) from player %s!", action, actor);
                throw new IllegalStateException(msg);
            }
            playersToAct--;
            switch (action) {
                case CHECK:
                    // Do nothing.
                    break;
                case CALL:
                    pot += actor.getBetIncrement();
                    break;
                case BET:
                    bet = minBet;
                    pot += actor.getBetIncrement();
                    playersToAct = activePlayers.size();
                    break;
                case RAISE:
                    bet += minBet;
                    pot += actor.getBetIncrement();
                    if (actor.getRaises() == MAX_RAISES) {
                        // Max. number of raises reached; other players get one more turn.
                        playersToAct = activePlayers.size() - 1;
                    } else {
                        // Otherwise, all players get another turn.
                        playersToAct = activePlayers.size();
                    }
                    break;
                case FOLD:
                    actor.setCards(null);
                    activePlayers.remove(actor);
                    if (activePlayers.size() == 1) {
                        // The player left wins.
                        playerWins(activePlayers.get(0));
                        playersToAct = 0;
                    }
                    break;
                default:
                    throw new IllegalStateException("Invalid action: " + action);
            }
            if (actor.isBroke()) {
                actor.setInAllPot(pot);
            }
            notifyBoardUpdated();
            notifyPlayerActed();
        }
        
        // Reset player's bets.
        for (Player player : activePlayers) {
            player.resetBet();
        }
        notifyBoardUpdated();
        notifyPlayersUpdated(false);
    }

    /**
     * Returns the allowed actions of a specific player.
     * 
     * @param player
     *            The player.
     * 
     * @return The allowed actions.
     */
    private Set<Action> getAllowedActions(Player player) {
        int actorBet = actor.getBet();
        Set<Action> actions = new HashSet<Action>();
        if (bet == 0) {
            actions.add(Action.CHECK);
            if (player.getRaises() < MAX_RAISES) {
                actions.add(Action.BET);
            }
        } else {
            if (actorBet < bet) {
                actions.add(Action.CALL);
                if (player.getRaises() < MAX_RAISES) {
                    actions.add(Action.RAISE);
                }
            } else {
                actions.add(Action.CHECK);
                if (player.getRaises() < MAX_RAISES) {
                    actions.add(Action.RAISE);
                }
            }
        }
        actions.add(Action.FOLD);
        return actions;
    }
    
    /**
     * Performs the Showdown.
     */
    private void doShowdown() {
	System.out.print("Board: ");
	for (Card card : board) {
	    System.out.print(card + " ");
	}
	System.out.println();
        // Look at each hand value, sorted from highest to lowest.
        Map<HandValue, List<Player>> rankedPlayers = getRankedPlayers();
        for (HandValue handValue : rankedPlayers.keySet()) {
            // Get players with winning hand value.
            List<Player> winners = rankedPlayers.get(handValue);
            if (winners.size() == 1) {
                // Single winner.
                Player winner = winners.get(0);
                winner.win(pot);
                notifyBoardUpdated();
                notifyPlayersUpdated(true);
                notifyMessage("%s wins the pot.", winner);
                break;
            } else {
                // Tie; share the pot amongs winners.
                int tempPot = pot;
                StringBuilder sb = new StringBuilder("Tie: ");
                for (Player player : winners) {
                    // Determine the player's share of the pot.
                    int potShare = player.getAllInPot();
                    if (potShare == 0) {
                        // Player is not all-in, so he competes for the whole pot.
                        potShare = pot / winners.size();
                    }
                    // Give the player his share of the pot.
                    player.win(potShare);
                    tempPot -= potShare;
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(String.format("%s wins $%d\n", player, potShare));
                    // If there is no more pot to divide, we're done.
                    if (tempPot == 0) {
                        break;
                    }
                }
                notifyBoardUpdated();
                notifyPlayersUpdated(true);
                notifyMessage(sb.append('.').toString());
                if (tempPot > 0) {
                    throw new IllegalStateException("Pot not empty after sharing between winners");
                }
                break;
            }
        }
    }
    
    /**
     * Returns the active players mapped and sorted by their hand value.
     * 
     * The players are sorted in descending order (highest value first).
     * 
     * @return The active players mapped by their hand value (sorted). 
     */
    private Map<HandValue, List<Player>> getRankedPlayers() {
	Map<HandValue, List<Player>> winners = new TreeMap<HandValue, List<Player>>();
	for (Player player : activePlayers) {
            // Create a hand with the community cards and the player's hole cards.
            Hand hand = new Hand(board);
            hand.addCards(player.getCards());
            // Store the player together with other players with the same hand value.
            HandValue handValue = new HandValue(hand);
            System.out.format("%s: %s\n", player, handValue);
            List<Player> playerList = winners.get(handValue);
            if (playerList == null) {
        	playerList = new LinkedList<Player>();
            }
            playerList.add(player);
            winners.put(handValue, playerList);
	}
	return winners;
    }
    
    /**
     * Let's a player win the pot.
     * 
     * @param player
     *            The winning player.
     */
    private void playerWins(Player player) {
        player.win(pot);
        pot = 0;
        notifyBoardUpdated();
        notifyMessage("%s wins.", player);
    }
    
    /**
     * Notifies listeners with a custom com.poker.game message.
     * 
     * @param message
     *            The formatted message.
     * @param args
     *            Any arguments.
     */
    private void notifyMessage(String message, Object... args) {
        message = String.format(message, args);
        for (Player player : players) {
            player.getClient().messageReceived(message);
        }
    }
    
    /**
     * Notifies clients that the board has been updated.
     */
    private void notifyBoardUpdated() {
        for (Player player : players) {
            player.getClient().boardUpdated(board, bet, pot);
        }
    }

    /**
     * Notifies clients that one or more players have been updated.
     * 
     * A player's secret information is only sent its own client; other clients
     * see only a player's public information.
     */
    private void notifyPlayersUpdated(boolean showdown) {
        for (Player playerToNotify : players) {
            for (Player player : players) {
                if (!showdown && !player.equals(playerToNotify)) {
                    // Hide secret information to other players.
                    player = player.publicClone();
                }
                playerToNotify.getClient().playerUpdated(player);
            }
        }
    }
    
    /**
     * Notifies clients that a player has acted.
     */
    private void notifyPlayerActed() {
        for (Player p : players) {
            Player playerInfo = p.equals(actor) ? actor : actor.publicClone();
            p.getClient().playerActed(playerInfo);
        }
    }
    
}
