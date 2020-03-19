package com.shell.mvppro.uitls;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class ProtocolParserUtils {
	private static final String nullString = "";

	public static String getJsonStr(JSONObject jsonObj, String name) {
		return getJsonStr(jsonObj, name, null);
	}

	public static int getJsonInt(JSONObject jsonObj, String name) {
		return getJsonInt(jsonObj, name, 0);
	}
	public static int getMyJsonInt(JSONObject jsonObj, String name) {
		return getJsonInt(jsonObj, name, -1);
	}

	public static long getJsonLong(JSONObject jsonObj, String name) {
		return getJsonLong(jsonObj, name, 0);
	}
	
	public static boolean getJsonBool(JSONObject jsonObj, String name) {
		return getJsonBool(jsonObj, name, false);
	}
	
	public static String getJsonStr(JSONObject jsonObj, String name, String defaultValue) {
		if (jsonObj == null){
			return  defaultValue;
		}
		try {
			if (jsonObj.containsKey(name)) {
				if (jsonObj.getString(name).equals(nullString)) {
					return "";
				} else {
					return jsonObj.getString(name);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
		return defaultValue;
	}

	public static int getJsonInt(JSONObject jsonObj, String name, int defaultValue) {
		if (jsonObj == null){
			return defaultValue;
		}
		try {
			if (jsonObj.containsKey(name)) {
				return jsonObj.getInteger(name);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return defaultValue;
	}

	public static long getJsonLong(JSONObject jsonObj, String name, long defaultValue) {
		if (jsonObj == null){
			return defaultValue;
		}
		try {
			if (jsonObj.containsKey(name)) {
				return jsonObj.getLong(name);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return defaultValue;
	}

	public static JSONObject getJsonObj(JSONObject jsonArr, String name) {
		if (jsonArr == null){
			return null;
		}
		try {
			if (jsonArr.containsKey(name)) {
				return jsonArr.getJSONObject(name);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	public static boolean getJsonBool(JSONObject jsonObj, String name, boolean defaultValue) {
		if (jsonObj == null){
			return defaultValue;
		}
		try {
			if (jsonObj.containsKey(name)) {
				return jsonObj.getBoolean(name);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return defaultValue;
	}
	
	public static JSONArray getArray(JSONObject o, String key) {
		if(o == null || !o.containsKey(key))
			return null;
		
		try {
			return o.getJSONArray(key);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getArrayString(JSONArray array, int index) {
		if (array == null){
			return "";
		}
		try {
			return array.getString(index);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return "";
	}
	
}
