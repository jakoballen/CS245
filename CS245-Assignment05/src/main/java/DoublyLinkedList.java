public class DoublyLinkedList<E> {
    private Node first;
    private int size;

    public void add(E value){
        if(isEmpty()){
            Node n = new Node(value, null,null);
            n.next = n;
            n.prev = n;
            first = n;
        }else{
            Node n = new Node(value, first, first.prev);
           first.prev.next = n;
           first.prev =n;
        }
        size++;
    }

    /*public void add(int index, E value){
    * if(first==null){
    *   Node n = new Node(value, null,null)
    *   n.next = n;
    *   n.prev =n;
    *   first =n;
    *  } else {
    *   Node n = first;
    *   for(int i=0; i<index; i++){
    *       n=n.next;
    *   }
    *   //n points to node that will follow new node
    *   Node n2 = new Node(value,n,n.prev);
    *   n.prev.next = n2.
    *   n.prev.next = n2;
    *   n.prev = n2;
    *   //update first if necessary
    *   if(index==0){
    *       first = n2;
    *   }
    *}
    * size++;
    * }
    * */
    public void add(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0 && !isEmpty()) {
            first = new Node(value, first, first.prev);
            first.next.prev = first;
            first.prev.next = first;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node current = first;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Node n = new Node(value, current.next, current);
            current.next = n;
            current.next.next.prev = n;
            size++;
        }
    }
    public void remove(int index){
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException();
        }else if(index ==0){
            Node n = first;
            n.prev.next = first.next;
            first = first.next;
            first.prev = n.prev;
            size--;
        }else{
            Node current = first;
            for(int i=0; i<index;i++){
                current = current.next;
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;

        }


    }
    public E  get(int index) {
        if (index < 0 || index>=size) {
            throw new IndexOutOfBoundsException();
        } else {
            Node current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.data;
        }
    }
    public int indexOf(E value){
        Node current = first;
        for(int i =0; i<size; i++){
            if(current.data == value){
                return i;
            }
            current = current.next;
        }
        return -1;
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
        }else {

            String str = "[" + first.data;
            Node current = first.next;
            for (int i = 1; i < size; i++) {

                str += ", " + current.data;
                current = current.next;
            }
            return str + "]";
        }
    }


    private class Node{
	    private E data;
	    private Node next;
	    private Node prev;

	    public Node(E data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}