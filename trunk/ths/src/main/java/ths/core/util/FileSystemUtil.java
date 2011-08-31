package ths.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Comparator;

public class FileSystemUtil {
	/**
	 * 复制单个文件 到指定目录
	 * 
	 * @param src 要复制的文件
	 * @param descDirectory 目标目录
	 * @param filename 指定新文件名称，如果为 <code>null</code>，则使用原文件名称。
	 * @return 成功返回 <code>true</code> 失败返回 <code>false</code>
	 */
	public static boolean copy(File src, File descDirectory, String filename) {
		if (!src.exists() || !descDirectory.exists() || !descDirectory.isDirectory() || !descDirectory.canWrite()) {
			return false;
		}
		
		if (StringUtil.isEmpty(filename)) {
			filename = src.getName();
		}
		
		File descFile = new File(descDirectory, filename);
		if (descFile.exists()) {
			return false;
		} 
		
		try {
			descFile.createNewFile();
			FileChannel fin = new FileInputStream(src).getChannel();
			FileChannel fout = new FileOutputStream(descFile).getChannel();			
			fin.transferTo(0, fin.size(), fout);
			fin.close();
			fout.close();
		} catch (Exception e) {
			return false;
		} 
		
		return true;
	}
	
	/**
	 * 复制单个文件 到指定目录
	 * 
	 * @param src 要复制的文件
	 * @param descDirectory 目标目录
	 * @return 成功返回 <code>true</code> 失败返回 <code>false</code>
	 * @see #copy(File src, File descDirectory, String filename)
	 */
	public static boolean copy(File src, File descDirectory){
		return copy(src, descDirectory, null);
	}
	
	/**
	 * 删除指定文件或目录(包含子目录下的所有文件及目录)
	 * 
	 * @param root 删除指定文件或目录
	 * @return 成功返回 <code>true</code> 失败返回 <code>false</code>
	 */
	public static boolean deleteRecursively(File root) {
		if (root != null && root.exists()) {
			if (root.isDirectory()) {
				File[] children = root.listFiles();
				if (children != null) {
					for (File child : children) {
						deleteRecursively(child);
					}
				}
			}
			
			return root.delete();
		}
		
		return false;
	}
	
	/**
	 * 安文件系统类型排序，目录排在前面，文件排后。
	 * 
	 * <pre>
		File[] files = root.listFiles();
		Arrays.sort(files, new FileSystemUtil.OrderByType());
	 * </pre>
	 */
	public static class OrderByType implements Comparator<File> {
		@Override
		public int compare(File o1, File o2) {
			if (o1.isDirectory() == o2.isDirectory()) {
				return 0;
			} else if (o1.isDirectory()) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
