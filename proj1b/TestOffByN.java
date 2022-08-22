import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void testOffByN(){
        CharacterComparator cc = new OffByN(5);
        assertFalse(cc.equalChars('a','h'));
        assertTrue(cc.equalChars('a','f'));
    }
}
