package ths.commons.io;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataSizeTest {

	@Test
	public void testParseReadable() {
		long result = DataSize.parseReadable("1 MB");
		assertEquals(1048576L, result);
	}
	
	@Test
	public void testToReadable() {
		String result = DataSize.toReadable(1048576L);
		assertEquals("1 MB", result);
	}
}
