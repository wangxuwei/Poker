package com.poker.game;

import javax.swing.border.Border;
import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Limit Texas Hold'em poker table.
 * <p/>
 * Controls the com.poker.game flow for a single poker table.
 *
 * @author woofgl
 */
public class Table {

    /**
     * The maximum number of bets or raises in a single hand per player.
     */
    private static final int MAX_RAISES = 4;

    /**
     * The size of the big blind.
     */
    private final int bigBlind;

    /**
     * The players at the table.
     */
    private final List<Player> players;

    /**
     * The active players in the current hand.
     */
    private final List<Player> activePlayers;

    /**
     * The deck of cards.
     */
    private final Deck deck;

    /**
     * The community cards on the board.
     */
    private final List<Card> board;

    /**
     * The current dealer position.
     */
    private int dealerPosition;

    /**
     * The current dealer.
     */
    private Player dealer;

    /**
     * The position of the acting player.
     */
    private int actorPosition;

    /**
     * The acting player.
     */
    private Player actor;

    /**
     * The minimum bet in the current hand.
     */
    private int minBet;

    /**
     * The current bet in the current hand.
     */
    private int bet;

    /**
     * The pot in the current hand.
     */
    private int pot;

    /**
     * Whether the com.poker.game is over.
     */
    private boolean gameOver;

    private final int DEFALT_MAX_PLAYER = 8;

    private final int maxPlayers = DEFALT_MAX_PLAYER;

    private final static Lock lock = new ReentrantLock();

    private final Condition actionChange;

    private Action lastAction = null;
    private Action currentAction = null;
    private Player currentActionPlayer = null;
    private Player lastActionPlayer = null;

    private TableState state = null;


    /**
     * Constructor.
     *
     * @param bigBlind The size of the big blind.
     */
    public Table(int bigBlind) {
        this.bigBlind = bigBlind;
        players = new ArrayList<Player>();
        activePlayers = new ArrayList<Player>();
        deck = new Deck();
        board = new ArrayList<Card>();
        actionChange = lock.newCondition();
    }

    /**
     * Adds a player.
     *
     * @param player   The player.
     * @param position the player site position
     */
    public void addPlayer(Player player, int position) {
        players.add(player);
    }

    /**
     * Main com.poker.game loop.
     */
    public void start() {
        resetGame();
        while (!gameOver) {
            try {
                playHand();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
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
        createState();
    }

    /**
     * Plays a single hand.
     */
    private void playHand() throws InterruptedException {
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
        createState();
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
            createState();
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
        createState();
    }

    /**
     * Posts the big blind.
     */
    private void postBigBlind() {
        actor.postBigBlind(bigBlind);
        pot += bigBlind;
        createState();
    }

    /**
     * Deals the Hole Cards.
     */
    private void dealHoleCards() {
        for (Player player : players) {
            player.setCards(deck.deal(2));
        }

        notifyMessage("%s deals the hole cards.", dealer);
    }

    /**
     * Deals a number of community cards.
     *
     * @param phaseName The name of the phase.
     * @param noOfCards The number of cards to deal.
     */
    private void dealCommunityCards(String phaseName, int noOfCards) {
        for (int i = 0; i < noOfCards; i++) {
            board.add(deck.deal());
        }

        notifyMessage("%s deals the %s.", dealer, phaseName);
    }

    /**
     * Performs a betting round.
     */
    //todo nedd reimplement
    private void doBettingRound() throws InterruptedException {
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

        while (playersToAct > 0) {
            rotateActor();
            Set<Action> allowedActions = getAllowedActions(actor);
            //Action action = actor.act(allowedActions, minBet, bet);
            lock.lock();
            try {

                while (currentAction == null) {
                    // This will wait until the value changes
                    actionChange.await();
                }
                Action action = currentAction;
                Player player = currentActionPlayer;
                lastAction = action;
                lastActionPlayer = player;

                currentAction = null;
                currentActionPlayer = null;
                if (player.equals(actor)) {
                    //not active actor, do nothing
                    continue;
                }


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
                createState();
            } finally {

                lock.unlock();
            }

            // Reset player's bets.
            for (Player player : activePlayers) {
                player.resetBet();
            }
            createState();
        }
    }

    /**
     * Returns the allowed actions of a specific player.
     *
     * @param player The player.
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
     * <p/>
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
     * @param player The winning player.
     */
    private void playerWins(Player player) {
        player.win(pot);
        pot = 0;
        notifyMessage("%s wins.", player);
    }

    /**
     * Notifies listeners with a custom com.poker.game message.
     *
     * @param message The formatted message.
     * @param args    Any arguments.
     */
    private void notifyMessage(String message, Object... args) {
        message = String.format(message, args);
        if (state == null) {
            createState();
        }
        state.setMessage(message);
    }



    private void createState() {
        state = new TableState(activePlayers, players, dealerPosition, actorPosition, board, pot, bigBlind, minBet);
    }




     void onAction(Action action, Player player) {
        lock.lock();
        try {
            this.currentAction = action;
            currentActionPlayer = player;
            actionChange.notifyAll();

        } finally {
            lock.unlock();
        }
    }

    public TableState getState() {
        if (state == null) {
            state = new TableState(activePlayers, players, dealerPosition, actorPosition, board, pot, bigBlind, minBet);
        }
        return state;
    }
}


