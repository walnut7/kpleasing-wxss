package com.kpleasing.wxss.web.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.dao.BankInfoDao;
import com.kpleasing.wxss.dao.CertInfoDao;
import com.kpleasing.wxss.dao.DrivingLicenseInfoDao;
import com.kpleasing.wxss.dao.FaceVideoDao;
import com.kpleasing.wxss.dao.OrderDao;
import com.kpleasing.wxss.dao.PersonInfoDao;
import com.kpleasing.wxss.dao.WorkInfoDao;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.entity.CertInfo;
import com.kpleasing.wxss.entity.DrivingLicenseInfo;
import com.kpleasing.wxss.entity.FaceVideo;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.entity.PersonInfo;
import com.kpleasing.wxss.entity.WorkInfo;
import com.kpleasing.wxss.enums.LOGIN_TYPE;
import com.kpleasing.wxss.esb.action.CRM007Action;
import com.kpleasing.wxss.esb.action.CRM010Action;
import com.kpleasing.wxss.esb.action.Leasing007Action;
import com.kpleasing.wxss.esb.action.Leasing016Action;
import com.kpleasing.wxss.esb.action.Leasing017Action;
import com.kpleasing.wxss.esb.action.XMC001Action;
import com.kpleasing.wxss.esb.response.CRM007Response;
import com.kpleasing.wxss.esb.response.CRM010Response;
import com.kpleasing.wxss.esb.response.LEASING007Response;
import com.kpleasing.wxss.esb.response.LEASING016Response;
import com.kpleasing.wxss.esb.response.LEASING017Response;
import com.kpleasing.wxss.esb.response.XMC001Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.web.service.ConfigService;
import com.kpleasing.wxss.web.service.OrderService;
import com.kpleasing.wxss.web.service.RegisterService;
import com.kpleasing.wxss.web.spring.SpringContextHolder;


@Service("RegisterService")
@Transactional
public class RegisterServiceImpl implements Serializable, RegisterService {

	/**	 * 	 */
	private static final long serialVersionUID = 2247893720277428911L;
	private static final Logger logger = Logger.getLogger(RegisterServiceImpl.class);
	
	@Autowired
	private OrderDao orderDao;
	
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
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private FaceVideoDao faceVideoDao;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public void saveCertInfoA(String custId, CertInfo certInfo) throws WXSSException {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(null != order) {
			logger.info("更新订单信息....");
			if(order.getLoginChannel()!=LOGIN_TYPE.AUTO.CODE) {
				order.setUserName(certInfo.getUserName());
				order.setCertId(certInfo.getCertId());
			} else {
				certInfo.setUserName(order.getUserName());
				certInfo.setCertId(order.getCertId());
			}
			
			order.setStepNo((byte)2);
			order.setUpdateAt(DateUtil.getDate());
			orderDao.update(order);
		
			CertInfo certA = (CertInfo) certInfoDao.findByUserId(order.getCustId());
			if(null == certA) {
				logger.info("保存用户身份证正面信息....");
				certInfo.setUserId(order.getCustId());
				certInfoDao.save(certInfo);
			} else {
				logger.info("更新用户身份证正面信息....");
				certA.setUserName(certInfo.getUserName());
				certA.setGender(certInfo.getGender());
				certA.setGenderCode(certInfo.getGenderCode());
				certA.setCertId(certInfo.getCertId());
				certA.setBirthday(certInfo.getBirthday());
				certA.setLiveAddr(certInfo.getLiveAddr());
				certA.setNation(certInfo.getNation());
				certInfoDao.update(certA);
			}
		} else { throw new WXSSException("ERROR", "【"+custId+"】账户下订单不存在......"); }
	}

	
	@Override
	public CertInfo queryCertInfoByCustId(String custId) {
		return certInfoDao.findByUserId(Integer.valueOf(custId));
	}


