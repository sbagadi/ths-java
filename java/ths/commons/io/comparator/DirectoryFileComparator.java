package ths.commons.io.comparator;

import java.io.File;
import java.util.Comparator;

/**
 * 安文件系统类型排序比较器，目录排在前面，文件排后。
 */
public class DirectoryFileComparator extends AbstractFileComparator {
	
    /** Singleton default comparator instance */
    public static final Comparator<File> DIRECTORY_COMPARATOR = new DirectoryFileComparator();

    /** Singleton reverse default comparator instance */
    public static final Comparator<File> DIRECTORY_REVERSE = new ReverseComparator(DIRECTORY_COMPARATOR);
	
    @Override
	public int compare(File file1, File file2) {
		return (getType(file1) - getType(file2));
	}
	
    private int getType(File file) {
        if (file.isDirectory()) {
            return 1;
        } else {
            return 2;
        }
    }
}
