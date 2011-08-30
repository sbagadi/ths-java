package ths.core.codec;

public final class Base64 
{
	private static byte[] tob64 = new byte[64];
	private static byte[] todec = new byte[128];
	
    private static final int  TWENTYFOURBITGROUP 	= 24;
    private static final int  EIGHTBIT           	= 8;
    private static final int  SIXTEENBIT         	= 16;
    private static final int  FOURBYTE           	= 4;
    private static final int  SIGN               	= -128;
    private static final byte PAD					= (byte)'=';
    
	static 
	{
		tob64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes();
		
		for(int i=0; i<todec.length; i++) {
			todec[i] = -1;
		}
		
		for(int i=0; i<64; i++) {
			todec[tob64[i]] = (byte)i;
		}
	}
	
	private Base64() {}
	
    public static boolean isBase64( String string )
    {
        return isArrayByteBase64(string.getBytes());
    }

    public static boolean isBase64( byte character )
    {
        return (character == PAD || todec[character] != -1);
    }
    
    public static boolean isArrayByteBase64( byte[] chars )
    {
        int length = chars.length;
        if (length == 0) return true;
   
        for (int i=0; i < length; i++) {
            if ( !isBase64(chars[i]) ) return false;
        }
        
        return true;
    }
    
    public static byte[] encode( byte[] binaryData )
    {
        int      lengthDataBits    = binaryData.length * EIGHTBIT;
        int      fewerThan24bits   = lengthDataBits % TWENTYFOURBITGROUP;
        int      numberTriplets    = lengthDataBits / TWENTYFOURBITGROUP;
        byte     encodedData[]     = null;


        if (fewerThan24bits != 0) {
            encodedData = new byte[ (numberTriplets + 1 ) * 4 ]; //data not divisible by 24 bit
        } else {
            encodedData = new byte[ numberTriplets * 4 ]; // 16 or 8 bit
        }

        byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

        int encodedIndex = 0;
        int dataIndex   = 0;
        int i           = 0;
  
        for ( i = 0; i<numberTriplets; i++ )
        {
            dataIndex = i*3;
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            b3 = binaryData[dataIndex + 2];

            l  = (byte)(b2 & 0x0f);
            k  = (byte)(b1 & 0x03);

            encodedIndex = i * 4;
            byte val1 = ((b1 & SIGN)==0)?(byte)(b1>>2):(byte)((b1)>>2^0xc0);
            byte val2 = ((b2 & SIGN)==0)?(byte)(b2>>4):(byte)((b2)>>4^0xf0);
            byte val3 = ((b3 & SIGN)==0)?(byte)(b3>>6):(byte)((b3)>>6^0xfc);

            encodedData[encodedIndex]   = tob64[ val1 ];
            encodedData[encodedIndex+1] = tob64[ val2 | ( k<<4 )];
            encodedData[encodedIndex+2] = tob64[ (l <<2 ) | val3 ];
            encodedData[encodedIndex+3] = tob64[ b3 & 0x3f ];
        }

        // form integral number of 6-bit groups
        dataIndex    = i*3;
        encodedIndex = i*4;
        if (fewerThan24bits == EIGHTBIT )
        {
            b1 = binaryData[dataIndex];
            k = (byte) ( b1 &0x03 );

            byte val1 = ((b1 & SIGN)==0)?(byte)(b1>>2):(byte)((b1)>>2^0xc0);
            encodedData[encodedIndex]     = tob64[ val1 ];
            encodedData[encodedIndex + 1] = tob64[ k<<4 ];
            encodedData[encodedIndex + 2] = PAD;
            encodedData[encodedIndex + 3] = PAD;
        }
        else if (fewerThan24bits == SIXTEENBIT)
        {

            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex +1 ];
            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0)?(byte)(b1>>2):(byte)((b1)>>2^0xc0);
            byte val2 = ((b2 & SIGN) == 0)?(byte)(b2>>4):(byte)((b2)>>4^0xf0);

            encodedData[encodedIndex]     = tob64[ val1 ];
            encodedData[encodedIndex + 1] = tob64[ val2 | ( k<<4 )];
            encodedData[encodedIndex + 2] = tob64[ l<<2 ];
            encodedData[encodedIndex + 3] = PAD;
        }

        return encodedData;
    }
   
    public static byte[] decode( byte[] base64Data )
    {
        // handle the edge case, so we don't have to worry about it later
        if (base64Data.length == 0) { return new byte[0]; }

        int      numberQuadruple    = base64Data.length/FOURBYTE;
        byte     decodedData[]      = null;
        byte     b1=0,b2=0,b3=0, b4=0, marker0=0, marker1=0;

        // Throw away anything not in base64Data
        int encodedIndex = 0;
        int dataIndex    = 0;
        {
            // this sizes the output array properly - rlw
            int lastData = base64Data.length;
            // ignore the '=' padding
            while (base64Data[lastData-1] == PAD)
            {
                if (--lastData == 0)
                {
                    return new byte[0];
                }
            }
            decodedData = new byte[ lastData - numberQuadruple ];
        }

        for (int i = 0; i < numberQuadruple; i++)
        {
            dataIndex = i * 4;
            marker0   = base64Data[dataIndex + 2];
            marker1   = base64Data[dataIndex + 3];

            b1 = todec[base64Data[dataIndex]];
            b2 = todec[base64Data[dataIndex +1]];

            if (marker0 != PAD && marker1 != PAD)
            {
                //No PAD e.g 3cQl
                b3 = todec[ marker0 ];
                b4 = todec[ marker1 ];

                decodedData[encodedIndex]   = (byte)(  b1 <<2 | b2>>4 ) ;
                decodedData[encodedIndex + 1] = (byte)(((b2 & 0xf)<<4 ) |( (b3>>2) & 0xf) );
                decodedData[encodedIndex + 2] = (byte)( b3<<6 | b4 );
            }
            else if (marker0 == PAD)
            {
                //Two PAD e.g. 3c[Pad][Pad]
                decodedData[encodedIndex]   = (byte)(  b1 <<2 | b2>>4 ) ;
            }
            else if (marker1 == PAD)
            {
                //One PAD e.g. 3cQ[Pad]
                b3 = todec[ marker0 ];

                decodedData[encodedIndex]   = (byte)(  b1 <<2 | b2>>4 );
                decodedData[encodedIndex + 1] = (byte)(((b2 & 0xf)<<4 ) |( (b3>>2) & 0xf) );
            }
            encodedIndex += 3;
        }
        
        return decodedData;
    }
    
	public static void main(String[] args) 
	{
		/*
		for(int i=0; i<tob64.length; i++) {
			System.out.print(tob64[i] +" ");
		}		
		
		System.out.println("");
		
		for(int i=0; i<todec.length; i++) {
			if(todec[i] != -1) {
				System.out.print("("+ i +")"+ todec[i] +" ");
			}
		}
		*/
		
		String en = new String(encode("tianhaishen".getBytes()));
		String de = new String(decode(en.getBytes()));
		
		System.out.println(en);
		System.out.println(de);
	}
}
