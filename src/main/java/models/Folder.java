/**
 * Folder model class
 *
 * @author Hubert Chen
 */
package models;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class Folder {
	private String name;
	private HashMap<String, Deck> decks;

	public Folder() {
		deck = new HashMap<String, Deck>();
	}

	public void addDeck(String deckName) {
		decks.put(deckName, new Deck());
	}

	public void removeDeck(String deckName) {
		decks.remove(deckName);
	}	

	public String getName() { return name; }

	public HashMap<String, Deck> getDecks() { return decks; }

	public void setName(String newName) {
		name = newName;
	}

	public void setDecks(HashMap<String, Deck> newDecks) {
		decks = newDecks;
	}
}
