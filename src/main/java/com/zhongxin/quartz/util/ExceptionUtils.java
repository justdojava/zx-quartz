/** 
 * Copyright: Copyright (c)2014
 * Company: 支付通(ICardPay) 
 */
package com.zhongxin.quartz.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author：eddysheng
 * @since：2014-3-24 下午12:08:52
 * @version:
 */
public class ExceptionUtils {

	public static String getStackTrace(Throwable e) {
		String estr = null;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			e.printStackTrace(pw);
			estr = sw.toString();
		} finally {
			pw.close();
		}
		return estr;
	}
}
