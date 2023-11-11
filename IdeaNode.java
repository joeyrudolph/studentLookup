/*
IdeaNode.java
Jake Vaughan

A helper class to take the data of an idea and store them in a node for use in a Hash table.
We needed pointers in order to do separate chaining, so we needed to take our idea object
and turn it into a node (that stores the same data as the idea object), so we can create linked
lists at every index of our hash table and utilize pointers.

3 May 2022
 */

import java.io.Serializable;

public class IdeaNode implements Serializable{
    private IdeaNode next;
    private final Idea idea;
    private int index;

    public IdeaNode(Idea i){
        next = null;
        idea = i;
        index = i.getIndex();

    }


    // getters and setters -- not too complex
    public IdeaNode getNext() { return next; }

    public Idea getIdea() { return idea; }

    public void setNext(IdeaNode i) { this.next = i; }

    public int getIndex(){ return index; }
}
