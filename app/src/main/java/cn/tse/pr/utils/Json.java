package cn.tse.pr.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.qiniu.android.utils.StringMap;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by xieye on 2017/6/5.
 */

public final class Json {
    private Json() {
    }

    public static String encode(StringMap map) {
        return new Gson().toJson(map.map());
    }

    public static String encode(Object obj) {
        return new GsonBuilder().serializeNulls().create().toJson(obj);
    }

    public static <T> T decode(String json, Class<T> classOfT) {
        return new Gson().fromJson(json, classOfT);
    }

    public static StringMap decode(String json) {
        // CHECKSTYLE:OFF
        Type t = new TypeToken<Map<String, Object>>() {
        }.getType();
        // CHECKSTYLE:ON
        Map<String, Object> x = new Gson().fromJson(json, t);
        return new StringMap(x);
    }
}