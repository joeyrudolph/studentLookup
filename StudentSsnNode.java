/*
StudentSsnNode.java
Aaron Saporito
4/2/22

This program implements a modified StudentSsnNode object which can
be used to store information and be linked to other StudentSsnNodes.
It stores a name and SSN. There are provided getter and 
setter methods in the class. This StudentSsnNode contains two instance
variables for 2 children, left and right.
*/
import java.io.Serializable;

public class StudentSsnNode implements Serializable {
	private StudentSsnNode left;
	private StudentSsnNode right;
	private Student student;

	public StudentSsnNode(Student s) {
		left = null;	
		right = null;
		student = s;
	}


	//Getters for left and right children
	public StudentSsnNode getRight() {
		return right;
	}
	
	public StudentSsnNode getLeft() {
		return left;
	}

	public Student getStudent() {
		return student;
	}


	//Setters for left and right children.
	public void setRight(StudentSsnNode right) {
		this.right = right;
	}
	
	public void setLeft(StudentSsnNode left) {
		this.left = left;
	}
		
}