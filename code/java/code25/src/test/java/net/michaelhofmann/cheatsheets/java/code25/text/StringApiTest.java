/*
 *  O R A N G E   O B J E C T S
 * 
 *  copyright by Orange Objects
 *  http://www.OrangeObjects.de
 * 
 */
package net.michaelhofmann.cheatsheets.java.code25.text;

import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class StringApiTest {
    
    public StringApiTest() {
    }
    
    @Test
    public void lines01() {
        
        String text = """
            First line and
            second line and another
            line plus
            a fourth line extra""";
        
        assertEquals(4, text.lines().count());
    }
    
    @Test
    public void transform01() {
        
        Function<String, String> toUpper = s -> s.toUpperCase();
        Function<String, String> toLower = s -> s.toLowerCase();

        assertEquals("ANIMAL", "Animal".transform(toUpper));
        assertEquals("animal", "Animal".transform(toLower));
    }
    
    @Test
    public void transform02() {
        
        Function<String, Long> converToLong = Long::parseLong;
        assertEquals(987654321, "987654321".transform(converToLong));
    }
    
    @Test
    public void join01() {

        List<String> list = List.of("java", "programming", "makes", "fun");
        String joined = String.join(" ", list);
        assertEquals("java programming makes fun", joined);
    }
    
    @Test
    public void translateEscapes01() {
        
        String s = "java\\nhello\\tword";
        
        System.out.println(s);
        System.out.println(s.translateEscapes()); 
    }
}
