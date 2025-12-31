/*
 *   O R A N G E   O B J E C T S
 * 
 *   copyright by Orange Objects
 *   http://www.OrangeObjects.de
 * 
 */
package net.michaelhofmann.cheatsheets.java.code25.optionals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Proof suite: preventing NullPointerExceptions using Optional.
 *
 * Focus:
 * - Optional.ofNullable(...).orElse(...)
 * - Safe navigation with map / flatMap
 * - Common Optional mistakes that STILL cause NPEs
 * - Stream integration with Optional
 *
 * Goal:
 * Each test demonstrates a concrete NPE risk and its Optional-based prevention.
 * 
 * @author Michael.Hofmann@OrangeObjects.de
 * 
 */
@DisplayName("Optional – NPE prevention patterns")
class OptionalNpePreventionTest {

    /* -------------------------------------------------
     * Basic patterns
     * ------------------------------------------------- */

    @Test
    @DisplayName("ofNullable().orElse(): returns default when value is null")
    void ofNullable_orElse_returnsDefaultInsteadOfThrowingNpe() {
        
        String value = null;
        String result = Optional.ofNullable(value)
                .orElse("default");
        assertEquals("default", result);
    }

    @Test
    @DisplayName("Optional.of(null) throws NPE immediately")
    void optionalOf_withNull_throwsNpe() {
        
        assertThrows(NullPointerException.class,
                () -> Optional.of(null));
    }

    @Test
    @DisplayName("Calling methods on null causes NPE, Optional prevents it")
    void optional_preventsDirectMethodCallNpe() {
        
        String value = null;
        // Classic NPE
        assertThrows(NullPointerException.class,
                () -> value.toUpperCase());

        // Optional-based safe alternative
        String safe = Optional.ofNullable(value)
                .map(String::toUpperCase)
                .orElse("DEFAULT");
        assertEquals("DEFAULT", safe);
    }


    /* -------------------------------------------------
     * Minimal domain model for the tests
     * ------------------------------------------------- */

    record User(Address address) {}

    record Address(String street) {}

    record Payment(Card card) {}

    record Card(LocalDate expiryDate) {}
    
    /* -------------------------------------------------
     * Chained navigation (multiple NPE risks)
     * ------------------------------------------------- */

    @Test
    @DisplayName("Chained Optional navigation eliminates multiple NPE risks")
    void chainedOptionalNavigation_isNpeSafe() {
        
        User user = new User(null); // address is null
        String street = Optional.ofNullable(user)
                .map(User::address)
                .map(Address::street)
                .orElse("Unknown");

        assertEquals("Unknown", street);
    }

    @Test
    @DisplayName("Same chained access without Optional throws NPE")
    void chainedAccess_withoutOptional_throwsNpe() {
        User user = new User(null);

        assertThrows(NullPointerException.class,
                () -> user.address().street());
    }

    /* -------------------------------------------------
     * Optional mistakes that still cause NPEs
     * ------------------------------------------------- */

    @Test
    @DisplayName("Calling get() on empty Optional throws NoSuchElementException")
    void optionalGet_onEmptyOptional_throwsException() {
        Optional<String> empty = Optional.empty();

        assertThrows(NoSuchElementException.class, empty::get);
    }

    @Test
    @DisplayName("orElseGet() is lazy, orElse() is eager")
    void orElse_vs_orElseGet_evaluationDifference() {
        Optional<String> present = Optional.of("value");

        // orElse: argument is evaluated eagerly
        assertThrows(IllegalStateException.class,
                () -> present.orElse(throwingFallback()));

        // orElseGet: supplier is lazy, not executed
        String safe = present.orElseGet(() -> throwingFallback());

        assertEquals("value", safe);
    }

    private String throwingFallback() {
        throw new IllegalStateException("fallback evaluated");
    }

    /* -------------------------------------------------
     * Realistic domain example
     * ------------------------------------------------- */

    @Test
    @DisplayName("Validation chain with filter(): avoids NPE and encodes business rules")
    void optional_filter_encodesValidationWithoutNpe() {
        Payment payment = new Payment(
                new Card(LocalDate.now().plusDays(1))
        );

        boolean valid =
                Optional.ofNullable(payment)
                        .map(Payment::card)
                        .map(Card::expiryDate)
                        .filter(date -> !date.isBefore(LocalDate.now()))
                        .isPresent();

        assertTrue(valid);
    }

    @Test
    @DisplayName("Same validation without Optional throws NPE if card is missing")
    void validationWithoutOptional_throwsNpe() {
        Payment payment = new Payment(null);

        assertThrows(NullPointerException.class,
                () -> payment.card().expiryDate().isBefore(LocalDate.now()));
    }

}

