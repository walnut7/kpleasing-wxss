package com.kpleasing.wxss.web.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.dao.BankInfoDao;
import com.kpleasing.wxss.dao.CertInfoDao;
import com.kpleasing.wxss.dao.DrivingLicenseInfoDao;
import com.kpleasing.wxss.dao.OrderDao;
import com.kpleasing.wxss.dao.PersonInfoDao;
import com.kpleasing.wxss.dao.SpSalesDao;
import com.kpleasing.wxss.dao.WorkInfoDao;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.entity.CertInfo;
import com.kpleasing.wxss.entity.DrivingLicenseInfo;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.entity.PersonInfo;
import com.kpleasing.wxss.entity.SpSales;
import com.kpleasing.wxss.entity.WorkInfo;
import com.kpleasing.wxss.enums.LOGIN_TYPE;
import com.kpleasing.wxss.esb.action.CRM001Action;
import com.kpleasing.wxss.esb.action.CRM002Action;
import com.kpleasing.wxss.esb.action.CRM004Action;
import com.kpleasing.wxss.esb.action.Leasing001Action;
import com.kpleasing.wxss.esb.response.CRM001Response;
import com.kpleasing.wxss.esb.response.CRM002Response;
import com.kpleasing.wxss.esb.response.CRM004AccountBean;
import com.kpleasing.wxss.esb.response.CRM004Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.web.service.ConfigService;
import com.kpleasing.wxss.web.service.LoginService;
import com.kpleasing.wxss.web.spring.SpringContextHolder;

@Service("LoginService")
@Transactional
public class LoginServiceImpl implements Serializable, LoginService {

	/**	 * 	 */
	private static final long serialVersionUID = 7535541948391979579L;
	private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private SpSalesDao salesDao;
	
	@Autowired
	private CertInfoDao certInfoDao;
	
	@Autowired
	private DrivingLicenseInfoDao drivingLicenseInfoDao;
	
	@Autowired
	private BankInfoDao bankInfoDao;
	
	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Autowired
	private WorkInfoDao workInfoDao;
	
	@Override
	public void sendVerifCode(String phone, String verifCode) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("verifCode", verifCode);
			
			Configurate config = configService.getConfig();
			Leasing001Action leasing001Action = SpringContextHolder.getBean(Leasing001Action.class);
			
