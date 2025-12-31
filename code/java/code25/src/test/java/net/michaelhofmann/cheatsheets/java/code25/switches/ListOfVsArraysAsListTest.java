/*
 *   O R A N G E   O B J E C T S
 * 
 *   copyright by Orange Objects
 *   http://www.OrangeObjects.de
 * 
 */
package net.michaelhofmann.cheatsheets.java.code25.switches;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;

/**
 * Proof suite: behavioral differences between List.of(...) and Arrays.asList(...).
 *
 * Topics covered:
 * - Mutability / Unmodifiable behavior (set/add/remove and derived operations)
 * - null handling
 * - backing store (array <-> list) for Arrays.asList
 * - implementation types (and "don't assume ArrayList")
 * - exception behavior (UnsupportedOperationException, NullPointerException, ClassCastException)
 * - a couple of common pitfalls (primitive array, iterators)
 *
 * @author Michael.Hofmann@OrangeObjects.de
 * 
 */

@DisplayName("List.of() vs Arrays.asList()")
class ListOfVsArraysAsListTest {

    @Test
    @DisplayName("Arrays.asList(): set() is allowed, add/remove are not (fixed-size list)")
    void arraysAsList_allowsSet_butNotResize() {
        
        List<String> list = Arrays.asList("a", "b", "c");

        // Element replacement is allowed
        list.set(0, "z");
        assertEquals(List.of("z", "b", "c"), list);

        // Structural changes are not allowed
        assertThrows(UnsupportedOperationException.class, () -> list.add("d"));
        assertThrows(UnsupportedOperationException.class, () -> list.remove("b"));
        assertThrows(UnsupportedOperationException.class, () -> list.clear());
    }

    @Test
    @DisplayName("List.of(): completely unmodifiable (set/add/remove/clear throw UOE)")
    void listOf_isCompletelyUnmodifiable() {
        
        List<String> list = List.of("a", "b", "c");

        assertThrows(UnsupportedOperationException.class, () -> list.set(0, "z"));
        assertThrows(UnsupportedOperationException.class, () -> list.add("d"));
        assertThrows(UnsupportedOperationException.class, () -> list.remove("b"));
        assertThrows(UnsupportedOperationException.class, () -> list.clear());
    }

    @Test
    @DisplayName("null: Arrays.asList allows null elements, List.of throws NPE at creation time")
    void nullHandling_differs() {
        
        List<String> a = Arrays.asList("a", null, "c");
        assertEquals(3, a.size());
        assertNull(a.get(1));

        // List.of rejects nulls immediately
        assertThrows(NullPointerException.class, () -> List.of("a", null, "c"));
    }

    @Test
    @DisplayName("Backing store: Arrays.asList is backed by the array (array -> list visibility)")
    void arraysAsList_isBackedByArray_arrayToList() {
        
        String[] arr = {"a", "b", "c"};
        List<String> list = Arrays.asList(arr);

        // Mutating the array mutates the list view
        arr[0] = "z";
        assertEquals(List.of("z", "b", "c"), list);
    }

    @Test
    @DisplayName("Backing store: Arrays.asList is backed by the array (list.set -> array visibility)")
    void arraysAsList_isBackedByArray_listToArray() {
        
        String[] arr = {"a", "b", "c"};
        List<String> list = Arrays.asList(arr);

        // Mutating the list mutates the array
        list.set(2, "y");
        assertArrayEquals(new String[]{"a", "b", "y"}, arr);
    }

    @Test
    @DisplayName("Do not assume ArrayList: casting Arrays.asList / List.of to ArrayList throws ClassCastException")
    void castingToArrayList_throwsClassCastException() {
        
        List<String> asList = Arrays.asList("a", "b", "c");
        List<String> ofList = List.of("a", "b", "c");

        // Both are List implementations, but typically NOT java.util.ArrayList
        assertThrows(ClassCastException.class, () -> {
            @SuppressWarnings("unused")
            ArrayList<String> x = (ArrayList<String>) asList;
        });

        assertThrows(ClassCastException.class, () -> {
            @SuppressWarnings("unused")
            ArrayList<String> y = (ArrayList<String>) ofList;
        });
    }

