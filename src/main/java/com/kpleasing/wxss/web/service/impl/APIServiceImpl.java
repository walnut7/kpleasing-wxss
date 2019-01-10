package com.kpleasing.wxss.web.service.impl;

import java.io.Serializable;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.dao.CarDao;
import com.kpleasing.wxss.dao.CarParamDao;
import com.kpleasing.wxss.dao.RCarSchemeDao;
import com.kpleasing.wxss.dao.SchemeDao;
import com.kpleasing.wxss.dao.SpInfoDao;
import com.kpleasing.wxss.dao.SpSalesDao;
import com.kpleasing.wxss.entity.Car;
import com.kpleasing.wxss.entity.CarParam;
import com.kpleasing.wxss.entity.RCarScheme;
import com.kpleasing.wxss.entity.Scheme;
import com.kpleasing.wxss.entity.SpInfo;
import com.kpleasing.wxss.entity.SpSales;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.protocol.request.SyncSPInfoRequest;
import com.kpleasing.wxss.protocol.request.SyncSaleInfo;
import com.kpleasing.wxss.protocol.request.SyncCarInfoRequest;
import com.kpleasing.wxss.protocol.request.SyncCarParam;
import com.kpleasing.wxss.protocol.request.SyncFinSchemeRequest;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.web.service.APIService;

@Service("APIService")
@Transactional
public class APIServiceImpl implements APIService,Serializable {

	/** *  */
	private static final long serialVersionUID = 3098917667664524098L;
	private static Logger logger = Logger.getLogger(APIServiceImpl.class);
	
	@Autowired
	private SpInfoDao spInfoDaoImpl;
	
	@Autowired
	private SpSalesDao spSalesDaoImpl;
	
	@Autowired
	private CarDao	carDaoImpl;
	
	@Autowired
	private CarParamDao carParamDaoImpl;
	
	@Autowired
	private SchemeDao schemeDaoImpl;
	
	@Autowired
	private RCarSchemeDao rCarSchemeDaoImpl;
	
