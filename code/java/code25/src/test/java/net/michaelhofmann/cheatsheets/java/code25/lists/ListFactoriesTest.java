/*
 *   O R A N G E   O B J E C T S
 * 
 *   copyright by Orange Objects
 *   http://www.OrangeObjects.de
 * 
 */
package net.michaelhofmann.cheatsheets.java.code25.lists;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */
public class ListFactoriesTest {

    // Arrays.asList() returns a fixed-size list backed by the array.
    private List<String> list1 = Arrays.asList("a", "b", "c"); 
    // List.of() returns an immutable list—it’s completely read-only.
    private List<String> list2 = List.of("a", "b", "c");

    public ListFactoriesTest() {
    }

    @Test
    public void change01() {

        // It is allowed to change values.
        list1.set(0, "z");
        assertEquals("z", list1.getFirst());

        // It is not allowed to add or remove elements
        Assertions.assertThrows(
                UnsupportedOperationException.class, () -> {
                    list1.add("d");
                });
        Assertions.assertThrows(
                UnsupportedOperationException.class, () -> {
                    list1.remove("b");
                });
    }

    @Test
    public void change02() {

        // It is not allowed to change, add or remove elements
        Assertions.assertThrows(
                UnsupportedOperationException.class, () -> {
                    list2.set(0, "z");
                });
        Assertions.assertThrows(
                UnsupportedOperationException.class, () -> {
                    list2.add("d");
                });
        Assertions.assertThrows(
                UnsupportedOperationException.class, () -> {
                    list2.remove("b");
                });
    }

}
