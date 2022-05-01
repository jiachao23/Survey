package com.jcohy.survey.service;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/4/19:19:23
 * @since 2022.0.1
 */
public class ClassModel {

	private String className;

	private Integer readCount;

	public String getClassName() {
		return className;
	}

	public ClassModel setClassName(String className) {
		this.className = className;
		return this;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public ClassModel setReadCount(Integer readCount) {
		this.readCount = readCount;
		return this;
	}
}
