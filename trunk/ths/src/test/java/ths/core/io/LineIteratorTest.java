package ths.core.io;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class LineIteratorTest {

	@Test
	public void testReader() throws IOException {
		//int lineNum = 1;
		LineIterator lt = FileUtils.lineIterator(new File("c:/java-utf8.txt"), "UTF-8");
		
		while (lt.hasNext()) {
			lt.next();
			//System.out.println(lineNum + " : " + lt.next());
			//lineNum++;
		}
		
		lt.close();
		
		
	}
}
