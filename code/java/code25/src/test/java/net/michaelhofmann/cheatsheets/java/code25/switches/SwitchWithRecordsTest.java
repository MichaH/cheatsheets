/*
 *  O R A N G E   O B J E C T S
 * 
 *  copyright by Orange Objects
 *  http://www.OrangeObjects.de
 * 
 */
package net.michaelhofmann.cheatsheets.java.code25.switches;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class SwitchWithRecordsTest {
    
    public record Tier(String name, int beineAnzahl) {}
    public record Mensch(String name) {}
    public record Flaeche(String name, int eckenAnzahl) {}
    
    public SwitchWithRecordsTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    public static String beschreibung(Object obj) {
        return switch (obj) {
            case Tier(String name, int beineAnzahl) -> "ein Tier mit %d Beinen".formatted(beineAnzahl);
            case Mensch(String name) -> "ein Mensch namens %s".formatted(name);
            default -> "irgend etwas unbekanntes";
        };
    }    
    
    @Test
    public void switchTest01() {
        
        Tier tier1 = new Tier("Giraffe", 4);
        Tier tier2 = new Tier("Tausendfuessler", 1000);
        Mensch mensch1 = new Mensch("Karl");
        
        assertEquals("das erste ist ein Tier mit 4 Beinen", "das erste ist " + beschreibung(tier1));
        assertEquals("das zweite ist ein Tier mit 1000 Beinen", "das zweite ist " + beschreibung(tier2));
        assertEquals("das dritte ist ein Mensch namens Karl", "das dritte ist " + beschreibung(mensch1));
        assertEquals("das vierte ist irgend etwas unbekanntes", "das vierte ist " + beschreibung("Guelle"));
    }
    
    
}
