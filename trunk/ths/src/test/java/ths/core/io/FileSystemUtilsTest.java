package ths.core.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileSystemUtilsTest {
	
	@Test
	public void testGetTempDirectoryPath() {
		String path = FileSystemUtils.getTempDirectoryPath();
		System.out.println(path);
	}
	
	@Test
	public void testGetUserDirectoryPath() {
		String path = FileSystemUtils.getUserDirectoryPath();
		System.out.println(path);
	}
	
	@Test
	public void testCopy() {
		boolean result = FileSystemUtils.copy(new File("c:/code.txt"), new File("C:/test"), "code_copy.txt", false);
		assertTrue(result);
	}
	
	@Test
	public void testCopyDirectory() throws IOException {
		FileSystemUtils.copyDirectory(new File("D:/temp/cankao/org.springframework.core-sources-3.0.6.RELEASE/org/springframework/core"), new File("C:/test"));
	}
	
	@Test
	public void testCopyDirectorySub() throws IOException {
		
		FileSystemUtils.copyDirectorySub(new File("D:/temp/cankao/org.springframework.core-sources-3.0.6.RELEASE/org/springframework/core"), new File("C:/test"));
	}
	
	
}
