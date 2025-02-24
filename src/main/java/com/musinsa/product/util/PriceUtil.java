package com.musinsa.product.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PriceUtil {
    private static final DecimalFormat df = new DecimalFormat("###,###");

    public static String format(BigDecimal price) {
        return df.format(price);
    }
}
