package ths.core.io;

import java.io.File;

import org.junit.Test;


public class FileUtilsTest {

	@Test
	public void test() {
		File file = new File("C:/java/ths-archetype-java");
		file.delete();
	}
}
