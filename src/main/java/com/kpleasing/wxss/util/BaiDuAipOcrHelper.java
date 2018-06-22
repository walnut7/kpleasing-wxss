package com.kpleasing.wxss.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.entity.CertInfo;
import com.kpleasing.wxss.entity.DrivingLicenseInfo;


public class BaiDuAipOcrHelper {
	public static final String APP_ID = "11072072";
	public static final String API_KEY = "VLnGkN42C2lLNc87KfuNNxM7";
	public static final String SECRET_KEY = "oQ3YrqCDFcd6tApCFtH7rUSX32NEG0zA";
	private static Logger logger = Logger.getLogger(BaiDuAipOcrHelper.class);
	private static BaiDuAipOcrHelper instance;
	private AipOcr aipOcr;
	
	public synchronized static BaiDuAipOcrHelper getInstance() {
		if (instance == null) {
			instance = new BaiDuAipOcrHelper();
		}
		return instance;
	}
	
	
	public BaiDuAipOcrHelper() {
		aipOcr = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		aipOcr.setConnectionTimeoutInMillis(2000);
		aipOcr.setSocketTimeoutInMillis(60000);
    }
	
	
	/**
	 * 
	 * @param image
	 * @return
	 * @throws Exception
	 */
	public CertInfo readIdCardFront(String image) throws Exception {
	    try {
		    HashMap<String, String> options = new HashMap<String, String>();
		    options.put("detect_direction", "true");
		    options.put("detect_risk", "false");
		    
		    String idCardSide = "front";
		    
		    JSONObject res = aipOcr.idcard(image, idCardSide, options);
		    logger.info(res.toString(2));
		    
		    return parseIDCardFrontInfo((Map<String, Object>)res.toMap().get("words_result"));
	    } catch(Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return null;
	}
	
	
	
	
	
	/**
	 * 解析识别身份证正面信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CertInfo parseIDCardFrontInfo(Map<String, Object> map) {
		logger.info("=="+map.toString());
		CertInfo certInfo = new CertInfo();
	    for (String key : map.keySet()) {
		    if("姓名".equals(key)) {
	            certInfo.setUserName((String)((Map<String, Object>) map.get(key)).get("words"));
	        } else if("民族".equals(key)) {
	            certInfo.setNation((String)((Map<String, Object>) map.get(key)).get("words"));
	        } else if("住址".equals(key)) {
	        	certInfo.setLiveAddr((String)((Map<String, Object>) map.get(key)).get("words"));
	        } else if("公民身份号码".equals(key)) {
	        	certInfo.setCertId((String)((Map<String, Object>) map.get(key)).get("words"));
	        } else if("出生".equals(key)) {
	        	certInfo.setBirthday(DateUtil.str2Date((String)((Map<String, Object>) map.get(key)).get("words"), DateUtil.yyyyMMdd));
	        } else if("性别".equals(key)) {
	        	certInfo.setGender((String)((Map<String, Object>) map.get(key)).get("words"));
	        	if("男".equals(certInfo.getGender())) {
	        		certInfo.setGenderCode("MALE");
	        	} else if("女".equals(certInfo.getGender())) {
	        		certInfo.setGenderCode("FEMALE");
	        	} else {
	        		certInfo.setGenderCode("UNKNOW");
	        	}
		  }
	  }
	  return certInfo;
	}
	
	
	/**
	 * 解析识别身份证反面信息
	 * @param image
	 * @return
	 */
	public CertInfo readIdCardBack(String image) {
		try {
		    // 传入可选参数调用接口
		    HashMap<String, String> options = new HashMap<String, String>();
		    options.put("detect_direction", "true");
		    options.put("detect_risk", "false");
		    
		    String idCardSide = "back";
		    
		    JSONObject res = aipOcr.idcard(image, idCardSide, options);
		    logger.info(res.toString(2));
		    
		    return parseIDCardBackInfo((Map<String, Object>)res.toMap().get("words_result"));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private CertInfo parseIDCardBackInfo(Map<String, Object> map) {
		logger.info("=="+map.toString());
		CertInfo certInfo = new CertInfo();
	    for (String key : map.keySet()) {
		    if("签发机关".equals(key)) {
	            certInfo.setCertAddr((String)((Map<String, Object>) map.get(key)).get("words"));
	        }
	  }
	  return certInfo;
	}


	/**
	 * 解析识别驾驶证信息
	 * @param image
	 * @return
	 */
	public DrivingLicenseInfo readDriveLicense(String image) {
	    try {
		    HashMap<String, String> options = new HashMap<String, String>();
		    options.put("detect_direction", "true");
		    
		    JSONObject res = aipOcr.drivingLicense(image, options);
		    logger.info(res.toString(2));
		    
		    return parseDrivingLicenseInfo((Map<String, Object>)res.toMap().get("words_result"));
	    } catch(Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private DrivingLicenseInfo parseDrivingLicenseInfo(Map<String, Object> map) {
		logger.info("=="+map.toString());
		DrivingLicenseInfo driving = new DrivingLicenseInfo();
	    for (String key : map.keySet()) {
		    if("准驾车型".equals(key)) {
		    	driving.setDriveType((String)((Map<String, Object>) map.get(key)).get("words"));
	        } else if("初次领证日期".equals(key)) {
	        	driving.setDriveFirstDate(DateUtil.str2Date((String)((Map<String, Object>) map.get(key)).get("words"), DateUtil.yyyyMMdd));
	        }
	    }
	    return driving;
	}


	/**
	 * 解析识别银行卡信息
	 * @param image
	 * @return
	 */
	public BankInfo readBankCardFront(String image) {
	    try {
		    HashMap<String, String> options = new HashMap<String, String>();
		    
		    JSONObject res = aipOcr.bankcard(image, options);
		    logger.info(res.toString(2));
		    
		    return parseBankCardInfo((Map<String, Object>)res.toMap().get("result"));
	    } catch(Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return null;
	}
	
	
	private BankInfo parseBankCardInfo(Map<String, Object> map) {
		logger.info("=="+map.toString());
		BankInfo bank = new BankInfo();
		bank.setRepayCardNo((String)map.get("bank_card_number"));
		bank.setRepayAccBank((String)map.get("bank_name"));
	    return bank;
	}
	
	
	
	/**
	 * 识别身份证正面，传递base64进行识别
	 * @param base64
	 * @return
	 * @throws Exception
	 */
	public CertInfo readIdCardFrontBase64(String base64) throws Exception{
		try {
		    HashMap<String, String> options = new HashMap<String, String>();
		    options.put("id_card_side", "front");
		    
		    byte[] file = Base64.decodeBase64(base64);
		    JSONObject res = aipOcr.idcard(file,"front", options);
		    logger.info(res.toString(2));
		    
		    return parseIDCardFrontInfo((Map<String, Object>)res.toMap().get("words_result"));
	    } catch(Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return null;
	}
	
	
	/**
	 * 身份证反面，传递base64进行识别
	 * @param base64
	 * @return
	 */
	public CertInfo readIdCardBackBase64(String base64) {
		try {
		    // 传入可选参数调用接口
		    HashMap<String, String> options = new HashMap<String, String>();
		    options.put("id_card_side", "back");
		    
		    String idCardSide = "back";
		    
		    byte[] file = Base64.decodeBase64(base64);
		    JSONObject res = aipOcr.idcard(file, idCardSide, options);
		    logger.info(res.toString(2));
		    
		    return parseIDCardBackInfo((Map<String, Object>)res.toMap().get("words_result"));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 驾驶证识别 ，传递base64进行识别
	 * @param base64
	 * @return
	 */
	public DrivingLicenseInfo readDriveLicenseBase64(String base64) {
	    try {
		    HashMap<String, String> options = new HashMap<String, String>();
		    
		    byte[] file = Base64.decodeBase64(base64);
		    JSONObject res = aipOcr.drivingLicense(file, options);
		    logger.info(res.toString(2));
		    
		    return parseDrivingLicenseInfo((Map<String, Object>)res.toMap().get("words_result"));
	    } catch(Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return null;
	}
	
	/**
	 * 驾驶证识别，传递base64字符串
	 * @param base64
	 * @return
	 */
	public BankInfo readBankCardFrontBase64(String base64) {
	    try {
		    HashMap<String, String> options = new HashMap<String, String>();
		    
		    byte[] file = Base64.decodeBase64(base64);
		    JSONObject res = aipOcr.bankcard(file, options);
		    logger.info(res.toString(2));
		    
		    return parseBankCardInfo((Map<String, Object>)res.toMap().get("result"));
	    } catch(Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return null;
	}
	
}
