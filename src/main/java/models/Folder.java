/**
 * Folder model class
 *
 * @author Hubert Chen
 */
package models;

import java.util.List;
import java.util.LinkedList;

public class Folder {
	private String name;
	private List<Deck> decks;

	public Folder() {
		decks = new LinkedList<Deck>();
	}

	/**
	 * Constructor to instantiate the name
	 * 
	 * @param name Name of the Folder
	 */
	public Folder(String name) {
		this.name = name;
	}

	/**
	 * Adds a deck to the folder without a description
	 *
	 * @param deckName The new Deck's name the user wants to add
	 */
	public void addDeck(String deckName) {
		decks.add(new Deck(deckName, ""));
	}
	/**
	 * Adds a deck to the folder with a description
	 * 
	 * @param deckName The new Deck's name the user wants to add
	 * @param description The description of the new deck
	 */
	public void addDeck(String deckName, String description) {
		decks.add(new Deck(deckName, description));
	}

	/**
	 * Finds a deck's position given a name.
	 *
	 * Returns -1 if not found
	 * 
	 * @param name Name of the deck who's index is to be found
	 */
	private int find(String name) {
		int counter = 0;

		for(Deck deck : decks) {
			if(deck.getName().equals(name))
				return counter;

			counter++;
		}	

		return -1;
	}

	/**
	 * Removes a deck from the Folder given an index
	 *
	 * @param index Index of the deck to be removed
	 */
	public void removeDeck(int index) {
		decks.remove(index);
	}	

	/**
	 * Removes a deck from the Folder given the name of the Deck
	 *
	 * @param name Name of the folder to be removed
	 */
	public void removeDeck(String name) {
		int index = find(name);

		if(index != -1) 
			decks.remove(index);
		else
			return;
	}

	/**
	 * Returns the current name of fodler
	 * @return Returns current name of Folder
	 */
	public String getName() { return name; }

	/**
	 * Returns the current decks
	 *
	 * @return Current decks
	 */
	public List<Deck> getDecks() { return decks; }

	/**
	 * Sets the Folder's name
	 *
	 * @param newName New name of the folder
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Sets the Folder's deck
	 *
	 * @param newDecks New deck to be saved
	 */
	public void setDecks(List<Deck> newDecks) { 
		decks = newDecks;
	}
}
