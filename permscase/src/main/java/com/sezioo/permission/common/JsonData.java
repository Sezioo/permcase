package com.sezioo.permission.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonData {
	private boolean ret;
	private String msg;
	private Object data;
	
	public JsonData(boolean ret) {
		this.ret = ret;
	}
	
	public static JsonData success(String msg,Object data) {
		JsonData jsonData = new JsonData(true);
		jsonData.msg = msg;
		jsonData.data = data;
		return jsonData;
	}
	
	public static JsonData success(Object data) {
		JsonData jsonData = new JsonData(true);
		jsonData.data = data;
		return jsonData;
	}
	
	public static JsonData success() {
		return new JsonData(true);
	}
	
	public static JsonData fail(String msg) {
		JsonData jsonData = new JsonData(false);
		jsonData.msg = msg;
		return jsonData;
	}
	
	public static JsonData fail() {
		return new JsonData(false);
	}
	
	public Map<String,Object> toMap(){
		Map<String,Object> mapData = new HashMap<>();
		mapData.put("ret", ret);
		mapData.put("msg", msg);
		mapData.put("data", data);
		return mapData;
	}
}
