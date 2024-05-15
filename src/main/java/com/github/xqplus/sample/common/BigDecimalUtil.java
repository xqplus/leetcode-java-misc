package com.github.xqplus.sample.common;

import java.math.BigDecimal;

public class BigDecimalUtil {

    public static double add (double a, double b) {
        BigDecimal decimalA = BigDecimal.valueOf(a);
        BigDecimal decimalB = BigDecimal.valueOf(b);
        return decimalA.add(decimalB).doubleValue();
    }
}
