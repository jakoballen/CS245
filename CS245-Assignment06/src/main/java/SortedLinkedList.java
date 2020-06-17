
public class SortedLinkedList <E extends Comparable<E>> {
    private Node first;
    private int size;

    public void add(E value){
        if(isEmpty()){
            first = new Node(value, null);
        }else if(value.compareTo(first.data)<0){
            first = new Node(value,first);
        }else{
            add(value, first);
        }
        size++;
    }

    public void remove(int index){
        if(index <0 || index >=size){
            throw new IndexOutOfBoundsException();
        }else if(index ==0){
            first = first.next;
        }else{
            remove(index, 0, first);
        }
        size--;

    }

    public E get(int index){
        if(index <0 || index >= size){
            throw new IndexOutOfBoundsException();
        }else{
            return get(index, 0, first);
        }
    }

    public int indexOf(E value){
        if(value.compareTo(first.data)<0){
            return -1;
        }else{
           return indexOf(value, 0, first);
        }
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int size(){
        return size;
    }

    public String toString(){
        if(isEmpty()){
            return "[]";
        }else if(size == 1){
            return "["+first.data+"]";
        } else {
            return "[" + first.data +", "+ toString(first.next)+"]";
        }
    }

    private void add(E value, Node n){
        if(n.next == null|| value.compareTo(n.next.data)<0){
            n.next = new Node(value, n.next);
        }else{
            add(value, n.next);
        }
    }

    private void remove(int index, int currentindex, Node n){
        if(index == size -1 && currentindex == index) {
            n.next = null;
        }else if(index == currentindex) {
            n.data = n.next.data;
            n.next = n.next.next;
        } else {
            remove(index, currentindex+1,n.next);
        }

    }

    private E get(int index, int currentindex, Node n){
        if(index == currentindex){
            return n.data;
        }else{
            return get(index, currentindex+1, n.next);
        }
    }

    private int indexOf(E value, int currentindex, Node n){
        if(n.data.equals(value)){
            return currentindex;
        }else if(n.next != null){
            return indexOf(value, currentindex+1, n.next);
        }else{
            return -1;
        }
    }

    private String toString(Node n){
        if(n.next == null){
            return n.data+"";
        }else{
            return n.data+", "+ toString(n.next);
        }
    }



    private class Node{
        private E data;
        private Node next;

        public Node(E data, Node next){
            this.data = data;
            this.next = next;

        }
    }
}