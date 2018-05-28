package com.wkrj.basic.dataDictionary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrj.basic.dataDictionary.bean.WkrjDataDictionary;
import com.wkrj.basic.dataDictionary.dao.WkrjDataDictionaryDao;

@Service("wkrjDataDictionaryService")
@Transactional
public class WkrjDataDictionaryServiceImpl implements WkrjDataDictionaryService {

	@Autowired
	private WkrjDataDictionaryDao dao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> getDataDictionary(String node) {
		List<Map<String, Object>> arrayList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listMaps = dao.getDataDictionaryTree(node);
		for (int i=0;i<listMaps.size();i++) {
			Map<String,Object> mapfirst = new HashMap<String,Object>();
			mapfirst.put("id",  listMaps.get(i).get("id"));
			mapfirst.put("typecode", listMaps.get(i).get("typecode"));
			mapfirst.put("typename", listMaps.get(i).get("typename"));
			mapfirst.put("typeparentid", listMaps.get(i).get("typeparentid"));
			mapfirst.put("notes", listMaps.get(i).get("notes"));
			mapfirst.put("text", listMaps.get(i).get("typename"));
			List<Map<String, Object>> childrenlist = getDataDictionary(listMaps.get(i).get("id").toString());
			if(childrenlist!=null && childrenlist.size()>0){
				mapfirst.put("children", childrenlist);
				mapfirst.put("isextend", false);//收缩			
			}
			arrayList.add(mapfirst);
		}
		return arrayList;
	}

	
	
	@Override
	public List<Map<String, Object>> getDataDictionaryTree(String parentId) {
		// TODO Auto-generated method stub
//		List<Map<String, Object>> arrayList = new ArrayList<Map<String,Object>>();
//		String sql="SELECT * FROM `wkrj_sys_data_dictionary` where id='"+parentId+"'";
//		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
//		if(list!=null){
//			Map<String,Object> mapfirst = new HashMap<String,Object>();
//			mapfirst.putAll(list.get(0));
//			if(getDataDictionaryChildCount(list.get(0).get("id")+"")>0){
//				mapfirst.put("children", getDataDictionary(list.get(0).get("id")+""));				
//			}
//			arrayList.add(mapfirst);
//		}
//		
		return getDataDictionary(parentId);
	}	
	
	
	@Override
	public boolean addDataDictionary(WkrjDataDictionary dd) {
		
		//dept.setDataDictionary_id(dept_id);
/*		String maxId="01";
		if(null==dd.getTypeparentid() || "".equals(dd.getTypeparentid())){
			dd.setTypeparentid("-1");
			//通过父id找到下面自己节点中最大的然后加1
			maxId = this.dao.getDataDictionaryChildMaxByPid("-1");
			
		}else{
			maxId = this.dao.getDataDictionaryChildMaxByPid(dd.getTypeparentid());
			if("00".equals(maxId)){
				maxId = dd.getTypeparentid()+maxId;
				dd.setId(addOne(maxId));
			}
		}
		//加上这句判断是为了修改时而加的
		if(null==dd.getId() || "".equals(dd.getId())){
			dd.setId(addOne(maxId));
		}
*/
		if(null==dd.getTypeparentid() || "".equals(dd.getTypeparentid())){
			dd.setTypeparentid("-1");
		}
		dd.setId(UUID.randomUUID()+"");
		return this.dao.addDataDictionary(dd);
	}
	
	@Override
	public boolean updateDataDictionary(WkrjDataDictionary dd) {
		
		//更新
		if(null==dd.getTypeparentid() || "".equals(dd.getTypeparentid())){
			dd.setTypeparentid("-1");
		}
		if(this.dao.updateDataDictionary(dd)){
			return true;
		}
		
		return false;
	}

	/**
	 * 在原有数字字符串的基础上加1
	 * @param testStr
	 * @return
	 */
	public String addOne(String testStr){
	    String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串
	    String numStr = strs[strs.length-1];//取出最后一组数字
	    if(numStr != null && numStr.length()>0){//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
	        int n = numStr.length();//取出字符串的长度
	        long num = (Long.parseLong(numStr)+1L);//将该数字加一
	        String added = String.valueOf(num);
	        n = Math.min(n, added.length());
	        //拼接字符串
	        return testStr.subSequence(0, testStr.length()-n)+added;
	    }else{
	        throw new NumberFormatException();
	    }
	}

	@Override
	public boolean delDataDictionary(String id) {
		return this.dao.delDataDictionary(id);
	}

	@Override
	public int getDataDictionaryChildCount(String parentId) {
		
		return this.dao.getDataDictionaryChildCount(parentId);
	}

	@Override
	public WkrjDataDictionary getDataDictionaryNameById(String deptId) {
		return this.dao.getDataDictionaryNameById(deptId);
	}

	@Override
	public List<Map<String, Object>> getDictionaryTree(String id) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> maps=this.dao.getChildrenDictionary(id);
		for(int i=0;i<maps.size();i++){
			Map<String,Object> mapfirst = new HashMap<String,Object>();
			mapfirst.put("id",  maps.get(i).get("id"));
			mapfirst.put("typecode", maps.get(i).get("typecode"));
			mapfirst.put("typename", maps.get(i).get("typename"));
			mapfirst.put("typeparentid", maps.get(i).get("typeparentid"));
			//展示成树状结构
			mapfirst.put("id", maps.get(i).get("id"));
			mapfirst.put("text", maps.get(i).get("typename"));
			mapfirst.put("children", getDictionaryTree(maps.get(i).get("id").toString()));
			list.add(mapfirst);
		}
		return list;
	}

	@Override
	public WkrjDataDictionary getDataDictionaryByTypeCode(String typeCode) {
		// TODO Auto-generated method stub`
		return dao.getDataDictionaryByTypeCode(typeCode);
	}



	@Override
	public List<Map<String, Object>> getValueByTypeCode(String parent_typecode,
			String typecode) {
		// TODO Auto-generated method stub
		String sql="SELECT a.* FROM `wkrj_sys_data_dictionary` as a LEFT JOIN wkrj_sys_data_dictionary as b on a.typeparentid=b.id "
				+ "where b.typecode='"+parent_typecode+"' and a.typecode='"+typecode+"'";
		return jdbcTemplate.queryForList(sql);
	}


	
}
