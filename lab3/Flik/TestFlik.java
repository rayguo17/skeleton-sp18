import org.junit.Test;
import static org.junit.Assert.*;



public class TestFlik {
    @Test
    public void testFlik(){
        assertFalse(Flik.isSameNumber(1,2));
        assertTrue(Flik.isSameNumber(1,1));
        assertTrue(Flik.isSameNumber(128,128));
    }
}
