/**
 * RunProgram.java
 * 5/22
 * Aaron Saporito
 * 
 * This file acts as the core of this project. It houses the main function as well as 
 * all user interactions with the system. All data can be added, deleted and searched through
 * in appropriate times. In addition all data is serialized and persists between sessions.
 * */

import java.util.Scanner;
import java.io.*;

public class RunProgram {
	//Global variables 
	private static int ideaIndex;
	private static StudentNumberBST studentNumberTree;
	private static IdeaHeap masterIdeaList;
	private static SsnBST ssnBST;
	private static IdeaHash bonusIdeaHash;

	public static void main(String[] args) {
		//On start all variables are intialized as empty
		ideaIndex = 1;
		studentNumberTree = new StudentNumberBST(); 
		masterIdeaList = new IdeaHeap();
		ssnBST = new SsnBST();
		bonusIdeaHash = new IdeaHash();

		mainMenu();
	}


	/**
	 * Object Creation/Deletion Funcs
	 **/
	
	// This function creates a student only when valid
	// inputs are entered. (SSN and student number must be numbers)
	private static Student createStudent() {
		StudentSsnNode s1;
		StudentNumberNode s2;
		// Collect user data, if the data is invalid an error is thrown
		// and the function is called again.
		try {
			Scanner in = new Scanner(System.in); 

			System.out.print("Student last name: "); 
			String name = in.nextLine();

			System.out.print("\nStudent ssn: ");
			int ssn = Integer.parseInt(in.nextLine());

			System.out.print("\nStudent email: ");
			String email = in.nextLine();

			System.out.print("\nStudent #: ");
			int studentNum = Integer.parseInt(in.nextLine()); 

			Student student = new Student(name, ssn, email, studentNum);
			s1 = new StudentSsnNode(student);
			s2 = new StudentNumberNode(student);
			//Adds the student to both search trees.
			ssnBST.insert(s1);
			studentNumberTree.insert(s2);

			saveData(); //Updates the save file.

			System.out.println("Successfully added the student");

			//Presents the user with the option to add ideas to the new student
			addStudentIdea(s1.getStudent()); 

			return s1.getStudent();
		} catch (NumberFormatException e) { 
			System.out.println("Invalid input, please try again");
		}
		return createStudent(); //Called when the original input is invalid.
	}


	//Recursively add ideas to a student, allowing for ideas to be added in succession.
	private static void addStudentIdea(Student student) {
		Scanner in = new Scanner(System.in);
		

		//Prompt to allow for repeated idea insertion
		System.out.println("\nWould you like to add another idea to this student? (y/n)");
		String line = in.nextLine();

		if (line.equals("y")) {
			System.out.print("Description: ");
			String input = in.nextLine(); 

			//Ensures the input is at least 20chars.
			while (input.length() < 20) { 
				System.out.println("Description must be > than 20 char, try again");
				System.out.print("Description: ");
				input = in.nextLine();
			}
			String desc = input;

			//Get a valid rating.
			int rating = validateRating(in);

			Idea tempIdea = new Idea(ideaIndex, student.getSSN(), desc, rating);

			//Add to individual and master idea lists.
			student.addIdea(tempIdea);
			masterIdeaList.insert(tempIdea);
			bonusIdeaHash.insert(new IdeaNode(tempIdea));

			ideaIndex++; //Update global idea count

			System.out.println("**Added idea**\n");
			saveData(); //Store data

			addStudentIdea(student); //Recursive call
		} else if (line.equals("n")) { //Base case: no more ideas, back to main menu.
			mainMenu();
		} else {
			addStudentIdea(student);
		}
	}

	//Delete an idea from a student
	public static void deleteIdea(Student student) {
		student.getIdeas().dequeue(); //Only deletes from student's queue.
		System.out.println("Successfully deleted");
		saveData(); //Store data
	}

