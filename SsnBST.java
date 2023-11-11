//Bjorn Schuepbach
// 4/28/2022
//This program implements the methods (isEmptyTree(), search(), insert(), traverse(), delete()) of the BST data structure
import java.io.Serializable;

public class SsnBST implements Serializable {
    //Instance variable
    private StudentSsnNode root = null;


    //Checks to see if the tree is empty or not
    public boolean isEmptyTree(){
        return root == null;
    }


    //Calls the search helper function
    public StudentSsnNode search(int ssn){
        return search2(root, ssn);
    }


    //Helper function for search
    public StudentSsnNode search2(StudentSsnNode t, int ssn){
        if (t == null){
            return null;
        }
        else if (t.getStudent().getSSN() == ssn){
            return t;
        }
        else if (t.getStudent().getSSN() > ssn){
            return search2(t.getLeft(), ssn);
        }
        else{
            return search2(t.getRight(), ssn);
        }
    }


    //Insert function which can call the insert helper function
    public void insert(StudentSsnNode p){
        if (root == null){
            root = p;
        }
        else{
            insert2(root, p);
        }
    }


    //Helper function for insert
    public void insert2(StudentSsnNode t, StudentSsnNode p){
        if (p.getStudent().getSSN() < t.getStudent().getSSN()){
            if (t.getLeft() == null){
                t.setLeft(p);
            }
            else{
                insert2(t.getLeft(), p);
            }
        }
        else{
            if (t.getRight() == null){
                t.setRight(p);
            }
            else{
                insert2(t.getRight(), p);
            }
        }
    }


    //Calls the traverse helper function
    public void traverse(){
        traverse2(root);
    }


    //Helper function for traverse
    public void traverse2(StudentSsnNode t){
        if (t != null){
            traverse2(t.getLeft());
            System.out.print(t.getStudent().getSSN() + " ");
            traverse2(t.getRight());
        }
    }


    //Calls the helper function for print tree
    public void printTree() {
        System.out.println();
        printTree2(root);
        System.out.println();
    }


    //Deletes a StudentSsnNode
    public void delete(StudentSsnNode t){
        if (root != null){
            if (t.getStudent().getSSN() == root.getStudent().getSSN()){
                root = deleteRoot(root);
            }
            else{
                delete2(t, root);
            }
        }
    }


    //Finds the successor of a StudentSsnNode
    public StudentSsnNode findSuccessor(StudentSsnNode x){
        while(x.getLeft() != null){
            x = x.getLeft();
        }
        return x;
    }


    //Deletes the root of the tree
    public StudentSsnNode deleteRoot(StudentSsnNode t){
        //Only has one child and also covers the no child case
        if (t.getLeft() == null){
            StudentSsnNode temp = t.getRight();
            t.setRight(null);
            return temp;
        }
        //Only has one child and also covers the no child case
        else if (t.getRight() == null){
            StudentSsnNode temp = t.getLeft();
            t.setLeft(null);
            return temp;
        }
        else{
            //2 children case
            StudentSsnNode successor = findSuccessor(t.getRight());
            delete(successor);
            successor.setLeft(t.getLeft());
            successor.setRight(t.getRight());
            return successor;
        }
    }


    //delete2 looks for the StudentSsnNode to be deleted
    //t = thing to delete, x = parent
    public void delete2(StudentSsnNode t, StudentSsnNode x){
        if (x.getLeft() != null && t.getStudent().getSSN() < x.getStudent().getSSN()){
            if (t.getStudent().getSSN() == x.getLeft().getStudent().getSSN()){
                x.setLeft(deleteRoot(t));
            }
            else{
                delete2(t, x.getLeft());
            }
        }
        else if (x.getRight() != null){
            if (t.getStudent().getSSN() == x.getRight().getStudent().getSSN()){
                x.setRight(deleteRoot(t));
            }
            else{
                delete2(t, x.getRight());
            }
        }
    }


    //printTree helper function
    private void printTree2(StudentSsnNode tree) {
        if (tree != null) {
            System.out.print(tree.getStudent().getSSN() + " ");

            if (tree.getLeft() != null){
                System.out.print("Left: " + tree.getLeft().getStudent().getSSN() + " ");
            }
            else{
                System.out.print("Left: null ");
            }
            if (tree.getRight() != null){
                System.out.println("Right: " + tree.getRight().getStudent().getSSN() + " ");
            }
            else{
                System.out.println("Right: null ");
            }
            printTree2(tree.getLeft());
            printTree2(tree.getRight());
        }
    }
}
