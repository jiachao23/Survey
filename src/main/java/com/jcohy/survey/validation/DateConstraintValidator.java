package com.jcohy.survey.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jcohy.survey.utils.DateUtils;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.04.0 2022/4/25:11:24
 * @since 2022.04.0
 */
//@Component
public class DateConstraintValidator implements ConstraintValidator<DateConstraint, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return DateUtils.parse(value) != null;
	}
}
