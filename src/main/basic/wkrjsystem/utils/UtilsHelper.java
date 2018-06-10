package wkrjsystem.utils;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class UtilsHelper {
	
	/**
	 * 时间格式化
	 */
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

	public static Map<String, Object> returnMap(List<?> list, long size) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", list);
		map.put("Total", size);

		return map;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getDateFormatTime(){
		return df.format(new Date());
	}
	
	/**
         * 获取当前日期
         * @return
         */
        public static String getDateFormatDate(){
                return df2.format(new Date());
        }
	
	/**
         * 获取x个月前的日期
         * @param date
         * @return
         */
        public static String getBeforeDate(){
            Calendar c = Calendar.getInstance();
            //过去一月
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            Date m = c.getTime();
            String mon = df2.format(m);
            return mon;
        }

	/*
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public final static String getIpAddress(HttpServletRequest request)
			throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

}
