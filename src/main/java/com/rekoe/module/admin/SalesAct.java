package com.rekoe.module.admin;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Times;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
@At("/admin")
@RequiresAuthentication
public class SalesAct {

	public enum Type {
		year, month;
	}

	@At("/sales/view")
	@Ok("fm:template.admin.sales.view")
	public Type view(@Param("type") Type type, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate, HttpServletRequest req) {
		if (type == null)
			type = Type.month;
		if (beginDate == null)
			beginDate = DateUtils.addMonths(Times.now(), -11);
		if (endDate == null)
			endDate = Times.now();
		Map<String, BigDecimal> localLinkedHashMap1 = new LinkedHashMap<String, BigDecimal>();
		Map<String, Integer> localLinkedHashMap2 = new LinkedHashMap<String, Integer>();
		Calendar localCalendar1 = DateUtils.toCalendar(beginDate);
		Calendar localCalendar2 = DateUtils.toCalendar(endDate);
		int i = localCalendar1.get(1);
		int j = localCalendar2.get(1);
		int k = localCalendar1.get(2);
		int m = localCalendar2.get(2);
		for (int n = i; n <= j; n++) {
			if (localLinkedHashMap1.size() >= 12)
				break;
			Calendar localCalendar3 = Calendar.getInstance();
			localCalendar3.set(1, n);
			Date localDate2;
			Integer localObject2;
			if (type == Type.year) {
				localCalendar3.set(2, localCalendar3.getActualMinimum(2));
				localCalendar3.set(5, localCalendar3.getActualMinimum(5));
				localCalendar3.set(11, localCalendar3.getActualMinimum(11));
				localCalendar3.set(12, localCalendar3.getActualMinimum(12));
				localCalendar3.set(13, localCalendar3.getActualMinimum(13));
				localCalendar3.set(2, localCalendar3.getActualMaximum(2));
				localCalendar3.set(5, localCalendar3.getActualMaximum(5));
				localCalendar3.set(11, localCalendar3.getActualMaximum(11));
				localCalendar3.set(12, localCalendar3.getActualMaximum(12));
				localCalendar3.set(13, localCalendar3.getActualMaximum(13));
				localDate2 = localCalendar3.getTime();
				localObject2 = 30;
				String time = localDate2.getTime() + "";
				localLinkedHashMap1.put(time, BigDecimal.ZERO);
				localLinkedHashMap2.put(time, Integer.valueOf(localObject2 != null ? ((Integer) localObject2).intValue() : 0));
			} else {
				for (int i1 = n == i ? k : localCalendar3.getActualMinimum(2); i1 <= (n == j ? m : localCalendar3.getActualMaximum(2)); i1++) {
					if (localLinkedHashMap1.size() >= 12)
						break;
					localCalendar3.set(2, i1);
					localCalendar3.set(5, localCalendar3.getActualMinimum(5));
					localCalendar3.set(11, localCalendar3.getActualMinimum(11));
					localCalendar3.set(12, localCalendar3.getActualMinimum(12));
					localCalendar3.set(13, localCalendar3.getActualMinimum(13));
					localDate2 = localCalendar3.getTime();
					localCalendar3.set(5, localCalendar3.getActualMaximum(5));
					localCalendar3.set(11, localCalendar3.getActualMaximum(11));
					localCalendar3.set(12, localCalendar3.getActualMaximum(12));
					localCalendar3.set(13, localCalendar3.getActualMaximum(13));
					localObject2 = new BigDecimal(20).intValue();// this.IIIllllI.getSalesAmount;
					Integer localInteger = new BigDecimal(30).intValue();// this.IIIllllI.getSalesVolume
					String time = localCalendar3.getTime().getTime() + "";
					localLinkedHashMap1.put(time, BigDecimal.ZERO);
					localLinkedHashMap2.put(time, Integer.valueOf(localInteger != null ? localInteger.intValue() : 0));
				}
			}
		}
		req.setAttribute("types", Type.values());
		req.setAttribute("type", type);
		req.setAttribute("beginDate", beginDate);
		req.setAttribute("endDate", endDate);
		req.setAttribute("salesAmountMap", localLinkedHashMap1);
		req.setAttribute("salesVolumeMap", localLinkedHashMap2);
		return type;
	}

	@At
	@Ok("fm:template.admin.sales.map")
	public Object map() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < 12; i++) {
			map.put(DateUtils.addMonths(Times.now(), -i).getTime() + "", i);
		}
		return map;
	}
}