	@Override
	public void saveCertInfoB(String custId, CertInfo certInfo) throws WXSSException {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(null != order) {
			logger.info("更新订单信息....");
			orderService.updateStepNo(order, (byte) 3);
		
			CertInfo certB = (CertInfo) certInfoDao.findByUserId(order.getCustId());
			if(null == certB) {
				logger.info("保存用户身份证反面面信息....");
				certInfo.setUserId(order.getCustId());
				certInfoDao.save(certInfo);
			} else {
				logger.info("更新用户身份证反面信息....");
				certB.setCertAddr(certInfo.getCertAddr());
				certInfoDao.update(certB);
			}
		} else { throw new WXSSException("ERROR", "【"+custId+"】账户下订单不存在......"); }
	}

	
	@Override
	public DrivingLicenseInfo queryDrivingLicenseInfoByCustId(String custId) {
		return drivingLicenseInfoDao.findByUserId(Integer.valueOf(custId));
	}
	

	@Override
	public void saveDrivingLicenseInfo(String custId, DrivingLicenseInfo driverInfo) throws WXSSException {
	
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(null != order) {
			logger.info("更新订单信息步骤号....");
			orderService.updateStepNo(order, (byte) 4);
		
			DrivingLicenseInfo driving = drivingLicenseInfoDao.findByUserId(order.getCustId());
			if(null == driving) {
				logger.info("保存驾照信息....");
				driverInfo.setUserId(order.getCustId());
				drivingLicenseInfoDao.save(driverInfo);
			} else {
				logger.info("更新驾照信息....");
				driving.setDriveType(driverInfo.getDriveType());
				driving.setDriveTypeDesc(driverInfo.getDriveTypeDesc());
				driving.setDriveFirstDate(driverInfo.getDriveFirstDate());
				drivingLicenseInfoDao.update(driving);
			}
		} else { throw new WXSSException("ERROR", "【"+custId+"】账户下订单不存在......"); }
	}


