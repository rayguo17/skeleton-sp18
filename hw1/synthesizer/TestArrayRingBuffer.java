package synthesizer;
import org.junit.Test;


import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertTrue("is empty",arb.isEmpty());
        assertFalse("is full", arb.isFull());

        for(int i=0;i<10;i++){
            arb.enqueue(i);
        }
        for(Integer x: arb){
            for(Integer y:arb){
                System.out.println("x:"+x+", y: "+y);
            }
        }
        assertFalse("is empty",arb.isEmpty());
        assertTrue("is full", arb.isFull());
        for(int i=0;i<10;i++){
            assertEquals(i,(long)arb.dequeue());
        }
        arb.enqueue(1);


    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
