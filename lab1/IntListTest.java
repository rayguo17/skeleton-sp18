import IntList.IntList;

public class IntListTest {
    public static void main(String... args){
        IntList ini = IntList.of(1,2,3,4);
        ini.addFirst(5);
        int a = ini.get(0);
        
        ini.appendSquare(9);
        int size = ini.size();
        for (int i=0;i<size;i++){
            System.out.println(ini.get(i));
        }
        
    }
}
