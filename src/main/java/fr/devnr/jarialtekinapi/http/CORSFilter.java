package fr.devnr.jarialtekinapi.http;

import fr.devnr.jarialtekinapi.utils.Config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class CORSFilter implements Filter {

    private static final String ALLOWED_ORIGINS = Config.get("cors.allowedOrigins");
    private static final String ALLOWED_METHODS = Config.get("cors.allowedMethods");
    private static final String ALLOWED_HEADERS = Config.get("cors.allowedHeaders");


    public CORSFilter() {}


    public void init(FilterConfig config) throws ServletException {}

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Add 'Access-Control' headers for CORS requests
        response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGINS);
        response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
        response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);

        chain.doFilter(request, response);
    }

    public void destroy() {}

}
