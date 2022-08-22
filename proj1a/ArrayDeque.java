public class ArrayDeque<T> implements List61B<T>{
    T[] items;
    int size;
    int first;
    int last;
     public ArrayDeque(){
         items = (T[])new Object[8];
         size=0;
         first=-1; //negative means circular
         last=-1;
     }
    public int size(){
         return size;
    }
    @Override
    public void printDeque(){
         if (first>last){
             for (int i = first; i < items.length ; i++) {
                 System.out.printf("%d ",items[i]);
             }
             for (int i = 0; i <= last; i++) {
                 System.out.printf("%d ",items[i]);
             }
         }else{
             for(int i=first;i<=last;i++){
                 System.out.printf("%d ",items[i]);
             }
         }
         System.out.println("");
    }
    public T removeFirst(){
         T returnValue;
         if(first==-1){
             return null;
         }
         if (first==last){
             returnValue=items[first];
             items[first]=null;
             first=-1;
             last=-1;
         }else{
             returnValue=items[first];
             items[first]=null;
             first = (first+1)% items.length;
         }
        size--;
         if (items.length>16){
             //calculate ratio
             float fS = size;
             float r = fS/ items.length;
             if (r<=0.25){
                 resize(items.length/2);
             }
         }

         return returnValue;
    }
    public T removeLast(){
        T returnValue;
        if(last==-1){
            return null;
        }
        if (first==last){
            returnValue=items[first];
            items[first]=null;
            first=-1;
            last=-1;
        }else{
            returnValue=items[last];
            items[last]=null;
            last= (last-1+ items.length)% items.length;
        }
        size--;
        if (items.length>16){
            //calculate ratio
            float fS = size;
            float r = fS/ items.length;
            if (r<=0.25){
                resize(items.length/2);
            }
        }

        return returnValue;
    }
     public void addLast(T x){
         if(last==-1){
             first=0;
             last=0;

         }else{
             if(size>= items.length){
                 resize(size*2);
             }
             if(last== items.length-1){
                 last=0;
             }else{
                 last++;
             }

         }
         size++;
         items[last]=x;
     }
     public void addFirst(T x){
         if (first==-1) {
             first = 0;
             last=0;
         }else{
             if (size==items.length){
                 resize(size*2);
             }
             if (first==0){
                 first=items.length-1;
             }else{
                 first--;
             }
         }
         size++;
         items[first] = x;

     }
     public T get(int index){
         if (index>=size){
             return null;
         }
         int realIndex = (first+index)%items.length;
         return items[realIndex];
     }
     public boolean isEmpty(){
         return size==0;
     }


     private void resize(int newSize){
         //everytimeresize shrink first to 0
         T[] tmpItems = (T[])new Object[newSize];
         if (first<last){
             System.arraycopy(items,first,tmpItems,0,last-first+1);
         } else  {
             System.arraycopy(items,first,tmpItems,0,items.length-first);
             System.arraycopy(items,0,tmpItems,items.length-first,last+1);
         }
         items=tmpItems;
         first=0;
         last=size-1;


     }
}
