package com.kpleasing.wxss.web.service;

import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.entity.CertInfo;
import com.kpleasing.wxss.entity.DrivingLicenseInfo;
import com.kpleasing.wxss.entity.FaceVideo;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.entity.PersonInfo;
import com.kpleasing.wxss.entity.WorkInfo;
import com.kpleasing.wxss.exception.WXSSException;

public interface RegisterService {

	/**
	 * 
	 * @param phone
	 * @param certInfo
	 * @throws WXSSException 
	 * @throws Exception 
	 */
	public void saveCertInfoA(String custId, CertInfo certInfo) throws WXSSException ;

	
	/**
	 * 
	 * @param custId
	 * @param certInfo
	 * @throws WXSSException 
	 */
	public void saveCertInfoB(String custId, CertInfo certInfo) throws WXSSException;
	
	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	public CertInfo queryCertInfoByCustId(String custId);

	
	/**
	 * 
	 * @param custId
	 * @param driverInfo
	 * @throws WXSSException 
	 */
	public void saveDrivingLicenseInfo(String custId, DrivingLicenseInfo driverInfo) throws WXSSException;


	/**
	 * 
	 * @param custId
	 * @return
	 */
	public DrivingLicenseInfo queryDrivingLicenseInfoByCustId(String custId);


	/**
	 * 
	 * @param custId
	 * @param bankInfo
	 * @throws WXSSException 
	 */
	public void saveBankInfo(String custId, BankInfo bankInfo) throws WXSSException;
	
	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	public BankInfo queryBankInfoByCustId(String custId);


	/**
	 * 
	 * @param custId
	 * @param personInfo
	 * @throws WXSSException 
	 */
	public void savePersonInfo(String custId, PersonInfo personInfo) throws WXSSException;
	
	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	public PersonInfo queryPersonInfoByCustId(String custId);

	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	public WorkInfo queryWorkInfoByCustId(String custId);


	/**
	 * 
	 * @param custId
	 * @param workInfo
	 * @throws WXSSException 
	 */
	public void saveWorkInfo(String custId, WorkInfo workInfo) throws WXSSException;


	/**
	 * 
	 * @param order
	 * @throws Exception 
	 */
	public void syncCustomerInfo(Order order) throws Exception;


	/**
	 * 
	 * @param order
	 * @throws Exception 
	 */
	public void createLeasingOrder(Order order) throws Exception;


	/**
	 * 
	 * @param custId
	 * @param fileName
	 */
	public void updateCertInfoImageBackPath(String custId, String fileName);


	/**
	 * 
	 * @param custId
	 * @param fileName
	 */
	public void updateDriverInfoImagePath(String custId, String fileName);

	/**
	 * @param custId
	 * @param fileName
	 */
	public void updateDriverInfoImageBackPath(String custId, String fileName);

	/**
	 * 
	 * @param custId
	 * @param fileName
	 */
	public void updateBankCardImageFrontPath(String custId, String fileName);


	/**
	 * 
	 * @param custId
	 * @param fileName
	 */
	public void updateBankCardImageBackPath(String custId, String fileName);


	/**
	 * 
	 * @param custId
	 * @param fileName
	 */
	public void updateCertInfoImageFrontPath(String custId, String fileName);


	/**
	 * @param dringvingType 
	 * 
	 */
	public String getDrivingTypeDesc(String dringvingType);


	/**
	 * 
	 * @param custId
	 * @param preVideoAudit
	 * @return
	 */
	public FaceVideo savePerVideoAudit(String custId, FaceVideo preVideoAudit);

	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	public FaceVideo queryFaceVideoByCustId(String custId);
	
	/**
	 * 
	 * @param audit
	 */
	public void sendPerVideoAuditReq(FaceVideo audit) throws WXSSException;


	
	
	public void payAuthentication(String custId, BankInfo bankInfo) throws WXSSException;



	public void bindPayment(String custId, String verifyCode) throws WXSSException;

	
	/**
	 * 
	 * @param custId
	 * @throws WXSSException 
	 */
	public void doFirstAudit(String custId) ;
}
