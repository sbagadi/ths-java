package ths.core.i18n;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

public class PinYin {
	private Properties unicodeToHanyuPinyinTable = null;
	private String[] pinyin;
	private boolean haveMultiple = false;
	
	public PinYin() {
        final String resourceName = "/ths/resources/pinyindb/unicode_to_hanyu_pinyin.txt";
        unicodeToHanyuPinyinTable = new Properties();
        try {
			unicodeToHanyuPinyinTable.load(new BufferedInputStream(PinYin.class.getResourceAsStream(resourceName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getFullSpell(String string, String split) {
		StringBuffer sb = new StringBuffer();
		getStringPinYin(string);
		for(int i=0; i<pinyin.length; i++) {
			if(null != pinyin[i]) {
				sb.append(pinyin[i]).append(split);
			}
		}
		return sb.toString();
	}
	
	public String getFirstSpell(String string) {
		StringBuffer sb = new StringBuffer();
		getStringPinYin(string);
		for(int i=0; i<pinyin.length; i++) {
			if(null != pinyin[i]) {
				sb.append(pinyin[i].substring(0, 1));
			}
		}
		return sb.toString();		
	}
	
	public boolean haveMultipleSpellWord() {
		return haveMultiple;
	}
	
	private void getStringPinYin(String string) {
		haveMultiple = false;
		char[] chars = string.toCharArray();
		pinyin = new String[chars.length];
		String record;
		for (int i=0; i<chars.length; i++) {
			record = getHanyuPinyinRecordFromChar(chars[i]);
			if(null != record) {
				record = record.substring(1, record.length()-2);
			}
			pinyin[i] = record;
		}
	}
	
    private String getHanyuPinyinRecordFromChar(char ch)
    {
    	if (unicodeToHanyuPinyinTable.isEmpty()) return null;
    	
        String hexStr = Integer.toHexString(ch).toUpperCase();
        String foundRecord = unicodeToHanyuPinyinTable.getProperty(hexStr);
        if (isValidRecord(foundRecord)) {
        	if (foundRecord.indexOf(',') > 0 ) {
        		haveMultiple = true;
        	}
        } else {
        	foundRecord = null;
        }
        
        return foundRecord;
    }
    
    private boolean isValidRecord(String record) {
    	if(null != record && !record.equals("(none0)")) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public static void main(String[] args) {
    	String name = "田海深";	
		PinYin pinyin = new PinYin();
		System.out.println(pinyin.getFullSpell(name, ""));
	}
}