	@Override
	public BankInfo queryBankInfoByCustId(String custId) {
		return bankInfoDao.findByUserId(Integer.valueOf(custId));
	}
	
	
	@Override
	public void payAuthentication(String custId, BankInfo bankInfo) throws WXSSException {
		CertInfo certInfo = certInfoDao.findByUserId(Integer.valueOf(custId));
		if(null != certInfo) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("custId", custId);
			map.put("externalNo", StringUtil.getSerialNo32());
			map.put("custName", certInfo.getUserName());
			map.put("certCode", certInfo.getCertId());
			
			logger.info("starting send quick pay verify code .....");
			Configurate config = configService.getConfig();
			Leasing016Action leasing016Action = SpringContextHolder.getBean(Leasing016Action.class);
			
			LEASING016Response leasing016Response = leasing016Action.doRequest(config, bankInfo, map);
			if("SUCCESS".equals(leasing016Response.getResult_code()) && "00".equals(leasing016Response.getResponse_code())) {
				BankInfo bank = bankInfoDao.findByUserId(Integer.valueOf(custId));
				if(null == bank) {
					logger.info("save bank info .....");
					bankInfo.setUserId(Integer.valueOf(custId));
					bankInfo.setExternalNo(map.get("externalNo"));
					bankInfo.setToken(leasing016Response.getToken());
					bankInfo.setStorablePan(leasing016Response.getStorable_pan());
					bankInfo.setSmsSendtime(DateUtil.getCurrentDate(DateUtil.yyyy_MM_ddHHmmss));
					bankInfoDao.save(bankInfo);
				} else {
					logger.info("update bank info .....");
					bank.setRepayAccBank(bankInfo.getRepayAccBank());
					bank.setRepayAccBankCode(bankInfo.getRepayAccBankCode());
					bank.setBankPhone(bankInfo.getBankPhone());
					bank.setExternalNo(map.get("externalNo"));
					bank.setToken(leasing016Response.getToken());
					bank.setRepayCardNo(bankInfo.getRepayCardNo());
					bank.setStorablePan(leasing016Response.getStorable_pan());
					bank.setSmsSendtime(DateUtil.getCurrentDate(DateUtil.yyyy_MM_ddHHmmss));
					bankInfoDao.update(bank);
				}
			} else {
				throw new WXSSException("ERROR", leasing016Response.getResponse_message());
			}
		} else {
			throw new WXSSException("ERROR", "查询用户身份信息出错！");
		}
	}
	
	@Override
	public void saveBankInfo(String custId, BankInfo bankInfo) throws WXSSException {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(null != order) {
			logger.info("更新订单信息步骤号....");
			orderService.updateStepNo(order, (byte) 5);
		
			BankInfo bank = bankInfoDao.findByUserId(order.getCustId());
			if(null == bank) {
				logger.info("保存银行账户信息....");
				bankInfo.setUserId(order.getCustId());
				bankInfoDao.save(bankInfo);
			} else {
				logger.info("更新银行账户信息....");
				bank.setRepayAccBank(bankInfo.getRepayAccBank());
				bank.setRepayAccBankCode(bankInfo.getRepayAccBankCode());
				bank.setRepayCardNo(bankInfo.getRepayCardNo());
				bank.setBankPhone(bankInfo.getBankPhone());
				bankInfoDao.update(bank);
			}
		} else { throw new WXSSException("ERROR", "【"+custId+"】账户下订单不存在......"); }
	}
	
	
	@Override
	public void bindPayment(String custId, String verifyCode) throws WXSSException {
		BankInfo bank = bankInfoDao.findByUserId(Integer.valueOf(custId));
		if(bank!=null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("vailidCode", verifyCode);
			
			logger.info("starting to bind with qpay .....");
			Leasing017Action leasing017Action = SpringContextHolder.getBean(Leasing017Action.class);
			
			Configurate config = configService.getConfig();
			LEASING017Response leasing017Response = leasing017Action.doRequest(config, bank, map);
			
			if(!"SUCCESS".equals(leasing017Response.getResult_code()) 
					|| !"00".equals(leasing017Response.getResponse_code())) {
				throw new WXSSException("ERROR", (leasing017Response.getResponse_message()==null)?leasing017Response.getResult_desc():leasing017Response.getResponse_message());
			}
		} else { throw new WXSSException("ERROR", "【"+custId+"】账户下订单不存在......"); }
	}


	@Override
	public PersonInfo queryPersonInfoByCustId(String custId) {
		return personInfoDao.findByUserId(Integer.valueOf(custId));
	}
	
	
	@Override
	public void savePersonInfo(String custId, PersonInfo personInfo) throws WXSSException {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(null != order) {
			logger.info("更新订单信息步骤号....");
			orderService.updateStepNo(order, (byte) 6);
		
			PersonInfo person = personInfoDao.findByUserId(order.getCustId());
			if(null == person) {
				logger.info("保存个人信息....");
				personInfo.setUserId(order.getCustId());
				personInfoDao.save(personInfo);
			} else {
				logger.info("更新个人信息....");
				person.setUserName(personInfo.getUserName());
				person.setLiveStatus(personInfo.getLiveStatus());
				person.setLiveStatusCode(personInfo.getLiveStatusCode());
				person.setEduLevel(personInfo.getEduLevel());
				person.setEduLevelCode(personInfo.getEduLevelCode());
				person.setProvince(personInfo.getProvince());
				person.setCity(personInfo.getCity());
				person.setFamilyAddr(personInfo.getFamilyAddr());
				person.setFamilyPhone(personInfo.getFamilyPhone());
				person.setEmail(personInfo.getEmail());
				person.setMarrStatus(personInfo.getMarrStatus());
				person.setMarrStatusCode(personInfo.getMarrStatusCode());
				person.setSpouseName(personInfo.getSpouseName());
				person.setSpouseCertType(personInfo.getSpouseCertType());
				person.setSpouseCertId(personInfo.getSpouseCertId());
				person.setSpousePhone(personInfo.getSpousePhone());
				person.setSpouseAnnualIncome(personInfo.getSpouseAnnualIncome());
				person.setSpouseAnnualIncomeCode(personInfo.getSpouseAnnualIncomeCode());
				person.setSpouseIncomeFrom(personInfo.getSpouseIncomeFrom());
				person.setSpouseIncomeFromCode(personInfo.getSpouseIncomeFromCode());
				person.setSpouseWorkUnit(personInfo.getSpouseWorkUnit());
				person.setContactRelation(personInfo.getContactRelation());
				person.setContactRelationCode(personInfo.getContactRelationCode());
				person.setContactName(personInfo.getContactName());
				person.setContactCertType(personInfo.getContactCertType());
				person.setContactCertId(personInfo.getContactCertId());
				person.setContactPhone(personInfo.getContactPhone());
				
				personInfoDao.update(person);
			}
		} else { throw new WXSSException("ERROR", "【"+custId+"】账户下订单不存在......"); }
	}


	@Override
	public WorkInfo queryWorkInfoByCustId(String custId) {
		return workInfoDao.findByUserId(Integer.valueOf(custId));
	}


	@Override
	public void saveWorkInfo(String custId, WorkInfo workInfo) throws WXSSException {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(null != order) {
			logger.info("更新订单信息步骤号....");
			order.setStepNo((byte)7);
			order.setUpdateAt(DateUtil.getDate());
			orderDao.update(order);
		
			WorkInfo work = workInfoDao.findByUserId(order.getCustId());
			if(null == work) {
				logger.info("保存工作信息....");
				workInfo.setUserId(order.getCustId());
				workInfoDao.save(workInfo);
			} else {
				logger.info("更新工作信息....");
				work.setWorkUnit(workInfo.getWorkUnit());
				work.setEntryYear(workInfo.getEntryYear());
				work.setPosition(workInfo.getPosition());
				work.setUnitTel(workInfo.getUnitTel());
				work.setWorkAddr(workInfo.getWorkAddr());
				work.setAnnualIncome(workInfo.getAnnualIncome());
				work.setWorkYear(workInfo.getWorkYear());
				work.setIncomeStatus(workInfo.getIncomeStatus());
				work.setIncomeStatusCode(workInfo.getIncomeStatusCode());
				work.setIncomeFrom(workInfo.getIncomeFrom());
				work.setIncomeFromCode(workInfo.getIncomeFromCode());
				workInfoDao.update(work);
			}
		} else { throw new WXSSException("ERROR", "【"+custId+"】账户下订单不存在......"); }
	}
	
	
	@Override
	public void syncCustomerInfo(Order order) throws WXSSException {
		logger.info("开始提交客户【"+order.getCustId()+"】 信息 .....");
		CRM007Action crm007Action = SpringContextHolder.getBean(CRM007Action.class);
		
		Configurate config = configService.getConfig();
		CRM007Response crm007Response = crm007Action.doRequest(config, order, null);
		
		if(!"SUCCESS".equals(crm007Response.getResult_code())) {
			throw new WXSSException("ERROR", crm007Response.getResult_desc());
		}
	}


	@Override
	public void createLeasingOrder(Order order) throws WXSSException {
		logger.info("starting begin order.....");
		Leasing007Action leasing007Action = SpringContextHolder.getBean(Leasing007Action.class);
		
		Configurate config = configService.getConfig();
		LEASING007Response leasing007Response = leasing007Action.doRequest(config, order, null);
		
		if(!"SUCCESS".equals(leasing007Response.getResult_code())) {
			throw new WXSSException("ERROR", leasing007Response.getResult_desc());
		}
	}

	
	@Override
	public void updateCertInfoImageFrontPath(String custId, String fileName) {
		CertInfo cert = (CertInfo) certInfoDao.findByUserId(Integer.valueOf(custId));
		if(null == cert) {
			logger.info("保存用户身份证正面图片....");
			cert = new CertInfo();
			cert.setUserId(Integer.valueOf(custId));
			cert.setCertFrontImagePath(fileName);
			certInfoDao.save(cert);
		} else {
			logger.info("更新用户身份证正面图片....");
			cert.setCertFrontImagePath(fileName);
			certInfoDao.update(cert);
		}
	}


	@Override
	public void updateCertInfoImageBackPath(String custId, String fileName) {
		CertInfo cert = (CertInfo) certInfoDao.findByUserId(Integer.valueOf(custId));
		if(null == cert) {
			logger.info("保存用户身份证反面图片....");
			cert = new CertInfo();
			cert.setUserId(Integer.valueOf(custId));
			cert.setCertBackImagePath(fileName);
			certInfoDao.save(cert);
		} else {
			logger.info("更新用户身份证反面图片....");
			cert.setCertBackImagePath(fileName);
			certInfoDao.update(cert);
		}
	}


	@Override
	public void updateDriverInfoImagePath(String custId, String fileName) {
		DrivingLicenseInfo driving = drivingLicenseInfoDao.findByUserId(Integer.valueOf(custId));
		if(null == driving) {
			logger.info("保存驾照正面信息....");
			driving = new DrivingLicenseInfo();
			driving.setUserId(Integer.valueOf(custId));
			driving.setDriveImagePath(fileName);
			drivingLicenseInfoDao.save(driving);
		} else {
			logger.info("更新驾照正面信息....");
			driving.setDriveImagePath(fileName);
			drivingLicenseInfoDao.update(driving);
		}
	}
	
	@Override
	public void updateDriverInfoImageBackPath(String custId, String fileName) {
		DrivingLicenseInfo driving = drivingLicenseInfoDao.findByUserId(Integer.valueOf(custId));
		if(null == driving) {
			logger.info("保存驾照反面信息....");
			driving = new DrivingLicenseInfo();
			driving.setUserId(Integer.valueOf(custId));
			driving.setDriveBackImagePath(fileName);
			drivingLicenseInfoDao.save(driving);
		} else {
			logger.info("更新驾照反面信息....");
			driving.setDriveBackImagePath(fileName);
			drivingLicenseInfoDao.update(driving);
		}
	}


	@Override
	public void updateBankCardImageFrontPath(String custId, String fileName) {
		BankInfo bank = bankInfoDao.findByUserId(Integer.valueOf(custId));
		if(null == bank) {
			logger.info("保存银行卡正面信息....");
			bank = new BankInfo();
			bank.setUserId(Integer.valueOf(custId));
			bank.setBankcardFrontImagePath(fileName);
			bankInfoDao.save(bank);
		} else {
			logger.info("更新银行卡正面信息....");
			bank.setBankcardFrontImagePath(fileName);
			bankInfoDao.update(bank);
		}
	}


	@Override
	public void updateBankCardImageBackPath(String custId, String fileName) {
		BankInfo bank = bankInfoDao.findByUserId(Integer.valueOf(custId));
		if(null == bank) {
			logger.info("保存银行卡反面信息....");
			bank = new BankInfo();
			bank.setUserId(Integer.valueOf(custId));
			bank.setBankcardBackImagePath(fileName);
			bankInfoDao.save(bank);
		} else {
			logger.info("更新银行卡反面信息....");
			bank.setBankcardBackImagePath(fileName);
			bankInfoDao.update(bank);
		}
	}


	@Override
	public String getDrivingTypeDesc(String dringvingType) {
		//configService
		return null;
	}


	@Override
	public FaceVideo savePerVideoAudit(String custId, FaceVideo faceVideo) {
		FaceVideo videoAudit = faceVideoDao.findByCustId(Integer.valueOf(custId));
		CertInfo certInfo = certInfoDao.findByUserId(Integer.valueOf(custId));
		if(videoAudit != null){
			videoAudit.setCustName(certInfo.getUserName());
			videoAudit.setFirstDate(faceVideo.getFirstDate());
			videoAudit.setSecondDate(faceVideo.getSecondDate());
			videoAudit.setUpdateAt(DateUtil.getDate());
			faceVideoDao.update(videoAudit);
			return videoAudit;
		}else{
			faceVideo.setCustId(Integer.valueOf(custId));
			faceVideo.setCustName(certInfo.getUserName());
			faceVideo.setCreateAt(DateUtil.getDate());
			faceVideo.setUpdateAt(DateUtil.getDate());
			faceVideoDao.save(faceVideo);
			return faceVideo;
		}
	}


	@Override
	public void sendPerVideoAuditReq(FaceVideo faceVideo) throws WXSSException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FaceVideo", faceVideo);
		
		CRM010Action crm010Action = SpringContextHolder.getBean(CRM010Action.class);
		
		Configurate config = configService.getConfig();
		CRM010Response crm010Response = crm010Action.doRequest(config, faceVideo, null);
		
		if(!"SUCCESS".equals(crm010Response.getResult_code())) {
			throw new WXSSException("ERROR", crm010Response.getResult_desc());
		}
	}


	@Override
	public FaceVideo queryFaceVideoByCustId(String custId) {
		return faceVideoDao.findByCustId(Integer.valueOf(custId));
	}


	@Override
	public void doFirstAudit(String custId) {
		try {
			XMC001Action xmc001Action = SpringContextHolder.getBean(XMC001Action.class);
			
			Configurate config = configService.getConfig();
			XMC001Response xmc001Response = xmc001Action.doRequest(config, custId, null);
			
//			if(!"SUCCESS".equals(xmc001Response.getResult_code())) {
//				throw new WXSSException("ERROR", xmc001Response.getResult_desc());
//			}
		} catch (WXSSException e) {
			logger.error(e.getDesc(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}