    @Test
    @DisplayName("Iterator removals: Arrays.asList and List.of both throw UOE via Iterator.remove()")
    void iteratorRemove_throwsUnsupportedOperationException() {
        
        List<String> asList = Arrays.asList("a", "b", "c");
        Iterator<String> it1 = asList.iterator();
        assertTrue(it1.hasNext());
        it1.next();

        // Arrays.asList uses a fixed-size list; iterator remove is unsupported
        assertThrows(UnsupportedOperationException.class, it1::remove);

        List<String> ofList = List.of("a", "b", "c");
        Iterator<String> it2 = ofList.iterator();
        assertTrue(it2.hasNext());
        it2.next();

        // List.of is immutable; iterator remove is unsupported
        assertThrows(UnsupportedOperationException.class, it2::remove);
    }

    @Test
    @DisplayName("Derived mutation operations: removeIf / replaceAll / sort still try to mutate and thus may throw UOE")
    void derivedMutations_throwUnsupportedOperationException() {
        
        List<String> asList = Arrays.asList("a", "b", "c");
        List<String> ofList = List.of("a", "b", "c");

        // removeIf implies removal -> unsupported for both
        assertThrows(UnsupportedOperationException.class, () -> asList.removeIf("b"::equals));
        assertThrows(UnsupportedOperationException.class, () -> ofList.removeIf("b"::equals));

        // replaceAll implies set(...) -> allowed for Arrays.asList, forbidden for List.of
        asList.replaceAll(s -> s.toUpperCase(Locale.ROOT));
        assertEquals(List.of("A", "B", "C"), asList);

        assertThrows(UnsupportedOperationException.class,
                () -> ofList.replaceAll(s -> s.toUpperCase(Locale.ROOT)));

        // sort implies set(...) -> allowed for Arrays.asList, forbidden for List.of
        List<String> asList2 = Arrays.asList("c", "a", "b");
        asList2.sort(Comparator.naturalOrder());
        assertEquals(List.of("a", "b", "c"), asList2);

        assertThrows(UnsupportedOperationException.class,
                () -> ofList.sort(Comparator.naturalOrder()));
    }

    @Test
    @DisplayName("subList: List.of.subList is still unmodifiable; Arrays.asList.subList remains fixed-size")
    void subList_mutabilityRulesCarryOver() {
        
        List<String> a = Arrays.asList("a", "b", "c", "d");
        List<String> aSub = a.subList(1, 3); // ["b","c"]

        // Element replacement is still allowed
        aSub.set(0, "x");
        assertEquals(List.of("a", "x", "c", "d"), a);

        // Structural changes still not allowed
        assertThrows(UnsupportedOperationException.class, () -> aSub.add("y"));
        assertThrows(UnsupportedOperationException.class, () -> aSub.remove("c"));

        List<String> o = List.of("a", "b", "c", "d");
        List<String> oSub = o.subList(1, 3);

        // Still immutable
        assertThrows(UnsupportedOperationException.class, () -> oSub.set(0, "x"));
        assertThrows(UnsupportedOperationException.class, () -> oSub.add("y"));
        assertThrows(UnsupportedOperationException.class, () -> oSub.remove("c"));
    }

    @Test
    @DisplayName("Common pitfall: Arrays.asList on primitive arrays creates a List<int[]> of size 1")
    void arraysAsList_onPrimitiveArray_isListOfArraySingleElement() {
        
        int[] arr = {1, 2, 3};

        // Because int[] is a single object argument (not auto-boxed), the list has one element: the array itself.
        List<int[]> list = Arrays.asList(arr);

        assertEquals(1, list.size());
        assertSame(arr, list.get(0));
    }

    @Test
    @DisplayName("Serialization smoke test (when elements are Serializable): roundtrip preserves equality")
    void serializability_smokeTest() throws Exception {
        
        List<String> asList = Arrays.asList("a", "b", "c");
        List<String> ofList = List.of("a", "b", "c");

        assertTrue(asList instanceof Serializable);
        assertTrue(ofList instanceof Serializable);

        List<String> asList2 = roundtrip(asList);
        List<String> ofList2 = roundtrip(ofList);

        assertEquals(asList, asList2);
        assertEquals(ofList, ofList2);
    }

    private static <T> T roundtrip(T obj) throws IOException, ClassNotFoundException {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            @SuppressWarnings("unchecked")
            T read = (T) ois.readObject();
            return read;
        }
    }
}
