package ths.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractHttpAction {
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected PrintWriter out;
	protected ServletContext servletContext;
	private ApplicationContext app;
	private String actionType = "html";
	
	protected abstract void service() throws Exception;

	protected final void setNoCache() {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);		
	}
	
	protected Object getServiceInstance(String name) {
		Object obj = null;
		if (app != null) {
			obj = app.getBean(name);
		}
		return obj;
	}
	
	public final void setRequestAndResponse(HttpServletRequest req, HttpServletResponse res) {
		request = req;
		response = res;
	}
	
	public final void setServletContext(ServletContext sc) {
		servletContext = sc;
		app = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}
	
	public final void setHtmlHeader() throws UnsupportedEncodingException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		actionType = "html";
	}
	
	public final void setImageHeader() throws IOException {
		response.setContentType("image/jpeg");
		actionType = "image";
	}	
	
	public void execute() throws Exception {
		
		service();
		
		if (!actionType.equals("image")) {
			out.flush();
			out.close();
		}
	}
}
