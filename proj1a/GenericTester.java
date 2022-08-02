public class GenericTester {
    public static void main(String[] args){
        LinkedListDeque<Integer> a = new LinkedListDeque<>();
        a.addFirst(1);
        a.addLast(2);
        int b = a.get(1);
        System.out.println(b);
        System.out.printf("size: %d\n",a.size());
        a.addLast(3);
        a.addLast(10);
        a.printDeque();
        LinkedListDeque<String> c = new LinkedListDeque<>();
        c.addLast("hahahahha");
        c.addFirst("what's up!");
        c.printDeque();
        String s = c.getRecursive(1);
        System.out.println(s);
    }
}
