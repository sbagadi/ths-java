package ths.web.filter.dispatcher;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ths.web.filter.BeanFilter;
import ths.web.filter.LogContextHelper;

/**
 * 通过SLF4J MDC来记录用户和请求的信息。
 * <p>
 * 下面是logback版本：
 * </p>
 * 
 * <pre>
 * &lt;layout class="ch.qos.logback.classic.PatternLayout"&gt;
 *     &lt;pattern&gt;%-4r [%d{yyyy-MM-dd HH:mm:ss}] - %X{remoteAddr} %X{requestURI} %X{referrer} %X{userAgent} %X{cookie.名称} - %m%n&lt;/pattern&gt;
 * &lt;/layout&gt;
 * </pre>
 */

public class LogDispatcherFilter extends BeanFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        LogContextHelper helper = new LogContextHelper(request);

        try {
            helper.setLoggingContext();

            chain.doFilter(request, response);
        } finally {
            helper.clearLoggingContext();
        }
    }
}
