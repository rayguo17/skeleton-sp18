import org.junit.Test;
import static org.junit.Assert.*;
//import static org.testng.AssertJUnit.assertEquals;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testPalindromecorrect(){
        String input = "cat";
        boolean res = palindrome.isPalindrome(input);
        assertFalse(res);

        assertTrue(palindrome.isPalindrome("racecar"));
    }

    @Test
    public void testOffOnePalin(){
        CharacterComparator offOne = new OffByOne();
        String input = "cat";
        boolean res = palindrome.isPalindrome(input,offOne);
        assertFalse(res);

        assertFalse(palindrome.isPalindrome("racecar",offOne));
        assertTrue(palindrome.isPalindrome("flake",offOne));
        assertFalse(palindrome.isPalindrome("rasdf",offOne));
    }
}
