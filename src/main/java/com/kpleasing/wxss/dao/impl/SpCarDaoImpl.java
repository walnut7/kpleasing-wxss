package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.CarDao;
import com.kpleasing.wxss.dao.SpCarDao;
import com.kpleasing.wxss.entity.Car;
import com.kpleasing.wxss.entity.SpCar;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.web.controller.CarlistController;

@Repository("SpCarDao")
public class SpCarDaoImpl extends BaseDaoImpl<SpCar, Integer> implements SpCarDao, Serializable {

	/** * */
	private static final long serialVersionUID = 6428233102084933138L;

	private static Logger logger = Logger.getLogger(CarlistController.class);

	/**
	 * 通过SP信息查询车辆信息
	 * 
	 * @param bp_id
	 * @param bp_code
	 * @return
	 */
	public List<SpCar> getSpCarList(Integer bpId, String bpCode) {
		//Object[] values = { DateUtil.getCurrentDate(DateUtil.yyyy_MM_dd), bpId, bpCode };
		Object[] values = {bpId, bpCode };
		
		String sql = "SELECT new com.kpleasing.wxss.entity.SpCar(c.brand as brand,c.series as series,c.modelId as modelId, c.model as model,c.seriesLogo as seriesLogo, '' as description1,'' as description2)  FROM Car c where exists (SELECT s.modelId   FROM    Scheme s,    RCarScheme sc,    SpInfo i  WHERE    s.planId = sc.planId   AND i.bpId = sc.bpId   AND sc.enabledFlag = 0   AND SYSDATE() BETWEEN s.validDateFrom   AND s.validDateTo   AND i.bpId = ?    AND i.bpCode = ?  and   c.modelId = s.modelId   )";
		List<SpCar> list =(List<SpCar>) this.find(sql, values);
		
		return list;
	}
	
	
	
	public SpCar getSpCarByModelIdUniqueOne(Integer bpId,String bpCode,Integer modelId) {
		//Object[] values = { DateUtil.getCurrentDate(DateUtil.yyyy_MM_dd), bpId, bpCode,modelId };
		Object[] values = {bpId, bpCode,modelId };
		String sql ="SELECT new com.kpleasing.wxss.entity.SpCar(c.brand as brand,c.series as series,c.modelId as modelId, c.model as model,c.seriesLogo as seriesLogo, '' as description1,'' as description2 ,s.purchaseTax ,s.insuranceFeeFinancing,c.msrp)  FROM Car c , Scheme s,    RCarScheme sc,    SpInfo i  WHERE    s.planId = sc.planId   AND i.bpId = sc.bpId   AND sc.enabledFlag = 0   AND SYSDATE() BETWEEN s.validDateFrom   AND s.validDateTo   AND i.bpId = ?    AND i.bpCode = ?  and   c.modelId = s.modelId and s.modelId=?";
		List<SpCar> list =(List<SpCar>) this.find(sql, values);
		
		return (list != null && list.size() >= 1) ? list.get(0) : null;
	}
	
	

}
