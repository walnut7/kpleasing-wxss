package com.kpleasing.wxss.web.spring;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class WebBindingInitializerLocal implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DateEditorSupport());
	}

	@Override
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditorSupport());
	}
}
