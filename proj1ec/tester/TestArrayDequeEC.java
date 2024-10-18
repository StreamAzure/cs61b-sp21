package tester;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;
public class TestArrayDequeEC {
    @Test
    public void myTest() {
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        int N = 100;
        String[] operationSequence = new String[N];
        String message = "";
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                int randomVar = StdRandom.uniform(0, 100);
                studentArrayDeque.addFirst(randomVar);
                arrayDequeSolution.addFirst(randomVar);

                operationSequence[i] = "addFirst(" + randomVar + ")";
                message += operationSequence[i] + "\n";

                assertEquals(message, arrayDequeSolution.size(), studentArrayDeque.size());
                assertEquals(message, arrayDequeSolution.get(0), studentArrayDeque.get(0));

            } else if (operationNumber == 1) {
                int randomVar = StdRandom.uniform(0, 100);
                studentArrayDeque.addLast(randomVar);
                arrayDequeSolution.addLast(randomVar);

                operationSequence[i] = "addLast(" + randomVar + ")";
                message += operationSequence[i] + "\n";

                assertEquals(message, arrayDequeSolution.size(), studentArrayDeque.size());
                assertEquals(message, arrayDequeSolution.get(arrayDequeSolution.size() - 1),
                        studentArrayDeque.get(studentArrayDeque.size() - 1));

            } else if (operationNumber == 2) {
                if (!studentArrayDeque.isEmpty()) {
                    Integer var1 = studentArrayDeque.removeFirst();
                    Integer var2 = arrayDequeSolution.removeFirst();

                    operationSequence[i] = "removeFirst()";
                    message += operationSequence[i] + "\n";

                    assertEquals(message, var2, var1);

                }
            } else if (operationNumber == 3) {
                if (!studentArrayDeque.isEmpty()) {
                    Integer var1 = studentArrayDeque.removeLast();
                    Integer var2 = arrayDequeSolution.removeLast();

                    operationSequence[i] = "removeLast()";
                    message += operationSequence[i] + "\n";

                    assertEquals(message, var2, var1);

                }
            }
        }
    }
}
