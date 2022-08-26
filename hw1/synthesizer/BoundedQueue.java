package synthesizer;

public interface BoundedQueue<T> extends Iterable<T> {
    public int capacity();
    public int fillCount();
    public void enqueue(T x);
    public T dequeue();
    public T peek();
    public default boolean isEmpty(){
        if(fillCount()==0){
            return true;
        }
        return false;
    }
    public default boolean isFull(){
        if(fillCount()==capacity()){
            return true;
        }
        return false;
    }

}
