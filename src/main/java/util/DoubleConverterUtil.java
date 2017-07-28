package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by David Szilagyi on 2017. 07. 28..
 */
public class DoubleConverterUtil {
    public static double convertDouble(double number, int decimal) {
        return new BigDecimal(number).setScale(decimal, RoundingMode.HALF_UP).doubleValue();
    }

}
