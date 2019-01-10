package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.BankInfoDao;
import com.kpleasing.wxss.dao.CertInfoDao;
import com.kpleasing.wxss.dao.ContactInfoDao;
import com.kpleasing.wxss.dao.DrivingLicenseInfoDao;
import com.kpleasing.wxss.dao.OrderDao;
import com.kpleasing.wxss.dao.PersonInfoDao;
import com.kpleasing.wxss.dao.WorkInfoDao;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.mongo.dao.OrderCollectionDao;
import com.kpleasing.wxss.pojo.SearchParam;

@Repository("OrderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order, Integer> implements OrderDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 960911524447513324L;
	private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);
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
	private ContactInfoDao contactInfoDao;
	
	@Autowired
	private WorkInfoDao workInfoDao;
	
	@Autowired
	private OrderCollectionDao orderCollectionDao;
	
	
	@Override
	public void save(Order order) {
		hibernateTemplate.save(order);
		orderCollectionDao.insertOrderCollection(order, "add");
	}
	
	@Override
	public void update(Order order) {
		hibernateTemplate.update(order);
		orderCollectionDao.insertOrderCollection(order, "modify");
	}
	
	@Override
	public void delete(Order order) {
		hibernateTemplate.delete(order);
		orderCollectionDao.insertOrderCollection(order, "delete");
	}


	@Override
	public Order findByCustId(Integer custId) {
		List<Order> list = this.findByProperty(CUST_ID, custId);
		if(list!=null && list.size()>0) {
			Order order = list.get(0);
			order.setCertInfo(certInfoDao.findByUserId(custId));
			order.setDrivingLicenseInfo(drivingLicenseInfoDao.findByUserId(custId));
			order.setBankInfo(bankInfoDao.findByUserId(custId));
			order.setPersonInfo(personInfoDao.findByUserId(custId));
			order.setContactInfo(contactInfoDao.findByUserId(custId));
			order.setWorkInfo(workInfoDao.findByUserId(custId));
			
			return order;
		}
		return null;
	}

	
	@Override
	public List<Order> findOrderSortByUpdate(SearchParam param) {
		StringBuilder hql = new StringBuilder("from Order as model where 1=1 ");
		Map<String, Object> mValue = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(param.getCustId())) {
			hql.append("and model.custId = :custid ");
			mValue.put("custid", Integer.valueOf(param.getCustId()));
		}
		
		if(StringUtils.isNotBlank(param.getUserName())) {
			hql.append("and model.userName like :userName ");
			mValue.put("userName", "%"+param.getUserName()+"%");
		}
		
		if(StringUtils.isNotBlank(param.getCertId())) {
			hql.append("and model.certId like :certId ");
			mValue.put("certId", "%"+param.getCertId()+"%");
		}
		
		if(StringUtils.isNotBlank(param.getPhone())) {
			hql.append("and model.phone like :phone ");
			mValue.put("phone", "%"+param.getPhone()+"%");
		}
		hql.append("order by model.createAt DESC ");
		Query<Order> query = this.getSession().createQuery(hql.toString());
		
		for (Map.Entry<String, Object> entry : mValue.entrySet()) {
			if("custid".equals(entry.getKey())) {
				query.setParameter(entry.getKey(), entry.getValue(), IntegerType.INSTANCE);
			} else {
				query.setParameter(entry.getKey(), entry.getValue(), StringType.INSTANCE);
			}
		}
		
		int firstResult = (param.getPage() - 1) * param.getPageSize();
		query.setFirstResult(firstResult);
		query.setMaxResults(param.getPageSize());
		return query.list();
	}

	
	
	@Override
	public int countTotalOrders(SearchParam param) {
		StringBuilder hql = new StringBuilder("select count(*) from Order as model where 1=1 ");
		Map<String, Object> mValue = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(param.getCustId())) {
			hql.append("and model.custId = :custid ");
			mValue.put("custid", Integer.valueOf(param.getCustId()));
		}
		
		if(StringUtils.isNotBlank(param.getUserName())) {
			hql.append("and model.userName like :userName ");
			mValue.put("userName", "%"+param.getUserName()+"%");
		}
		
		if(StringUtils.isNotBlank(param.getCertId())) {
			hql.append("and model.certId like :certId ");
			mValue.put("certId", "%"+param.getCertId()+"%");
		}
		
		if(StringUtils.isNotBlank(param.getPhone())) {
			hql.append("and model.phone like :phone ");
			mValue.put("phone", "%"+param.getPhone()+"%");
		}
		Query query = this.getSession().createQuery(hql.toString());
		for (Map.Entry<String, Object> entry : mValue.entrySet()) {
			if("custid".equals(entry.getKey())) {
				query.setParameter(entry.getKey(), entry.getValue(), IntegerType.INSTANCE);
			} else {
				query.setParameter(entry.getKey(), entry.getValue(), StringType.INSTANCE);
			}
		}
		return ((Number)query.uniqueResult()).intValue();   
	}
}
