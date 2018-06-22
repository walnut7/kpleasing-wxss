package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.CarDao;
import com.kpleasing.wxss.entity.Car;

@Repository("CarDao")
public class CarDaoImpl extends BaseDaoImpl<Car, Integer> implements CarDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 6428233102084933138L;
	
	
	/**
	 * 通过SP信息查询车辆信息
	 * @param bp_id
	 * @param bp_code
	 * @return
	 */
	public List<Car> getSpCarList(Integer bp_id,String bp_code) {
		Object[] values=new Object[2];
		values[0]=bp_id;
		values[1]=bp_code;
		
		String sql="SELECT\r\n" + 
				"	c.*\r\n" + 
				"FROM\r\n" + 
				"	tb_car c,\r\n" + 
				"	(\r\n" + 
				"		SELECT DISTINCT\r\n" + 
				"			s.model_id\r\n" + 
				"		FROM\r\n" + 
				"			tb_scheme s,\r\n" + 
				"			r_car_scheme sc,\r\n" + 
				"			tb_sp_info i\r\n" + 
				"		WHERE\r\n" + 
				"			s.plan_id = sc.plan_id\r\n" + 
				"		AND i.bp_id = sc.bp_id\r\n" + 
				"		AND sc.enabled_flag = 1\r\n" + 
				"		AND NOW() BETWEEN s.valid_date_from\r\n" + 
				"		AND s.valid_date_to\r\n" + 
				"		AND i.bp_id = ? \r\n" + 
				"		AND i.bp_code = ? \r\n" + 
				"    order by s.create_at desc	\r\n" + 
				") t1\r\n" + 
				"WHERE\r\n" + 
				"	c.model_id = t1.model_id\r\n" + 
				"";
		List<Car> list=this.find(sql, values);
		return list;

}


	@Override
	public Car findCarInfoByModelId(Integer modelId) {
		List<Car> carlist = this.findByProperty("modelId", modelId);
		return (carlist!=null && carlist.size()>0)?carlist.get(0):null;
	}
}
