package com.windf.core.util;

public class NumberUtil {
	public static boolean isNumber(Object obj) {
		boolean result = false;
		if (obj != null) {
			if (ClassTypeUtil.isNumber(obj.getClass())) {
				result = true;
			} else {
				String str = obj.toString();
				if (RegularUtil.match(str, RegularUtil.NUMBER_VALUE)) {
					result = true;
				}
			}
		}
		
		return result;
	}
	
    public static int getRandom(int count) {
    	return (int) Math.round(Math.random() * (count));
    }
}
