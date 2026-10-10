package SplitWise.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money {
    private Money() {
    }

    public static BigDecimal of(String value) {
        return new BigDecimal(value).setScale(2, RoundingMode.UNNECESSARY);
    }

    public static BigDecimal zero() {
        return new BigDecimal("0.00");
    }

    public static BigDecimal normalize(BigDecimal value) {
        return value.setScale(2, RoundingMode.UNNECESSARY);
    }

    public static boolean isZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }
}
