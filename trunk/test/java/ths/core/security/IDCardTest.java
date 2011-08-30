package ths.core.security;

import static org.junit.Assert.*;

import org.junit.Test;

public class IDCardTest {

	@Test
	public void testIsIDCard15() {
		assertTrue(IDCard.isIDCard("431222198302035310"));
	}
	
	@Test
	public void testIsIDCard18() {
		assertTrue(IDCard.isIDCard("124578457845784571"));
	}

	@Test
	public void testIsIDCardWithX() {
		assertTrue(IDCard.isIDCard("44010619860710145X"));
	}
}
