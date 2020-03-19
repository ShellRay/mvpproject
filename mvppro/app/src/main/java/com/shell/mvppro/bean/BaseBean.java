package com.shell.mvppro.bean;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shell.mvppro.uitls.ProtocolParserUtils;

import java.io.Serializable;

/**
 * 所有http回包的基类
 */
public class BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int state;
    private String message;
    private Object tag;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return state == 0;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void specialParse(String content) {

    }

    /**当请求接口失败时返回的json*/
    public void specialErrorParse(String content) {

    }

    protected String getString(JSONObject o, String key) {
        return ProtocolParserUtils.getJsonStr(o, key, "");
    }

    protected int getInt(JSONObject o, String key) {
        return ProtocolParserUtils.getJsonInt(o, key);
    }
    protected int getMyInt(JSONObject o, String key) {
        return ProtocolParserUtils.getMyJsonInt(o, key);
    }

    protected long getLong(JSONObject o, String key) {
        return ProtocolParserUtils.getJsonLong(o, key);
    }

    protected int getInt(JSONObject o, String key, int defaultValue) {
        return ProtocolParserUtils.getJsonInt(o, key, defaultValue);
    }

    protected boolean getBoolean(JSONObject o, String key) {
        return ProtocolParserUtils.getJsonBool(o, key);
    }

    protected JSONArray getArray(JSONObject o, String key) {
        return ProtocolParserUtils.getArray(o, key);
    }

    protected String getArrayString(JSONArray array, int index) {
        return ProtocolParserUtils.getArrayString(array, index);
    }

    protected JSONObject getJSONObject(JSONObject jsonObject, String key) {
        if(jsonObject == null)
            return null;

        try {
            if (jsonObject.containsKey(key)) {
                return jsonObject.getJSONObject(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected JSONObject getJSONObject(JSONArray jsonArray,int index){
        if(jsonArray == null)
            return null;

        try{
            return jsonArray.getJSONObject(index);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public  double getDouble(JSONObject contentJson, String key){

        double result = 0.0d;
        if(contentJson.containsKey(key)){
            try {
                result = contentJson.getDouble(key);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  result;
    }
}
