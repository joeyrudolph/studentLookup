//Joey Rudolph
// 4/28/2022
//This program implements the methods (isEmptyTree(), search(), insert(), traverse(), delete()) of the BST data structure
import java.io.Serializable;

public class StudentNumberBST implements Serializable {
     //Instance variable
     private StudentNumberNode root = null;

     
     //Checks to see if the tree is empty or not
     public boolean isEmptyTree(){
          return root == null;
     }

     
     //Calls the search helper function
     public StudentNumberNode search(int studentNumber){
          return search2(root, studentNumber);
     }

     
     //Helper function for search
     public StudentNumberNode search2(StudentNumberNode t, int studentNumber){
          if (t == null){
               return null;
          } 
          else if (t.getStudent().getStudentNumber() == studentNumber){
               return t;
          }
          else if (t.getStudent().getStudentNumber() > studentNumber){
               return search2(t.getLeft(), studentNumber);
          }
          else{
               return search2(t.getRight(), studentNumber);
          }
     }


     //Insert function which can call the insert helper function
     public void insert(StudentNumberNode p){
          if (root == null){
               root = p;
          }
          else{
               insert2(root, p);
          }
     }


     //Helper function for insert
     public void insert2(StudentNumberNode t, StudentNumberNode p){
          if (p.getStudent().getStudentNumber() < t.getStudent().getStudentNumber()){
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
          System.out.println("-------------");
          traverse2(root);
     }


     //Helper function for traverse
     public void traverse2(StudentNumberNode t){
          if (t != null && t.getStudent() != null){
               traverse2(t.getLeft());
               System.out.println("--");
               System.out.println("Student #: " + t.getStudent().getStudentNumber());
               System.out.println("Last name: " + t.getStudent().getLastName());
               System.out.println("SSN: " + t.getStudent().getSSN());
               System.out.println("Average score: " + t.getStudent().getIdeaAverage());
               System.out.println();
               traverse2(t.getRight());
          }
     }


     //Calls the helper function for print tree
     public void printTree() {
       System.out.println();
       printTree2(root);
       System.out.println();
  }


     //Deletes a StudentNumberNode
  public void delete(StudentNumberNode t){
     if (root != null){
       if (t.getStudent().getStudentNumber() == root.getStudent().getStudentNumber()){
            root = deleteRoot(root);
       }
       else{
            delete2(t, root);
       }	
  }
}


     //Finds the successor of a StudentNumberNode
public StudentNumberNode findSuccessor(StudentNumberNode x){
     while(x.getLeft() != null){
          x = x.getLeft();
     }
     return x;
}


     //Deletes the root of the tree
public StudentNumberNode deleteRoot(StudentNumberNode t){
          //Only has one child and also covers the no child case
     if (t.getLeft() == null){
          StudentNumberNode temp = t.getRight();
          t.setRight(null);
          return temp;
     }
          //Only has one child and also covers the no child case
     else if (t.getRight() == null){
          StudentNumberNode temp = t.getLeft();
          t.setLeft(null);
          return temp;
     }
     else{
	       //2 children case
          StudentNumberNode successor = findSuccessor(t.getRight());
          delete(successor);
          successor.setLeft(t.getLeft());
          successor.setRight(t.getRight());
          return successor;
     }
}


     //delete2 looks for the StudentNumberNode to be deleted
     //t = thing to delete, x = parent
public void delete2(StudentNumberNode t, StudentNumberNode x){
     if (x.getLeft() != null && t.getStudent().getStudentNumber() < x.getStudent().getStudentNumber()){
          if (t.getStudent().getStudentNumber() == x.getLeft().getStudent().getStudentNumber()){
               x.setLeft(deleteRoot(t));
          }
          else{
               delete2(t, x.getLeft());
          }
     }
     else if (x.getRight() != null){
          if (t.getStudent().getStudentNumber() == x.getRight().getStudent().getStudentNumber()){
               x.setRight(deleteRoot(t));
          }
          else{
               delete2(t, x.getRight());
          }
     }
}


     //printTree helper function
private void printTree2(StudentNumberNode tree) {
     if (tree != null) {
       System.out.print(tree.getStudent().getStudentNumber() + " ");

       if (tree.getLeft() != null){
            System.out.print("Left: " + tree.getLeft().getStudent().getStudentNumber() + " ");
       }
       else{
          System.out.print("Left: null ");
     }
     if (tree.getRight() != null){
          System.out.println("Right: " + tree.getRight().getStudent().getStudentNumber() + " ");
     }
     else{
          System.out.println("Right: null ");
     }
     printTree2(tree.getLeft());
     printTree2(tree.getRight());
}
}
}
