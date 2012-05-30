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

package com.poker.game;

/**
 * Represents the value of a poker hand.
 * 
 * Implements the Comparable interface with <b>reversed</b> (descending sort) order.
 * 
 * @author woofgl
 */
public class HandValue implements Comparable<HandValue> {
    
    /** The hand. */
    private final Hand hand;
    
    /** The hand value type. */
    private final HandValueType type;
    
    /** The exact, numeric hand value. */
    private final int value;
    
    /**
     * Constructor.
     * 
     * @param hand
     *            The hand.
     */
    public HandValue(Hand hand) {
	this.hand = hand;
	HandEvaluator evaluator = new HandEvaluator(hand);
	type = evaluator.getType();
	value = evaluator.getValue();
    }
    
    /**
     * Returns the hand.
     * 
     * @return The hand.
     */
    public Hand getHand() {
	return hand;
    }
    
    /**
     * Returns the hand value type.
     * 
     * @return The hand value type.
     */
    public HandValueType getType() {
	return type;
    }
    
    /**
     * Returns a description of the hand value type.
     * 
     * @return The description of the hand value type.
     */
    public String getDescription() {
	return type.getDescription();
    }
    
    /**
     * Returns the exact, numeric hand value.
     * 
     * @return The exact, numeric hand value.
     */
    public int getValue() {
	return value;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return value;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (obj instanceof HandValue) {
	    return ((HandValue) obj).getValue() == value;
	} else {
	    return false;
	}
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return String.format("%s (%d)", type.getDescription(), value);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(HandValue handValue) {
	if (value > handValue.getValue()) {
	    return -1;
	} else if (value < handValue.getValue()) {
	    return 1;
	} else {
	    return 0;
	}
    }

}
