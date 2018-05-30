package com.wkrj.headmasterreading.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wkrjsystem.utils.AjaxJson;
import wkrjsystem.utils.UtilsHelper;

import com.wkrj.headmasterreading.bean.ReadCase;
import com.wkrj.headmasterreading.bean.ReadCaseFile;
import com.wkrj.headmasterreading.service.MessageListService;
@Controller
@RequestMapping("messageList")
public class MessageListController {
	@Autowired
	private MessageListService service;
	
	@RequestMapping("getMessageList")
	@ResponseBody
	public Object getMessageList(int pagesize, int page){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = service.getMessageList(offset,pagesize);
		return UtilsHelper.returnMap(list, list.size());
	}
	
	@RequestMapping("getCaseReadFile")
	@ResponseBody
	public Object getCaseReadFile(String must_read_id){
		List<Map<String,Object>> list = service.getCaseReadFile(must_read_id);
		return UtilsHelper.returnMap(list, list.size());
	}
	
	
	/**
	 * 删除案例
	 * @param books_id
	 * @return
	 */
	@RequestMapping("deleteCaseRead")
	@ResponseBody
	public Object deleteCaseRead(String must_read_id){
		AjaxJson json = new AjaxJson();
		if (service.deleteCaseRead(must_read_id)) {
			json.setSuccess(true);
			json.setMsg("删除成功");
		}
		return json;
	}
	
	@RequestMapping("updateCaseRead")
	@ResponseBody
	public Object updateCaseRead(ReadCase readCase){
		AjaxJson json = new AjaxJson();
		ReadCaseFile file = new ReadCaseFile();
		if(readCase.getFile_yname()!=null&&!"".equals(readCase.getFile_yname())){
			file.setFile_yname(readCase.getFile_yname());
			file.setFile_xname(readCase.getFile_xname());
		}
		if (service.updateCaseRead(readCase,file)) {
			json.setSuccess(true);
			json.setMsg("更新成功");
		}
		return json;
	}
	
	
}
