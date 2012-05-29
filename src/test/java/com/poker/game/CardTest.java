package com.poker.game;


import org.junit.Assert;
import org.junit.Test;

public class CardTest {
    /**
     * Tests the basics (good-weather).
     */
    @Test
    public void basics() {
        Card card = new Card(CardRank.TEN, CardSuit.HEARTS);
        Assert.assertNotNull(card);
        Assert.assertEquals(CardRank.TEN, card.getRank());
        Assert.assertEquals(CardSuit.HEARTS, card.getSuit());

        card = new Card(CardRank.TEN.ordinal(), CardSuit.HEARTS.ordinal());
        Assert.assertNotNull(card);
        Assert.assertEquals(CardRank.TEN.ordinal(), card.getRankValue());
        Assert.assertEquals(CardSuit.HEARTS.ordinal(), card.getSuitValue());
        Assert.assertEquals("Th", card.toString());
        card = new Card("   As "); // Automatic trimming.
        Assert.assertNotNull(card);
        Assert.assertEquals(CardRank.ACE, card.getRank());
        Assert.assertEquals(CardSuit.SPADES, card.getSuit());
        Assert.assertEquals("As", card.toString());
    }

    /**
     * Tests the constructors (bad-weather).
     */
    @Test
    public void testConstructors() {
        @SuppressWarnings("unused")
        Card card = null;

        // Numeric rank too low.
        try {
            card = new Card(-1, 0);
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Numeric rank too high.
        try {
            card = new Card(Card.NO_OF_RANKS, 0);
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Numeric suit too low.
        try {
            card = new Card(0, -1);
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Numeric suit too high.
        try {
            card = new Card(0, Card.NO_OF_SUITS);
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Null string.
        try {
            card = new Card(null);
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Empty string.
        try {
            card = new Card("");
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // String too short.
        try {
            card = new Card("A");
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // String too long.
        try {
            card = new Card("Ahx");
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Unknown rank character.
        try {
            card = new Card("xh");
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Unknown rank character.
        try {
            card = new Card("xh");
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Unknown suit character.
        try {
            card = new Card("Ax");
            Assert.fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Tests the card ordering.
     */
    @Test
    public void sortOrder() {
        // Diamond is lower, Clubs is higher.
        Card _2d = new Card("2d");
        Card _3d = new Card("3d");
        Card _2c = new Card("2c");
        Card _3c = new Card("3c");
        Assert.assertEquals(_2d, _2d);
        Assert.assertFalse(_2d.equals(_3d));
        Assert.assertFalse(_2d.equals(_2c));
        Assert.assertEquals(0, _2d.hashCode());
        Assert.assertEquals(1, _2c.hashCode());
        Assert.assertEquals(4, _3d.hashCode());
        Assert.assertEquals(5, _3c.hashCode());
        Assert.assertTrue(_2d.compareTo(_2d) == 0);
        Assert.assertTrue(_2d.compareTo(_3d) < 0);
        Assert.assertTrue(_3d.compareTo(_2d) > 0);
        Assert.assertTrue(_2d.compareTo(_2c) < 0);
        Assert.assertTrue(_2c.compareTo(_2d) > 0);
    }

}

