package com.kpleasing.wxss.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kpleasing.wxss.entity.Car;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.entity.Scheme;
import com.kpleasing.wxss.entity.SpCar;
import com.kpleasing.wxss.entity.SpInfo;
import com.kpleasing.wxss.web.service.BpCarListService;
import com.kpleasing.wxss.web.service.SpBpInfoService;

/**
 * 获取SP、车辆、方案的基本信息
 * 
 * @author zhangxing
 *
 */
@Controller
@RequestMapping("/carList")
public class CarlistController {
	private static final Logger logger = Logger.getLogger(CarlistController.class);

	@Autowired
	private BpCarListService bpCarListServer;

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
	 * 获取Sp准入的方案信息
	 * 
	 * @param model_id
	 * @return
	 */
	@RequestMapping(value = "/carDetail", method = RequestMethod.GET)
	public String getSpCarPlan(@RequestParam Integer bpId, @RequestParam String bpCode, @RequestParam Integer modelId,
			ModelMap model) {

		// 获取sp的信息
		SpInfo sp = spBpInfoServer.getSpBpInfo(bpId, bpCode);
		model.put("sp", sp);

		// 获取车型信息
		SpCar car = bpCarListServer.getSpCarByModelIdUniqueOne(bpId, bpCode, modelId);
		model.put("car", car);
		logger.info("--------车car:" + car);
		// 获取车型ID参数
		List<Scheme> schemes = bpCarListServer.getSchemeList(bpId, bpCode, modelId);
		logger.info("--------方案信息schemes:" + schemes.toString());
		model.put("schemes", schemes);

		return "car_detail";
	}

	/**
	 * 进入登录页面
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/gotoLogin", method = RequestMethod.GET)
	public String gotoLogin(HttpServletRequest req, ModelMap model) {
		return "login";
	}

	/**
	 * 首页清单
	 * 
	 * @param bp_id
	 * @param bp_code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String gotoMain(@RequestParam Integer bpId, @RequestParam String bpCode, ModelMap model) {
		// 获取bp_id和bp_code
		SpInfo sp = spBpInfoServer.getSpBpInfo(bpId, bpCode);
		model.put("sp", sp);

		// // 获取SP的车型信息
		List<SpCar> spCars = bpCarListServer.getSpCarList(bpId, bpCode);
		model.put("spcars", spCars);

		return "main";
	}

}
