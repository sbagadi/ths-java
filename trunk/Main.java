import ths.crypto.Base64;
import ths.crypto.XXTEA;

public class Main 
{
    public static void main(String[] args) 
    {
    	String out = null;
    	//out = Base64.encode("tianhaishen".getBytes());
    	out = Base64.encode(XXTEA.encrypt("tianhaishen".getBytes(), "z9w1b6q".getBytes()));
    	System.out.println(out);
    	
    	out = new String(XXTEA.decrypt(Base64.decode(out), "z9w1b6q".getBytes()));
    	System.out.println(out);
    }	
}
