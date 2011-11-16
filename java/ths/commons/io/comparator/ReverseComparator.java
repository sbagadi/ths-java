package ths.commons.io.comparator;

import java.io.File;
import java.util.Comparator;

public class ReverseComparator extends AbstractFileComparator {
	
	private final Comparator<File> delegate;

    public ReverseComparator(Comparator<File> delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("Delegate comparator is missing");
        }
        this.delegate = delegate;
    }
    
    @Override
    public int compare(File file1, File file2) {
        return delegate.compare(file2, file1); // parameters switched round
    }
}
