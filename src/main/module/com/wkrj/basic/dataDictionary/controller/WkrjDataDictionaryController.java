package com.wkrj.basic.dataDictionary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wkrj.basic.dataDictionary.bean.WkrjDataDictionary;
import com.wkrj.basic.dataDictionary.service.WkrjDataDictionaryService;

import wkrjsystem.utils.AjaxJson;

@Controller
@RequestMapping("wkrjsystem/wkrjDataDictionary")
public class WkrjDataDictionaryController {
	
	@Autowired
	private WkrjDataDictionaryService dataDictionaryService;
	
	
	@RequestMapping("getDataDictionary")
	@ResponseBody
	public Object getDataDictionary(String parentId){
		//return dataDictionaryService.getDataDictionary("-1");
		if(null==parentId||"".equals(parentId)){
			parentId="-1";
		}
		List<Map<String, Object>>  list = dataDictionaryService.getDataDictionary(parentId);
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("Rows", list);
		return map;
	}
	
	
	
	@RequestMapping("getDataDictionaryTree")
	@ResponseBody
	public Object getDataDictionaryTree(String parentId){
		if(null==parentId||"".equals(parentId)){
			parentId="-1";
		}
		List<Map<String, Object>>  list = dataDictionaryService.getDataDictionaryTree(parentId);
		return list;
	}	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("add")
	@ResponseBody
	public AjaxJson addDataDictionary(WkrjDataDictionary data){
		
		AjaxJson json = new AjaxJson();		
		if(data.getTypeparentid()==null || "".equals(data.getTypeparentid())){
			data.setTypeparentid("-1");
		}
		//查询名称是否存在
		@SuppressWarnings("deprecation")
                int count=jdbcTemplate.queryForInt("SELECT count(*) from wkrj_sys_data_dictionary where typecode='"+data.getTypecode()+"' and typeparentid='"+data.getTypeparentid()+"'");
		if(count>0){
			json.setMsg("编码已存在！");
			json.setSuccess(false);
			return json;
		}		
		if(dataDictionaryService.addDataDictionary(data)){
			json.setSuccess(true);
			json.setMsg("保存成功");
		}
		
		return json;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public AjaxJson updateDataDictionary(WkrjDataDictionary data){		
		AjaxJson json = new AjaxJson();		
		//查询名称是否存在
		if(data.getTypeparentid()==null || "".equals(data.getTypeparentid())){
			data.setTypeparentid("-1");
		}
		@SuppressWarnings("deprecation")
                int count=jdbcTemplate.queryForInt("SELECT count(*) from wkrj_sys_data_dictionary where typecode='"+data.getTypecode()+"' and typeparentid='"+data.getTypeparentid()+"' and id!='"+data.getId()+"'");
		if(count>0){
			json.setMsg("编码已存在！");
			json.setSuccess(false);
			return json;
		}		
		
		if(dataDictionaryService.updateDataDictionary(data)){
			json.setSuccess(true);
			json.setMsg("保存成功");
		}
		
		return json;
	}
	
	@RequestMapping("del")
	@ResponseBody
	public AjaxJson del(String id){
		
		AjaxJson json = new AjaxJson();
		
		//首先判断是否还有孩子
		if(dataDictionaryService.getDataDictionaryChildCount(id)<=0){
			if(dataDictionaryService.delDataDictionary(id)){
				json.setSuccess(true);
				json.setMsg("删除成功");
				return json;
			}
		}else{
			json.setMsg("请先删除子节点");
			json.setSuccess(false);
			return json;
		}
		return json;
	}
	
	//根据字典编码，获取combotree数据
	@RequestMapping("getDataDictionaryByCode")
	@ResponseBody
	public Object getDataDictionaryByCode(HttpServletRequest request,String typeCode){
		//根据typeCode 查询出id
		WkrjDataDictionary dataDictionary=dataDictionaryService.getDataDictionaryByTypeCode(typeCode);
		if(dataDictionary!=null){
			return dataDictionaryService.getDictionaryTree(dataDictionary.getId());			
		}
		return null;
	}
	
}
