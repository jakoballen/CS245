import java.util.Arrays;

public class HashMap<K,V> {
    private static final double MAX_LOAD_FACTOR = 0.75;
    private HashEntry[] elementData;
    private final HashEntry REMOVED;
    private int size;

    public HashMap(){
        elementData = new HashMap.HashEntry[10];
        REMOVED = new HashEntry(null,null);
        size = 0;
    }

    public void put(K key, V value){



        if(!containsKey(key)){
            if(loadFactor()>= MAX_LOAD_FACTOR){
                rehash();
            }
            int bucket = hashFunction(key);
            if(elementData[bucket] == null) {
                elementData[bucket] = new HashEntry(key, value);
                size++;
            } else if (elementData[bucket] != null || elementData[bucket].equals(REMOVED)) {
                    HashEntry current = elementData[bucket];
                    while (current != null) {
                        bucket++;
                        if (bucket >= elementData.length) bucket = 0;
                        current = elementData[bucket];
                    }
                    elementData[bucket] = new HashEntry(key, value);
                    size++;


                }
            }else {
            int bucket = hashFunction(key);
            HashEntry current = elementData[bucket];
            while (!key.equals(current.getKey())) {
                bucket++;
                if (bucket >= elementData.length) bucket = 0;
                current = elementData[bucket];
            }
            elementData[bucket].value = value;

        }

    }
    public void clear(){
        Arrays.fill(elementData, null);
        size =0;
    }
    public boolean containsKey(K key){
        int bucket = hashFunction(key);
        HashEntry current = elementData[bucket];

        while(current != null){
            if(key.equals(current.getKey())){
                return true;
            }else{
                bucket++;
                if(bucket >= elementData.length) bucket =0;
                current = elementData[bucket];
            }
        }
        return false;

    }

    public boolean isEmpty(){
        return size() ==0;
    }

    public V remove(Object key){
        int bucket = hashFunction(key);
        HashEntry current = elementData[bucket];
        if(current != null){
            if(key.equals(current.getKey())){
                elementData[bucket] = REMOVED;
                size--;
                return current.getValue();
            }else{
                while(current != null){
                    if(key.equals(current.getKey())){
                        elementData[bucket] = REMOVED;
                        size--;
                        return current.getValue();
                    }else{
                        bucket++;
                        if(bucket >= elementData.length) bucket =0;
                        current = elementData[bucket];
                    }
                }
            }
        }
        return null;
    }

    public int size(){
        return size;
    }

    public String toString() {

        String result = "[";

        if (!isEmpty()) {
            for (int i = 0; i < elementData.length; i++) {

                if (elementData[i] != null && elementData[i] != REMOVED) {
                    result += elementData[i].getValue() + ", ";
                }
            }
            result = result.substring(0, result.length() - 2);
        }
        return result + "]";
    }
    private int hashFunction(Object key){
        return Math.abs(key.hashCode()) % elementData.length;
    }
    private double loadFactor(){
        return (double) size/elementData.length;
    }

    private void rehash(){
        HashEntry [] old = elementData;
        elementData = new HashMap.HashEntry[2 * old.length];
        Arrays.fill(elementData,null);
        size =0;

        for(int i =0; i<old.length; i++){
            if(old[i] != null && old[i] != REMOVED){
                put(old[i].key, old[i].value);
            }
        }

    }


    public class HashEntry{
        private K key;
        private V value;

        public HashEntry(K key, V value){
            this.key = key;
            this.value = value;
        }
        public K getKey(){
            return key;
        }
        public V getValue() {
            return value;
        }
    }
}