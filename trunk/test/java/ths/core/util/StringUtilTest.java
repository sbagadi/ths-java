package ths.core.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testRemoveNonDigits() {
		String src = "我的版本号:Tom2-f3.";
		String reslut = StringUtil.removeNonDigits(src);
		assertEquals("23", reslut);
		
	}
	
	@Test
	public void testTrim() {
		String text = " ";
		String reslut = StringUtil.trim(text, null);
		assertEquals("", reslut);
	}

	@Test
	public void testLeftTrim() {
		String text = " ";
		String reslut = StringUtil.leftTrim(text);
		assertEquals("", reslut);
	}
	
	@Test
	public void testFirstCharToUpperCase() {
		String text = "cAt";
		String reslut = StringUtil.firstToUpCase(text);
		assertEquals("CAt", reslut);		
	}
	
	@Test
	public void testFirstCharToUpperCase1() {
		String text = "CAt";
		String reslut = StringUtil.firstToUpCase(text);
		assertEquals("CAt", reslut);		
	}
	
	@Test
	public void testFirstCharToLowerCase() {
		String text = "CAt";
		String reslut = StringUtil.firstToLowCase(text);
		assertEquals("cAt", reslut);		
	}
	
	@Test
	public void testFirstCharToLowerCase1() {
		String text = "Cat";
		String reslut = StringUtil.firstToLowCase(text);
		assertEquals("cat", reslut);
	}
	
	@Test
	public void testSubstringCnChar() {
		String text = "我1A是a中2国人";
		String reslut = StringUtil.abbreviate(text, 5);
		assertEquals("我A是a", reslut);
	}	
	
}
