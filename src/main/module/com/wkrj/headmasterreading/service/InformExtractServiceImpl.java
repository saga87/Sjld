package com.wkrj.headmasterreading.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import wkrjsystem.utils.Guid;

import com.wkrj.headmasterreading.bean.ReadCase;
import com.wkrj.headmasterreading.bean.ReadCaseFile;
import com.wkrj.headmasterreading.dao.InformExtractDao;

@Service("informExtractService")
@Transactional
public class InformExtractServiceImpl implements InformExtractService {
	
	@Autowired
	private InformExtractDao dao;
	
	@Override
	public List<Map<String, Object>> getFinishedEvent(int offset, int pagesize,
			String event_no, String event_title, String event_content,
			String business_type) {
		return dao.getFinishedEvent(offset, pagesize, event_no, event_title, event_content, business_type);
	}

	@Override
	public boolean addReadCase(ReadCase readCase, ReadCaseFile file) {
		try{
			if(readCase.getMust_read_id()!=null
					&&!"".equals(readCase.getMust_read_id())){
				
			}else{
				String must_read_id = Guid.getGuid();
				readCase.setMust_read_id(must_read_id);
			}
			if(file.getFile_yname()!=null&&!"".equals(file.getFile_yname())){
				String yfilenames = file.getFile_yname();
				String xfilenames = file.getFile_xname();
				for (int i = 0, len = yfilenames.split(",").length; i < len; i++) {
					file.setFile_yname(yfilenames.split(",")[i]);
					file.setFile_xname(xfilenames.split(",")[i]);
					file.setFile_id(Guid.getGuid());
					file.setMust_read_id(readCase.getMust_read_id());
					if (!dao.addReadCaseFile(file)) {
						return false;
					}
				}
			}
			dao.addReadCase(readCase);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public String uploadPic(MultipartFile readcase_icons,
			HttpServletRequest request, String foledArrress) {

		
		
		if(null==foledArrress || "".equals(foledArrress)){
			foledArrress="upload/readcase/";
		}
		
		String realPath = request.getSession().getServletContext().getRealPath("/")+foledArrress;
		
		String yFileName = readcase_icons.getOriginalFilename();//原文件名
		
		long timeMillis = System.currentTimeMillis();//获取时间戳 这种速度更快一些
		String extra = "wkrj";//由于样式不能是数字所以加上wkrj来区分
		
		String xFileName = extra+timeMillis+extra+yFileName.substring(yFileName.lastIndexOf("."), yFileName.length());//现文件名
		
		try {
			org.apache.commons.io.FileUtils.copyInputStreamToFile(readcase_icons.getInputStream(), new File(realPath,xFileName));
		} catch (Exception e) {
//			System.err.println("上传附件出错了");
		}
		return "{\"success\":\"true\",\"filename\":\""+xFileName+"\",\"yFileName\":\""+yFileName+"\"}";
	}

}
