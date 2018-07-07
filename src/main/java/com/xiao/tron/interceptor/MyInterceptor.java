package com.xiao.tron.interceptor;

import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyInterceptor implements HandlerInterceptor {
    
    static Logger logger = Logger.getLogger(MyInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object 
            handler) 
            throws Exception {
        
        logger.info("preHandle Method: " + httpServletRequest.getMethod());
        logger.info("preHandle Scheme: " + httpServletRequest.getScheme());
        logger.info("Before handling the request");
//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
//        Map<String, Object> trace = getTrace(wrappedRequest, httpServletResponse.getStatus());
//        getBody(wrappedRequest, trace);
//        logTrace(wrappedRequest, trace);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

        logger.info("After handling the request");
//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
//        Map<String, Object> trace = getTrace(wrappedRequest, response.getStatus());
//        getBody(wrappedRequest, trace);
//        logTrace(wrappedRequest, trace);
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
        Map<String, Object> trace = getTrace(wrappedRequest, httpServletResponse.getStatus());
        getBody(wrappedRequest, trace);
        logTrace(wrappedRequest, trace);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("After rendering the view");
        
        
        
    }


    private void getBody(ContentCachingRequestWrapper request, Map<String, Object> trace) {
        // wrap request to make sure we can read the body of the request (otherwise it will be consumed by the actual
        // request handler)
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }

                trace.put("body", payload);
            }
        }
    }

    private void logTrace(HttpServletRequest request, Map<String, Object> trace) {
        Object method = trace.get("method");
        Object path = trace.get("path");
        Object statusCode = trace.get("statusCode");

        logger.info(String.format("%s %s produced an error status code '%s'. Trace: '%s'", method, path, statusCode,
                trace));
    }

    protected Map<String, Object> getTrace(HttpServletRequest request, int status) {
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

        Principal principal = request.getUserPrincipal();

        Map<String, Object> trace = new LinkedHashMap<String, Object>();
        trace.put("method", request.getMethod());
        trace.put("path", request.getRequestURI());
//        trace.put("principal", principal.getName());
        trace.put("query", request.getQueryString());
        trace.put("statusCode", status);
        
        return trace;
    }
}
