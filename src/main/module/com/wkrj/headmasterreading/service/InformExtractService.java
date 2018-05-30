package com.wkrj.headmasterreading.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.wkrj.headmasterreading.bean.ReadCase;
import com.wkrj.headmasterreading.bean.ReadCaseFile;



public interface InformExtractService {
	List<Map<String,Object>> getFinishedEvent(int offset,int pagesize,String event_no,String event_title,
			String event_content,String business_type);
	boolean addReadCase(ReadCase readCase,ReadCaseFile file);
	
	public String uploadPic(MultipartFile readcase_icons,HttpServletRequest request,String foledArrress);
}
