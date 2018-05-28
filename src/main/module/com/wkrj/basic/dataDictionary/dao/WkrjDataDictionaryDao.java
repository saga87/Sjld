package com.wkrj.basic.dataDictionary.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wkrj.basic.dataDictionary.bean.WkrjDataDictionary;

public interface WkrjDataDictionaryDao {
	
	public List<Map<String, Object>> getDataDictionaryTree(@Param("parentId")String parent_id);
	
	public List<WkrjDataDictionary> getDataDictionary(String parent_id);
	
	/**
	 * 判断是否还有子节点，如果大于0说明有
	 * @param parentId
	 * @return
	 */
	public int getDataDictionaryChildCount(String parentId);
	
	public String getDataDictionaryChildMaxByPid(String parentId);
	
	public boolean addDataDictionary(WkrjDataDictionary dd);
	
	public boolean updateDataDictionary(WkrjDataDictionary dd);
	
	public boolean delDataDictionary(String id);
	
	public WkrjDataDictionary getDataDictionaryNameById(String id);
	public List<Map<String,Object>> getChildrenDictionary(@Param("id") String id);
	
	public WkrjDataDictionary getDataDictionaryByTypeCode(@Param("typeCode") String typeCode);
	
	public WkrjDataDictionary getDataDictionaryByName(String typeName);

	
}
