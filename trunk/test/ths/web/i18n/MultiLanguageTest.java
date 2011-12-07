package ths.web.i18n;

import static org.junit.Assert.*;

import org.junit.Test;

public class MultiLanguageTest {

	@Test
	public void testGetLanguage() {
		MultiLanguage ml = new MultiLanguage();
		ml.init(new String[][]{
				{"en"},
				{"zh"}
		});
		
		String msg1 = ml.getFormatLanguage("zh", "msg.login", "Tian");
		String msg2 = ml.getFormatLanguage("en", "msg.login", "Tian");
		
		System.out.println(msg1);
		System.out.println(msg2);
		
		assertTrue(true);
	}

}
