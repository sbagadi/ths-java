package ths.core.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryStringTest {

	@Test
	public void testToBinaryStringLong() {
		String result = RadixTransUtil.longToBinary(10L);
		assertEquals("0000000000000000000000000000000000000000000000000000000000001010", result);
	}

	@Test
	public void testToBinaryStringInt() {
		String result = RadixTransUtil.intToBinary(10);
		assertEquals("00000000000000000000000000001010", result);
	}

	@Test
	public void testToBinaryStringShort() {
		String result = RadixTransUtil.shortToBinary((short)10);
		assertEquals("0000000000001010", result);
	}

	@Test
	public void testToBinaryStringChar() {
		String result = RadixTransUtil.charToBinary('a');
		assertEquals("0000000001100001", result);
	}

	@Test
	public void testToBinaryStringByte() {
		String result = RadixTransUtil.byteToBinary((byte)10);
		assertEquals("00001010", result);
	}

}