	//Delete an entire student
	private static void deleteStudent(Student student) {
		StudentSsnNode s1 = new StudentSsnNode(student);
		StudentNumberNode s2 = new StudentNumberNode(student);

		//Removes from both places that it is stored.
		ssnBST.delete(s1);
		studentNumberTree.delete(s2);

		System.out.println("Deleted: " + student.getLastName());
		saveData(); //Store data

		mainMenu();
	}


	/**
	 *  Search Functions 
	 **/ 

	//Search via student number
	private static Student studentNumberSearch() {
		Scanner in = new Scanner(System.in);
		int num;

		//Checks for a valid student number, if the input is nAn the function is
		// called again.
		System.out.print("Input the student's #: ");
		try {
			num = Integer.parseInt(in.nextLine());
		} catch (NumberFormatException e) { 
			System.out.println("Invalid input, try again.");
			return studentNumberSearch(); 
		}

		//Finds the student in the tree if the studet exists
		Student student;
		try {
			student = studentNumberTree.search(num).getStudent();
		} catch (NullPointerException e) {
			System.out.println("\n\n\nStudent not found.");
			return null;
		}

		return student;
	}

	//Search via SSN
	private static Student ssnSearch() {
		Scanner in = new Scanner(System.in);
		int num;

		//Checks for a valid SSN, if the input is nAn the function is
		// called again.
		System.out.print("Input the student's SSN: ");
		try {
			num = Integer.parseInt(in.nextLine());
		} catch (Exception e) { 
			System.out.println("Invalid input, try again.");
			return ssnSearch();
		}

		//Finds the student in the tree if the student exists
		Student student;
		try {
			student = ssnBST.search(num).getStudent();
		} catch (NullPointerException e) {
			System.out.println("\n\n\nStudent not found.");
			return null;
		}

		return student;
	}

	private static IdeaNode bonusSearch() {
		Scanner in = new Scanner(System.in);
		int num;

		System.out.print("Input the idea number: ");
		try {
			num = Integer.parseInt(in.nextLine());
		} catch (Exception e) { 
			System.out.println("Invalid input, try again.");
			return bonusSearch();
		}

		IdeaNode result = bonusIdeaHash.lookUp(num);
		if (result == null) {
			System.out.println("Idea not found");
			return null;
		} else {
			return result;
		}
	}

	 /**
	  *  Data persistence functions
	  **/ 
	
	//Save all data
	public static void saveData() {
		try {   
            //File streams
			FileOutputStream file = new FileOutputStream("data.ser");
			ObjectOutputStream out = new ObjectOutputStream(file);

            // Serialize each instance variable
			out.writeObject(studentNumberTree);
			out.writeObject(masterIdeaList);
			out.writeObject(ideaIndex);
			out.writeObject(ssnBST);
			out.writeObject(bonusIdeaHash);

			out.close();
			file.close();

		} //Error handling.
		catch(IOException e) {
			System.out.println("IOException is caught: " + e);
		}
	}

	//Read and reassign stored data
	public static void readData() {
		try {   
            // Read all instance var objects
			FileInputStream file = new FileInputStream("data.ser");
			ObjectInputStream in = new ObjectInputStream(file);

			StudentNumberBST numBst = (StudentNumberBST) in.readObject();
			IdeaHeap heap = (IdeaHeap) in.readObject();
			Integer index = (Integer) in.readObject();
			SsnBST socialBst = (SsnBST) in.readObject();
			IdeaHash ideaHash = (IdeaHash) in.readObject();

			//Assign the stored data to the live program variables.
			studentNumberTree = numBst;
			ideaIndex = index;
			masterIdeaList = heap;
			ssnBST = socialBst;
			bonusIdeaHash = ideaHash;

			in.close();
			file.close();
		} //Error handling. 
		catch(IOException ex) { 
	 		//System.out.println("IOException is caught: " + ex);
		}
		catch(ClassNotFoundException ex)
		{
	 		System.out.println("ClassNotFoundException is caught");
		}
	}

	 /**
	  * Menu/Input Functions
	  **/

