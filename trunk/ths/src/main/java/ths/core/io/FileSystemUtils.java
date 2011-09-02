package ths.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import ths.core.util.StringUtils;

public class FileSystemUtils {
    
	/**
	 * 检测指定的文件系统对象{@link File}是否有效
	 * <p>
	 * 有效包含对象不为 <code>null</code> 和文件系统是存在的
	 * </p>
	 * 
	 * @param file 文件系统对象
	 * @return <code>true</code> 有效; <code>false</code> 无效
	 */
	public static boolean isValid(File file) {
		return file != null && file.exists();
	}
	
	/**
	 * 取得临时目录路径
	 * 
	 * @return 临时目录路径
	 */
	public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }
	
	/**
	 * 取得临时目录的{@link File}对象
	 * 
	 * @return File对象
	 */
    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }
    
    /**
     * 取得当前用户主目录路径
     * 
     * @return 当前用户主目录路径
     */
    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }
    
    /**
     * 取得当前用户主目录的{@link File}对象
     * 
     * @return File对象
     */
    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }
    
	/**
	 * 复制单个文件 到指定目录
	 * 
	 * @param src 要复制的文件
	 * @param destDirectory 目标目录
	 * @param filename 指定新文件名称，如果为 <code>null</code>，则使用原文件名称
	 * @param coverlay 如果目标目录存在指定文件是否覆盖，true 覆盖 false 不覆盖
	 * @return <code>true</code> 复制成功; <code>false</code> 失败
	 */
	public static boolean copy(File src, File destDirectory, String filename, boolean coverlay) {
		if (!isValid(src) || !isValid(destDirectory)
				|| !src.isFile() || !src.canRead()
				|| !destDirectory.isDirectory() || !destDirectory.canWrite()) {
			return false;
		}
		
		if (StringUtils.isEmpty(filename)) {
			filename = src.getName();
		}
		
		File destFile = new File(destDirectory, filename);
		if (destFile.exists()) {
			if (coverlay) {
				destFile.delete();
			} else {
				return false;
			}
		} 
		
		FileChannel in = null;
		FileChannel out = null;
		try {
			destFile.createNewFile();
			in = new FileInputStream(src).getChannel();
			out = new FileOutputStream(destFile).getChannel();			
			in.transferTo(0, in.size(), out);
		} catch (Exception e) {
			return false;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
		
		return true;
	}
	
	/**
	 * 复制单个文件 到指定目录
	 * 
	 * @param src 要复制的文件
	 * @param destDirectory 目标目录
	 * @param coverlay 如果目标目录存在指定文件是否覆盖，true 覆盖 false 不覆盖
	 * @return <code>true</code> 复制成功; <code>false</code> 失败
	 * @see #copy(File src, File destDirectory, String filename, boolean coverlay)
	 */
	public static boolean copy(File src, File destDirectory, boolean coverlay){
		return copy(src, destDirectory, null, coverlay);
	}
	
	/**
	 * 复制指定目录(包含子目录下的所有文件及目录)到目标目录。如果目标目录存在同名文件，则不进行文件的复制。
	 * 
	 * @param src 要复制的目录
	 * @param dest 复制到的目标目录
	 * @throws IOException 复制单个文件出错
	 */
	public static void copyDirectory(File src, File dest) throws IOException {
		if (!isValid(src) || !isValid(dest) || !src.isDirectory() || !dest.isDirectory()) {
			throw new IOException("Source '" + src + "' or destination '" + dest + "' is not a valid directory.");
		}
		
        if (src.getCanonicalPath().equals(dest.getCanonicalPath())) {
            throw new IOException("Source '" + src + "' and destination '" + dest + "' are the same");
        }
        
        if (!dest.canWrite()) {
        	throw new IOException("Destination '" + dest + "' is read-only");
        }
        
		//如果检查到目标目录存在相同文件，则提示出错
		dest = new File(dest, src.getName());
		if (dest.exists()) {
			throw new IOException("File '" + src.getName() + "' exists in Destination");
		}
		
		copyRecursively(src, dest);
	}
	
	/**
	 * 复制指定目录下的所有目录(包含子目录下的所有文件及目录)和文件到目标目录。如果目标目录存在同名文件，则不进行文件的复制。
	 * 
	 * @param src 要复制的目录
	 * @param dest 复制到的目标目录
	 * @throws IOException 复制单个文件出错
	 */
	public static void copyDirectorySub(File src, File dest) throws IOException {
		if (!isValid(src) || !isValid(dest) || !src.isDirectory() || !dest.isDirectory()) {
			throw new IOException("Source '" + src + "' or destination '" + dest + "' is not a valid directory.");
		}
		
        if (src.getCanonicalPath().equals(dest.getCanonicalPath())) {
            throw new IOException("Source '" + src + "' and destination '" + dest + "' are the same");
        }
        
        if (!dest.canWrite()) {
        	throw new IOException("Destination '" + dest + "' exists but is read-only");
        }
		
		//如果检查到目标目录存在相同文件，则提示出错
		File[] srcList = src.listFiles();
		File target;
		if (srcList != null) {
			for(File file : srcList) {
				target = new File(dest, file.getName());
				if (target.exists()) {
					throw new IOException("File '" + src.getName() + "' exists in Destination");
				}
			}
		}
		
		copyRecursively(src, dest);
	}
	
	/**
	 * 递归复制指定目录(包含子目录下的所有文件和目录)
	 * 
	 * @param src 要复制的目录
	 * @param dest 复制到的目标目录
	 * @throws IOException 复制单个文件出错
	 */
	private static void copyRecursively(File src, File dest) throws IOException {
		if (src.isFile()) {
			if (!copy(src, dest.getParentFile(), null, false)) {
				throw new IOException("Failed to copy file:" + src);
			}
		} else if(src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			
			File[] children = src.listFiles();
			if (children != null) {
				for (File child : children) {
					copyRecursively(child, new File(dest, child.getName()));
				}
			}
		}
	}
	
	/**
	 * 删除单个文件
	 * 
	 * @param file 待删除的文件
	 * @return <code>true</code> 删除成功; <code>false</code> 失败
	 */
	public static boolean delete(File file) {
		if (!isValid(file) && file.isFile()) {
			return file.delete();
		}
		
		return false;
	}
	
	/**
	 * 删除指定目录(包含子目录下的所有文件及目录)
	 * 
	 * @param root 要删除的目录
	 * @return <code>true</code> 删除成功; <code>false</code> 失败
	 */	
	public static boolean deleteDirectory(File root) {
		if (!isValid(root) || !root.isDirectory()) {
			return false;
		}
		
		deleteRecursively(root);
		
		if (!root.exists()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 递归删除指定目录(包含子目录下的所有文件和目录)
	 * 
	 * @param root 要删除的目录
	 */
	private static void deleteRecursively(File root) {
		if (!root.isDirectory()) {
			root.delete();
		} else {
			File[] children = root.listFiles();
			if (children != null) {
				for (File child : children) {
					deleteRecursively(child);
				}
			}
			root.delete();
		}
	}
}
