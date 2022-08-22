public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        arr.addFirst(1);
        arr.addLast(2);
        for (int i = 0; i < 10; i++) {
            arr.addLast(10);
        }
        for (int i = 0; i < 10; i++) {
            arr.addFirst(20);
        }
        for (int i = 0; i < 10; i++) {
            arr.addFirst(30);
        }
        for(int j=0;j<20;j++){
            arr.removeFirst();
        }

        for(int j=0;j<12;j++){
            arr.removeLast();
        }
        arr.addFirst(1);
        arr.printDeque();
    }
}
