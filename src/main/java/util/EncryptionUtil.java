package util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class EncryptionUtil {
    public static String generateSHA1(String string) {
        return DigestUtils.sha1Hex(string);
    }
}
