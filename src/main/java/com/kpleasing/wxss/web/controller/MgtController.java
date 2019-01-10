package com.kpleasing.wxss.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.enums.LOGIN_TYPE;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;
import com.kpleasing.wxss.pojo.SearchParam;
import com.kpleasing.wxss.util.JsonUtil;
import com.kpleasing.wxss.util.JsonUtil.JsonDateValueProcessor;
import com.kpleasing.wxss.web.service.OrderService;
import com.kpleasing.wxss.web.service.RegisterService;

import net.sf.json.JsonConfig;


@Controller
@RequestMapping(value = "manage")
public class MgtController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(MgtController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RegisterService registerService;
	
	
	/**
	 * 进件管理主页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "console", method = RequestMethod.GET)
	public ModelAndView openConsoleMain(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		return new ModelAndView("manage/console", model);
	}
	
	
	/**
	 * 进件跟踪
	 * @param request
	 * @param custid
	 * @return
	 */
	@RequestMapping(value = "track", method = RequestMethod.GET)
	public ModelAndView openTrackPage(HttpServletRequest request, String custid) {
		ModelMap model = new ModelMap();
		model.put("custid", custid);
		return new ModelAndView("manage/detail/track", model);
	}
	
	
	/**
	 * 进件流程图
	 * @param request
	 * @param custid
	 * @return
	 */
	@RequestMapping(value = "flowchart", method = RequestMethod.GET)
	public ModelAndView openRsFlowChart(HttpServletRequest request, String custid) {
		ModelMap model = new ModelMap();
		model.put("custid", custid);
		return new ModelAndView("manage/detail/flowchart", model);
	}
	
	/**
	 * 模拟用户页面
	 * @param request
	 * @param custid
	 * @return
	 */
	@RequestMapping(value = "shadow", method = RequestMethod.GET)
	public ModelAndView trackShadowPage(HttpServletRequest request, String custid) {
		ModelMap model = new ModelMap();
		logger.info("跟踪用户【"+custid+"】信息......");
		try {
			Order order = orderService.getCurrentOrderByCustId(custid);
			if(null != order) {
				HttpSession session = request.getSession(false);
				LoginUser loginUser = new LoginUser();
				loginUser.setUserId(custid);
				loginUser.setUserName(order.getUserName());
				loginUser.setPhone(order.getPhone());
				loginUser.setCertCode(order.getCertId());
				loginUser.setOpenId(order.getWxOpenId());
				loginUser.setLoginType(LOGIN_TYPE.MANUAL.CODE);
				
				session.setAttribute(USER_INFO, loginUser);
				
				StringBuilder paramters =  new StringBuilder();
				paramters.append("?bpId=").append(order.getBpId()).append("&bpCode=").append(order.getBpCode())
				.append("&planId=").append(order.getPlanId()).append("&saleId=").append(order.getSaleId());
				return new ModelAndView("redirect:/register/route" + paramters.toString());
			}
		} catch (WXSSException e) {
			logger.error(e.getDesc(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("manage/detail/error", model);
	}
	
	
	/**
	 * 进件用户列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "order_list", method = RequestMethod.POST)
	public @ResponseBody String getOrderListInfo(HttpServletRequest request, SearchParam param) {
		logger.info("search infomation："+param.toString());
		try {
			List<Order> orders = orderService.getAllOrderList(param);
			int count = orderService.getTotalOrderCount(param);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			StringBuilder rtnJson = new StringBuilder();
			rtnJson.append("{\"total\":" + count + ",\"page\":" + param.getPage() + ",\"rows\":").append(JsonUtil.JSON_List2String(orders, jsonConfig)).append("}");
			return rtnJson.toString();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"total\":0,\"page\":1,\"rows\":[]}";
	}
	
	
	/**
	 * 不开浦发电子户
	 * @param request
	 * @param custid
	 * @return
	 */
	@RequestMapping(value = "igone_spdb", method = RequestMethod.POST)
	public @ResponseBody String igoneOpenSpdbAccount(HttpServletRequest request, String custid) {
		try {
			registerService.updateSpdbFlag(custid, (byte)1);
			return "设置成功!";
		} catch (WXSSException e) {
			logger.error(e.getDesc(), e);
			return "设置失败【"+e.getDesc()+"】!";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "设置失败【"+e.getMessage()+"】!";
		}
	}
	
	
	/**
	 *  微信授权解绑
	 * @param request
	 * @param custid
	 * @return
	 */
	@RequestMapping(value = "unbind", method = RequestMethod.POST)
	public @ResponseBody String unbindWXOpenid(HttpServletRequest request, String custid) {
		logger.info("=====>"+custid);
		return "success";
	}
}
