package com.ken.material.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-24
 */
public class MoneyUtil {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
    public static final String EMPTY = "";

    public static String format(BigDecimal decimal) {
        if (decimal == null) {
            return EMPTY;
        } else {
            BigDecimal bigDecimal = decimal.setScale(2, 4);
            DECIMAL_FORMAT.applyPattern("0.##");
            return DECIMAL_FORMAT.format(bigDecimal);
        }
    }

    public static int toFee(BigDecimal decimal) {
        return decimal == null ? 0 : decimal.multiply(new BigDecimal(100)).setScale(0, 4).intValue();
    }

    public static String toYuanString(Long fee) {
        return format(toYuan(fee));
    }

    public static BigDecimal toYuan(Long fee) {
        if (fee == null) {
            fee = 0L;
        }

        return (new BigDecimal(fee + "")).divide(new BigDecimal(100), 2, 4);
    }
}
