public class LinkedListDeque<T> {
    public GenericNode sentinel;
    private int size;

    public class GenericNode{
        public GenericNode prev;
        public GenericNode next;
        public T Val;
        public GenericNode(T v, GenericNode p, GenericNode n){
            Val = v;
            prev = p;
            next = n;
        }
    }

    public LinkedListDeque(){
        sentinel = new GenericNode(null,null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public void addFirst(T item){
        GenericNode newNode = new GenericNode(item,sentinel,sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size +=1;
    }
    public void addLast(T item){
        GenericNode newNode = new GenericNode(item,sentinel.prev,sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }
    public T get(int index){
        if(index >= size){
            return null;
        }
        if (size-1 - index > index){
            GenericNode tmp = sentinel;
            for (int i=0;i<=index;i++){
                tmp = tmp.next;
            }
            return tmp.Val;

        }else{
            GenericNode tmp = sentinel;
            for(int i=0;i<=size-1-index;i++){
                tmp = tmp.prev;
            }
            return tmp.Val;

        }
    }
    public int size(){
        return size;
    }

    public void printDeque(){
        GenericNode tmp = sentinel.next;
        while(!tmp.equals(sentinel)){
            System.out.print(tmp.Val.toString()+" ");
            tmp = tmp.next;
        }
        System.out.println("");
    }
    public T removeLast(){
        if (size ==0){
            return null;
        }
        T val= sentinel.prev.Val;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return val;
    }
    public T removeFirst(){
        if (size == 0) {
            return null;
        }
        T val = sentinel.next.Val;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        return val;
    }
    public T getRecursive(int index){
        if (index >= size){
            return null;
        }
        return getRecursive(sentinel.next,index);
    }
    private T getRecursive(GenericNode root,int index){
        if (index ==0){
            return root.Val;
        }
        return getRecursive(root.next,index-1);

    }
}
