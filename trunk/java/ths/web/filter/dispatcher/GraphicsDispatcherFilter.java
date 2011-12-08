package ths.web.filter.dispatcher;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ths.web.AbstractAction;
import ths.web.ActionController;
import ths.web.ActionUrl;

public class GraphicsDispatcherFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(GraphicsDispatcherFilter.class);
	private static final ActionController controller = new ActionController();
	private ServletContext sc;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		sc = filterConfig.getServletContext();
		sc.log("filter: "+ filterConfig.getFilterName() +" initialization complete.");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        ActionUrl ac = controller.getActionUrl(request);
		try {
			AbstractAction action = controller.getInstance(ac);
			action.setRequestAndResponse(request, response);
			action.setServletContext(sc);
			action.setImageHeader();
			action.execute();
		} catch (ClassNotFoundException e) {
			logger.warn("Action class [{}] is not found.", ac.getActionClassName());
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (Exception e) {
			logger.warn("Action [{}] runtime error:", e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public void destroy() {
	}	
}
