package ths.core.codec;

import org.junit.Test;


public class Base32Test {

	@Test
	public void testEncodeAndDecode() {
		String text = "ÊÖ»ú";
		
        String encoded = Base32.encode(text.getBytes());
        byte[] decoded = Base32.decode(encoded);
        
        System.out.println(" Original: " + text);
        System.out.println("encoded: " + encoded);
        System.out.println("encoded: " + new String(decoded));
	}
}
