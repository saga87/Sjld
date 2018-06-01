package com.wkrj.headmasterreading.service;

import java.io.File;
import java.util.List;
import java.util.Map;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wkrjsystem.utils.Guid;
import wkrjsystem.utils.UtilsHelper;

import com.wkrj.headmasterreading.bean.MessageManage;
import com.wkrj.headmasterreading.bean.ReadCase;
import com.wkrj.headmasterreading.bean.ReadCaseFile;
import com.wkrj.headmasterreading.dao.InformExtractDao;
import com.wkrj.headmasterreading.dao.MessageListDao;

@Service("messageListService")
@Transactional
public class MessageListServiceImpl implements MessageListService {
	@Autowired
	private MessageListDao dao;
	@Autowired
	private InformExtractDao informExtractDao;

	//路径
	private String realPath= getClass().getClassLoader().getResource("/").getPath()+ "../../upload/readcase/";
	
	@Override
	public List<Map<String, Object>> getMessageList(int offset, int pagesize) {
		return dao.getMessageList(offset, pagesize);
	}

	@Override
	public List<Map<String, Object>> getCaseReadFile(String must_read_id) {
		return dao.getCaseReadFile(must_read_id);
	}

	@Override
	public boolean deleteCaseRead(String must_read_id) {
		try {
			if(deleteLocalFile(must_read_id)&&dao.deleteReadCase(must_read_id)){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean deleteLocalFile(String must_read_id){
		List<ReadCaseFile> fileList = dao.getCrFile(must_read_id);
		
		for (int i = 0; i < fileList.size(); i++) {
			//删除本地附件
			String sPath = this.realPath+fileList.get(i).getFile_xname();
			File file = new File(sPath);  
			   // 路径为文件且不为空则进行删除  
			if (file.isFile() && file.exists()) {  
				file.delete();  
			}
		}
		
		if(fileList.size()>0){
			if(dao.deleteReadCaseFile(null, must_read_id)){
				return true;
			}else{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean updateCaseRead(ReadCase readCase, ReadCaseFile file) {
		try {
			if(file.getFile_yname()!=null&&!"".equals(file.getFile_yname())){
				//先删除数据之前的附件信息重新添加
				dao.deleteReadCaseFile(null, readCase.getMust_read_id());
				
				String yfilenames = file.getFile_yname();
				String xfilenames = file.getFile_xname();
				for (int i = 0, len = yfilenames.split(",").length; i < len; i++) {
					file.setFile_yname(yfilenames.split(",")[i]);
					file.setFile_xname(xfilenames.split(",")[i]);
					file.setFile_id(Guid.getGuid());
					file.setMust_read_id(readCase.getMust_read_id());
					file.setFile_inputtime(UtilsHelper.getDateFormatTime());
					if (!informExtractDao.addReadCaseFile(file)) {
						return false;
					}
				}
			}
			dao.updateReadCase(readCase);
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean addMessage(MessageManage mm) {
		
		return dao.addMessage(mm);
	}
	
}
