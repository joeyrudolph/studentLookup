// A max heap class for the ideas in the final project
// Jake Vaughan
// 28 April 2022
import java.io.Serializable;

public class IdeaHeap implements Serializable {

    private final Idea[] h = new Idea[511]; //creating array
    private int n; // counter of elements

    public IdeaHeap() {
        n = 0; // creates heap, sets n to 0
    }

    /*
    getters and booleans - number of ideas in arr, root of arr, if empty, if leaf, parent, right/left child
     */
    public int getNumIdea(){ // returns number of ideas in the heap
        return n;
    }

    public Idea findMax() {
        if (n != 0) {
            return h[0]; //returns root of Heap
        } else {
            System.out.println("The list of ideas is empty");
            return null;
        }
    }

    public boolean isEmpty() {
        return n == 0; // checks to see if n is 0, if it is it returns true
    }

    private boolean isLeaf(int i) {
        return i > (n / 2) && i <= n; //checks to see if any node at index i has any children
    }

    private int parent(int i) {
        return (i - 1) / 2; // gets parent of node at index i
    }

    private int rightChild(int i) {
        return (2 * i) + 2; // gets right child of node at index i
    }

    private int leftChild(int i) {
        return (2 * i) + 1; // gets left child of node at index i
    }

    /*
    modifiers for the arr - insert, swap, deleteMax, maxHeapify
     */

    public void insert(Idea x) {
        int current = n; // pointer
        h[n] = x; // putting new node at last index n
        heapify(0);
        while (current > 1 && h[current].getRating() > h[parent(current)].getRating()) { //compare parent to our node
            swap(current, parent(current)); //bubbles node up if necessary, if not it stays where it is in Heap
            current = parent(current); //compares again
        }
        n++;
    }

    private void swap(int i, int j) { //swapping two nodes
        Idea temp = h[i]; //pointer
        h[i] = h[j]; //set node at index i to the spot where node at index j is
        h[j] = temp; //sets node at index j to temp
    }

    public void deleteMax() { //deletes the root of Heap
        swap(0, n-1); // swaps root and last node in arr
        h[n-1] = null; // sets last node to null, "deleting it"
        heapify(0); // sort
        n--; // decrement
    }

    private void heapify(int i) { // this method reorders after maxHeap is called, intended to be called on the root
        int left = leftChild(i); // pretty much a sort method, MAX HEAP****
        int right = rightChild(i);
        

        if (!isLeaf(i)) { //checking to see if there are children
            if (h[left] != null && h[right] != null) { // if both children are not null
                if (h[left].getRating() >= h[right].getRating()) { // if left child is greater than right
                    if (h[left].getRating() >= h[i].getRating()) { // if left child is greater than the root
                        swap(i, left); // swap
                        heapify(left); // sort
                    }
                }
                if (h[right].getRating() >= h[left].getRating()) { // if right child is greater than left
                    if (h[right].getRating() >= h[i].getRating()) { // if right child is greater than root
                        swap(i, right); // swap
                        heapify(right); // sort
                    }
                }
            }
            if (h[left] == null && h[right] != null) { // if there is no left child but a right child
                if (h[right].getRating() >= h[i].getRating()) { // if the right child is greater than root
                    swap(i, right); // swap
                    heapify(right); // sort
                }
            }
            if (h[right] == null && h[left] != null) { // if there is no right child but a left child
                if (h[left].getRating() >= h[i].getRating()) { // if the left child is greater than root
                    swap(i, left); // swap
                    heapify(left); // sort
                }
            }
        }
    }

    // printer
    public void printHeap () { // prints every element in the heap, in order
        if (isEmpty()){
            System.out.println("Heap is empty");
        }
        else {
            for (int i = 0; i < n; i++) { // i increments from 0 to value one less than n
                System.out.println(i + "--->" + " Idea: " + h[i].getDescription() + "; Rating: " + h[i].getRating()); // print Idea Description and rating
            }
        }
        System.out.println();
    }
}
