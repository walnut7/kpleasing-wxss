package com.kpleasing.wxss.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.dao.CityDao;
import com.kpleasing.wxss.dao.ConfigDao;
import com.kpleasing.wxss.dao.DictConfigDao;
import com.kpleasing.wxss.dao.ProvinceDao;
import com.kpleasing.wxss.dao.SecurityKeyDao;
import com.kpleasing.wxss.entity.City;
import com.kpleasing.wxss.entity.Config;
import com.kpleasing.wxss.entity.DictConfig;
import com.kpleasing.wxss.entity.Province;
import com.kpleasing.wxss.web.service.ConfigService;

/**
 * 
 * @author howard.huang
 *
 */
@Service("ConfigService")
@Transactional
public class ConfigServiceImpl implements ConfigService {
	
	private static final Logger logger = Logger.getLogger(ConfigServiceImpl.class);
	private Configurate config = null;
	
	@Autowired
	private ConfigDao configDao;
	
	@Autowired
	private SecurityKeyDao securKeyEao;
	
	@Autowired
	private DictConfigDao dictConfigDao;
	
	@Autowired
	private ProvinceDao provinceDao;
	
	@Autowired
	private CityDao cityDao;
	
	
	@Override
	public List<DictConfig> getDictByPId(Integer pId) {
		return dictConfigDao.findDictByParentNodeCode(pId);
	}

	@Override
	public List<Province> getProvinceList() {
		return provinceDao.findAll();
	}

	@Override
	public List<City> getCities(String provinceCode) {
		return cityDao.findByProperty("provinceCode", provinceCode);
	}

	@Override
	public Configurate getConfig() {
		if(null == config) {
			logger.info("初始化配置参数.");
			config = new Configurate();
			List<Config> list = configDao.findAll();
			Map<String, String> map = new HashMap<String, String>();
			for (Config conf : list) {
				map.put(conf.getCode(), conf.getValue());
			}
			
			config.ESB_SERVER_URL = map.get("ESB_SERVER_URL");
			config.DES_KEY = map.get("DES_KEY");
			config.DES_IV = map.get("DES_IV");
			config.ESB_DES_KEY = map.get("ESB_DES_KEY");
			config.ESB_DES_IV = map.get("ESB_DES_IV");
			config.ESB_SIGN_KEY = map.get("ESB_SIGN_KEY");
			config.ESB_SEC_CODE = map.get("ESB_SEC_CODE");
			config.ESB_SEC_VALUE = map.get("ESB_SEC_VALUE");
			config.MSG_MODEL = map.get("MSG_MODEL");
			config.IMG_PATH = map.get("IMG_PATH");
			config.IMG_URL = map.get("IMG_URL");
			config.DEBUG_LEVEL = Integer.valueOf(map.get("DEBUG_LEVEL"));
			config.VERIF_INTERVAL = Integer.valueOf(map.get("VERIF_INTERVAL"));
			config.XMC_PERSON_URL = map.get("XMC_PERSON_URL");
			config.XMC_LOGIN_KEY = map.get("XMC_LOGIN_KEY");
			config.FV_IMG_URL = map.get("FV_IMG_URL");
			config.WXSS_LOGIN_KEY = map.get("WXSS_LOGIN_KEY");
			config.AUTO_LOGIN_TIMEOUT = map.get("AUTO_LOGIN_TIMEOUT");
			config.SPDB_SECRET = map.get("SPDB_SECRET");
			config.SPDB_CLIENT_ID = map.get("SPDB_CLIENT_ID");
			config.SPDB_BIZ_SCENE1 = map.get("SPDB_BIZ_SCENE1");
			config.SPDB_BIZ_SCENE2 = map.get("SPDB_BIZ_SCENE2");
			config.SPDB_INST_NUM = map.get("SPDB_INST_NUM");
			config.SPDB_GET_AUTHR_CODE_URL = map.get("SPDB_GET_AUTHR_CODE_URL");
			config.SPDB_MEDIA_UPLOAD_URL = map.get("SPDB_MEDIA_UPLOAD_URL");
			config.SPDB_OPEN_ACCOUNT_URL = map.get("SPDB_OPEN_ACCOUNT_URL");
			config.SPDB_QUERY_ACCOUNT_URL = map.get("SPDB_QUERY_ACCOUNT_URL");
			config.LEASING_CONTACT_URL = map.get("LEASING_CONTACT_URL");
			config.LEASING_CONTACT_KEY = map.get("LEASING_CONTACT_KEY");
			
			config.setSecurityKeyList(securKeyEao.findAll());
		}
		return config;
	}

	@Override
	public String getDictByCode(String code) {
		DictConfig dictConf = dictConfigDao.findDictByNodeCode(code);
		return dictConf.getNodeValue();
	}
}