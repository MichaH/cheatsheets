/*
 *   O R A N G E   O B J E C T S
 * 
 *   copyright by Orange Objects
 *   http://www.OrangeObjects.de
 * 
 */
package net.michaelhofmann.cheatsheets.java.code25.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */
public class OperationTest {

    public OperationTest() {
    }

    @ParameterizedTest(name = "{0}: {1} ? {2} = {3}")
    @MethodSource("testData")
    void testApply(Operation operation,
            BigDecimal a,
            BigDecimal b,
            BigDecimal expected) {

        BigDecimal result = operation.apply(a, b);
        assertEquals(0, expected.compareTo(result),
                () -> "Expected " + expected + " but was " + result);
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(Operation.ADD,
                        new BigDecimal("2.5"),
                        new BigDecimal("1.5"),
                        new BigDecimal("4.0")),
                Arguments.of(Operation.SUBTRACT,
                        new BigDecimal("5"),
                        new BigDecimal("3"),
                        new BigDecimal("2")),
                Arguments.of(Operation.MULTIPLY,
                        new BigDecimal("2"),
                        new BigDecimal("4"),
                        new BigDecimal("8")),
                Arguments.of(Operation.DIVIDE,
                        new BigDecimal("10"),
                        new BigDecimal("4"),
                        new BigDecimal("2.5"))
        );
    }
}
