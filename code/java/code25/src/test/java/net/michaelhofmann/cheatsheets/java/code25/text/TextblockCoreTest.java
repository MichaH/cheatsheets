/*
 *  O R A N G E   O B J E C T S
 * 
 *  copyright by Orange Objects
 *  http://www.OrangeObjects.de
 * 
 */
package net.michaelhofmann.cheatsheets.java.code25.text;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class TextblockCoreTest {
    
    public TextblockCoreTest() {
    }
    
    @Test
    public void testTextblockWithFormat() {
        
        // Error 1: formatted() here works only for the last part.
        String text = "This text with %d words"
                + " should be a one liner with %d line".formatted(11, 1);
        assertEquals("This text with %d words should be a one liner with 11 line", text);
        
        // Solution 1: We need addidional braces
        text = ("This text with %d words"
                + " should be a one liner with %d line").formatted(11, 1);
        assertEquals("This text with 11 words should be a one liner with 1 line", text);

        // Correct but with indent and newline at the end.
        text = """
                    This text with %d words should be a one liner with %d line
               """
                .formatted(11, 1);
        assertEquals("     This text with 11 words should be a one liner with 1 line\n", text);

        // Correct but with newline at the end.
        text = """
               This text with %d words should be a one liner with %d line
               """
                .formatted(11, 1);
        assertEquals("This text with 11 words should be a one liner with 1 line\n", text);

        // Correct
        text = """
               This text with %d words should be a one liner with %d line"""
                .formatted(11, 1);
        assertEquals("This text with 11 words should be a one liner with 1 line", text);

        // Correct
        text = """
               This text with %d words should be a one liner with %d line \
               and a lot of words behind"""
                .formatted(11, 1);
        assertEquals("This text with 11 words should be a one liner with 1 line and a lot of words behind", text);
        
        // Correct
        text = """
        This text with %d words should be a one liner with %d line \
        and a lot of words behind"""
        .formatted(11, 1);
        assertEquals("This text with 11 words should be a one liner with 1 line and a lot of words behind", text);
        
    }
    
}
