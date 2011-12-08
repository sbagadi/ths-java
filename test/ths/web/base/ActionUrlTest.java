package ths.web.base;

import static org.junit.Assert.*;

import org.junit.Test;

import ths.web.ActionUrl;

public class ActionUrlTest {

	@Test
	public void testGetClassName() {
		ActionUrl ac = new ActionUrl("product/goods_detail.htm", "127.0.0.1", "web.action.");
		ac.parse();

		String className = ac.getActionClassName();
		System.out.println(className);
		assertTrue(true);
	}

}