	@Override
	public void saveSPAndSaleInfo(SyncSPInfoRequest syncSPInfoRequest) throws WXSSException {
		try{
			logger.info("开始保存SP及销售信息......");
			Integer bpId = Integer.valueOf(syncSPInfoRequest.getBp_id());
			SpInfo spInfo = spInfoDaoImpl.findSpInfoByBpId(bpId);
			if(spInfo == null ){
				logger.info("新增SP信息(bp_id："+ bpId + ")");
				spInfo = new SpInfo();
				spInfo.setBpId(bpId.intValue());
				spInfo.setBpCode(syncSPInfoRequest.getBp_code());
				spInfo.setBpName(syncSPInfoRequest.getBp_name());
				spInfo.setOrganizationCode(syncSPInfoRequest.getOrganization_code());
				spInfo.setCity(syncSPInfoRequest.getCity());
				spInfo.setProvince(syncSPInfoRequest.getProvince());
				spInfo.setImageUrl(syncSPInfoRequest.getImage_url());
				if("Y".equals(syncSPInfoRequest.getEnabled_flag())){
					spInfo.setEnabledFlag((byte)0);
				}else{
					spInfo.setEnabledFlag((byte)1);
				}
				spInfo.setCreateAt(DateUtil.getDate());
				spInfo.setUpdateAt(DateUtil.getDate());
				spInfoDaoImpl.save(spInfo);
				
			}else{
				logger.info("更新SP信息(bp_id："+ bpId + ")");
				spInfo.setBpCode(syncSPInfoRequest.getBp_code());
				spInfo.setBpName(syncSPInfoRequest.getBp_name());
				spInfo.setOrganizationCode(syncSPInfoRequest.getOrganization_code());
				spInfo.setCity(syncSPInfoRequest.getCity());
				spInfo.setProvince(syncSPInfoRequest.getProvince());
				spInfo.setImageUrl(syncSPInfoRequest.getImage_url());
				if("Y".equals(syncSPInfoRequest.getEnabled_flag())){
					spInfo.setEnabledFlag((byte)0);
				}else{
					spInfo.setEnabledFlag((byte)1);
				}
				spInfo.setUpdateAt(DateUtil.getDate());
				spInfoDaoImpl.update(spInfo);
			}
			
			//重置SP门店销售信息
			logger.info("重置SP门店销售信息...");
			spSalesDaoImpl.updateSalesBySQL(bpId);
			
			List<SyncSaleInfo> sales_list = syncSPInfoRequest.getSales();
			if(sales_list != null && sales_list.size() > 0){
				logger.info("保存SP门店销售信息....");
				for(SyncSaleInfo si:sales_list){
					Integer saleId = Integer.valueOf(si.getSale_id());
					SpSales sales = spSalesDaoImpl.findSalesInfoBySaleId(saleId);
					if(sales == null){
						logger.info("新增门店销售(" + saleId + ")信息......");
						sales = new SpSales();
						sales.setSaleId(saleId.intValue());
						sales.setBpId(bpId.intValue());
						sales.setSaleName(si.getSale_name());
						sales.setSalePhone(si.getSale_phone());
						if("Y".equals(si.getEnabled_flag())){
							sales.setEnabledFlag((byte)0);
						}else{
							sales.setEnabledFlag((byte)1);
						}
						spSalesDaoImpl.save(sales);
						
					}else{
						logger.info("更新门店销售(" + saleId + ")信息.......");
						sales.setBpId(bpId.intValue());
						sales.setSaleName(si.getSale_name());
						sales.setSalePhone(si.getSale_phone());
						if("Y".equals(si.getEnabled_flag())){
							sales.setEnabledFlag((byte)0);
						}else{
							sales.setEnabledFlag((byte)1);
						}
						spSalesDaoImpl.update(sales);
					}	
				}
			}
			
		} catch (Exception e){
			logger.error("数据处理异常:"+e.getMessage(), e);
			throw new WXSSException("FAILED", "数据处理异常");
		}

	}

