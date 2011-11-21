package ths.core.loaders;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import ths.core.Loader;

public class ServletResource extends InputStreamResource {

    private static final long serialVersionUID = 2499229996487593996L;
    
    private final String path;
    
    private final transient ServletContext servletContext;

    public ServletResource(Loader loader, String name, String encoding, String path, ServletContext servletContext) {
        super(loader, name, encoding);
        this.path = path;
        this.servletContext = servletContext;
    }

    public InputStream getInputStream() throws IOException {
        return servletContext.getResourceAsStream(path);
    }

}