package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.SpSalesDao;
import com.kpleasing.wxss.entity.SpSales;

@Repository("SpSalesDao")
public class SpSalesDaoImpl extends BaseDaoImpl<SpSales, Integer> implements SpSalesDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -3507028608376173169L;
	private final static String BP_ID = "bpId";

	@Override
	public SpSales findSalesInfoBySaleId(Integer saleId) {
		List<SpSales> saleslist = this.findByProperty("saleId", saleId);
		return (saleslist!=null && saleslist.size()>0)?saleslist.get(0):null;
	}

	@Override
	public List<SpSales> findSalesListByBpId(Integer bpId) {
		String queryString = "from SpSales as model where model.bpId=? and model.enabledFlag=0 order by model.saleName ASC ";
		return this.find(queryString, bpId);
	}
	
	@Override
	public void updateSalesBySQL(Integer bpId) {
		Session session = this.getSession();
		//Transaction tx = session.beginTransaction();
		String hqlUpdate = "update SpSales s set s.enabledFlag = :enabledFlag where s.bpId = :bpId";
		int updatedEntities = session.createQuery( hqlUpdate )
		.setByte( "enabledFlag", (byte)1 )
		.setInteger("bpId", bpId )
		.executeUpdate();
		//tx.commit();
		//session.close();
	}

}
