public class RotatingSLList<T> extends LinkedListDeque<T>{
    public void rotateRight(){
        T x = removeLast();
        addFirst(x);
    }
}
