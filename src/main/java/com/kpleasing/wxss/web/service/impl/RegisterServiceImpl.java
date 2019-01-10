package com.kpleasing.wxss.web.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.dao.BankInfoDao;
import com.kpleasing.wxss.dao.CertInfoDao;
import com.kpleasing.wxss.dao.ContactInfoDao;
import com.kpleasing.wxss.dao.DrivingLicenseInfoDao;
import com.kpleasing.wxss.dao.FaceVideoDao;
import com.kpleasing.wxss.dao.OrderDao;
import com.kpleasing.wxss.dao.PersonInfoDao;
import com.kpleasing.wxss.dao.SchemeDao;
import com.kpleasing.wxss.dao.WorkInfoDao;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.entity.CertInfo;
import com.kpleasing.wxss.entity.ContactInfo;
import com.kpleasing.wxss.entity.DrivingLicenseInfo;
import com.kpleasing.wxss.entity.FaceVideo;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.entity.PersonInfo;
import com.kpleasing.wxss.entity.Scheme;
import com.kpleasing.wxss.entity.WorkInfo;
import com.kpleasing.wxss.enums.LOGIN_TYPE;
import com.kpleasing.wxss.esb.action.CRM007Action;
import com.kpleasing.wxss.esb.action.CRM010Action;
import com.kpleasing.wxss.esb.action.Leasing007Action;
import com.kpleasing.wxss.esb.action.Leasing016Action;
import com.kpleasing.wxss.esb.action.Leasing017Action;
import com.kpleasing.wxss.esb.action.Leasing018Action;
import com.kpleasing.wxss.esb.action.Leasing019Action;
import com.kpleasing.wxss.esb.action.Leasing020Action;
import com.kpleasing.wxss.esb.action.XMC001Action;
import com.kpleasing.wxss.esb.response.CRM007Response;
import com.kpleasing.wxss.esb.response.CRM010Response;
import com.kpleasing.wxss.esb.response.LEASING007Response;
import com.kpleasing.wxss.esb.response.LEASING016Response;
import com.kpleasing.wxss.esb.response.LEASING017Response;
import com.kpleasing.wxss.esb.response.LEASING018Response;
import com.kpleasing.wxss.esb.response.LEASING019Response;
import com.kpleasing.wxss.esb.response.XMC001Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.mongo.collections.SpdbInterfaceLogCollection;
import com.kpleasing.wxss.mongo.dao.SpdbInterfaceLogCollectionDao;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.SPDBHelper;
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
	private ContactInfoDao contactInfoDao;
	
	@Autowired
	private WorkInfoDao workInfoDao;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private FaceVideoDao faceVideoDao;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SchemeDao schemeDao;
	
	@Autowired
	private SpdbInterfaceLogCollectionDao spdbInterfaceLogCollectionDao;
	
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
	public BankInfo queryBankInfoByCustId(String custId) throws WXSSException {
		CertInfo certInfo = certInfoDao.findByUserId(Integer.valueOf(custId));
		if(null==certInfo) throw new WXSSException("ERROR", "请先至身份证信息页面补充身份证信息！");
		
		logger.info("查询客户开户信息......");
		Configurate config = configService.getConfig();
		Leasing019Action leasing019Action = SpringContextHolder.getBean(Leasing019Action.class);
		
		LEASING019Response leasing019Response = leasing019Action.doRequest(config, certInfo.getCertId(), null);
		if("SUCCESS".equals(leasing019Response.getResult_code())) {
			if(StringUtils.isNotBlank(leasing019Response.getSpdb_stCard_no())) {
				BankInfo bank = bankInfoDao.findByUserId(Integer.valueOf(custId));
				if(null == bank) {
					bank = new BankInfo();
					bank.setUserId(Integer.valueOf(custId));
					bank.setSpdbStcardNo(leasing019Response.getSpdb_stCard_no());
					bankInfoDao.save(bank);
				} else {
					bank.setSpdbStcardNo(leasing019Response.getSpdb_stCard_no());
					bankInfoDao.update(bank);
				}
				return bank;
			}
		}
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
	public void updateSpdbFlag(String custid, byte flag) throws WXSSException {
		BankInfo bank = queryBankInfoByCustId(custid);
		if(null == bank) {
			bank = new BankInfo();
			bank.setUserId(Integer.valueOf(custid));
			bankInfoDao.save(bank);
		} else {
			bank.setSpdbFlag((byte) 1);
			bankInfoDao.update(bank);
		}
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
	public ContactInfo queryContactInfoByCustId(String custId) {
		return contactInfoDao.findByUserId(Integer.valueOf(custId));
	}
	
	@Override
	public void saveContactInfo(String custId, ContactInfo contactInfo) throws WXSSException {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(null != order) {
			logger.info("更新订单信息步骤号....");
			orderService.updateStepNo(order, (byte) 7);
		
			ContactInfo contact = contactInfoDao.findByUserId(order.getCustId());
			if(null == contact) {
				logger.info("保存联系人信息....");
				contactInfo.setUserId(order.getCustId());
				contactInfoDao.save(contactInfo);
			} else {
				logger.info("更新联系人信息....");
				contact.setSpouseName(contactInfo.getSpouseName());
				contact.setSpouseCertType(contactInfo.getSpouseCertType());
				contact.setSpouseCertId(contactInfo.getSpouseCertId());
				contact.setSpousePhone(contactInfo.getSpousePhone());
				contact.setSpouseAnnualIncome(contactInfo.getSpouseAnnualIncome());
				contact.setSpouseAnnualIncomeCode(contactInfo.getSpouseAnnualIncomeCode());
				contact.setSpouseIncomeFrom(contactInfo.getSpouseIncomeFrom());
				contact.setSpouseIncomeFromCode(contactInfo.getSpouseIncomeFromCode());
				contact.setSpouseWorkUnit(contactInfo.getSpouseWorkUnit());
				contact.setContactRelation(contactInfo.getContactRelation());
				contact.setContactRelationCode(contactInfo.getContactRelationCode());
				contact.setContactName(contactInfo.getContactName());
				contact.setContactCertType(contactInfo.getContactCertType());
				contact.setContactCertId(contactInfo.getContactCertId());
				contact.setContactPhone(contactInfo.getContactPhone());
				contact.setContact2Relation(contactInfo.getContact2Relation());
				contact.setContact2RelationCode(contactInfo.getContact2RelationCode());
				contact.setContact2Name(contactInfo.getContact2Name());
				contact.setContact2CertType(contactInfo.getContact2CertType());
				contact.setContact2CertId(contactInfo.getContact2CertId());
				contact.setContact2Phone(contactInfo.getContact2Phone());
				
				contactInfoDao.update(contact);
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
			order.setStepNo((byte)8);
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
	public void doIdentityAuth(Order order) throws WXSSException {
		logger.info("开始认证客户【"+order.getCustId()+"】 身份信息 .....");
		Leasing018Action leasing018Action = SpringContextHolder.getBean(Leasing018Action.class);
		
		Configurate config = configService.getConfig();
		LEASING018Response leasing018Response = leasing018Action.doRequest(config, order, null);
		
		if(!"SUCCESS".equals(leasing018Response.getResult_code())) {
			throw new WXSSException("ERROR", order.getPhone() +"非客户"+ order.getUserName() +"实名认证手机号");
//			throw new WXSSException("ERROR", leasing018Response.getResult_desc());
		}/*else{
			if(leasing018Response.getAuth_status()==null || "1".equals(leasing018Response.getAuth_status())){
				throw new WXSSException("ERROR", "姓名、手机号、身份证三要素认证未通过");
			}
		}*/
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
		synchronized(leasing007Action) {
			Configurate config = configService.getConfig();
			LEASING007Response leasing007Response = leasing007Action.doRequest(config, order, null);
			
			if(!"SUCCESS".equals(leasing007Response.getResult_code())) {
				throw new WXSSException("ERROR", leasing007Response.getResult_desc());
			}
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
		if(videoAudit != null) {
			videoAudit.setCustName(certInfo.getUserName());
			videoAudit.setFirstDate(faceVideo.getFirstDate());
			videoAudit.setSecondDate(faceVideo.getSecondDate());
			videoAudit.setUpdateAt(DateUtil.getDate());
			faceVideoDao.update(videoAudit);
			return videoAudit;
		} else {
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
			
			/*if("SUCCESS".equals(xmc001Response.getResult_code())) {
				throw new WXSSException("ERROR", xmc001Response.getResult_desc());
			}*/
		} catch (WXSSException e) {
			logger.error(e.getDesc(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	private SpdbInterfaceLogCollection getSpdbInterfaceLog(String custId) {
		SpdbInterfaceLogCollection spdbInterfaceLogCollection = new SpdbInterfaceLogCollection();
		try {
			Order order = orderDao.findByCustId(Integer.valueOf(custId));
			spdbInterfaceLogCollection.setCust_id(Integer.valueOf(custId));
			spdbInterfaceLogCollection.setCust_name(order.getUserName());
			spdbInterfaceLogCollection.setCert_type(order.getCertType());
			spdbInterfaceLogCollection.setCert_code(order.getCertId());
			spdbInterfaceLogCollection.setPhone(order.getPhone());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return spdbInterfaceLogCollection;
	}


	@Override
	public void SendSpdbVerifyCode(String custId, BankInfo bankInfo) throws WXSSException {
		SpdbInterfaceLogCollection spdbLog = getSpdbInterfaceLog(custId);
		
		Configurate config = configService.getConfig();
		Map<String, Object> mapResultData = SPDBHelper.doSpdbGetAuthrCode(config, bankInfo.getBankPhone(), spdbLog);
		String resultCode = (String) mapResultData.get("statusCode");
		spdbInterfaceLogCollectionDao.insertSpdbInterfaceLogCollection(spdbLog, "GET_AUTHR_CODE", resultCode);
		
		if("0000".equals(resultCode)) {
			BankInfo bank = bankInfoDao.findByUserId(Integer.valueOf(custId));
			if(null == bank) {
				logger.info("保存二、三类户验证码....");
				bank = new BankInfo();
				bank.setUserId(Integer.valueOf(custId));
//				bank.setSpdbVerifyCode((String)mapResultData.get("randomPassword"));
				bank.setSmsSendtime(DateUtil.getCurrentDate(DateUtil.yyyy_MM_ddHHmmss));
				bank.setRepayAccBank(bankInfo.getRepayAccBank());
				bank.setRepayAccBankCode(bankInfo.getRepayAccBankCode());
				bank.setBankPhone(bankInfo.getBankPhone());
				bank.setRepayCardNo(bankInfo.getRepayCardNo());
				bankInfoDao.save(bank);
			} else {
				logger.info("更新二、三类户验证码....");
//				bank.setSpdbVerifyCode((String)mapResultData.get("randomPassword"));
				bank.setSmsSendtime(DateUtil.getCurrentDate(DateUtil.yyyy_MM_ddHHmmss));
				bank.setRepayAccBank(bankInfo.getRepayAccBank());
				bank.setRepayAccBankCode(bankInfo.getRepayAccBankCode());
				bank.setBankPhone(bankInfo.getBankPhone());
				bank.setRepayCardNo(bankInfo.getRepayCardNo());
				bankInfoDao.update(bank);
			}
		} else {
			String msgError = SPDBHelper.getReflectErrorInfo(resultCode, (String)mapResultData.get("statusMsg"));
			throw new WXSSException("ERROR", "验证码发送错误，错误代码【"+msgError+"】");
		}
	}


	@Override
	public void openSpdbAccount(String custId, String verifyCode) throws WXSSException {
		String msgError = null;
		SpdbInterfaceLogCollection spdbLog = getSpdbInterfaceLog(custId);
		BankInfo bank = bankInfoDao.findByUserId(Integer.valueOf(custId));
		if(null==bank) throw new WXSSException("ERROR", "账户信息不存在！");
		
		CertInfo cert = (CertInfo) certInfoDao.findByUserId(Integer.valueOf(custId));
		if(null == cert) {
			throw new WXSSException("ERROR", "证件信息不存在！");
		}
		
		if(StringUtils.isBlank(bank.getSpdbCertCode()) || bank.getSpdbCertCode().equals(cert.getCertId())) {
			if(bank.getSpdbFlag() == (byte)1 || StringUtils.isNotBlank(bank.getSpdbStcardNo())) return;
		}
		
		Configurate config = configService.getConfig();
		String image1 = config.IMG_PATH + cert.getCertFrontImagePath();
		String image2 = config.IMG_PATH + cert.getCertBackImagePath();
		Map<String, Object> mapMediaUploadData = SPDBHelper.doSpdbMediaUpload(config, image1, image2, spdbLog);
		String resultCode = (String) mapMediaUploadData.get("statusCode");
		spdbInterfaceLogCollectionDao.insertSpdbInterfaceLogCollection(spdbLog, "MEDIA_UPLOAD", resultCode);
		
		if("0000".equals(resultCode)) {
			mapMediaUploadData.put("custname", cert.getUserName());
			mapMediaUploadData.put("address", cert.getCertAddr());
			mapMediaUploadData.put("mobileNum", bank.getBankPhone());
			mapMediaUploadData.put("bankNum", bank.getRepayAccBankCode());
			mapMediaUploadData.put("cardNum", bank.getRepayCardNo());
			mapMediaUploadData.put("memberId", custId);
			mapMediaUploadData.put("randomPassword", verifyCode);
			if(StringUtils.isNotBlank((String)mapMediaUploadData.get("idNo"))) {
				bank.setSpdbCertCode((String) mapMediaUploadData.get("idNo"));
			}
			
			Map<String, Object> mapOpenAccountData = SPDBHelper.doSpdbOpenAccount(config, mapMediaUploadData, spdbLog);
			resultCode = (String) mapOpenAccountData.get("statusCode");
			spdbInterfaceLogCollectionDao.insertSpdbInterfaceLogCollection(spdbLog, "OPEN_ACCOUNT", resultCode);
			
			if("0000".equals(resultCode)) {
				String uuid = (String) mapOpenAccountData.get("uuid");
				bank.setSpdbUuid(uuid);
				bank.setSpdbOpenAccountTime(DateUtil.getDate());
				bankInfoDao.update(bank);
				
				Map<String, Object> mapQueryAccountData = SPDBHelper.doSpdbQueryAccount(config, uuid, spdbLog);
				resultCode = (String) mapQueryAccountData.get("statusCode");
				spdbInterfaceLogCollectionDao.insertSpdbInterfaceLogCollection(spdbLog, "QUERY_ACCOUNT", resultCode);
				
				if("0000".equals(resultCode)) {
					bank.setSpdbAccountId((String) mapQueryAccountData.get("accountId"));
					bank.setSpdbStcardNo((String) mapQueryAccountData.get("stCardNum"));
					bank.setSpdbAccountType((String) mapQueryAccountData.get("openTypeFlag"));
					bankInfoDao.update(bank);
					
					logger.info("更新订单信息步骤号....");
					Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
					orderService.updateStepNo(order, (byte) 5);
					
					// 通知开户账户 
					final Map<String, String> __map = new HashMap<String, String>();
					__map.put("certCode", cert.getCertId());
					__map.put("stCardNo", (String) mapQueryAccountData.get("stCardNum"));
					new Thread() {
						public void run() {
							Leasing020Action leasing020Action = SpringContextHolder.getBean(Leasing020Action.class);
							Configurate config = configService.getConfig();
							try {
								leasing020Action.doRequest(config, null, __map);
							} catch (WXSSException e) {
								e.printStackTrace();
							}
						}
					}.start();
					
				} else {
					msgError = SPDBHelper.getReflectErrorInfo((String)mapQueryAccountData.get("statusCode"), (String)mapQueryAccountData.get("statusMsg"));
					if(!msgError.equals("SUCCESS")) {
						throw new WXSSException("ERROR", "开户失败！失败信息【"+msgError+"】！");
					} else {
						bank.setSpdbFlag((byte) 1);
						bankInfoDao.update(bank);
					}
				}
			} else {
				msgError = SPDBHelper.getReflectErrorInfo((String)mapOpenAccountData.get("statusCode"), (String)mapOpenAccountData.get("statusMsg"));
				if(!msgError.equals("SUCCESS")) {
					throw new WXSSException("ERROR", "开户出错！错信信息【"+msgError+"】！");
				} else {
					bank.setSpdbFlag((byte) 1);
					bankInfoDao.update(bank);
				}
			}
		} else {
			msgError = SPDBHelper.getReflectErrorInfo((String)mapMediaUploadData.get("statusCode"), (String)mapMediaUploadData.get("statusMsg"));
			if(!msgError.equals("SUCCESS")) {
				throw new WXSSException("ERROR", "开户证件信息上传失败！错信码【"+mapMediaUploadData.get("statusMsg")+"】！");
			} else {
				bank.setSpdbFlag((byte) 1);
				bankInfoDao.update(bank);
			}
		}
	}


	
	@Override
	public String getSchemePlanType(Order order) {
		if(null != order && null != order.getPlanId()) {
			Scheme scheme = schemeDao.findSchemeByPlanId(order.getPlanId());
			return scheme.getPlanType();
		}
		return null;
	}
}