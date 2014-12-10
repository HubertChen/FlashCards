/** 
 * Flashcard model class.
 *
 * @author: Hubert Chen
 */
package models;

public class Flashcard { 
	private int deckId;
	private int id;
	private String front;
	private String back;
	private String picture;

	public Flashcard() {}

	/**
	 * Constructor to automatically set both sides of a 
	 * Flashcard
	 * 
	 * @param aFront Front content
	 * @param aBack Back content
	 */
	public Flashcard(String aFront, String aBack) {
		front = aFront;
		back = aBack;
	}

	/** 
	 * Gets the content on the front of the Flashcard
	 *
	 * @return String of the front content
	 */
	public String getFront() { return front; }

	/** 
	 * Gets the content on the back of the Flashcad
	 *
	 * @return String of the back content
	 */
	public String getBack() { return back; }
	
	/**
	 * Gets the id of the Flashcard
	 *
	 * @return ID of flashcard
	 */
	public int getId() { return id; }

	/** 
	 * Gets the ID of the deck
	 *
	 * @return ID of the deck in which the flashcard belongs
	 */
	public int getDeckId() { return deckId; }

	/** 
	 * Gets the picture file name
	 *
	 * @return File name of picture
	 */
	public String getPicture() { return picture; }

	/**
	 * Sets the content on the front of the Flashcard
	 * 
	 * @param newFront String of the new content
	 */
	public void setFront(String newFront) {
		front = newFront;
	}

	/**
	 * Sets the content on the back of the Flashcard
	 * 
	 * @param newBack String of the new content
	 */
	public void setBack(String newBack) {
		back = newBack;
	}

	/** 
	 * Sets ID of the Flashcard
	 *
	 * @param newId New ID of the flashcard
	 */
	public void setId(int newId) {
		id = newId;
	}

	/**
	 * Sets ID of the deck in which this card belongs
	 *
	 * @param newDeckID New ID of deck
	 */
	public void setDeckId(int newDeckId) {
		deckId = newDeckId;
	}

	/** 
	 * Sets the picture associated with the card
	 *
	 * @param newPicture New Picture
	 */
	public void setPicture(String newPicture) {
		picture = newPicture;
	}
}
