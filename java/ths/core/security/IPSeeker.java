package ths.core.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IPSeeker {
	private IPLocation local;
	private long ipLong;
	private RandomAccessFile dbFile;
	private int recordCount, countryFlag;
	private long rangeEnd, rangeBegin, startIP, endIP, endIPOff, firstStartIP, lastStartIP;
	private byte[] b4 = new byte[4];
	private byte[] b3 = new byte[3];
	private byte b1;
	
	public IPSeeker(String file) throws Exception
	{
		try {
			dbFile = new RandomAccessFile(file, "r");
			dbFile.seek(0);
			dbFile.read(b4);
			firstStartIP = readBytesToLong(b4);
			dbFile.read(b4);
			lastStartIP = readBytesToLong(b4);
			recordCount = (int)((lastStartIP - firstStartIP) / 7);
			if (recordCount <= 1) {
				dbFile.close();
				dbFile = null;
			}
		} catch (Exception e) {
			dbFile = null;
		}			
	}
	
	public IPLocation getLocation(String ip) throws Exception {
		ipLong = ipToLong(ip);
		local = new IPLocation();
		
		if (dbFile != null && ipLong>0) {
			rangeBegin = 0;
			rangeEnd = recordCount;
			
			long recordNo;
			do {
				recordNo = (rangeBegin + rangeEnd) / 2;
				getStartIP(recordNo);
				if (ipLong == startIP) {
					rangeBegin = recordNo;
					break;
				}
				if (ipLong > startIP)
					rangeBegin = recordNo;
				else
					rangeEnd = recordNo;
			} while (rangeBegin < rangeEnd - 1);
	
			getStartIP(rangeBegin);
			getEndIP();
			getCountry(ipLong);
		}
		
		return local;
	}
	
	public void close()
	{
		if (dbFile != null) {
			try {
				dbFile.close();
			} catch (IOException e) {
			}
			dbFile = null;
		}
	}	
	
	private long readBytesToLong(byte[] b) {
		long ret = 0;
		for (int i = 0; i < b.length; i++) {
			long t = 1L;
			for (int j = 0; j < i; j++) {
				t = t * 256L;
			}
			ret += ((b[i] < 0) ? 256 + b[i] : b[i]) * t;
		}
		return ret;
	}

	private long ipToLong(String ip) {
		long ret = 0;
		if (ip.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")) {
			String[] arr = ip.split("\\.");
			for (int i = 0; i < arr.length; i++) {
				long l = 1;
				for (int j = 0; j < i; j++) {
					l *= 256;
				}
				
				try {
					ret += Long.parseLong(arr[arr.length - i - 1]) * l;
				} catch (Exception e) {
					ret += 0;
				}
			}
		}
		
		return ret;
	}

	private byte[] getFlagStr(long offSet) throws IOException {
		int flag = 0;
		do {
			dbFile.seek(offSet);
			b1 = dbFile.readByte();
			flag = (b1 < 0) ? 256 + b1 : b1;
			if (flag == 1 || flag == 2) {
				dbFile.read(b3);
				if (flag == 2) {
					countryFlag = 2;
					endIPOff = offSet - 4;
				}
				offSet = readBytesToLong(b3);
			} else {
				break;
			}
		} while (true);

		if (offSet < 12) {
			return null;
		} else {
			dbFile.seek(offSet);
			return getStr();
		}
	}
		
	private byte[] getStr() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte c = dbFile.readByte();
		do {
			out.write(c);
			c = dbFile.readByte();
		} while (c != 0 && dbFile.getFilePointer() < dbFile.length());
		
		return out.toString("GBK").getBytes();
	}

	private void getCountry(long ip) throws IOException {
		if (countryFlag == 1 || countryFlag == 2) {
			local.setCountry(getFlagStr(endIPOff + 4));
			if (countryFlag == 1) {
				local.setArea(getFlagStr(dbFile.getFilePointer()));
				if (ipLong >= ipToLong("255.255.255.0") && ipLong <= ipToLong("255.255.255.255")) {
					local.setArea(getFlagStr(endIPOff + 21));
					local.setCountry(getFlagStr(endIPOff + 12));
				}
			} else {
				local.setArea(getFlagStr(endIPOff + 8));
			}
		} else {
			local.setCountry(getFlagStr(endIPOff + 4));
			local.setArea(getFlagStr(dbFile.getFilePointer()));
		}
	}

	private long getStartIP(long recordNo) throws IOException {
		long offSet = firstStartIP + recordNo * 7;
		dbFile.seek(offSet);
		
		dbFile.read(b4);
		startIP = readBytesToLong(b4);
		
		dbFile.read(b3);
		endIPOff = readBytesToLong(b3);
		
		return startIP;
	}
	
	private long getEndIP() throws IOException {
		dbFile.seek(endIPOff);
		
		dbFile.read(b4);
		endIP = readBytesToLong(b4);
		
		b1 = dbFile.readByte();
		countryFlag = (b1 < 0) ? 256 + b1 : b1;		
		
		return endIP;
	}
	
	
	public static void main(String[] args) throws Exception {

		long initUsedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		long start = System.currentTimeMillis();

		IPSeeker seeker = new IPSeeker("src/QQWry.Dat");
		java.util.Random random = new java.util.Random();
		IPLocation local;
		for (int i=1; i<255; i++) {
			local = seeker.getLocation(random.nextInt(255) +"."+ random.nextInt(255) +"."+ random.nextInt(255) +"."+ random.nextInt(255));
			System.out.println(local.getCountryAsString() + " - " + local.getAreaAsString());
		}
		seeker.close();
		
		long end = System.currentTimeMillis();
		long endUsedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		
		System.out.println("time spent:"+ (end - start) +" ns");
		System.out.println("memory consumes:"+ (endUsedMemory - initUsedMemory));
	}

}