	/**
	 * 保存车辆及参数信息
	 * @param syncCarInfoRequest
	 * @throws WXSSException
	 */
	@Override
	public void saveCarAndParamInfo(SyncCarInfoRequest syncCarInfoRequest) throws WXSSException {
		try{
			logger.info("开始保存车辆信息......");
			Integer modelId = Integer.valueOf(syncCarInfoRequest.getModel_id());
			Car car = carDaoImpl.findCarInfoByModelId(modelId);
			if(car == null){
				logger.info("新增车辆信息(model_id：" + modelId + ")");
				car = new Car();
				car.setModelId(modelId.intValue());
				car.setFirstLetter(syncCarInfoRequest.getFirst_letter());
				car.setBrand(syncCarInfoRequest.getBrand());
				car.setSeries(syncCarInfoRequest.getSeries());
				car.setSeriesLogo(syncCarInfoRequest.getSeries_logo());
				car.setModel(syncCarInfoRequest.getModel());
				car.setSaleStatus(syncCarInfoRequest.getSale_status());
				car.setMenufactory(syncCarInfoRequest.getMenufactory());
				car.setYear(syncCarInfoRequest.getYear());
				car.setPublicTime(syncCarInfoRequest.getPublic_time());
				car.setMsrp(syncCarInfoRequest.getMsrp());
				car.setModelType(syncCarInfoRequest.getModel_type());
				if("Y".equals(syncCarInfoRequest.getEnabled_flag())){
					car.setEnabledFlag((byte)0);
				}else{
					car.setEnabledFlag((byte)1);
				}
				car.setCreateAt(DateUtil.getDate());
				car.setUpdateAt(DateUtil.getDate());
				carDaoImpl.save(car);
				
			}else{
				logger.info("更新车辆信息(model_id：" + modelId + ")");
				car.setFirstLetter(syncCarInfoRequest.getFirst_letter());
				car.setBrand(syncCarInfoRequest.getBrand());
				car.setSeries(syncCarInfoRequest.getSeries());
				car.setSeriesLogo(syncCarInfoRequest.getSeries_logo());
				car.setModel(syncCarInfoRequest.getModel());
				car.setSaleStatus(syncCarInfoRequest.getSale_status());
				car.setMenufactory(syncCarInfoRequest.getMenufactory());
				car.setYear(syncCarInfoRequest.getYear());
				car.setPublicTime(syncCarInfoRequest.getPublic_time());
				car.setMsrp(syncCarInfoRequest.getMsrp());
				car.setModelType(syncCarInfoRequest.getModel_type());
				if("Y".equals(syncCarInfoRequest.getEnabled_flag())){
					car.setEnabledFlag((byte)0);
				}else{
					car.setEnabledFlag((byte)1);
				}
				car.setUpdateAt(DateUtil.getDate());
				carDaoImpl.update(car);
			}
			
			List<SyncCarParam> cp_list = syncCarInfoRequest.getCar_params();
			if(cp_list !=null && cp_list.size()>0){
				logger.info("保存车辆参数信息....");
				for(SyncCarParam scp: cp_list){
					CarParam car_param = carParamDaoImpl.findCarParamByPoropertys(modelId,scp.getParam_name());
					if(car_param == null){
						logger.info("新增参数信息........");
						car_param = new CarParam();
						car_param.setModelId(modelId.intValue());
						car_param.setModelParamName(scp.getParam_name());
						car_param.setModelParamValue(scp.getParam_value());
						carParamDaoImpl.save(car_param);
						
					}else{
						logger.info("更新参数信息........");
						car_param.setModelParamValue(scp.getParam_value());
						carParamDaoImpl.update(car_param);
					}
				}
			}
			
		} catch (Exception e){
			logger.error("数据处理异常:"+e.getMessage(), e);
			throw new WXSSException("FAILED", "数据处理异常");
		}
	}