	//Menu for all ideas.
	public static void masterIdeaMenu() {
		System.out.println("-------------------------------");
		System.out.println("|      Master Ideas Menu      |");
		System.out.println("-------------------------------");

		System.out.println("\nSelect an option:");
		System.out.println("[1]: Get best idea");
		System.out.println("[2]: Delete best idea");
		System.out.println("[3]: Return to main screen");

		Scanner in = new Scanner(System.in);

		String input = in.nextLine();
	 	//[1]: Get best idea from master idea list.
		if (input.equals("1")) {
			Idea idea = masterIdeaList.findMax();

			//Ensures there is an idea to find.
			if (idea != null) {
				System.out.println("Description: " + idea.getDescription());
				System.out.println("Rating: " + idea.getRating());
				System.out.println("Submitter SSN: " + idea.getSsn());
				System.out.println("Idea index: " + idea.getIndex());
			} else {
				System.out.println("No ideas found");
			}

	 	} // [2]: Remove the best idea
	 	else if (input.equals("2")) {
	 		if (masterIdeaList.getNumIdea() > 0) {
	 			masterIdeaList.deleteMax();
	 			System.out.println("Deleted idea from master list");
	 			saveData(); //Store data
	 		} else {
	 			System.out.println("No ideas found");
	 		}

	 	} // [3]: Return to main menu
	 	else if (input.equals("3")) {
	 		mainMenu();
	 	} //Error case
	 	else {
	 		System.out.println("Invalid input, try again.");
	 		masterIdeaMenu();
	 	}

	 	//Return to this menu after an action
	 	masterIdeaMenu();
	 }

	 //Menu for dealing with students
	 private static void studentMenu(Student student) {
	 	System.out.println("------------------------");
	 	System.out.println("|     Student Menu     |");
	 	System.out.println("------------------------");

	 	student.print();
	 	System.out.println("\nSelect an option:");
	 	System.out.println("[1]: Access student's ideas");
	 	System.out.println("[2]: Add another student");
	 	System.out.println("[3]: Delete this student");
	 	System.out.println("[4]: Change last name");
	 	System.out.println("[5]: Change email address");
	 	System.out.println("[6]: Return to main screen");

	 	Scanner in = new Scanner(System.in);
	 	
	 	String input = in.nextLine();
	 	//[1]: Acess student's ideas.
	 	if (input.equals("1")) {
	 		ideasMenu(student);

	 	} 
	 	// [2]: Add a student
	 	else if (input.equals("2")) {
	 		System.out.println("\n");
	 		createStudent();
	 	} 
	 	// [3]: Delete this student
	 	else if (input.equals("3")) {
	 		deleteStudent(student);
	 	} 
	 	// [4]: Change last name
	 	else if (input.equals("4")) {
	 		System.out.println("New last name: "); //Replaces Lastname
	 		student.setLastName(in.nextLine());
	 		saveData();

	 		System.out.println("Success");
	 		studentMenu(student);
	 	} 
	 	// [5]: Change email
	 	else if (input.equals("5")) {
	 		System.out.println("New email: ");
	 		student.setEmail(in.nextLine()); //Replaces the email with a new email.

	 		saveData();
	 		System.out.println("Success");
	 		studentMenu(student);
	 	} 
	 	// [6]: Return to main menu
	 	else if (input.equals("6")) {
	 		mainMenu();
	 	} 
	 	//Error case
	 	else {
	 		System.out.println("Invalid input, try again.");
	 		studentMenu(student);
	 	}
	 }

	 //Main menu and intial startup screen
	 private static void mainMenu() {
	 	System.out.println("------------------------------------------");
	 	System.out.println("|               Main Menu                 |");
	 	System.out.println("------------------------------------------");

	 	System.out.println("\nChoose an option:");
	 	System.out.println("[1]: Lookup student via SSN");
	 	System.out.println("[2]: Lookup student via student number");
	 	System.out.println("[3]: Access idea collection");
	 	System.out.println("[4]: Insert student");
	 	System.out.println("[5]: Print all students");
	 	System.out.println("[6]: Idea search via idea number (bonus)");
	 	System.out.println("[7]: Exit");
		readData(); //Updates instance variables with saved data.
		checkInput(); //Handles inputs on the main menu
	}

