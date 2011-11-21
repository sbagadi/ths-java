package ths.web.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.support.ServletContextResourceLoader;

import static ths.commons.lang.Assert.*;

public abstract class BeanFilter implements Filter {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final Set<String> requiredProperties = new HashSet<String>();
    private FilterConfig filterConfig;
    
    protected final void addRequiredProperty(String name) {
        this.requiredProperties.add(name);
    }

    public final void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;

        logInServletAndLoggingSystem("Initializing filter: " + getFilterName());

        try {
            PropertyValues pvs = new FilterConfigPropertyValues(getFilterConfig(), requiredProperties);
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
            ResourceLoader resourceLoader = new ServletContextResourceLoader(getServletContext());
            bw.registerCustomEditor(Resource.class, new ResourceEditor(resourceLoader));
            initBeanWrapper(bw);
            bw.setPropertyValues(pvs, true);
        } catch (Exception e) {
            throw new ServletException("Failed to set bean properties on filter: " + getFilterName(), e);
        }

        try {
            init();
        } catch (Exception e) {
            throw new ServletException("Failed to init filter: " + getFilterName(), e);
        }

        logInServletAndLoggingSystem(getClass().getSimpleName() + " - " + getFilterName()+ ": initialization completed");
    }    
    
    protected final void logInServletAndLoggingSystem(String msg) {
        getServletContext().log(msg);
        log.info(msg);
    }

    protected void initBeanWrapper(BeanWrapper bw) throws BeansException {
    }

    protected void init() throws Exception {
    }

    public void destroy() {
    }

    public final FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public final String getFilterName() {
        return filterConfig == null ? null : filterConfig.getFilterName();
    }

    public final ServletContext getServletContext() {
        return filterConfig == null ? null : filterConfig.getServletContext();
    }

    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            String method = httpRequest.getMethod();

            if ("HEAD".equalsIgnoreCase(method)) {
                httpResponse = new NoBodyResponse(httpResponse);
            }

            try {
                doFilter(httpRequest, httpResponse, chain);
            } finally {
                if (httpResponse instanceof NoBodyResponse) {
                    ((NoBodyResponse) httpResponse).setContentLength();
                }
            }
        } else {
            log.debug("Skipped filtering due to the unknown request/response types: {}, {}", request.getClass().getName(), response.getClass().getName());
            chain.doFilter(request, response);
        }
    }

    protected abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

    private static class FilterConfigPropertyValues extends MutablePropertyValues {
        private static final long serialVersionUID = -5359131251714023794L;

        public FilterConfigPropertyValues(FilterConfig config, Set<String> requiredProperties) throws ServletException {
            Set<String> missingProps = new TreeSet<String>(requiredProperties);

            for (Enumeration<?> e = config.getInitParameterNames(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                String value = config.getInitParameter(key);

                addPropertyValue(new PropertyValue(key, value));
                missingProps.remove(key);
            }

            assertTrue(missingProps.isEmpty(), "Initialization for filter %s failed.  "+ "The following required properties were missing: %s", config.getFilterName(), missingProps);
        }
    }


    private static class NoBodyResponse extends HttpServletResponseWrapper {
        private NoBodyOutputStream noBody = new NoBodyOutputStream();
        private PrintWriter writer;
        private boolean didSetContentLength;

        public NoBodyResponse(HttpServletResponse response) {
            super(response);
        }

        public void setContentLength() {
            if (!didSetContentLength) {
                super.setContentLength(noBody.getContentLength());
            }
        }

        @Override
        public void setContentLength(int len) {
            super.setContentLength(len);
            didSetContentLength = true;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return noBody;
        }

        @Override
        public PrintWriter getWriter() throws UnsupportedEncodingException {
            if (writer == null) {
                writer = new PrintWriter(new OutputStreamWriter(noBody, getCharacterEncoding()));
            }

            return writer;
        }
    }

    private static class NoBodyOutputStream extends ServletOutputStream {
        private int contentLength;

        public NoBodyOutputStream() {
            contentLength = 0;
        }

        public int getContentLength() {
            return contentLength;
        }

        @Override
        public void write(int b) {
            contentLength++;
        }

        @Override
        public void write(byte[] buf, int offset, int len) throws IOException {
            if (len >= 0) {
                contentLength += len;
            } else {
                throw new IOException("negative length");
            }
        }
    }    
}
