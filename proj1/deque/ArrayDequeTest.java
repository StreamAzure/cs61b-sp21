package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<>();
        assertTrue(ad1.isEmpty());

        ad1.addFirst("front");

        assertEquals(1, ad1.size());
        assertFalse(ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        ad1.printDeque();
    }

    @Test
    public void addRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(19);
        ad1.addFirst(20);
        ad1.addLast(21);
        assertEquals(20, (int) ad1.removeFirst());
        assertEquals(21, (int) ad1.removeLast());
        ad1.printDeque();
    }

    @Test
    public void resizeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        int N = 1000000;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 100);
                ad1.addLast(randVal);
                cnt += 1;
            }  else if (operationNumber == 1) {
                int randVal = StdRandom.uniform(0, 100);
                ad1.addFirst(randVal);
                cnt += 1;
            }  else if (operationNumber == 2) {
                if (!ad1.isEmpty()) {
                    ad1.removeLast();
                    cnt -= 1;
                }
            }  else if (operationNumber == 3) {
                if (!ad1.isEmpty()) {
                    ad1.removeFirst();
                    cnt -= 1;
                }
            }
            assertEquals(cnt, ad1.size());
            System.out.println(ad1.getUsage());
        }
    }

    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                ad1.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size1 = ad1.size();
                System.out.println("size: " + size1);
            } else if (operationNumber == 3) {
                // removeLast
                if (!ad1.isEmpty()) {
                    Integer i2 = ad1.removeLast();
                    System.out.println("removeLast: " + i2);
                }
            }
        }
    }

    @Test
    public void multipleAddTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        int len = 1000000;
        int[] test = new int[len];
        for (int i = 0; i < len; i++){
            int randInt = StdRandom.uniform(0, 100);
            test[i] = randInt;
            ad1.addLast(randInt);
        }
        assertEquals(len, ad1.size());
        for (int i = 0; i < len; i++){
            Integer result = ad1.removeFirst();
            assertNotNull(result);
            assertEquals(len-i-1, ad1.size());
//            System.out.println(ad1.removeFirst());
        }
        assertTrue(ad1.isEmpty());
        for (int i = 0; i < len; i++){
            int randInt = StdRandom.uniform(0, 100);
            test[i] = randInt;
            ad1.addFirst(randInt);
        }
    }

}
