/*
Program name: Idea.java
Names: Aaron Saporito, Joey Rudolph
Date: 4/22/22

Desc: A node implementation for an idea. Each idea has an index,
ssn of the submitter, a rating, and a description. It also
supports using pointers to access other ideas.
*/
import java.io.Serializable;

public class Idea implements Serializable{
	private int index;
	private int ssn;
	private int rating;
	
	private String description;

	public Idea(int index, int ssn, String description, int rating) {
			this.index = index;
			this.ssn = ssn;
			this.description = description;
			this.rating = rating;
	}


	/** 
	 * Getters
	 **/
	public int getIndex() {
		return index;
	}

	public int getSsn() {
		return ssn;
	}

	public int getRating() {
		return rating;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Setters
	 **/
	public void setIndex(int index) {
		this.index = index;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//Method to print the details of an idea.
	public void print() {
		System.out.println("Master index: " + index);
		System.out.println("Description: " + description);
		System.out.println("Rating: " + rating);
		System.out.println("SSN: " + ssn + "\n");
	}
}