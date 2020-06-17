import java.util.*;

public class Sack<E> {
    public static final int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int size;

    public Sack(){
        this(DEFAULT_CAPACITY);
    }
    public Sack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        } else {
            elementData = (E[]) new Object[capacity];
            size = 0;
        }
    }

    public boolean isEmpty(){
        return size ==0;
    }

    public void add(E item){
        ensureCapacity(size+1);
        elementData[size]=item;
        size++;

    }

    public E grab(){
        if(isEmpty()){
            return null;
        }else{
            Random rand = new Random();
            int randomNum = rand.nextInt(size);
            E element = elementData[randomNum];
            remove(randomNum);
            return element;
        }

    }

    public E[] dump() {
        E[] dump = Arrays.copyOf(this.elementData,size);
        Arrays.fill(elementData, null);
        size =0;
        return dump;
    }

    private void ensureCapacity(int capacity){
        if(capacity>this.elementData.length){
            int newCapacity = 2*this.elementData.length+1;
            if(capacity>newCapacity){
                newCapacity = capacity;
            }
            this.elementData = Arrays.copyOf(this.elementData, newCapacity);
        }
    }

    private void remove(int index){
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException("index: "+index);
        }else{
            elementData[index]= elementData[size-1];
            elementData[size-1]= null;
            size--;
        }

    }
}