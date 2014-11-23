/** 
 * Deck model class.
 *
 * @author: Hubert Chen
 */
package models;

import java.util.LinkedList;

public class Deck { 
	private LinkedList<Flashcard> flashcards;

	/**
	 * Public constructor that instantiates the deck
	 */
	public Deck(){ flashcards = new LinkedList<Flashcard>(); }

	/**
	 * Takes a flashcard and adds it to the end of the deck
	 *
	 * @param front String value for the front of the card
	 * @param back String value for the back of the card
	 */
	public void addCard(String front, String back) {
		if(front == null || back == null)
			return;

		flashcards.add(new Flashcard(front, back));
	}

	/**
	 * Takes a flashcard and adds it to the given index of the deck.
	 * Other flashcards are all pushed back.
	 *
	 * @param index Index flashcard is to be inserted
	 * @param front String vlaue for the front of the card
	 * @param back String value for the back of the card
	 */
	public void addCard(int index, String front, String back) {
		if(front == null || back == null)
			return;

		try {
			flashcards.add(index, new Flashcard(front, back));
		} catch (IndexOutOfBoundsException error) {
			System.out.println(
				"ERROR: Flashcard could not be inserted into the deck." + 
				"\n Index: " + index +
				"\n Front Content: " + front +
				"\n Back Content: " + back
			);
			return;
		}
	}

	/**
	 * Gets a flashcard within the deck, given an index
	 *
	 * @param index Index of needle card (0 starting index)
	 * @return Flashcard object at the given index or null if not found
	 */
	public Flashcard getCard(int index) {
		try {
			Flashcard card = flashcards.get(index);
			return card;
		} catch(IndexOutOfBoundsException error) {
			return null;
		}
	}

	/**
	 * Removes a card given its index
	 *
	 * @param index Index of the Flashcard to be removed.
	 */
	public void removeCard(int index) {
			try {
				flashcards.remove(index);
			} catch(IndexOutOfBoundsException error) {
				System.out.println(
					"Error: Flashcard could not be removed from deck." + 
					"\n Index: " + index
				);
				return;
			}
	}

	/**
	 * Removes the first card within the deck
	 */
	public void removeFirstCard() {
		if(flashcards.size() < 0)
			return;

		flashcards.removeFirst();
	}

	/**
	 * Removes the last card within the deck
	 */
	public void removeLastCard() {
		if(flashcards.size() < 0) 
			return;

		flashcards.removeLast();
	}

	/** 
	 * Returns the current deck
	 *
	 * @return Deck
	 */
	public LinkedList<Flashcard> getDeck() { return flashcards; }

	/**
	 * Sets the deck with a new deck
	 * 
	 * @param newList New deck
	 */
	public void setDeck(LinkedList<Flashcard> newDeck){
			flashcards = newDeck;
	}
}
