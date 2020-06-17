import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<E> {
    public static final int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int size;

    public Stack(){
        this(DEFAULT_CAPACITY);
    }

    public Stack(int capacity){
        if(capacity<0){
            throw new IllegalArgumentException();
        }else {
            elementData = (E[]) new Object[capacity];
            size=0;
        }
    }
    public boolean isEmpty(){
        return size==0;
    }

    public void push(E item){
        ensureCapacity(size+1);
        elementData[size]=item;
        size++;
    }

    public E pop(){
        if(isEmpty()){
            throw new EmptyStackException();
        }else{
            E item = elementData[size-1];
            elementData[size-1]=null;
            size--;
            return item;
        }
    }

    public E peek(){
        if(isEmpty()){
            throw new EmptyStackException();
        }else{
            return elementData[size-1];
        }
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
}