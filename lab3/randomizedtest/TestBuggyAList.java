package randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Object> aListNoResizing = new AListNoResizing<>();
        BuggyAList<Object> buggyAList = new BuggyAList<>();
        int[] data = new int[]{5, 6, 7, 8};
        for (int i : data) {
            aListNoResizing.addLast(i);
            buggyAList.addLast(i);
        }
        int cnt = data.length;
        while (cnt > 0) {
            cnt--;
            int a = (int) aListNoResizing.removeLast();
            int b = (int) buggyAList.removeLast();
            assertEquals(a, b);
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();

        int N = 500000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyAList.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size1 = buggyAList.size();
                assertEquals(size, size1);
                System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                if (L.size() == 0) {
                    continue;
                }
                Integer last = L.getLast();
                Object last1 = buggyAList.getLast();
                assertEquals(last, last1);
                System.out.println("getLast: " + last);
            } else if (operationNumber == 3) {
                if (L.size() == 0) {
                    continue;
                }
                Integer i1 = L.removeLast();
                Integer i2 = buggyAList.removeLast();
                assertEquals(i1, i2);
                System.out.println("removeLast: " + i1);
            }
        }
    }
}
