package com.kpleasing.wxss.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kpleasing.wxss.web.service.DemoService;


@Controller
@RequestMapping(value = "/demo")
public class DemoController {
	
	private static final Logger logger = Logger.getLogger(DemoController.class);
	
	@Autowired
	private DemoService demoService;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String doTest(HttpServletRequest request, HttpServletResponse response) {
		logger.info("跳转测试页面.....");
		return "register/demo";
	}
	
	@RequestMapping(value = "/mongotest", method = RequestMethod.GET)
	public String doMongoTest(HttpServletRequest request, HttpServletResponse response) {
		logger.info("entry mongo test .....");
		demoService.testMongoInsert();
		return "register/demo";
	}
}
