package ths.core.io;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表示一个存储大小，可以用易读的方式来解析和显示
 */
public class DataSize {

	/**
	 * 1个字节
	 */
	public final static long ONE = 1L;

	/**
	 * 1KB所占用的字节数
	 */
	public static final long ONE_KB = 1024L;

	/**
	 * 1MB 所占用的字节数
	 */
	public static final long ONE_MB = 1024 * 1024L;

	/**
	 * 1GB 所占用的字节数
	 */
	public static final long ONE_GB = 1024 * 1024 * 1024L;

	/**
	 * 1TB 所占用的字节数
	 */
	public static final long ONE_TB = 1024 * 1024 * 1024 * 1024L;

	/**
	 * 数据的字节大小
	 */
	private final long bytes;
	
	/**
	 * 易读表示形式
	 */
	private final String readable;

	public DataSize(long bytes) {
		this.bytes = bytes < 0 ? -1 : bytes;
		this.readable = toReadable(bytes);
	}

	public DataSize(String readableSize) {
		this.bytes = parseReadable(readableSize);
		this.readable = toReadable(this.bytes);
	}
	
	/**
	 * 取得数据的字节大小
	 * @return 字节数
	 */
	public long getBytes() {
		return this.bytes;
	}
	
	/**
	 * 取得数据的易读表示形式（1 bytes; 1 KB; 1 MB; 1 GB; 1.25MB）
	 * @return 易读表示形式
	 */
	public String getReadable() {
		return this.readable;
	}

	@Override
	public int hashCode() {
		return 31 + (int) (bytes ^ bytes >>> 32);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (obj instanceof DataSize) {
			return bytes == ((DataSize) obj).bytes;
		}

		return false;
	}

	@Override
	public String toString() {
		return readable;
	}

	/**
	 * 将字节数转换成易读形式。会损失精度（因为保留两位小数）常用于友好的显示数据大小。
	 * 
	 * <pre>
	 * DataSize.toReadable(-20) = n/a
	 * DataSize.toReadable(1) = 1 bytes
	 * DataSize.toReadable(1024) = 1 KB
	 * DataSize.toReadable(1048576) = 1 MB
	 * DataSize.toReadable(1073741824) = 1 GB
	 * DataSize.toReadable(1099511627776) = 1 TB
	 * DataSize.toReadable(1280) = 1.25 KB
	 * </pre>
	 * 
	 * @param size
	 *            字节数
	 * @return 易读形式; 小于<code>0</code>的一律转换成<code>n/a</code>
	 */
	public static String toReadable(long size) {
		if (size < 0) {
			return "N/A";
		}

		DecimalFormat format = new DecimalFormat("#.##");
		String readableSize;

		if (size / ONE_TB > 0) {
			readableSize = format.format((double) size / ONE_TB) + " TB";
		} else if (size / ONE_GB > 0) {
			readableSize = format.format((double) size / ONE_GB) + " GB";
		} else if (size / ONE_MB > 0) {
			readableSize = format.format((double) size / ONE_MB) + " MB";
		} else if (size / ONE_KB > 0) {
			readableSize = format.format((double) size / ONE_KB) + " KB";
		} else {
			readableSize = format.format(size) + " bytes";
		}

		return readableSize;
	}

	/**
	 * 将易读形式的表示转换成字节数。
	 * 
	 * <pre>
	 * DataSize.parseReadable(1 bytes) = 1
	 * DataSize.parseReadable(1 KB) = 1024
	 * DataSize.parseReadable(1 MB) = 1048576
	 * DataSize.parseReadable(1 GB) = 1073741824
	 * DataSize.parseReadable(1 TB) = 1099511627776
	 * DataSize.parseReadable(1.25 KB) = 1280
	 * </pre>
	 * 
	 * @param readableSize
	 *            易读形式
	 * @return 字节数; <code>-1</code> 表示一个错误的格式
	 */
	public static long parseReadable(String readableSize) {
		long byteSize = -1L;

		if ("N/A".equalsIgnoreCase(readableSize)) {
			return byteSize;
		}

		Pattern pattern = Pattern.compile(
				"(\\d+(\\.\\d+)?)\\s*(bytes|KB|MB|GB|TB)?",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(readableSize);

		if (matcher.matches()) {
			double size = Double.parseDouble(matcher.group(1));
			String unit = matcher.group(3).trim();

			if ("TB".equals(unit)) {
				byteSize = (long) (size * ONE_TB);
			} else if ("GB".equals(unit)) {
				byteSize = (long) (size * ONE_GB);
			} else if ("MB".equals(unit)) {
				byteSize = (long) (size * ONE_MB);
			} else if ("KB".equals(unit)) {
				byteSize = (long) (size * ONE_KB);
			} else {
				byteSize = (long) size;
			}
		}

		return byteSize;
	}
}
