package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.BankInfoDao;
import com.kpleasing.wxss.dao.CertInfoDao;
import com.kpleasing.wxss.dao.DrivingLicenseInfoDao;
import com.kpleasing.wxss.dao.OrderDao;
import com.kpleasing.wxss.dao.PersonInfoDao;
import com.kpleasing.wxss.dao.WorkInfoDao;
import com.kpleasing.wxss.entity.Order;

@Repository("OrderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order, Integer> implements OrderDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 960911524447513324L;
	private final static String CUST_ID = "custId";
	private final static String PHONE = "phone";
	
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
	public Order findByCustId(Integer custId) {
		List<Order> list = this.findByProperty(CUST_ID, custId);
		if(list!=null && list.size()>0) {
			Order order = list.get(0);
			order.setCertInfo(certInfoDao.findByUserId(custId));
			order.setDrivingLicenseInfo(drivingLicenseInfoDao.findByUserId(custId));
			order.setBankInfo(bankInfoDao.findByUserId(custId));
			order.setPersonInfo(personInfoDao.findByUserId(custId));
			order.setWorkInfo(workInfoDao.findByUserId(custId));
			
			return order;
		}
		return null;
	}

}
