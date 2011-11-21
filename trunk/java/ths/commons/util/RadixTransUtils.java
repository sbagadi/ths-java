package ths.commons.util;

import ths.core.codec.Hex;

public class RadixTransUtils {

	public static String longToBinary(long val) {
		StringBuffer sb = new StringBuffer();
		for (int i = 63; i >= 0; i--) {
			sb.append(1L & (val >>> i));
		}
		return sb.toString();			
	}
	
	public static String intToBinary(int val) {
		return toBinaryString(val, 31);
	}
	
	public static String shortToBinary(short val) {
		return toBinaryString(val, 15);
	}
	
	public static String charToBinary(char val) {
		return toBinaryString(val, 15);
	}
	
	public static String byteToBinary(byte val) {
		return toBinaryString(val, 7);
	}
	
	public static long binaryToLong(String binary) {
		return Long.valueOf(binary, 2).longValue();
	}
	
	public static int binaryToInt(String binary) {
		return Integer.valueOf(binary, 2).intValue();
	}
	
	private static String toBinaryString(int val, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = length; i >= 0; i--) {
			sb.append(1 & (val >>> i));
		}
		return sb.toString();			
	}
	
	public static String byteToHex(byte[] bits) {
		return Hex.encode(bits);
	}
	
	public static byte[] hexToByte(String ascii) {
		return Hex.decode(ascii);
	}
}
