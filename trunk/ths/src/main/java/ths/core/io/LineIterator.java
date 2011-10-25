package ths.core.io;

import static ths.core.lang.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 文本文件按行读取的迭代器，NIO实现。
 * 
 * <pre>
 * LineIterator lt = FileUtils.lineIterator(new File("c:/java-utf8.txt"), "UTF-8");
 * 	while (lt.hasNext()) {
 * 		String line = lt.next();
 * 		//行处理代码
 * 	}
 * 	lt.close();
 * </pre>
 * 
 */
public class LineIterator implements Iterator<String> {

	private FileChannel fc;
	private Scanner sc;

	public LineIterator(File file, Charset charset) throws IOException {
		fc = new FileInputStream(file).getChannel();
		MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

		CharsetDecoder decoder = charset.newDecoder();
		CharBuffer charBuffer = decoder.decode(byteBuffer);

		String separator = System.getProperty("line.separator");
		sc = new Scanner(charBuffer).useDelimiter(separator);
	}

	@Override
	public boolean hasNext() {
		return sc.hasNext();
	}

	@Override
	public String next() {
		return sc.next();
	}

	@Override
	public void remove() {
		unsupportedOperation("Remove unsupported on LineIterator");
	}

	public void close() {
		IOUtils.closeQuietly(fc);
	}

	public static void closeQuietly(LineIterator iterator) {
		if (iterator != null) {
			iterator.close();
		}
	}
}