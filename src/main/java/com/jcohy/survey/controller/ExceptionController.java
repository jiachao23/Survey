package com.jcohy.survey.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jcohy.survey.SurveyException;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.04.0 2022/4/25:11:41
 * @since 2022.04.0
 */
@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({ BindException.class })
	public String handleException(Exception ex, Model model) {
		StringBuilder sb = new StringBuilder();
		if (ex instanceof BindException) {
			BindException bindException = (BindException) ex;
			BindingResult bindingResult = bindException.getBindingResult();
			bindingResult.getFieldErrors().forEach(fieldError -> {
				sb.append(fieldError.getDefaultMessage());
			});
		}

		System.out.println(sb);
		model.addAttribute("message", sb.toString());
		return "redirect:/error";
	}

	@ExceptionHandler({ SurveyException.class })
	public String handleException(SurveyException ex, Model model) {
		String message = ex.getMessage();
		model.addAttribute("message", message);
		return "redirect:/error";
	}
}
