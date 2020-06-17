import java.util.Iterator;
import java.util.List;
import java.util.*;

public class BinarySearchTree<E extends Comparable<E>> {
    private BSTNode<E> root; // root of overall tree
    private int numElements;

    // post: constructs an empty search tree
    public BinarySearchTree() {
        root = null;
        numElements =0;
    }

    // post: value added to tree so as to preserve binary search tree
    public void add(E value) {
        if(!contains(value)) {
            root = add(root, value);
            numElements++;
        }
    }

    // post: value added to tree so as to preserve binary search tree
    private BSTNode<E> add(BSTNode<E> node, E value) {
        if (node == null) {
            node = new BSTNode<E>(value);
        } else if (node.data.compareTo(value) > 0) {
            node.left = add(node.left, value);
        } else if (node.data.compareTo(value) < 0) {
            node.right = add(node.right, value);
        }
        return node;
    }

    // post: returns true if tree contains value, returns false otherwise
    public boolean contains(E value) {
        return contains(root, value);
    }   

    // post: returns true if given tree contains value, returns false otherwise
    private boolean contains(BSTNode<E> node, E value) {
        if (node == null) {
            return false;
        } else {
            int compare = value.compareTo(node.data);
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                return contains(node.left, value);
            } else {   // compare > 0
                return contains(node.right, value);
            }
        }

    }

    public void remove(E value){
        root = remove(root, value);
    }
    private BSTNode<E> remove(BSTNode<E> node, E value) {
        if (node == null) {
            return null;
        } else if (node.data.compareTo(value)>0) { //value<node.data
            node.left = remove(node.left, value);

        } else if (node.data.compareTo(value)<0) { //value>node.data
            node.right = remove(node.right, value);
        } else {
            if (node.right == null) {
                numElements--;
                return node.left;
            } else if (node.left == null) {
                numElements--;
                return node.right;
            } else {
                node.data = getMax(node.left);
                node.left = remove(node.left, node.data);
            }
        }
        return node;
    }

    public void clear(){
        root = null;
        numElements =0;
    }

    public boolean isEmpty(){
        return numElements ==0;
    }
    public int size(){
        return numElements;
    }

    public E[] toArray(){
        /*List<E> alist = new ArrayList<>();
        Comparable[] list = new Comparable[numElements];
        toArray(root, alist);
        for (int i =0; i<numElements; i++){
            list[i] = alist.get(i);
        }

        return (E[]) list;*/
        ArrayList<E> aList = new ArrayList<>();
        E[] arr = (E[]) new Comparable[this.numElements];
        toArray(this.root,aList);
        return aList.toArray(arr);

    }
    private void toArray(BSTNode<E> node, List<E> aList){
        if(node != null) {
            toArray(node.left, aList);
            aList.add(node.data);
            toArray(node.right, aList);
        }


    }
    public Iterator<E> iterator(){
        return new Iterator<>(root);
    }

    public static class Iterator<E>{
       private Stack<BSTNode<E>> stack;

       public Iterator(BSTNode<E> node){
           stack = new Stack<>();
           if(node != null) {
               while (node != null) {
                   stack.push(node);
                   node = node.left;
               }
           }
       }
       public boolean hasNext(){
           return !stack.empty();
       }
       public E next() {
           if (hasNext()) {
               BSTNode<E> node = stack.pop();


               if (node.right != null) {
                   BSTNode<E> right = node.right;
                   stack.push(right);
                   while(right.left != null){
                       stack.push(right.left);
                       right = right.left;

                   }
               }
               return node.data;
           }
           else{
               return null;
           }
       }

    }
    private E getMax(BSTNode<E> node){
        if(node.right == null){
            return node.data;
        }else{
            return getMax(node.right);
        }
    }


    private static class BSTNode<E> {
        public E data;
        public BSTNode<E> left;
        public BSTNode<E> right;

        public BSTNode(E data) {
            this(data, null, null);
        }

        public BSTNode(E data, BSTNode<E> left, BSTNode<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}
