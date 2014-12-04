/** 
 * Deck model class.
 *
 * @author: Hubert Chen
 */
package models;

import java.util.LinkedList;

public class Deck { 
	private String name;
	private String description;
	private LinkedList<Flashcard> flashcards;

	public Deck(){
		flashcards = new LinkedList<Flashcard>();
	}

	/**
	 * Constructor that instantiates the name and description
	 *
	 * @param name Name of the Deck
	 * @param description Description of the Deck
	 */
	public Deck(String name, String description) { 
		this.name = name;
		this.description = description;
	} 

	/**
	 * Adds a flashcard to the end of the deck
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
		if(index < 0 || index > flashcards.size() - 1)
			return;

		flashcards.add(index, new Flashcard(front, back));
	}

	/**
	 * Gets a flashcard within the deck, given an index
	 *
	 * @param index Index of needle card (0 starting index)
	 * @return Flashcard object at the given index or null if not found
	 */
	public Flashcard getCard(int index) {
		if(index < 0 || index > (flashcards.size() - 1))
			return null;
		
		return flashcards.get(index);
	}

	/**
	 * Removes a card given its index
	 *
	 * @param index Index of the Flashcard to be removed.
	 */
	public void removeCard(int index) {
		if(index < 0 || index > (flashcards.size() - 1))
			return;

		flashcards.remove(index);
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
	 * Returns the current deck (All the flashcards)
	 *
	 * @return The complete set of flashcards associated with this deck
	 */
	public LinkedList<Flashcard> getFlashcards() { return flashcards; }

	/**
	 * Returns the name of the deck
	 * 
	 * @return Deck name
	 */
	public String getName() { return name; }

	/** 
	 * Returns the description of the deck
	 * 
	 * @return Deck description
	 */
	public String getDescription() { return description; }

	/**
	 * Sets the current deck of flashcards to a new one
	 * 
	 * @param newList New set of flashcards
	 */
	public void setDeck(LinkedList<Flashcard> newFlashcards){
			flashcards = newFlashcards;
	}

	/** 
	 * Set the name of the name of the deck
	 * 
	 * @param newName New name of the deck
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Set the description of the deck
	 *
	 * @param newDescription New description of the deck
	 */
	public void setDescription(String newDescription) {
		description = newDescription;
	}
}
