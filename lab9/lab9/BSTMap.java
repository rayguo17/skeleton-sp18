package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if(root==null){
            return null;
        }
        if(key.compareTo(p.key)>0){
            //key larger than p.key
            return getHelper(key,p.right);
        }else if(key.compareTo(p.key)<0){
            return getHelper(key,p.left);
        }else{
            return p.value;
        }

    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key,root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if(p==null){
            size++;
            return new Node(key,value);
        }else if (key.compareTo(p.key)>0){
            //key larger than p.key
            p.right= putHelper(key,value,p.right);
        } else if (key.compareTo(p.key)<0) {
            //key smaller than p.key
            p.left =  putHelper(key,value,p.left);
        }

        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        //insertion recursively find the place to put
        root = putHelper(key,value,root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Print out BSTMap in order of increasing Key. */
    public void printInorder(){
        printHelper(root);
        System.out.println("");
    }
    private void printHelper(Node p){
        if(p!=null){
            printHelper(p.right);
            System.out.printf("%s ",p.key);
            printHelper(p.left);
        }
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        Node pNode = root;
        Stack<Node> st = new Stack<>();
        while(pNode!=null || !st.empty()){
            if(pNode!=null){
                set.add(pNode.key);
                st.push(pNode);
                pNode = pNode.left;
            }else{
                Node node = st.pop();
                pNode = node.right;
            }
        }
        return set;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if(get(key)==null){
            return null;
        }else{
            Node result = removeHelper(key,root);
            if(result==null){
                return null;
            }else{
                return result.value;
            }
        }


    }
    private Node removeHelper(K key,Node p){
        if(p==null){
            return null;
        }
        if(key.compareTo(p.key)<0){
            p.left = removeHelper(key,p.left);
        } else if (key.compareTo(p.key)>0) {
            p.right = removeHelper(key,p.right);
        } else if (p.left==null) {
            return p.right;
        }else if (p.right==null){
            return p.left;
        }else{
            p.right = swapSmallest(p.right,p);
        }
        return p;
    }
    private Node swapSmallest(Node p,Node r){
        if(p.left==null){
            r.key=p.key;
            r.value = p.value;
            return p.right;
        }else{
            p.left = swapSmallest(p.left,r);
            return p;
        }
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {

        if(get(key)==value){
            return remove(key);
        }else{
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
