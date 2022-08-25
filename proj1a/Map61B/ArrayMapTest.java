package Map61B;


import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayMapTest {

    @Test
    public void test(){
        ArrayMap<Integer,Integer> am = new ArrayMap<Integer,Integer>();
        am.put(2,5);
        int expected = 5;
        assertEquals((Integer)expected,am.get(2));

    }
}
