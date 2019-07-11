package com.yeah.java.employment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;

//9--12
//14--18
//20--23
public class Workorder {

	private List<WorkOrderVo> workOrders;
	
	public Workorder() {
		workOrders = new ArrayList<>();
		workOrders.add(new WorkOrderVo("A", "2018-11-11 09:20:00", 12));
		//workOrders.add(new WorkOrderVo("B", "2018-11-12 09:20:00", 12));
		//workOrders.add(new WorkOrderVo("C", "2018-11-11 09:20:00", 12));
		//workOrders.add(new WorkOrderVo("D", "2018-11-11 09:20:00", 12));
		//workOrders.add(new WorkOrderVo("E", "2018-11-11 09:20:00", 12));
	}
	/**
	 * 
	 * @Description: 将字符串格式的日期转为Data类实例
	 * @param: @param dateStr
	 * @return: Date      
	 */
	public Date string2Date(String dateStr) {
		if (Objects.isNull(dateStr) || "".equals(dateStr)) {
			throw new RuntimeException("参数为空");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch(Exception ex) {
			throw new RuntimeException("解析日期" + dateStr + "失败");
		}
		
		return date;
	}
	
	/**
	 * 
	 * @Description: 获取日期所属的星期 
	 * @param: @param date
	 * @param: @return      
	 * @return: int      
	 */
	public int dayOfWeek(Date date) {
		if (date == null) {
			throw new RuntimeException("参数为空");
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 0, 1, ... 5, 6
		// SUN, MON, ... FRI, SAT
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		
		out.println(day);
		return day;
	}
	
	
	public String workOrderEndTime() {
		String time = null;
		
		return time;
	}
	
	public static void main(String[] args) {
		Workorder workOrder = new Workorder();
		Date date = workOrder.string2Date("2019-07-10 09:20:00");
		int dayOfWeek = workOrder.dayOfWeek(date);
		
		
		
	}
}
