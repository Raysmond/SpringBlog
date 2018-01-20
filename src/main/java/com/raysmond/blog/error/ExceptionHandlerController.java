package com.raysmond.blog.error;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * General error handler for the application.
 */
@ControllerAdvice
@Slf4j
class ExceptionHandlerController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFound(HttpServletRequest request, NotFoundException exception) {
        String uri = request.getRequestURI();

        log.error("Request page: {} raised NotFoundException {}", uri, exception);

        ModelAndView model = new ModelAndView("error/general");
        model.addObject("status", HttpStatus.NOT_FOUND.value());
        model.addObject("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addObject("path", uri);
        model.addObject("customMessage", exception.getMessage());

        return model;
    }

    /**
     * Handle all exceptions
     */
//	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpServletRequest request, Exception exception) {
        String uri = request.getRequestURI();
        log.error("Request page: {} raised exception {}", uri, exception);

        ModelAndView model = new ModelAndView("error/general");
        model.addObject("error", Throwables.getRootCause(exception).getMessage());
        model.addObject("status", Throwables.getRootCause(exception).getCause());
        model.addObject("path", uri);
        model.addObject("customMessage", exception.getMessage());

        return model;
    }
}