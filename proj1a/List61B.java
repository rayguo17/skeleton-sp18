public interface List61B<Item> {
    public void addFirst(Item x);
    public void addLast(Item y);
    //public Item getFirst();
    //public Item getLast();
    public Item removeLast();
    public Item get(int i);
    //public void insert(Item x, int position);
    public int size();

    default public void printDeque(){
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i)+" ");
        }

    }
}