	/**
	 * 保存融资方案信息
	 * @param syncFinSchemeRequest
	 * @throws WXSSException
	 */
	@Override
	public void saveFinSchemeInfo(SyncFinSchemeRequest syncFinSchemeRequest) throws WXSSException {
		try {
			logger.info("开始保存融资方案信息......");
			Integer planId = Integer.valueOf(syncFinSchemeRequest.getPlan_id());
			Scheme scheme = schemeDaoImpl.findSchemeByPlanId(planId);
			if(scheme == null ){
				logger.info("新增融资方案信息(plan_id："+ planId + ")");
				scheme = new Scheme();
				scheme.setPlanId(planId);
				scheme.setPlanDesc(syncFinSchemeRequest.getPlan_desc());
				scheme.setModelId(Integer.valueOf(syncFinSchemeRequest.getModel_id()));
				scheme.setLeaseItemAmount(syncFinSchemeRequest.getLease_item_amount());
				scheme.setDownpaymentAmount(syncFinSchemeRequest.getDownpayment_amount());
				scheme.setDepositAmount(syncFinSchemeRequest.getDeposit_amount());
				scheme.setPurchaseTax(syncFinSchemeRequest.getPurchase_tax());
				scheme.setInsuranceFeeFinancing(syncFinSchemeRequest.getInsurance_fee_financing());
				scheme.setCarPlateFee(syncFinSchemeRequest.getCar_plate_fee());
				scheme.setGpsFee(syncFinSchemeRequest.getGps_fee());
				scheme.setTaxinsurance(syncFinSchemeRequest.getTaxinsurance());
				scheme.setPckprice(syncFinSchemeRequest.getPckprice());
				scheme.setLeaseTimes(syncFinSchemeRequest.getLease_times());
				scheme.setPlanType(syncFinSchemeRequest.getPlan_type());
				scheme.setRental(syncFinSchemeRequest.getRental());
				scheme.setIntRate(syncFinSchemeRequest.getInt_rate());
				scheme.setRental112(syncFinSchemeRequest.getRental_1_12());
				scheme.setRental1348(syncFinSchemeRequest.getRental_13_48());
				scheme.setBuyoutAmount(syncFinSchemeRequest.getBuyout_amount());
				scheme.setValidDateFrom(syncFinSchemeRequest.getValid_date_from());
				scheme.setValidDateTo(syncFinSchemeRequest.getValid_date_to());
				scheme.setPlanSynopsis(syncFinSchemeRequest.getPlan_synopsis());
				scheme.setCreateAt(DateUtil.getDate());
				scheme.setUpdateAt(DateUtil.getDate());
				schemeDaoImpl.save(scheme);
				
			}else{
				logger.info("更新融资方案信息(plan_id："+ planId + ")");
				scheme.setPlanDesc(syncFinSchemeRequest.getPlan_desc());
				scheme.setModelId(Integer.valueOf(syncFinSchemeRequest.getModel_id()));
				scheme.setLeaseItemAmount(syncFinSchemeRequest.getLease_item_amount());
				scheme.setDownpaymentAmount(syncFinSchemeRequest.getDownpayment_amount());
				scheme.setDepositAmount(syncFinSchemeRequest.getDeposit_amount());
				scheme.setPurchaseTax(syncFinSchemeRequest.getPurchase_tax());
				scheme.setInsuranceFeeFinancing(syncFinSchemeRequest.getInsurance_fee_financing());
				scheme.setCarPlateFee(syncFinSchemeRequest.getCar_plate_fee());
				scheme.setGpsFee(syncFinSchemeRequest.getGps_fee());
				scheme.setTaxinsurance(syncFinSchemeRequest.getTaxinsurance());
				scheme.setPckprice(syncFinSchemeRequest.getPckprice());
				scheme.setLeaseTimes(syncFinSchemeRequest.getLease_times());
				scheme.setPlanType(syncFinSchemeRequest.getPlan_type());
				scheme.setRental(syncFinSchemeRequest.getRental());
				scheme.setIntRate(syncFinSchemeRequest.getInt_rate());
				scheme.setRental112(syncFinSchemeRequest.getRental_1_12());
				scheme.setRental1348(syncFinSchemeRequest.getRental_13_48());
				scheme.setBuyoutAmount(syncFinSchemeRequest.getBuyout_amount());
				scheme.setValidDateFrom(syncFinSchemeRequest.getValid_date_from());
				scheme.setValidDateTo(syncFinSchemeRequest.getValid_date_to());
				scheme.setPlanSynopsis(syncFinSchemeRequest.getPlan_synopsis());
				scheme.setUpdateAt(DateUtil.getDate());
				schemeDaoImpl.update(scheme);
			}
			
			Integer bpId = Integer.valueOf(syncFinSchemeRequest.getBp_id());
			logger.info("保存服务商方案信息......");
			RCarScheme rCarScheme = rCarSchemeDaoImpl.findRCarSchemeByPropertys(planId,bpId);
			if(rCarScheme == null){
				rCarScheme = new RCarScheme();
				rCarScheme.setPlanId(planId.intValue());
				rCarScheme.setBpId(bpId.intValue());
				rCarScheme.setEnabledFlag((byte)0);
				rCarSchemeDaoImpl.save(rCarScheme);
			}else{
				rCarScheme.setPlanId(planId.intValue());
				rCarScheme.setBpId(bpId.intValue());
				rCarScheme.setEnabledFlag((byte)0);
				rCarSchemeDaoImpl.update(rCarScheme);
			}
			
		} catch (Exception e){
			logger.error("数据处理异常:"+e.getMessage(), e);
			throw new WXSSException("FAILED", "数据处理异常");
		}
	}

}
