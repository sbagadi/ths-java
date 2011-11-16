package ths.commons.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import ths.commons.lang.Constants;

public class FileUtils {

	public static String getFileType(File file) {
		return "";
	}
	
	public static String getReadableSize(long size) {
		return DataSize.toReadable(size);
	}
	
	public static String getReadableSize(File file) {
		return DataSize.toReadable(file.length());
	}
	
	/**
	 * Convert from a <code>File</code> to a <code>URL</code>.
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static URL toURL(File file) throws IOException {
		return file.toURI().toURL();
	}
	
    static String decodeUrl(String url) {
        String decoded = url;
        if (url != null && url.indexOf('%') >= 0) {
            int n = url.length();
            StringBuffer buffer = new StringBuffer();
            ByteBuffer bytes = ByteBuffer.allocate(n);
            for (int i = 0; i < n;) {
                if (url.charAt(i) == '%') {
                    try {
                        do {
                            byte octet = (byte) Integer.parseInt(url.substring(i + 1, i + 3), 16);
                            bytes.put(octet);
                            i += 3;
                        } while (i < n && url.charAt(i) == '%');
                        continue;
                    } catch (RuntimeException e) {
                        // malformed percent-encoded octet, fall through and
                        // append characters literally
                    } finally {
                        if (bytes.position() > 0) {
                            bytes.flip();
                            buffer.append(Charset.forName("UTF-8").decode(bytes).toString());
                            bytes.clear();
                        }
                    }
                }
                buffer.append(url.charAt(i++));
            }
            decoded = buffer.toString();
        }
        return decoded;
    }
    
    /**
     * Convert from a <code>URL</code> to a <code>File</code>.
     * 
     * @param url
     * @return
     */
    public static File toFile(URL url) {
        if (url == null || !"file".equalsIgnoreCase(url.getProtocol())) {
            return null;
        } else {
            String filename = url.getFile().replace('/', File.separatorChar);
            filename = decodeUrl(filename);
            return new File(filename);
        }
    }
    
    /**
     * 得到一个按行读取文件的迭代器
     * 
     * @param file 要读取的文本文件
     * @param encoding 文件字符编码
     * @return {@link ths.core.io.LineIterator} 对象
     * @throws IOException 文件不存在或系统不支持的字符编码
     */
    public static LineIterator lineIterator(File file, String encoding) throws IOException {
    	LineIterator reader = null;
    	try {
        	Charset charset = Charset.forName(encoding);
        	reader = new LineIterator(file, charset);
            return reader;
        } catch (IOException e) {
        	LineIterator.closeQuietly(reader);
            throw e;
        }
    }
    
    public static LineIterator lineIterator(File file) throws IOException {
    	LineIterator reader = null;
    	try {
        	reader = new LineIterator(file, Constants.DEFAULT_CHARSET_UTF8);
            return reader;
        } catch (IOException e) {
        	LineIterator.closeQuietly(reader);
            throw e;
        }
    }    
}
