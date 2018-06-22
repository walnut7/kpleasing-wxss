package com.kpleasing.wxss.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kpleasing.wxss.entity.SpInfo;
import com.kpleasing.wxss.web.service.SpBpInfoService;

@Controller
@RequestMapping("/spBpInfo")
public class SpBpInfoController {

	private static final Logger logger = Logger.getLogger(SpBpInfoController.class);

	@Autowired
	private SpBpInfoService spBpInfoServer;

	/**
	 * 判断 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * AJAX获取Sp的基本信息
	 * 
	 * @param bp_id
	 * @param bp_code
	 * @param pw
	 */
	@RequestMapping(value = "getSpBpInfo", method = RequestMethod.POST)
	public @ResponseBody String getSpBpInfo(HttpServletRequest request, String bp_id, String bp_code) {
		logger.info("-----获取sp的信息-----------");
		SpInfo sp = new SpInfo();
		// 判断是否是数字
		logger.info("bp_id:" + bp_id + ",bp_code:" + bp_code);

		if (isInteger(bp_id) == false) {
			bp_id = "";
		}

		// 如果ID和CODE有一者为空，则直接终止请求
		if ("".equals(bp_id) || "".equals(bp_code)) {
			return "{\"result\":\"failed\",\"message\":\"出错\"}";
		}
		try {
			logger.info("-----开始调用数据库-------------");
			// 从数据库获取SP的基本信息
			sp = spBpInfoServer.getSpBpInfo(Integer.parseInt(bp_id), bp_code);
			logger.info(sp.toString());
			String sp_json_str = JSON.toJSONString(sp);
			
			return sp_json_str;

		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		return "{\"result\":\"failed\",\"message\":\"出错\"}"; 
	}

}
