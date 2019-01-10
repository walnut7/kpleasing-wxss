package com.kpleasing.wxss.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.entity.CertInfo;
import com.kpleasing.wxss.entity.City;
import com.kpleasing.wxss.entity.ContactInfo;
import com.kpleasing.wxss.entity.DictConfig;
import com.kpleasing.wxss.entity.DrivingLicenseInfo;
import com.kpleasing.wxss.entity.FaceVideo;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.entity.PersonInfo;
import com.kpleasing.wxss.entity.Province;
import com.kpleasing.wxss.entity.WorkInfo;
import com.kpleasing.wxss.enums.LOGIN_TYPE;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;
import com.kpleasing.wxss.util.BaiDuAipOcrHelper;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.FileUtil;
import com.kpleasing.wxss.util.JsonUtil;
import com.kpleasing.wxss.util.SPDBHelper;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.web.service.ConfigService;
import com.kpleasing.wxss.web.service.OrderService;
import com.kpleasing.wxss.web.service.RegisterService;

import net.sf.json.JsonConfig;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping(value = "register")
public class RegisterController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(RegisterController.class);
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ConfigService configService;
	
	@RequestMapping(value = "route")
	public String doRegisterRoute(HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginUser loginUser = getLoginUserObject(request);
			Order order = orderService.getCurrentOrderByLoginUser(loginUser);
			
			String bpId = request.getParameter("bpId");
			String bpCode = request.getParameter("bpCode");
			String planId = request.getParameter("planId");
			String saleId = request.getParameter("saleId");
			orderService.setBpInfo(order, bpId, bpCode, planId, saleId);
			orderService.initFaceVideoInfo(loginUser.getUserId(), bpId, planId);
			
			if(null != order) {
				if(LOGIN_TYPE.MANUAL.CODE == order.getLoginChannel()) {
					if(null == order.getBpId() || null == order.getPlanId()) {
						return "redirect:/logout";
					}
				}
				
				logger.info("start register......当前步骤=" + order.getStepNo());
				switch (order.getStepNo()) {
				    case 0:  ;
				    case 1: return "redirect:certInfoA";
				    case 2: return "redirect:certInfoB";
				    case 3: return "redirect:driverInfo";
				    case 4: return "redirect:bankInfo";
				    case 5: return "redirect:personInfo";
				    case 6: return "redirect:contactInfo";
				    case 7: return "redirect:workInfo";
				    case 8: return "redirect:confirm";
				    case 9: ;
				    case 10: return "redirect:faceVideo";
				    //case 10: return "redirect:videoAudit";
				    default : return "redirect:certInfoA";
				}
			}
		} catch (WXSSException e) {
			logger.error("register route error:"+e.getDesc(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "redirect:certInfoA";
	}
	
	
	/**
	 * 跳转至身份证正面信息收集页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "certInfoA", method = RequestMethod.GET)
	public ModelAndView redirectCertInfoA(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			CertInfo certInfo = registerService.queryCertInfoByCustId(custId);
			if(null != certInfo) {
				model.put("cert", certInfo);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/cert_infoA", model);
	}
	
	
	/**
	 * 保存身份证正面信息
	 * @param request
	 * @param certInfo
	 * @return
	 */
	@RequestMapping(value = "saveCertInfoA", method = RequestMethod.POST)
	public @ResponseBody String saveCertInfoA(HttpServletRequest request, CertInfo certInfo) {
		try {
			logger.info("start save cert info A......" );
			String custId = this.getLoginUserCustId(request);
			
			String applyNo = orderService.getRunningOrder(custId);
			if(StringUtils.isNotBlank(applyNo)) {
				logger.info("系统含有进行中的订单："+applyNo);
				return "{\"result\":\"failed\",\"message\":\"您当前含有未完成的订单，订单号："+applyNo+".\"}";
			}
			registerService.saveCertInfoA(custId, certInfo);
			
			return "{\"result\":\"success\",\"message\":\"保存成功!\"}";
		} catch (WXSSException e) {
			logger.error(e.getDesc(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"提交失败!\"}";
	}
	
	
	/**
	 * 跳转至身份证反面信息收集页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "certInfoB", method = RequestMethod.GET)
	public ModelAndView redirectCertInfoB(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			CertInfo certInfo = registerService.queryCertInfoByCustId(custId);
			if(null!=certInfo) {
				model.put("cert", certInfo);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/cert_infoB", model);
	}
	
	
	/**
	 * 保存身份证反面信息
	 * @param request
	 * @param certInfo
	 * @return
	 */
	@RequestMapping(value = "saveCertInfoB", method = RequestMethod.POST)
	public @ResponseBody String saveCertInfoB(HttpServletRequest request, CertInfo certInfo) {
		try {
			logger.info("start save cert info B......" );
			String custId = this.getLoginUserCustId(request);
			
			String applyNo = orderService.getRunningOrder(custId);
			if(StringUtils.isNotBlank(applyNo)) {
				logger.info("系统含有进行中的订单："+applyNo);
				return "{\"result\":\"failed\",\"message\":\"您当前含有未完成的订单，订单号："+applyNo+".\"}";
			}
			registerService.saveCertInfoB(custId, certInfo);
			
			return "{\"result\":\"success\",\"message\":\"保存成功!\"}";
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"提交失败!\"}";
	}
	
	
	/**
	 * 跳转至驾照信息收集页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "driverInfo", method = RequestMethod.GET)
	public ModelAndView redirectDriverInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			DrivingLicenseInfo driverInfo = registerService.queryDrivingLicenseInfoByCustId(custId);
			if(null!=driverInfo) {
				model.put("driver", driverInfo);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/driver_info", model);
	}
	
	
	/**
	 * 保存驾照相关信息
	 * @param request
	 * @param driverInfo
	 * @return
	 */
	@RequestMapping(value = "saveDriverInfo", method = RequestMethod.POST)
	public @ResponseBody String saveDriverInfo(HttpServletRequest request, DrivingLicenseInfo driverInfo) {
		try {
			logger.info("start save driving license info ......" );
			String custId = this.getLoginUserCustId(request);
			
			String applyNo = orderService.getRunningOrder(custId);
			if(StringUtils.isNotBlank(applyNo)) {
				logger.info("系统含有进行中的订单："+applyNo);
				return "{\"result\":\"failed\",\"message\":\"您当前含有未完成的订单，订单号："+applyNo+".\"}";
			}
			registerService.saveDrivingLicenseInfo(custId, driverInfo);
			
			return "{\"result\":\"success\",\"message\":\"保存成功!\"}";
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"提交失败!\"}";
	}
	
	
	/**
	 * 跳转至银行账户信息收集页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "bankInfo", method = RequestMethod.GET)
	public ModelAndView redirectBankInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			LoginUser loginUser = getLoginUserObject(request);
			Order order = orderService.getCurrentOrderByLoginUser(loginUser);
			
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			BankInfo bankInfo = registerService.queryBankInfoByCustId(custId);
			model.put("spdb_cert_flag", 1);
			if(null!=bankInfo) {
				if(StringUtils.isNotBlank(bankInfo.getSpdbCertCode())) {
					if(!bankInfo.getSpdbCertCode().equals(order.getCertId())) {
						model.put("spdb_cert_flag", 0);
					}
				}
				model.put("bank", bankInfo);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/bank_info", model);
	}
	
	
	/**
	 * 浦发银行-随机密码获取
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "sendVerifyCode", method = RequestMethod.POST)
	public @ResponseBody String sendSPDBVerifyCode(HttpServletRequest request, BankInfo bankInfo) {
		try {
			String custId = this.getLoginUserCustId(request);
			registerService.SendSpdbVerifyCode(custId, bankInfo);
			
			return "{\"result\":\"success\",\"message\":\"短信已发送，请注意查收！\"}";
		} catch(WXSSException e) {
			logger.error(e.getMessage(), e);
			return "{\"result\":\"failed\",\"message\":\""+e.getDesc()+"\"}";
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"短信验证码发送失败，请重试!\"}";
	}
	
	
	/**
	  * 保存银行账户相关信息
	 * @param request
	 * @param driverInfo
	 * @return
	 */
	@RequestMapping(value = "saveBankInfo", method = RequestMethod.POST)
	public @ResponseBody String bindAndSaveBankInfo(HttpServletRequest request, BankInfo bankInfo) {
		try {
			logger.info("save bank info ......");
			String custId = this.getLoginUserCustId(request);
			String verifyCode = request.getParameter("verifyCode");
			
			String applyNo = orderService.getRunningOrder(custId);
			if(StringUtils.isNotBlank(applyNo)) {
				logger.info("系统含有进行中的订单："+applyNo);
				return "{\"result\":\"failed\",\"message\":\"您当前含有未完成的订单，订单号："+applyNo+".\"}";
			}
			
			// 保存信息
			registerService.saveBankInfo(custId, bankInfo);
			
			// 浦发二、三类户开户
			registerService.openSpdbAccount(custId, verifyCode);
			
			
			return "{\"result\":\"success\",\"message\":\"操作成功!\"}";
		} catch (WXSSException e) {
			String errorMsg = SPDBHelper.getReflectErrorInfo(null, e.getDesc());
			logger.error("开户错误提示信息："+errorMsg , e);
			return "{\"result\":\"failed\",\"message\":\""+errorMsg+"\"}";
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"提交失败!\"}";
	}
	
	
	/**
	 * 跳转至个人信息收集页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "personInfo", method = RequestMethod.GET)
	public ModelAndView redirectPersonInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			PersonInfo personInfo = registerService.queryPersonInfoByCustId(custId);
			if(null!=personInfo) {
				model.put("person", personInfo);
			} 
			
			CertInfo certInfo = registerService.queryCertInfoByCustId(custId);
			model.put("username", certInfo.getUserName());
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/person_info", model);
	}
	
	
	/**
	 * 保存个人信息
	 * @param request
	 * @param driverInfo
	 * @return
	 */
	@RequestMapping(value = "savePersonInfo", method = RequestMethod.POST)
	public @ResponseBody String savePersonInfo(HttpServletRequest request, PersonInfo personInfo) {
		try {
			logger.info("start save driving license info ......" );
			String custId = this.getLoginUserCustId(request);
			
			String applyNo = orderService.getRunningOrder(custId);
			if(StringUtils.isNotBlank(applyNo)) {
				logger.info("系统含有进行中的订单："+applyNo);
				return "{\"result\":\"failed\",\"message\":\"您当前含有未完成的订单，订单号："+applyNo+".\"}";
			}
			registerService.savePersonInfo(custId, personInfo);
			
			return "{\"result\":\"success\",\"message\":\"保存成功!\"}";
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"提交失败!\"}";
	}
	

	/**
	 * 跳转至联系人页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "contactInfo", method = RequestMethod.GET)
	public ModelAndView redirectContactInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户婚姻状态......");
			PersonInfo personInfo = registerService.queryPersonInfoByCustId(custId);
			if(null!=personInfo) {
				model.put("marrycode", personInfo.getMarrStatusCode());
			}
			
			logger.info("查询【"+custId+"】用户提交的联系人信息......");
			ContactInfo contactInfo = registerService.queryContactInfoByCustId(custId);
			if(null!=contactInfo) {
				model.put("contact", contactInfo);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/contact_info", model);
	}
	
	
	/**
	 * 保存联系人信息
	 * @param request
	 * @param contactInfo
	 * @return
	 */
	@RequestMapping(value = "saveContactInfo", method = RequestMethod.POST)
	public @ResponseBody String saveContactInfo(HttpServletRequest request, ContactInfo contactInfo) {
		try {
			logger.info("start save driving license info ......" );
			String custId = this.getLoginUserCustId(request);
			
			String applyNo = orderService.getRunningOrder(custId);
			if(StringUtils.isNotBlank(applyNo)) {
				logger.info("系统含有进行中的订单："+applyNo);
				return "{\"result\":\"failed\",\"message\":\"您当前含有未完成的订单，订单号："+applyNo+".\"}";
			}
			registerService.saveContactInfo(custId, contactInfo);
			
			return "{\"result\":\"success\",\"message\":\"保存成功!\"}";
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"提交失败!\"}";
	}
	
	
	/**
	 *  跳转至工作信息收集页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "workInfo", method = RequestMethod.GET)
	public ModelAndView redirectWorkInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			WorkInfo workInfo = registerService.queryWorkInfoByCustId(custId);
			if(null!=workInfo) {
				model.put("work", workInfo);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/work_info", model);
	}
	
	
	/**
	 * 保存工作信息
	 * @param request
	 * @param driverInfo
	 * @return
	 */
	@RequestMapping(value = "saveWorkInfo", method = RequestMethod.POST)
	public @ResponseBody String saveWorkInfo(HttpServletRequest request, WorkInfo workInfo) {
		try {
			logger.info("start save driving license info ......" );
			String custId = this.getLoginUserCustId(request);
			
			String applyNo = orderService.getRunningOrder(custId);
			if(StringUtils.isNotBlank(applyNo)) {
				logger.info("系统含有进行中的订单："+applyNo);
				return "{\"result\":\"failed\",\"message\":\"您当前含有未完成的订单，订单号："+applyNo+".\"}";
			}
			registerService.saveWorkInfo(custId, workInfo);
			
			return "{\"result\":\"success\",\"message\":\"保存成功!\"}";
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"提交失败!\"}";
	}
	
	
	/**
	 * 跳转至信息确认页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "confirm", method = RequestMethod.GET)
	public ModelAndView redirectConfirmInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			Order order = orderService.getCurrentOrderByLoginUser(getLoginUserObject(request));
			if(null!=order) {
				model.put("order", order);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/confirm", model);
	}
	
	 
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitInfo", method = RequestMethod.POST)
	public @ResponseBody String submitConfirmInfo(HttpServletRequest request) {
		try {
			logger.info("start submit info ......" );
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			Order order = orderService.getCurrentOrderByLoginUser(getLoginUserObject(request));
			
			String applyNo = orderService.getRunningOrder(custId);
			if(StringUtils.isNotBlank(applyNo)) {
				logger.info("系统含有进行中的订单："+applyNo);
				return "{\"result\":\"failed\",\"message\":\"您当前含有未完成的订单，订单号："+applyNo+".\"}";
			}
			
			// 身份认证
			if(order.getLoginChannel()==LOGIN_TYPE.MANUAL.CODE) {
				logger.info("身份认证.");
				registerService.doIdentityAuth(order); 
			}
			
			// 同步用户信息
			registerService.syncCustomerInfo(order);
			
			// 创建订单
			if(order.getLoginChannel()==LOGIN_TYPE.MANUAL.CODE) {
				logger.info("认证同步.");
				registerService.doFirstAudit(custId);
				
				logger.info("创建订单.");
				registerService.createLeasingOrder(order);
			}
			
			// 更新步骤号
			orderService.updateStepNo(Integer.valueOf(custId), (byte)8);
			
			return "{\"result\":\"success\",\"message\":\"保存成功!\"}";
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
			return "{\"result\":\"failed\",\"message\":\""+e.getDesc()+"\"}";
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"result\":\"failed\",\"message\":\"提交失败!\"}";
	}
	
	
	/**
	 * 跳转至视频面签预约页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "videoAudit", method = RequestMethod.GET)
	public ModelAndView redirectPreAuditVideo(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		try {
			String custId = this.getLoginUserCustId(request);
			logger.info("查询【"+custId+"】用户提交的信息......");
			FaceVideo facevideo = registerService.queryFaceVideoByCustId(custId);
			if(null!=facevideo) {
				Configurate config = configService.getConfig();
				model.put("fvImgUrl", config.FV_IMG_URL+facevideo.getSeriesLogo());
				model.put("facevideo", facevideo);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("register/pre_audit", model);
	}
	
	
	/**
	 * 提交视频面签预约
	 * @param request
	 * @param preVideoAudit
	 * @return
	 */
	@RequestMapping(value = "faceVideo")
	public ModelAndView submitPerAudit(HttpServletRequest request, FaceVideo preVideoAudit) {
		logger.info("start face vedio booking ......" );
		Configurate config = configService.getConfig();
		try {
			String custId = this.getLoginUserCustId(request);
			Order order = orderService.getCurrentOrderByLoginUser(getLoginUserObject(request));
			
			// 保存预约信息
//			FaceVideo faceVedio = registerService.savePerVideoAudit(custId, preVideoAudit);
			
			// 推送预约信息至CRM
//			logger.info("推送视频面签预约信息至CRM...");
//			registerService.sendPerVideoAuditReq(faceVedio);
			
			// 更新步骤号
			logger.info("更新步骤号...");
			orderService.updateStepNo(Integer.valueOf(custId), (byte)9);
			
			// 自动登录跳转享买车个人中心页面
			StringBuilder parameters = new StringBuilder();
			parameters.append("cust_id=").append(custId)
			.append("&phone=").append(order.getPhone())
			.append("&time_stamp=").append(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
			
			String result = parameters.toString() + "&key=" + config.XMC_LOGIN_KEY;
			logger.info("Sign Before MD5："+ result);
			String sign = Security.MD5Encode(result).toUpperCase();
			logger.info("Sign Result："+sign);
			
			parameters.append("&sign=").append(sign);
			
			logger.info("redirect url : " + config.XMC_PERSON_URL+"?"+parameters.toString());
			return new ModelAndView(new RedirectView(config.XMC_PERSON_URL+"?"+parameters.toString()));
			
		} catch (WXSSException e) {
			logger.error(e.getDesc(), e);
		}
		return new ModelAndView(new RedirectView(config.XMC_PERSON_URL)); 
	}
	
	
	/**
	 * 下拉参数选择
	 * @param request
	 * @param driverInfo
	 * @return
	 */
	@RequestMapping(value = "getSelectParamList", method = RequestMethod.POST)
	public @ResponseBody String getDictParamList(HttpServletRequest request, String category) {
		try {
			Order order = orderService.getCurrentOrderByLoginUser(getLoginUserObject(request));
			String pType = registerService.getSchemePlanType(order);
			
			StringBuilder strDict = new StringBuilder();
			List<DictConfig> dicts = configService.getDictByPId(Integer.valueOf(category));
			strDict.append("[");
			for(DictConfig dict : dicts) {
				strDict.append("{\"label\":\"").append(dict.getNodeValue()).append("\",")
				       .append("\"value\":\"").append(dict.getNodeCode()).append("\",},");
			}
			if(null != pType && "BO".equals(pType) && "86".equals(category)) {
				strDict.append("{\"label\":\"中国招商银行\",\"value\":\"CMB\",},");
			}
			strDict.append("]");
			return strDict.toString();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "[]";
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSelectProvince", method = RequestMethod.POST)
	public @ResponseBody String getProvinceList(HttpServletRequest request) {
		try {
			StringBuilder strProvince = new StringBuilder();
			List<Province> provinceList = configService.getProvinceList();
			strProvince.append("[");
			for(Province province : provinceList) {
				strProvince.append("{\"label\":\"").append(province.getProvinceName()).append("\",")
				       .append("\"value\":\"").append(province.getProvinceCode()).append("\",},");
			}
			strProvince.append("]");
			return strProvince.toString();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "[]";
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSelectCity", method = RequestMethod.POST)
	public @ResponseBody String getCityList(HttpServletRequest request, String provinceCode) {
		try {
			StringBuilder strCity = new StringBuilder();
			List<City> cities = configService.getCities(provinceCode);
			strCity.append("[");
			for(City city : cities) {
				strCity.append("{\"label\":\"").append(city.getCityName()).append("\",")
				       .append("\"value\":\"").append(city.getCityCode()).append("\",},");
			}
			strCity.append("]");
			return strCity.toString();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "[]";
	}
	
	
	/**
	 * 
	 * @param request
	 * @param type
	 * @param base64string
	 * @return
	 */
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	public @ResponseBody String uploadImageInfo(HttpServletRequest request, String type, String base64string) {
		long start1 = System.currentTimeMillis();
		try {
			String custId = this.getLoginUserCustId(request);
			Configurate config = configService.getConfig();
			File f = new File(config.IMG_PATH + File.separator + "rs" + File.separator + custId);
			if(!f.exists() && !f.isDirectory()) {
				f.mkdirs();
			}
			String fileName= File.separator + "rs" + File.separator + custId + File.separator + StringUtil.getDateSerialNo6() + ".jpg";
			String fullpath = config.IMG_PATH + fileName;
			logger.info("上传图片类型："+ type + "\t图片存放路径： " + fullpath);
			
			if(generateImage(base64string, fullpath)) {
				BaiDuAipOcrHelper ocrHelper = BaiDuAipOcrHelper.getInstance();
				if("cert".equals(type)) {
					// 默认解析身份证（配偶身份证，紧急联系人身份证信息）
					long start = System.currentTimeMillis();
					// CertInfo certInfo = ocrHelper.readIdCardFront(fullpath);
					CertInfo certInfo = ocrHelper.readIdCardFrontBase64(base64string);
					long end = System.currentTimeMillis();
					FileUtil.record("身份证OCR解析起始时间："+start+"\t身份证OCR解析完成时间："+end+"\t总计用时："+ (end-start) + "毫秒\n");
					
					certInfo.setCertFrontImagePath(fileName);
					
					JsonConfig jsonConfig = new JsonConfig();
				    jsonConfig.registerJsonValueProcessor(java.util.Date.class,  new JsonUtil.JsonDateValueProcessor("yyyy年MM月dd日"));
				    String json = JsonUtil.JSON_Bean2String(certInfo, jsonConfig);
				    logger.info(json);
				    
				    return json;
				} else if("certA".equals(type)) {
					registerService.updateCertInfoImageFrontPath(custId, fileName);
					
					long start = System.currentTimeMillis();
//					CertInfo certInfoA = ocrHelper.readIdCardFront(fullpath);
					CertInfo certInfoA = ocrHelper.readIdCardFrontBase64(base64string);
					long end = System.currentTimeMillis();
					FileUtil.record("身份证正面OCR解析起始时间："+start+"\t身份证正面OCR解析完成时间："+end+"\t总计用时："+ (end-start) + "毫秒\n");
					
					certInfoA.setCertFrontImagePath(fileName);
					
					JsonConfig jsonConfig = new JsonConfig();
				    jsonConfig.registerJsonValueProcessor(java.util.Date.class,  new JsonUtil.JsonDateValueProcessor("yyyy年MM月dd日"));
				    String json = JsonUtil.JSON_Bean2String(certInfoA, jsonConfig);
				    logger.info(json);
				    
				    return json;
				} else if("certB".equals(type)) {
					registerService.updateCertInfoImageBackPath(custId, fileName);
					
					long start = System.currentTimeMillis();
//					CertInfo certInfoB = ocrHelper.readIdCardBack(fullpath);
					CertInfo certInfoB = ocrHelper.readIdCardBackBase64(base64string);
					long end = System.currentTimeMillis();
					FileUtil.record("身份证反面OCR解析起始时间："+start+"\t身份证反面OCR解析完成时间："+end+"\t总计用时："+ (end-start) + "毫秒\n");
					
					certInfoB.setCertBackImagePath(fileName);
					
					String json = JsonUtil.JSON_Bean2String(certInfoB);
					logger.info(json);
					
					return json;
				} else if("driverInfoA".equals(type)) {
					registerService.updateDriverInfoImagePath(custId, fileName);
					
					long start = System.currentTimeMillis();
//					DrivingLicenseInfo drivingA = ocrHelper.readDriveLicense(fullpath);
					DrivingLicenseInfo drivingA = ocrHelper.readDriveLicenseBase64(base64string);
					long end = System.currentTimeMillis();
					FileUtil.record("驾照正本OCR解析起始时间："+start+"\t驾照正本OCR解析完成时间："+end+"\t总计用时："+ (end-start) + "毫秒\n");
					
					drivingA.setDriveTypeDesc(configService.getDictByCode(drivingA.getDriveType()));
					drivingA.setDriveImagePath(fileName);
					
					JsonConfig jsonConfig = new JsonConfig();
				    jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonUtil.JsonDateValueProcessor("yyyy年MM月dd日"));
				    String json = JsonUtil.JSON_Bean2String(drivingA, jsonConfig);
				    logger.info(json);
				    
				    return json;
				} else if("driverInfoB".equals(type)) {
					registerService.updateDriverInfoImageBackPath(custId, fileName);
				} else if("bankInfoA".equals(type)) {
					registerService.updateBankCardImageFrontPath(custId, fileName);
					
					long start = System.currentTimeMillis();
//					BankInfo bankA = ocrHelper.readBankCardFront(fullpath);
					BankInfo bankA = ocrHelper.readBankCardFrontBase64(base64string);
					long end = System.currentTimeMillis();
					FileUtil.record("银行卡OCR解析起始时间："+start+"\t银行卡OCR解析完成时间："+end+"\t总计用时："+ (end-start) + "毫秒\n");
					
					
					bankA.setBankcardFrontImagePath(fileName);
					
					String json = JsonUtil.JSON_Bean2String(bankA);
				    logger.info(json);
					
				    return json;
				} else if("bankInfoB".equals(type)) {
					registerService.updateBankCardImageBackPath(custId, fileName);
				} else {
				
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			long end1 = System.currentTimeMillis();
			try {
				FileUtil.record("=====后台开始处理时间："+ start1 +"\t后台完成时间："+end1+"\t后台处理总计用时："+ (end1-start1) + "毫秒\n");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "[]";
	}
	
	
	private boolean generateImage(String imgStr, String path) {
		long start = System.currentTimeMillis();
		OutputStream out = null;
		if (StringUtils.isBlank(imgStr))  {
			logger.debug("文件为空");
			return false;
		}
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = decoder.decodeBuffer(imgStr);  // 解密
			
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}

			logger.info("保存路径::"+path);
			out = new FileOutputStream(path);
			out.write(b);
			
			long end = System.currentTimeMillis();
			FileUtil.record("上传起始时间："+start+"\t上传完成时间："+end+"\t总计用时："+ (end-start) + "毫秒\n");
			return true;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(out!=null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
