package ths.core.security;

/** 
 * 身份证验证（支持15位或18位省份证）<br/> 
 * 
 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本码和一位数字校验码组成。
 * 排列顺序从左至右依次为：六位数字地址区位码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 
 * 身份证号码结构： 
 *
 * <ol> 
 * <li>17位数字和1位校验码：6位地址码数字，8位生日数字，3位出生时间顺序号，1位校验码。</li> 
 * <li>地址码（前6位）：表示对象常住户口所在县（市、镇、区）的行政区划代码，按GB/T2260的规定执行。</li>  
 * <li>出生日期码，（第七位 至十四位）：表示编码对象出生年、月、日，按GB按GB/T7408的规定执行，年、月、日代码之间不用分隔符。</li>  
 * <li>顺序码（第十五位至十七位）：表示在同一地址码所标示的区域范围内，对同年、同月、同日出生的人编订的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。</li>   
 * <li>校验码（第十八位数）：<br/>   
 * <ul> 
 * 	<li>十七位数字本体码加权求和公式 s = sum(Ai*Wi), 
 * 		i = 0,...,16    
 *  	Ai:表示第i位置上的身份证号码数字值.
 *  	Wi:表示第i位置上的加权因(7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2)</li> 
 * 	<li>计算模 Y = mod(S, 11)</li>  
 * 	<li>通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2</li>   
 * </ul>
 * </li>
 * </ol> 
 */
public class IDCard {
	
	/** wi =2(n-1)(mod 11) */ 
    private static final int[] WI = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    
    /** verify digit */
    private static final char[] VI = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    
    private String birthDay;
    private String homeplace;
    private byte sex;
    
    private IDCard() {
    }
    
	public IDCard parse(String id) {
		if (!isIDCard(id)) {
			return null;
		}
        
        if (id.length() == 15) {
        	id = transTo18(id);
        }
        
        String code = id.substring(0, 6);
        String birth = id.substring(6, 14);
        
        IDCard user = new IDCard();
        user.sex = (byte)(id.charAt(16)%2 == 0 ? 0 : 1);
        user.birthDay = birth.substring(0, 4) + "-" + birth.substring(4,6) + "-" + birth.substring(6,8);
        user.homeplace = getAddress(code);
        
        return user;
	}
	
	public String getBirthDay() {
		return birthDay;
	}
		
	public byte getSex() {
		return sex;
	}
	
	public String getHomeplace() {
		return homeplace;
	}
	
	public static boolean isIDCard(String id) {
        if (id == null || (id.length() != 15 && id.length() != 18)) { 
            return false;
        }
        
        if (id.length() == 15) {
        	id = transTo18(id);
        }
        
        char sigma = getSigma(id);
        char lastChar = id.charAt(17);
        
        return sigma == lastChar;
	}
	
	private static char getSigma(String id) {
	    int sum = 0;
	    for (int i = 0; i < 17; i++) {
	        int ai = Integer.parseInt(id.substring(i, i + 1));
	        sum += ai * WI[i];
	    } 
	    int index = sum % 11;
	    
	    return VI[index];
	}
	
	private static String transTo18(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append(id.substring(0, 6)).append("19").append(id.substring(6, 15));
		sb.append(getSigma(id));
		return sb.toString();
	}
	
	/**
	 * GB/T 2260-2010 中华人民共和国行政区划代码 
	 * @see http://www.stats.gov.cn/tjbz/xzqhdm/t20110726_402742468.htm
	 * 
	 * @param code
	 * @return
	 */
	private String getAddress(String code) {
		//InputStream is = getClass().getResourceAsStream(filePath);
		//BufferedReader buffReader = new BufferedReader(new InputStreamReader(is,charset));		
		return "";
	}
}
