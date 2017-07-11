package util;

import java.util.UUID;

public class TokenGenerator {
	
	public String createToken(){
		return UUID.randomUUID().toString();
	}
}
