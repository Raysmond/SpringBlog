package com.raysmond.blog.config.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raysmond.blog.support.web.ViewHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Date;

/**
 * Request processing time logging
 *
 * @author: Raysmond
 */
public class RequestProcessingTimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory
            .getLogger(RequestProcessingTimeInterceptor.class);

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        String url = request.getRequestURL().toString();

        logger.info("Start Request:" + url + ": Time=" + new Date());
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

        long startTime = (Long) request.getAttribute("startTime");
        long time = System.currentTimeMillis() - startTime;

        String url = request.getRequestURL().toString();

        logger.info("Complete Request:" + url + ": Time=" + new Date());
        logger.info("Time Taken=" + time+"ms");
    }

}