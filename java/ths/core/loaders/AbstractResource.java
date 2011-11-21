package ths.core.loaders;

import ths.core.Resource;
import ths.core.Loader;

public abstract class AbstractResource implements Resource {

    private static final long serialVersionUID = 6834431114838915042L;

    private final transient Loader loader;
    
    private final String name;
    
    private final String encoding;

    public AbstractResource(Loader loader, String name, String encoding) {
        this.loader = loader;
        this.name = name;
        this.encoding = encoding;
    }

    public Loader getLoader() {
        return loader;
    }

    public String getName() {
        return name;
    }

    public String getEncoding() {
        return encoding;
    }

    public long getLastModified() {
        return -1;
    }

    public long getLength() {
        return -1;
    }

}
