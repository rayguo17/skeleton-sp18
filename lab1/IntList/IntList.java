package IntList;

public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r){
        first = f;
        rest = r;
    }

    public IntList(){
        this(0, null);
    }

    public void addFirst(int x){
        IntList next = new IntList(first, rest);
        first = x;
        rest = next;
    }
    public int size(){
        if (rest ==null){
            return 1;
        }
        return 1 + rest.size();
    }
    public int iterativeSize(){
        IntList tmp = this;
        int size =0;
        while (tmp != null){
            size++;
            tmp = tmp.rest;
        }
        return size;
    }
    public int get(int i){
        IntList p = this;
        int n=0;
        while (p != null ){
            if (n==i){
                return p.first;
            }else{
                n++;
                p=p.rest;
            }
        }
        return -1;
    }
    public void appendSquare(int x){
        IntList dummy = new IntList(0, this);
        IntList tmp = dummy;
        while(tmp.rest != null){
            System.out.println(tmp);
            IntList newNode = new IntList(tmp.rest.first*tmp.rest.first,tmp.rest.rest);
            tmp.rest.rest = newNode;
            tmp = newNode;
        }
        tmp.rest = new IntList(x, null);

    }
    public static IntList of(int ...a){
        if( a.length<=0){
            return null;
        }
        IntList dummy = new IntList();
        IntList tmp = dummy;
        for (int i=0;i<a.length;i++){
            tmp.rest = new IntList(a[i], null);
            tmp = tmp.rest;
        }
        return dummy.rest;
    }
}
