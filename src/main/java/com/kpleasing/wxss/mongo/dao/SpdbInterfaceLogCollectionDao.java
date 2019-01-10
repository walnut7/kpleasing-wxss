package com.kpleasing.wxss.mongo.dao;

import java.util.List;

import com.kpleasing.wxss.mongo.collections.SpdbInterfaceLogCollection;

public interface SpdbInterfaceLogCollectionDao {
	
	
	/**
	 * 
	 * @param spdbInterfaceLog
	 * @param operType
	 * @param resultCode 
	 */
	public void insertSpdbInterfaceLogCollection(SpdbInterfaceLogCollection spdbInterfaceLog, String operType, String resultCode);

	
	
	/**
	 * 
	 * @param spdbInterfaceLogCollection
	 * @return 
	 */
	public List<SpdbInterfaceLogCollection> searchSpdbOpertionLog(SpdbInterfaceLogCollection spdbInterfaceLogCollection);
	

}