			leasing001Action.doRequest(config, phone, map);
			
		} catch (WXSSException e) {
			logger.error(e.getDesc(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	
	@Override
	public LoginUser getLoginUser(LoginUser loginUser, LOGIN_TYPE LTYPE) throws WXSSException {
		try {
			Order order = null;
			if(StringUtils.isNotBlank(loginUser.getUserId())) {
				order = (Order) orderDao.findByCustId(Integer.valueOf(loginUser.getUserId()));
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("ChannelType", "0");
			map.put("ChannelCode", loginUser.getPhone());
			
			logger.info("channel_Id="+ loginUser.getPhone());
			Configurate config = configService.getConfig();
			CRM002Action crm002Action = SpringContextHolder.getBean(CRM002Action.class);
			CRM002Response crm002Response = crm002Action.doRequest(config, "", map);
			
			LoginUser sysUser = new LoginUser();
			if(null == order) {
				if("SUCCESS".equals(crm002Response.getResult_code())) {
					sysUser.setUserId(crm002Response.getCust_id());
					sysUser.setPhone(crm002Response.getPhone());
					sysUser.setUserName(crm002Response.getCust_name());
					sysUser.setLoginType(LTYPE.CODE);
					sysUser.setCertCode(crm002Response.getCert_code());
					saveLoginUserToLocal(crm002Response.getCust_id(), sysUser);
				}
			} else {
				sysUser.setUserId(String.valueOf(order.getCustId()));
				sysUser.setPhone(order.getPhone());
				if("SUCCESS".equals(crm002Response.getResult_code())) {
					order.setUserName(crm002Response.getCust_name());
					order.setCertId(crm002Response.getCert_code());
					sysUser.setUserName(crm002Response.getCust_name());
					sysUser.setCertCode(crm002Response.getCert_code());
				}
				order.setLoginChannel((byte)LTYPE.CODE);
				order.setUpdateAt(DateUtil.str2Date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss)));
				orderDao.update(order);
			}
			return sysUser;
			
		} catch(Exception e) {
			logger.error("login info error:", e);
			throw new WXSSException("FAILED", "信息查询出错！");
		}
	}


	@Override
	public LoginUser generateLoginUser(LoginUser loginUser) {
		try {
			Configurate config = configService.getConfig();
			CRM001Action crm001Action = SpringContextHolder.getBean(CRM001Action.class);
			
			CRM001Response crm001Response = crm001Action.doRequest(config, loginUser, null);
			
			LoginUser sysUser = new LoginUser();
			if("SUCCESS".equals(crm001Response.getResult_code())) {
				sysUser.setUserId(crm001Response.getCust_id());
				sysUser.setPhone(crm001Response.getPhone());
				
				saveLoginUserToLocal(crm001Response.getCust_id(), loginUser);
			}
			return sysUser;
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	

	/**
	 * 本地保存用户数据
	 * @param custId 
	 * @param userId
	 * @param phone
	 */
	private void saveLoginUserToLocal(String custId, LoginUser loginUser) {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(null == order) {
			order = new Order();
			order.setCustId(Integer.valueOf(custId));
			order.setUserName(loginUser.getUserName());
			order.setPhone(loginUser.getPhone());
			order.setCertId(loginUser.getCertCode());
			order.setStatus((byte)1);
			order.setLoginChannel((byte)loginUser.getLoginType());
			order.setCreateAt(DateUtil.str2Date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss)));
			order.setUpdateAt(DateUtil.str2Date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss)));
			orderDao.save(order);
		}
	}


	@Override
	public List<SpSales> getSaleListByBpId(String bpId) {
		return salesDao.findByProperty("bpId", Integer.valueOf(bpId));
	}


	@Override
	public void verifyAutoLogin(LoginUser loginUser) throws WXSSException {
		Configurate config = configService.getConfig();
		String signFromAPIResponse = loginUser.getSign();
		if (StringUtils.isBlank(signFromAPIResponse)) {
			logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			throw new WXSSException("FAILED", "参数错误！");
		}
		
		logger.info("Before Sign:" + loginUser.getSign());
		logger.info("Sign Before MD5:" + loginUser.getContentSign(config.WXSS_LOGIN_KEY));
		String result = Security.MD5Encode(loginUser.getContentSign(config.WXSS_LOGIN_KEY)).toUpperCase();
		logger.info("Sign Result:" + result);

		if (!result.equals(loginUser.getSign())) {
			logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
			throw new WXSSException("FAILED", "参数错误！");
		}
		
		Date timestamp = DateUtil.getAfterSeconds(
				DateUtil.str2Date(loginUser.getTimeStamp(), DateUtil.yyyyMMddHHmmss), Integer.valueOf(config.AUTO_LOGIN_TIMEOUT));
		if (DateUtil.compareDateDiff(timestamp, DateUtil.getDate()) < 0) {
			logger.info("请求超时!!!");
			throw new WXSSException("FAILED", "请求超时！");
		}
	}
	
	
	private void saveOrderFromRemoteCrm(CRM004Response crm004Response, Integer custId) {
		Order order = (Order) orderDao.findByCustId(custId);
		if(order==null) {
			order = new Order();
			order.setCustId(custId);
			order.setLoginChannel((byte)LOGIN_TYPE.AUTO.CODE);
			order.setUserName(crm004Response.getCust_name());
			order.setPhone(crm004Response.getPhone());
			order.setCertId(crm004Response.getCert_code());
			order.setCreateAt(DateUtil.getDate());
			order.setUpdateAt(DateUtil.getDate());
			orderDao.save(order);
		} else {
			order.setLoginChannel((byte)LOGIN_TYPE.AUTO.CODE);
			order.setUserName(crm004Response.getCust_name());
			order.setPhone(crm004Response.getPhone());
			order.setCertId(crm004Response.getCert_code());
			order.setUpdateAt(DateUtil.getDate());
			orderDao.update(order);
		}
	}
	
	
	private void saveCertInfoFromRemoteCrm(CRM004Response crm004Response, Integer custId) {
		CertInfo certInfo = certInfoDao.findByUserId(custId);
		if(null == certInfo) {
			certInfo = new CertInfo();
			certInfo.setUserId(custId);
			certInfo.setUserName(crm004Response.getCust_name());
			certInfo.setGender("");
			certInfo.setGenderCode(crm004Response.getGender());
			certInfo.setNation("");
			
			if(StringUtils.isNotBlank(crm004Response.getBirthday())) {
				certInfo.setBirthday(DateUtil.str2Date(crm004Response.getBirthday()));
			}
			certInfo.setLiveAddr("");
			certInfo.setCertId(crm004Response.getCert_code());
			certInfo.setCertAddr("");
			
			certInfoDao.save(certInfo);
		}
	}
	

	private void saveDrivingInfoFromRemoteCrm(CRM004Response crm004Response, Integer custId) {
		DrivingLicenseInfo driver = drivingLicenseInfoDao.findByUserId(custId);
		if(null == driver) {
			driver = new DrivingLicenseInfo();
			driver.setUserId(custId);
			driver.setDriveType("");
			driver.setDriveTypeDesc("");
			
			drivingLicenseInfoDao.save(driver);
		}
	}


	private void saveBankInfoFromRemoteCrm(CRM004Response crm004Response, Integer custId) {
		BankInfo bank = bankInfoDao.findByUserId(custId);
		if(null == bank) {
			CRM004AccountBean account = crm004Response.getAccounts().get(0);
			bank = new BankInfo();
			bank.setUserId(custId);
			bank.setRepayAccBank(account.getBank_full_name());
			bank.setRepayAccBankCode(account.getBank_code());
			bank.setRepayCardNo(account.getAcc_no());
			bank.setBankPhone("");
			
			bankInfoDao.save(bank);
		}
	}
	

	private void savePersonInfoFromRemoteCrm(CRM004Response crm004Response, Integer custId) {
		PersonInfo person = personInfoDao.findByUserId(custId);
		if(null == person) {
			person = new PersonInfo();
			person.setUserId(custId);
			person.setUserName("");
			person.setLiveStatus("");
			person.setLiveStatusCode("");
			person.setEduLevel("");
			person.setEduLevelCode("");
			person.setProvince("");
			person.setCity("");
			person.setFamilyAddr("");
			person.setFamilyPhone("");
			person.setEmail("");
			person.setMarrStatus("");
			person.setMarrStatusCode("");
			person.setSpouseName("");
			person.setSpouseCertType("");
			person.setSpouseCertId("");
			person.setSpousePhone("");
			person.setSpouseAnnualIncome("");
			person.setSpouseAnnualIncomeCode("");
			person.setSpouseIncomeFrom("");
			person.setSpouseIncomeFromCode("");
			person.setSpouseWorkUnit("");
			person.setContactRelation("");
			person.setContactRelationCode("");
			person.setContactName("");
			person.setContactCertType("");
			person.setContactCertId("");
			person.setContactPhone("");
			
			personInfoDao.save(person);
		}
	}
	
	
	private void saveWorkInfoFromRemoteCrm(CRM004Response crm004Response, Integer custId) {
		WorkInfo work = workInfoDao.findByUserId(custId);
		if(null == work) {
			work = new WorkInfo();
			work.setUserId(custId);
			work.setWorkUnit(crm004Response.getWork_unit());
			work.setEntryYear(crm004Response.getEntry_year());
			work.setPosition(crm004Response.getPosition());
			work.setUnitTel(crm004Response.getUnit_tel());
			work.setIncomeStatus("");
			work.setIncomeStatusCode(crm004Response.getIncome_status());
			work.setIncomeFrom("");
			work.setIncomeFromCode(crm004Response.getIncome_from());
			work.setAnnualIncome(crm004Response.getAnnual_income());
			work.setWorkAddr("");
			work.setWorkYear(crm004Response.getWork_year());

			workInfoDao.save(work);
		}
	}


	@Override
	public void syncRemoteCustInfo(LoginUser loginUser) {
		try {
			Configurate config = configService.getConfig();
			CRM004Action crm004Action = SpringContextHolder.getBean(CRM004Action.class);
			CRM004Response crm004Response = crm004Action.doRequest(config, loginUser.getUserId(), null);
			
			if("SUCCESS".equals(crm004Response.getResult_code())) {
				Integer custId = Integer.valueOf(loginUser.getUserId());
				logger.info("保存tb_order信息.");
				saveOrderFromRemoteCrm(crm004Response, custId);
				
				logger.info("保存tb_cert_info信息.");
				saveCertInfoFromRemoteCrm(crm004Response, custId);
				
				logger.info("保存tb_driving_license_info信息.");
				saveDrivingInfoFromRemoteCrm(crm004Response, custId);
				
				logger.info("保存tb_bank_info信息.");
				saveBankInfoFromRemoteCrm(crm004Response, custId);
				
				logger.info("保存tb_person_info信息.");
				savePersonInfoFromRemoteCrm(crm004Response, custId);
				
				logger.info("保存tb_work_info信息.");
				saveWorkInfoFromRemoteCrm(crm004Response, custId);
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}