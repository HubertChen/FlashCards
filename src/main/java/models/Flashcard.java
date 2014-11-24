/** 
 * Flashcard model class.
 *
 * @author: Hubert Chen
 */
package models;

public class Flashcard { 
	private String front;
	private String back;

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
	 * @return String of the back content
	 */
	public String getFront() { return front; }

	public String getBack() { return back; }

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
}
