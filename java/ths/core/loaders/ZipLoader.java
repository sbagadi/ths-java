package ths.core.loaders;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipFile;

import ths.core.Resource;
import ths.commons.util.UrlUtils;

public class ZipLoader extends AbstractLoader {
	
	private File file;
	
	public void setLoaderConfigurable(LoaderConfigurable configurable) {
	    super.setLoaderConfigurable(configurable);
	    file = new File(this.configurable.getDirectory());
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
