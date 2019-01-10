package com.kpleasing.wxss.esb.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.CRM007AccountBean;
import com.kpleasing.wxss.esb.request.CRM007ContactBean;
import com.kpleasing.wxss.esb.request.CRM007Request;
import com.kpleasing.wxss.esb.response.CRM007Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.util.XMLHelper;

/**
 * 
 * @author howard.huang
 *
 */
@Service
public class CRM007Action extends AbastractBaseAction<CRM007Request, CRM007Response, Order> {

	private static final Logger logger = Logger.getLogger(CRM007Action.class);
	
	@Override
	protected CRM007Request generateRequestEntity(Configurate config, Order order, Map<String, String> map) throws WXSSException {
		CRM007Request crm007Request = new CRM007Request();
		logger.info("开始封装CRM007接口请求实体:"+order.getCustId());
		crm007Request.setApi_code("CRM007");
		crm007Request.setReq_serial_no(StringUtil.getSerialNo32());
		crm007Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		crm007Request.setSecurity_code(config.ESB_SEC_CODE);
		crm007Request.setSecurity_value(config.ESB_SEC_VALUE);
		crm007Request.setCust_id(String.valueOf(order.getCustId()));
		crm007Request.setCust_name(order.getUserName());
		crm007Request.setCust_name_spell(StringUtil.toPingyin(order.getUserName()));
		crm007Request.setCert_type(order.getCertType());
		crm007Request.setCert_code(order.getCertId());
		crm007Request.setPhone(order.getPhone());
		crm007Request.setBirthday(DateUtil.date2Str(order.getCertInfo().getBirthday(), DateUtil.yyyyMMdd));
		crm007Request.setGender(order.getCertInfo().getGenderCode());
		crm007Request.setNation(order.getCertInfo().getNation());
		crm007Request.setDrive_model(order.getDrivingLicenseInfo().getDriveType());
		crm007Request.setAnnual_income(order.getWorkInfo().getAnnualIncome());
		crm007Request.setRel_flag("Y");
		crm007Request.setIncome_from(order.getWorkInfo().getIncomeFromCode());
		crm007Request.setIncome_status(order.getWorkInfo().getIncomeStatusCode());
		crm007Request.setEntry_year(order.getWorkInfo().getEntryYear());
		crm007Request.setLive_status(order.getPersonInfo().getLiveStatusCode());
		crm007Request.setWork_unit(order.getWorkInfo().getWorkUnit());
		crm007Request.setWork_addr(order.getWorkInfo().getWorkAddr());
		crm007Request.setPosition(order.getWorkInfo().getPosition());
		crm007Request.setEdu_level(order.getPersonInfo().getEduLevelCode());
		crm007Request.setMarr_status(order.getPersonInfo().getMarrStatusCode());
		crm007Request.setSpouse_name(order.getContactInfo().getSpouseName());
		crm007Request.setSpouse_cert_type(order.getContactInfo().getSpouseCertType());
		crm007Request.setSpouse_cert_code(order.getContactInfo().getSpouseCertId());
		crm007Request.setSpouse_phone(order.getContactInfo().getSpousePhone());
		crm007Request.setSpouse_income_from(order.getContactInfo().getSpouseIncomeFromCode());
		crm007Request.setSpouse_annual_income(order.getContactInfo().getSpouseAnnualIncomeCode());
		crm007Request.setSpouse_work_unit(order.getContactInfo().getSpouseWorkUnit());
		crm007Request.setWork_year(order.getWorkInfo().getWorkYear());
		crm007Request.setUnit_tel(order.getWorkInfo().getUnitTel());
		crm007Request.setEmail(order.getPersonInfo().getEmail());
		crm007Request.setCert_org(order.getCertInfo().getCertAddr());
		
		crm007Request.setZip_code("200233");
		crm007Request.setCert_addr(order.getCertInfo().getLiveAddr());
		crm007Request.setFamily_tel(order.getPersonInfo().getFamilyPhone());
		crm007Request.setContact_addr(order.getPersonInfo().getFamilyAddr());
		crm007Request.setCust_type("2");
		crm007Request.setCust_memo("");
		crm007Request.setCust_status("Y");
		crm007Request.setMemo("");
		String certFrontPath = order.getCertInfo().getCertFrontImagePath();
		if(certFrontPath != null){
			crm007Request.setIdcard_front_img(config.IMG_URL+certFrontPath);
			crm007Request.setIf_file_type(certFrontPath.substring(certFrontPath.lastIndexOf(".")+1, certFrontPath.length()));
		}
		String certBackPath = order.getCertInfo().getCertBackImagePath();
		if(certBackPath !=null){
			crm007Request.setIdcard_back_img(config.IMG_URL+certBackPath);
			crm007Request.setIb_file_type(certBackPath.substring(certBackPath.lastIndexOf(".")+1, certBackPath.length()));
		}
		String driveLicensePath = order.getDrivingLicenseInfo().getDriveImagePath();
		if(driveLicensePath !=null){
			crm007Request.setDrivelicense_img(config.IMG_URL+driveLicensePath);
			crm007Request.setDf_file_type(driveLicensePath.substring(driveLicensePath.lastIndexOf(".")+1, driveLicensePath.length()));
		}
		String driveLicenseBackPath = order.getDrivingLicenseInfo().getDriveBackImagePath();
		if(driveLicenseBackPath !=null){
			crm007Request.setDrivelicense_back_img(config.IMG_URL+driveLicenseBackPath);
			crm007Request.setDb_file_type(driveLicenseBackPath.substring(driveLicenseBackPath.lastIndexOf(".")+1, driveLicenseBackPath.length()));
		}
		String bankCardFrontPath = order.getBankInfo().getBankcardFrontImagePath();
		if(bankCardFrontPath !=null){
			crm007Request.setBankcard_front_img(config.IMG_URL+bankCardFrontPath);
			crm007Request.setBf_file_type(bankCardFrontPath.substring(bankCardFrontPath.lastIndexOf(".")+1, bankCardFrontPath.length()));
		}
		String bankCardBackPath = order.getBankInfo().getBankcardBackImagePath();
		if(bankCardBackPath !=null){
			crm007Request.setBankcard_back_img(config.IMG_URL+bankCardBackPath);
			crm007Request.setBb_file_type(bankCardBackPath.substring(bankCardBackPath.lastIndexOf(".")+1, bankCardBackPath.length()));
		}
		
		// 婚姻状态为已婚，紧急联系人为配偶信息
		List<CRM007ContactBean> contacts = new ArrayList<CRM007ContactBean>();
		CRM007ContactBean contact = new CRM007ContactBean();
		if(!"MARRIED".equals(order.getPersonInfo().getMarrStatusCode())) {
			contact.setRelation(order.getContactInfo().getContactRelationCode());
			contact.setContact_name(order.getContactInfo().getContactName());
			contact.setIs_important_contact("");
			contact.setContact_cert_type(order.getContactInfo().getContactCertType());
			contact.setContact_cert_code(order.getContactInfo().getContactCertId());
			contact.setContact_work_unit("");
			contact.setContact_phone(order.getContactInfo().getContactPhone());
			contact.setContact_email("");
			contact.setContact_fax("");
			contact.setContact_addr("");
		} else {
			contact.setRelation("2");
			contact.setContact_name(order.getContactInfo().getSpouseName());
			contact.setIs_important_contact("");
			contact.setContact_cert_type(order.getContactInfo().getSpouseCertType());
			contact.setContact_cert_code(order.getContactInfo().getSpouseCertId());
			contact.setContact_work_unit("");
			contact.setContact_phone(order.getContactInfo().getSpousePhone());
			contact.setContact_email("");
			contact.setContact_fax("");
			contact.setContact_addr("");
		}
		contacts.add(contact);
		
		//紧急联系人2
		CRM007ContactBean contact2 = new CRM007ContactBean();
		contact2.setRelation(order.getContactInfo().getContact2RelationCode());
		contact2.setContact_name(order.getContactInfo().getContact2Name());
		contact2.setIs_important_contact("");
		contact2.setContact_cert_type(order.getContactInfo().getContact2CertType());
		contact2.setContact_cert_code(order.getContactInfo().getContact2CertId());
		contact2.setContact_work_unit("");
		contact2.setContact_phone(order.getContactInfo().getContact2Phone());
		contact2.setContact_email("");
		contact2.setContact_fax("");
		contact2.setContact_addr("");
		contacts.add(contact2);
		
		crm007Request.setContacts(contacts);
		
		List<CRM007AccountBean> accounts = new ArrayList<CRM007AccountBean>();
		CRM007AccountBean account = new CRM007AccountBean();
		account.setAcc_name(order.getCertInfo().getUserName());
		account.setAcc_no(order.getBankInfo().getRepayCardNo());
		account.setBank_code(order.getBankInfo().getRepayAccBankCode());
		account.setBank_full_name(order.getBankInfo().getRepayAccBank());
		account.setBranch_bank_name(order.getBankInfo().getRepayAccBank());
		account.setBank_phone(order.getBankInfo().getBankPhone());
		account.setCurrency("");
		account.setWithhold_unit("2");
		account.setIs_withhold_acc("Y");
		accounts.add(account);
		crm007Request.setAccounts(accounts);
		
		try {
			crm007Request.setSign(Security.getSign(crm007Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		
		return crm007Request;
	}
	
	
	@Override
	protected String generateRequestXML(CRM007Request crm007Request) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder requestXml = new StringBuilder();
		requestXml.append("<?xml version=\"1.0\"?>")
		.append("<esb><head>")
		.append("<api_code><![CDATA[").append(crm007Request.getApi_code()).append("]]></api_code>")
		.append("<req_serial_no><![CDATA[").append(crm007Request.getReq_serial_no()).append("]]></req_serial_no>")
		.append("<req_date><![CDATA[").append(crm007Request.getReq_date()).append("]]></req_date>")
		.append("<security_code><![CDATA[").append(crm007Request.getSecurity_code()).append("]]></security_code>")
		.append("<security_value><![CDATA[").append(crm007Request.getSecurity_value()).append("]]></security_value>")
		.append("<sign><![CDATA[").append(crm007Request.getSign()).append("]]></sign>")
		.append("</head><body>");
		
		requestXml.append(XMLHelper.getXMLFromBean(crm007Request));
		
		List<CRM007ContactBean> contacts = crm007Request.getContacts();
		if(null == contacts || contacts.isEmpty()) {
			requestXml.append("<contacts/>");
		} else {
			requestXml.append("<contacts>");
			for(CRM007ContactBean contact : contacts) {
				requestXml.append("<contact>").append(XMLHelper.getXMLFromBean(contact)).append("</contact>");
			}
			requestXml.append("</contacts>");
		}
		
		List<CRM007AccountBean> accounts = crm007Request.getAccounts();
		if(null == accounts || accounts.isEmpty()) {
			requestXml.append("<accounts/>");
		} else {
			requestXml.append("<accounts>");
			for(CRM007AccountBean account : accounts) {
				requestXml.append("<account>").append(XMLHelper.getXMLFromBean(account)).append("</account>");
			}
			requestXml.append("</accounts>");
		}
		requestXml.append("</body></esb>");
		
		return requestXml.toString();
	}

	
	@Override
	protected CRM007Response initResponseEntity() {
		return new CRM007Response();
	}

}
