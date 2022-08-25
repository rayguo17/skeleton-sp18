package Map61B;

import java.util.ArrayList;
import java.util.List;

public class ArrayMap<K,V> implements Map61B<K,V>{
    private K[] keys;
    private V[] values;
    int size;

    public ArrayMap(){
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size =0;
    }

    private int keyIndex(K key){
        for(int i=0;i<size;i++){
            if(keys[i]==key){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void put(K key, V value) {
        int index = keyIndex(key);
        if (index == -1){
            keys[size]=key;
            values[size]=value;
            size++;
        }else{
            values[index]=value;
        }

    }

    @Override
    public boolean containsKey(K key) {
        int index = keyIndex(key);
        return index!=-1;
    }

    @Override
    public V get(K key) {
        int index = keyIndex(key);
        if (index != -1){
            return values[index];
        }
        return null;
    }

    @Override
    public List<K> keys() {
        List<K> keyList = new ArrayList<>();
        for(int i=0;i<size;i++){
            keyList.add(keys[i]);
        }
        return keyList;
    }

    @Override
    public int size() {
        return size;
    }
}
