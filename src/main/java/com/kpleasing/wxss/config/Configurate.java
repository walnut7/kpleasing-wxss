package com.kpleasing.wxss.config;

import java.io.Serializable;
import java.util.List;

import com.kpleasing.wxss.entity.SecurityKey;

public class Configurate implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -1188177717011179188L;
	public String ESB_SERVER_URL = null;
	public String ESB_DES_KEY = null;
	public String ESB_DES_IV = null;
	public String DES_KEY = null;
	public String DES_IV = null;
	public String ESB_SIGN_KEY = null;
	public String ESB_SEC_CODE = null;
	public String ESB_SEC_VALUE = null;
	public String MSG_MODEL = null;
	public String IMG_PATH = null;
	public String IMG_URL = null;
	public int DEBUG_LEVEL = 0;
	public int VERIF_INTERVAL = 0;
	public String XMC_PERSON_URL = null;
	public String XMC_LOGIN_KEY = null;
	public String FV_IMG_URL = null;
	public String WXSS_LOGIN_KEY = null;
	public String AUTO_LOGIN_TIMEOUT = null;
	public String SPDB_SECRET = null;
	public String SPDB_CLIENT_ID = null;
	public String SPDB_BIZ_SCENE1 = null;
	public String SPDB_BIZ_SCENE2 = null;
	public String SPDB_INST_NUM = null;
	public String SPDB_GET_AUTHR_CODE_URL = null;
	public String SPDB_MEDIA_UPLOAD_URL = null;
	public String SPDB_OPEN_ACCOUNT_URL = null;
	public String SPDB_QUERY_ACCOUNT_URL = null;
	public String LEASING_CONTACT_URL = null;
	public String LEASING_CONTACT_KEY = null;
	
	private List<SecurityKey> securityKeyList;
    
	public List<SecurityKey> getSecurityKeyList() {
		return securityKeyList;
	}

	public void setSecurityKeyList(List<SecurityKey> securityKeyList) {
		this.securityKeyList = securityKeyList;
	}
	
	/**
	 * 根据apiCode确认配置
	 * @param apiCode
	 * @return
	 */
	public SecurityKey getSecurityKey(String apiCode) {
		List<SecurityKey> securKeyList = this.getSecurityKeyList();
		if(securKeyList != null) {
			for(SecurityKey securKey : securKeyList) {
				if(apiCode.equals(securKey.getApiName())) {
					return securKey; 
				}
			}
		}
		return null;
	}
}
