package ths.template.support.web;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ths.template.Engine;
import ths.template.Template;
import ths.core.loaders.ServletLoader;

/**
 * HttlServlet. (Integration, Singleton, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class HttlServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    public static final String CONFIGURATION = "configuration";
    
    private transient Engine engine;
    
    private boolean isOutputStream;
    
    @Override
    public void init() throws ServletException {
        ServletLoader.setServletContext(getServletContext());
        String config = getServletConfig().getInitParameter(CONFIGURATION);
        
        if (config != null && config.length() > 0) {
            if (config.startsWith("/")) {
                this.engine = Engine.getEngine(getServletContext().getContextPath() + config);
            } else {
                this.engine = Engine.getEngine(config);
            }
        } else {
            this.engine = Engine.getEngine();
        }
        isOutputStream = "true".equalsIgnoreCase(engine.getConfiguration().getOutputStream());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Template template = engine.getTemplate(getTemplatePath(request));
            ParameterMap context = new ParameterMap(request);
            if (isOutputStream) {
                template.render(context, response.getOutputStream());
            } else {
                template.render(context, response.getWriter());
            }
            response.flushBuffer();
        } catch (ParseException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    protected String getTemplatePath(HttpServletRequest request)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null && path.length() > 0)
            return path;
        return request.getServletPath();
    }

}
