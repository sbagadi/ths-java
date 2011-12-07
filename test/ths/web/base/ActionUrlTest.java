package ths.web.base;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActionUrlTest {

	@Test
	public void testGetClassName() {
		ActionUrl ac = new ActionUrl("product/goods_detail.htm", "127.0.0.1");
		ac.parse();
		
		String className = ac.getActionClassName("web.action.");
		System.out.println(className);
		assertTrue(true);
	}

}
