package com.raysmond.blog.error;

import com.raysmond.blog.BlogSetting;
import com.raysmond.blog.support.web.ViewHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Throwables;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandlerController {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

	private BlogSetting blogSetting;
	private ViewHelper viewHelper;

	@Autowired
	public ExceptionHandlerController(BlogSetting blogSetting, ViewHelper viewHelper){
		this.blogSetting = blogSetting;
		this.viewHelper = viewHelper;
	}

	private void addModelHelpers(ModelAndView model){
		model.addObject("App", blogSetting);
		model.addObject("viewHelper", viewHelper);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView notFound(HttpServletRequest request, NotFoundException exception){
		String uri = request.getRequestURI();
		logger.error("Request page: " + uri + " raised NotFoundException : " + exception);

		ModelAndView model = new ModelAndView("error/general");
		model.addObject("statusCode", HttpStatus.NOT_FOUND.value());
		model.addObject("exceptionMessage", HttpStatus.NOT_FOUND.getReasonPhrase());
		model.addObject("requestUri", uri);
		model.addObject("message", exception.getMessage());

		addModelHelpers(model);
		return model;
	}

	/**
	 * Handle all exceptions
	 */
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler(Exception.class)
	public ModelAndView exception(HttpServletRequest request, Exception exception) {
		String uri = request.getRequestURI();
		logger.error("Request page: " + uri + " raised exception : " + exception);

		ModelAndView model = new ModelAndView("error/general");
		model.addObject("exceptionMessage", Throwables.getRootCause(exception).getMessage());
		model.addObject("statusCode", Throwables.getRootCause(exception).getCause());
		model.addObject("requestUri", uri);
		model.addObject("message", exception.getMessage());

		addModelHelpers(model);
		return model;
	}
}