package util;

import java.util.UUID;

public class TokenGenerator {
	public static String createToken(){
		return UUID.randomUUID().toString();
	}
}
