/**
IdeaQueue.java
Aaron Saporito
4/26/22

This program implements a queue for ideas in the form of an array. This is done by keeping track of the size and the front
of the array using 2 instance variables, n and front. It includes stadard queue methods such as front, enqueue and 
dequeue. It passes all given tests.
*/

import java.io.Serializable;

public class IdeaQueue implements Serializable {
	private int n; //Length
	private int front; //Front
	private Idea queue[];
	private int average;

	final private int QUEUE_SIZE = 10;
	
	public IdeaQueue() {
		n = 0;
		front = 0;
		average = 0;
		queue = new Idea[QUEUE_SIZE];
	}
	
	//Gets the Idea at the front index of the queue
	public Idea front() {
		return queue[front];
	}
	
	//Removes a Idea from the front of the queue.
	public Idea dequeue() {
		if (n != 0) {
			int pos = front;
			front = (front + 1) % QUEUE_SIZE; //Reassigns front, accounting for wrapping.
			
			n--; //Updates length
			return queue[pos];
		} else {
			return null;
		}
		
	}
	
	//Adds a Idea to the queue
	public Idea[] enqueue(Idea x) {
		if (n == 10) {
			dequeue();
		}

		int pos = (front + n) % QUEUE_SIZE; //Determines new position, accounting for wrapping.
		queue[pos] = x;
		n++;

		average = average + ((x.getRating() - average)/n);
		return queue;
	}
	
	//
	public boolean isEmpty() {
		return n==0;
	}
	
	// printQueue method for QueueA
	public void printQueue() {
		if (n == 0) {
			System.out.println("No ideas found for this student");
			return;
		}
		int tail = (front + n) % QUEUE_SIZE;
		
		if (front < tail)
		   for(int i = front; i < tail; i++) 
		       queue[i].print();
		else {
		   for(int i = front; i < QUEUE_SIZE; i++) 
		       queue[i].print();          
		   for(int i = 0; i < tail; i++) 
		       queue[i].print();
		}          
	}

	public int getFront() {
		return front;
	}
	public int getAverage() {
		return average;
	}
}