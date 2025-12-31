/*
 *   O R A N G E   O B J E C T S
 * 
 *   copyright by Orange Objects
 *   http://www.OrangeObjects.de
 * 
 */
package net.michaelhofmann.cheatsheets.java.code25.enums;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */
public enum Operation {

    ADD {
        @Override
        public BigDecimal apply(BigDecimal a, BigDecimal b) {
            return a.add(b);
        }
    },
    SUBTRACT {
        @Override
        public BigDecimal apply(BigDecimal a, BigDecimal b) {
            return a.subtract(b);
        }
    },
    MULTIPLY {
        @Override
        public BigDecimal apply(BigDecimal a, BigDecimal b) {
            return a.multiply(b);
        }
    },
    DIVIDE {
        @Override
        public BigDecimal apply(BigDecimal a, BigDecimal b) {
            return a.divide(b, MathContext.DECIMAL128);
        }
    };

    public abstract BigDecimal apply(BigDecimal a, BigDecimal b);
}
