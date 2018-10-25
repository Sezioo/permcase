package com.sezioo.permission.util;

import org.apache.commons.lang3.StringUtils;

public class LevelUtil {
	public static String SEPERATOR = ".";
	public static String ROOT = "0";
	public static String caculateLevel(String parentLevel,Integer parentId) {
		if(StringUtils.isBlank(parentLevel)) {
			return ROOT;
		}
		return StringUtils.join(parentLevel,SEPERATOR,parentId);
	}
}
