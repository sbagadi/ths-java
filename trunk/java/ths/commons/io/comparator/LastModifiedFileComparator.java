package ths.commons.io.comparator;

import java.io.File;
import java.util.Comparator;

public class LastModifiedFileComparator extends AbstractFileComparator {
	
    /** Last modified comparator instance */
    public static final Comparator<File> LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();

    /** Reverse last modified comparator instance */
    public static final Comparator<File> LASTMODIFIED_REVERSE = new ReverseComparator(LASTMODIFIED_COMPARATOR);
    
	@Override
	public int compare(File file1, File file2) {
        long result = file1.lastModified() - file2.lastModified();
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return 0;
        }
	}

}
