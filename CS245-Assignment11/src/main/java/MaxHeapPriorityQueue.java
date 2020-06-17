import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class MaxHeapPriorityQueue<E extends Comparable<E>> {
    private E[] elementData;
    private int size;

    public MaxHeapPriorityQueue(){
        elementData = (E[]) new Comparable[10];
        size = 0;
    }

    public void add(E value) {
        // resize if necessary
        if (size + 1 >= elementData.length) {
            elementData = Arrays.copyOf(elementData, elementData.length * 2);
        }

        // insert as new rightmost leaf
        elementData[size + 1] = value;

        // "bubble up" toward root as necessary to fix ordering
        int index = size + 1;
        boolean found = false;   // have we found the proper place yet?
        while (!found && hasParent(index)) {
            int parent = parent(index);
            if (elementData[index].compareTo(elementData[parent]) > 0) {
                swap(elementData, index, parent(index));
                index = parent(index);
            } else {
                found = true;  // found proper location; stop the loop
            }
        }

        size++;
    }

    public boolean isEmpty(){
        return size ==0;
    }

    public E peek(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        return elementData[1];
    }

    public E remove() {
        E result = peek();

        // move rightmost leaf to become new root
        elementData[1] = elementData[size];
        size--;

        // "bubble down" root as necessary to fix ordering
        int index = 1;
        boolean found = false;   // have we found the proper place yet?
        while (!found && hasLeftChild(index)) {
            int left = leftChild(index);
            int right = rightChild(index);
            int child = left;
            if (hasRightChild(index) &&
                    elementData[right].compareTo(elementData[left]) > 0) {
                child = right;
            }

            if (elementData[index].compareTo(elementData[child]) < 0) {
                swap(elementData, index, child);
                index = child;
            } else {
                found = true;  // found proper location; stop the loop
            }
        }

        return result;
    }

    public int size(){
        return size;
    }

    public void clear(){
        Arrays.fill(elementData, null);
        size = 0;
    }

    public boolean contains(Object value){
     return contains(value, 1);

    }

    public Object[] toArray(){
        Object[] a = new Object[size];
        for(int i=1; i<=size;i++){
            a[i-1] = elementData[i];
        }
        return a;
    }

    public String toString(){
        String result = "[";
        if(!isEmpty()){
            result += elementData[1];
            for(int i=2; i<=size; i++){
                result += ", "+elementData[i];
            }
        }
        return result+"]";
    }


    public Iterator<E> iterator(){
        return new MHPQIterator<E>();
    }

    public static void heapSort(Comparable[] a, int size){
        MaxHeapPriorityQueue pq = new MaxHeapPriorityQueue();
        pq.elementData =a;
        pq.size = size;

        for(int i = (pq.size-1)/2; i>=0; i--){
            pq.bubbleDown(i);
        }
        while(!pq.isEmpty())
        {
            pq.elementData[pq.size-1] = pq.sortRemove();
        }

    }

    private void bubbleDown(int index){
        //while has left child
        while((index * 2 + 1)<size){
            int left = index * 2 +1;
            int right = left +1;
            int child = left;
            if((index * 2 + 2) < size && elementData[right].compareTo(elementData[left]) > 0){
                child = right;
            }
            //swap parent with child
            if(elementData[index].compareTo(elementData[child])<0){
                swap(elementData, index, child);
                index = child;
            }
        }

    }

    private E sortRemove(){
        E result = elementData[0];
    }

    private boolean contains(Object value, int index){
       if(index>size || elementData[index].compareTo((E) value) <0){
           return false;
       }else if(value.equals(elementData[index])){
           return true;
       }else{
           return contains(value, leftChild(index)) || contains(value, rightChild(index));
       }
    }

    private int parent(int index){
        return index/2;
    }

    private int leftChild(int index){
        return index*2;
    }

    private int rightChild(int index){
        return index*2+1;
    }

    private boolean hasParent(int index){
        return index>1;
    }

    private boolean hasLeftChild(int index){
        return leftChild(index)<= size;
    }

    private boolean hasRightChild(int index){
        return rightChild(index)<= size;
    }


    private void swap(E[] a, int index1,int index2){
        E temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    public  class MHPQIterator<E> implements Iterator<E> {
        private int index;

        public MHPQIterator(){
            index =1;
        }

        public boolean hasNext(){
            return index<=size;
        }
        public E next() {
            return (E) elementData[index++];
        }
    }

}
