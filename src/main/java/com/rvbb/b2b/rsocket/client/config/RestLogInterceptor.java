package com.rvbb.b2b.rsocket.client.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RestLogInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception arg3)
            throws Exception {
        logInput(req, handler);
        logOutput(res);
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO: validate areaToken and typical input parameters.
        return true;
    }

    private void logInput(HttpServletRequest request, Object body) throws IOException {
        log.info("Incomming Request >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("URL          : {}", request.getRequestURL());
        log.info("Method       : {}", request.getMethod());
        log.info("Content-Type : {}", request.getHeader("Content-Type"));
        if (log.isDebugEnabled()) {
            log.debug("Handler      : {}", !ObjectUtils.isEmpty(body) ? body.toString() : "empty");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Incomming Request");
    }

    private void logOutput(HttpServletResponse response) throws IOException {
        log.info("Response >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("Status code       : {}", response.getStatus());
        if (log.isDebugEnabled()) {
            try {
                // @TODO
            } catch (Exception e) {
                log.error(
                        "////////////////Parse Rest response fail, most reason are exception or response is not a ServiceResponse object///////////////");
            }
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Response");
    }
}