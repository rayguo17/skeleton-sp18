public class VengefulSLList <T> extends  LinkedListDeque<T>{
    LinkedListDeque<T> deletedItems;
    public void printLostItems(){
        deletedItems.printDeque();
    }
    public VengefulSLList(){
        super();
        deletedItems = new LinkedListDeque<T>();
    }
    public VengefulSLList(T x){
        super(x);

    }

    @Override
    public T removeLast(){
        T x = super.removeLast();
        deletedItems.addLast(x);
        return x;
    }
}
