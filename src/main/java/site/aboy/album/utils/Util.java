package site.aboy.album.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Util {

    public static String FormatString(int code, String info){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("message", info);
        return json.toJSONString();
    }

    public static String FormJSONObject(int code, JSONObject object){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("data", object);
        return json.toJSONString();
    }

    public static String FormatJSONArray(int code, JSONArray array){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("data", array);
        return  json.toJSONString();
    }
}
