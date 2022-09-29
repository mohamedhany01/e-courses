package deque;

import java.util.Comparator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    @Test
    public void getMaxItemInTheListOfIntegers() {

        // Old implementation using anonymous class "Old java"
//        Comparator<Integer> comparatorInt = new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2.intValue() - o1.intValue();
//            }
//        };

        // Modern implementation using lambda/arrow function "Modern java"
        Comparator<Integer> comparatorInt = (o1, o2) -> o2 - o1;

        MaxArrayDeque<Integer> listInt = new MaxArrayDeque<>(comparatorInt);

        for (int i = 0; i < 1000; i++) {
            listInt.addFirst(i);
        }

        assertEquals(999, listInt.max().intValue());
        assertEquals(999, listInt.max(comparatorInt).intValue());

        listInt.addFirst(1000);
        assertEquals(1000, listInt.max().intValue());
        assertEquals(1000, listInt.max(comparatorInt).intValue());
    }

    @Test
    public void getMaxItemInTheListOfStrings() {
        // Old implementation using anonymous class "Old java"
//        Comparator<String> comparatorString = new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        };

        // Modern implementation using lambda/arrow function "Modern java"
        Comparator<String> comparatorString = Comparator.naturalOrder();

        MaxArrayDeque<String> listStrings = new MaxArrayDeque<>(comparatorString);
        listStrings.addFirst("cherry");
        listStrings.addFirst("grape");
        listStrings.addFirst("banana");
        listStrings.addFirst("lemon");
        listStrings.addFirst("coconut");
        listStrings.addFirst("kiwi");

        assertEquals("banana", listStrings.max());
        assertEquals("banana", listStrings.max(comparatorString));
    }

    @Test
    public void testEmptyList() {
        Comparator<Integer> comparatorInt = (o1, o2) -> o2 - o1;

        MaxArrayDeque<Integer> listInt = new MaxArrayDeque<>(comparatorInt);

        assertEquals(null, listInt.max());
        assertEquals(null, listInt.max(comparatorInt));
    }
}