	//Menu for an individual students collection of up to 10 ideas.
	private static void ideasMenu(Student student) {
		System.out.println("------------------------");
		System.out.println("|      Ideas Menu      |");
		System.out.println("------------------------");

		//Prints the selected students ideas
		student.getIdeas().printQueue();

		System.out.println("\nSelect an option:");
		System.out.println("[1]: Delete oldest idea");
		System.out.println("[2]: Add an idea");
		System.out.println("[3]: Return to main screen");

		Scanner in = new Scanner(System.in);

		String input = in.nextLine();
	 	//[1]: Delete an idea from this student
		if (input.equals("1")) {
			deleteIdea(student);
			
			ideasMenu(student);
	 	} 
	 	// [2]: Add an idea to this student
	 	else if (input.equals("2")) {
	 		addStudentIdea(student);
	 	} 
	 	// [3]: Return to main menu
	 	else if (input.equals("3")) {
	 		mainMenu();
	 	} 
	 	//Error case
	 	else {
	 		System.out.println("Invalid input, try again.");
	 		ideasMenu(student);
	 	}

	 }

	 //Handles inputs for the mainMenu
	 private static void checkInput() {
	 	Scanner in = new Scanner(System.in);
	 	System.out.print("Input a number: ");
	 	String input = in.nextLine();

	 	// [1]: Search by SSN
	 	if(input.equals("1")) {
	 		Student student = ssnSearch();
	 		if (student == null) {
	 			mainMenu();
	 		} else {
	 			studentMenu(student);
	 		}
	 	} 
	 	// [2]: Search by Student #
	 	else if(input.equals("2")) {
	 		Student student = studentNumberSearch();
	 		if (student == null) {
	 			mainMenu();
	 		} else {
	 			studentMenu(student);
	 		}

	 	} 
	 	// [3]: Enter Idea Menu 
	 	else if (input.equals("3")) {
			//Enter master idea list menu.
	 		masterIdeaMenu();
		} 
		//Insert student
		else if (input.equals("4")) { 
			createStudent();
		} 
		// Print all students, ordered by student #
		else if (input.equals("5")) {
			System.out.println();

			studentNumberTree.traverse();
			
			System.out.println("-------------");
			mainMenu();
		} //
		else if (input.equals("6")) {
			IdeaNode out = bonusSearch();
			if (out == null) {
				mainMenu();
			} else {
				bonusIdeaMenu(out);
			}
		}
		//Exit program
		else if (input.equals("7")) {
			System.exit(0);
		}
		//Error case
		else {
			System.out.println("Invalid input, please try again");
			checkInput();
		}
	}

	//Bonus Idea menu for displaying and deleting idea via index
	private static void bonusIdeaMenu(IdeaNode i) {
		Scanner in = new Scanner(System.in);
		Idea idea = i.getIdea();

		System.out.println("------------------------");
		System.out.println("|      Idea Menu       |");
		System.out.println("------------------------");

		System.out.println("Idea #: " + idea.getIndex());
		System.out.println("SSN: " + idea.getSsn());
		System.out.println("Description: " + idea.getDescription());
		System.out.println("Rating: " + idea.getRating());

		System.out.println("\nWould you like to delete this idea? (y/n)"); 
		if (in.nextLine().equals("y")) {
			bonusIdeaHash.delete(i);
			saveData();
			System.out.println("Deleted idea");
		}
		mainMenu();
	}
	//Validates a rating input
	private static int validateRating(Scanner in) {
		System.out.print("Rating (0-100): "); 
		String input = in.nextLine();
		int rating;

		//First checks that the input is a number
		try {
			rating = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			//If it fails, it re-calls the function.
			System.out.println("Invalid rating");
			return validateRating(in);
		}

		//2nd check to confirm that the rating is within the valid 0-100 range.
		if (rating >= 0 && rating <= 100) {
			return rating;
		} else {
			//If it fails, it re-calls the function.
			System.out.println("Invalid rating");
			return validateRating(in);
		}
	}
}