package lab9;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        //decided
        int buckNumber = hash(key);
        return buckets[buckNumber].get(key);

    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int n = hash(key);
        //calculate load factor.
        if(!buckets[n].containsKey(key)){
            if(isLoadFull()){
                resize(buckets.length*2);
                n = hash(key);
            }
            size++;
        }
        buckets[n].put(key,value);


    }
    private boolean isLoadFull(){
        return (double)size/(double)buckets.length>MAX_LF;
    }
    private void resize(int capacity){
        //for things in bucket need to recalculate hash and then allocate to new bucket.
        ArrayMap<K,V>[] newMap = new ArrayMap[capacity];
        for(int i=0;i<capacity;i++){
            newMap[i] = new ArrayMap<>();
        }
        for(int i=0;i< buckets.length;i++){
            for(K key:buckets[i]){
                //recalculate hash,
                int n = hash(key,capacity);
                newMap[n].put(key,buckets[i].get(key));
            }
        }
        buckets = newMap;
    }
    private int hash(K key, int cap){
        if (key == null) {
            return 0;
        }
        return Math.floorMod(key.hashCode(), cap);
    }
    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        MyHashMap<String,String> b = new MyHashMap<>();
        b.put("Hello","World");
        b.size();
        b.put("Hello","Kelvin");
        b.size();
    }
}
