package ths.core.loaders;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipFile;

import ths.core.Resource;
import ths.core.Loader;
import ths.commons.util.UrlUtils;

/**
 * ZipLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ZipLoader extends AbstractLoader {
	
	private File file;
	
	public void init(String inputEncoding, String directory, String suffix) {
		super.init(inputEncoding, directory, suffix);
	    file = new File(this.directory);
	}
	
	protected List<String> doList(String directory, String[] suffixes) throws IOException {
	    ZipFile zipFile = new ZipFile(file);
	    try {
	        return UrlUtils.listZip(zipFile, suffixes);
	    } finally {
	        zipFile.close();
	    }
    }
	
	public Resource doLoad(String name, String encoding, String path) throws IOException {
		return new ZipResource(this, name, encoding, file);
	}

}
