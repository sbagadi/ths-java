package ths.core.loaders;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ths.core.Resource;
import ths.commons.util.UrlUtils;

public class UrlLoader extends AbstractLoader {
    
    public List<String> doList(String directory, String[] suffixes) throws IOException {
        return UrlUtils.listUrl(new URL(directory), this.configurable.getSuffixes());
    }
    
    protected Resource doLoad(String name, String encoding, String path) throws IOException {
        return new UrlResource(this, name, encoding, path);
    }
    
}
