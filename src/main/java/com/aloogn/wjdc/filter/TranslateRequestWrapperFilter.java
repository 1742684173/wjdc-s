package com.aloogn.wjdc.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TranslateRequestWrapperFilter implements Filter{

    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }

    /**
     * 过滤
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
            //System.out.println(((RequestWrapper) requestWrapper).getBody());
            // do something
        }
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
//        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 初始化
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
