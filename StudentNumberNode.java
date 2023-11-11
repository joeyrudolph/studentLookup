/*
StudentNumberNode.java
Aaron Saporito
4/2/22

This program implements a modified StudentNumberNode object which can
be used to store information and be linked to other StudentNumberNodes.
It stores a name and SSN. There are provided getter and 
setter methods in the class. This StudentNumberNode contains two instance
variables for 2 children, left and right.
*/
import java.io.Serializable;

public class StudentNumberNode implements Serializable {
	private StudentNumberNode left;
	private StudentNumberNode right;
	private Student student;

	public StudentNumberNode(Student s) {
		left = null;	
		right = null;
		student = s;
	}


	//Getters for left and right children
	public StudentNumberNode getRight() {
		return right;
	}
	
	public StudentNumberNode getLeft() {
		return left;
	}

	public Student getStudent() {
		return student;
	}


	//Setters for left and right children.
	public void setRight(StudentNumberNode right) {
		this.right = right;
	}
	
	public void setLeft(StudentNumberNode left) {
		this.left = left;
	}
		
}