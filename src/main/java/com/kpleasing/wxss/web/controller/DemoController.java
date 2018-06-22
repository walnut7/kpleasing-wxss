package com.kpleasing.wxss.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "demo")
public class DemoController {
	
	private static final Logger logger = Logger.getLogger(DemoController.class);
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String doTest(HttpServletRequest request, HttpServletResponse response) {
		logger.info("跳转测试页面.....");
		return "register/demo";
	}
}
