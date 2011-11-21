package ths.web.util;

import javax.servlet.http.HttpServletRequest;

public class ClientDetector {
	
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("proxy-client-ip");
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("wl-proxy-client-ip");
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
	
	
}
