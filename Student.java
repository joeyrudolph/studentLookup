/*
	Program name: Student.java
	Name: Aaron Saporito
	Date: 4/21/22

	Description: A Node for students that contains
	personal information as well as a queue for their ideas.
*/
import java.io.Serializable;

public class Student implements Serializable {
	private String lastName;
	private String email;

	private int ssn;
	private int shortSsn;
	private int ideaAverage;
	private int studentNumber;

	private IdeaQueue ideas;

	public Student() {
		lastName = "";
		email = "";

		ssn = 0;
		shortSsn = 0;
		studentNumber = 0;
		ideaAverage = 0;

		ideas = null;		
	}

	public Student(String name, int ssn, String email, int studentNumber) {
		this.lastName = name;
		this.email = email;

		this.ssn = ssn;
		this.shortSsn = ssn % 1000;
		this.studentNumber = studentNumber;

		this.ideas = new IdeaQueue();
	}

	/**
	 * Getters 
	 **/
	public String getLastName() {
		return lastName;
	}

	public int getSSN() {
		return ssn;
	}

	public int getShortSsn() {
		return shortSsn;
	}

	public String getEmail() {
		return email;
	}

	public int getIdeaAverage() {
		return ideaAverage;
	}

	public IdeaQueue getIdeas() {
		return ideas;
	}


	public int getStudentNumber() {
		return studentNumber;
	}
	/**
	 * Setters
	 **/ 
	public void setLastName(String name) {
		this.lastName = name;
	}

	public void setSSN(int ssn) {
		this.ssn = ssn;
		this.shortSsn = ssn % 1000;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//TBD
	public void setIdeaAverage() {
		ideaAverage = ideas.getAverage();
	}

	public void addIdea(Idea idea) {
		ideas.enqueue(idea);
		setIdeaAverage();
	}

	public void setStudentNumber(int num) {
		this.studentNumber = num;
	}


	//Prints student details
	public void print() {
		System.out.println("Name: " + lastName);
		System.out.println("Email: " + email);
		System.out.println("SSN: " + ssn);
		System.out.println("Student #: " + studentNumber);
		System.out.println("Idea Average: " + ideaAverage);
	}
}