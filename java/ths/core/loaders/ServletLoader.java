package ths.core.loaders;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ths.core.Resource;
import ths.core.Loader;
import ths.core.loaders.AbstractLoader;
import ths.commons.util.UrlUtils;

/**
 * ServletLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ServletLoader extends AbstractLoader implements ServletContextListener {
    
    private static ServletContext SERVLET_CONTEXT;
    
    private ServletContext servletContext;
    
    public ServletLoader() {
    }
    
    public ServletLoader(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    public void contextInitialized(ServletContextEvent sce) {
        this.servletContext = sce.getServletContext();
    }
    
    public void contextDestroyed(ServletContextEvent sce) {
        this.servletContext = null;
    }
    
    public static void setServletContext(ServletContext servletContext) {
        SERVLET_CONTEXT = servletContext;
    }
    
    public List<String> doList(String directory, String[] suffixes) throws IOException {
        return UrlUtils.listUrl(servletContext.getResource(directory), suffixes);
    }

    protected Resource doLoad(String name, String encoding, String path) throws IOException {
		return new ServletResource(this, name, encoding, path, servletContext != null ? servletContext : SERVLET_CONTEXT);
	}

}
