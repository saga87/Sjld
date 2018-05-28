package com.wkrj.basic.dataDictionary.service;

import java.util.List;
import java.util.Map;

import com.wkrj.basic.dataDictionary.bean.WkrjDataDictionary;

public interface WkrjDataDictionaryService {
    
	
	public List<Map<String,Object>> getDataDictionary(String parent_id);
	public List<Map<String,Object>> getDataDictionaryTree(String parentId);
	public boolean addDataDictionary(WkrjDataDictionary dept);
	
	public boolean updateDataDictionary(WkrjDataDictionary dd);
	
	public boolean delDataDictionary(String id);
	
	/**
	 * 判断是否还有子节点，如果大于0说明有
	 * @param parentId
	 * @return
	 */
	public int getDataDictionaryChildCount(String parentId);
	
	public WkrjDataDictionary getDataDictionaryNameById(String deptId);
	
	public List<Map<String, Object>> getDictionaryTree(String typeCode);
	
	public WkrjDataDictionary getDataDictionaryByTypeCode(String typeCode);
	
	
	
	/**
	 * 获取信息
	 * @param parent_typecode
	 * @param typecode
	 * @return
	 */
	public List<Map<String,Object>> getValueByTypeCode(String parent_typecode,String typecode);
	
}
