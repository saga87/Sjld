package com.wkrj.headmasterreading.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wkrj.headmasterreading.bean.ReadCase;
import com.wkrj.headmasterreading.bean.ReadCaseFile;
import com.wkrj.headmasterreading.service.InformExtractService;

import wkrjsystem.utils.AjaxJson;
import wkrjsystem.utils.UtilsHelper;

/**
 * 信息提取
 * @author wlp
 * @Description:
 * @date 2018年5月30日 上午9:19:11
 */

@Controller
@RequestMapping("informExtract")
public class InformExtractController {
	
	@Autowired
	private InformExtractService service;
	
	/**
	 * 获取已完成事项列表
	 * @param pagesize 数量
	 * @param page 页
	 * @param event_no 工单号
	 * @param event_title 工单标题
	 * @param event_content 工单内容
	 * @param business_type 工单分类
	 * @return
	 */
	@RequestMapping("getFinishedEvent")
	@ResponseBody
	public Object getFinishedEvent(int pagesize, int page,String event_no,String event_title,
			String event_content,String business_type){
		int offset = (page-1) * pagesize;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = service.getFinishedEvent(offset,pagesize,event_no,event_title,event_content,business_type);
		return UtilsHelper.returnMap(list, list.size());
	}
	
	@RequestMapping("uploadCase")
	@ResponseBody
	public Object uploadCase(HttpServletRequest request,ReadCase readCase){
		AjaxJson json = new AjaxJson();
		ReadCaseFile file = new ReadCaseFile();
		if(readCase.getFile_yname()!=null&&!"".equals(readCase.getFile_yname())){
			file.setFile_yname(readCase.getFile_yname());
			file.setFile_xname(readCase.getFile_xname());
		}
		if (service.addReadCase(readCase, file)) {
			json.setSuccess(true);
			json.setMsg("添加成功");
		}
		return json;
	}
	
	@RequestMapping("uploadFile")
	@ResponseBody
	public String uploadFile(HttpServletRequest request,MultipartFile readcase_file,
			String foledArrress){
		
		return service.uploadPic(readcase_file, request, foledArrress);
	}
	
	
}
