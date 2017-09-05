package util;

import java.util.UUID;

public class UUIDTokenGenerator implements TokenGenerator {
	public static String createToken(){
		return UUID.randomUUID().toString();
	}
}
