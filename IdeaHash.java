/*
IdeaHash.java
A class intended to put ideas in a hash table of 191, and to be able to search for an idea, delete ideas from this hash,
and add new ideas, that are not in the idea heap that is a part of the system. This is bulk storage for ideas for use
later.

Jake Vaughan
5 May 2022
 */
import java.io.Serializable;

public class IdeaHash implements Serializable {

    private final IdeaNode[] h = new IdeaNode[191];
    private int n;

    public IdeaHash() { n = 0;}

    public boolean isEmpty(){ // is empty
        return n == 0;
    }

    private int hashCode(IdeaNode x) { // hash code
        int key = x.getIndex() % 191;
        return key;
    }

    public void insert(IdeaNode x){ // inserts a node into the hash table
        //System.out.println("inserted " + x.getIdea().getDescription());
        int index = hashCode(x);
        IdeaNode temp = h[index];
        if (temp == null){
            h[index] = x;
        }
        else{
            while ( temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(x);
        }
        n++;
        //printHash();
    }

    public void delete(IdeaNode x) { // removes node from hash table
        int i = hashCode(x);
        System.out.println("deleted " + x.getIdea().getDescription());
        if (h[i] != null) {
            IdeaNode temp = h[i];
            IdeaNode previous = null;
            while (temp.getNext() != null && temp.getNext().getIndex() != x.getIndex()) {
                previous = temp;
                temp = temp.getNext();
            }

            if (temp.getIndex() == x.getIndex()) {
                if (previous == null){
                    h[i] = temp.getNext();
                    temp.setNext(null);
                }
            }
            else{
                previous.setNext(temp.getNext());
                temp.setNext(null);
            }
        }
        n--;
    }

    public IdeaNode lookUp(int x) { // search
        int index = x % 191;
        IdeaNode temp = h[index];
        
        while (temp != null && temp.getIndex() != x){
            temp = temp.getNext();
        }
        if (temp != null && temp.getIndex() == x){
            return temp;
        }
        else{
            return null;
        }
    }

    void printHash() { // print the entire hash table
        for (int i = 0; i < 191; i++){
            if (h[i] != null){
                if (h[i].getNext() != null){
                    IdeaNode temp = h[i];
                    while ( temp != null){
                        System.out.print(temp.getIdea().getDescription() + " ---> ");
                        temp = temp.getNext();
                    }
                }
                else{
                    System.out.println(h[i].getIdea().getDescription());
                }
            }
            else{
                System.out.println();
                System.out.println("-");
            }
        }

    }
}