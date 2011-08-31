package ths.core.codec;

/**
 * 十六进制编码和解码 
 */
public class Hex 
{
	private static final byte[] DIGITS_UPPER = "0123456789ABCDEF".getBytes();
	
	public static String encode(byte[] data) 
    {
        int l = data.length;
        byte[] out = new byte[l << 1];

        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_UPPER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_UPPER[0x0F & data[i]];
        }
        
        return new String(out);
    }
    
    public static byte[] decode(String ascii)
    {
    	byte[] data = ascii.getBytes();
        int len = data.length;
        if ((len & 0x01) != 0) return null;

        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            
            f = f | toDigit(data[j], j);
            j++;
            
            out[i] = (byte)(f & 0xFF);
        }

        return out;
    }
    
    private static int toDigit(byte ch, int index)
    {
        int digit = Character.digit(ch, 16);
        if (digit == -1) return 0;
        
        return digit;
    }
}
