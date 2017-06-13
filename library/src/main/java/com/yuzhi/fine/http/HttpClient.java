package com.yuzhi.fine.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.R;
import com.yuzhi.fine.common.AppContext;
import com.yuzhi.fine.model.SearchParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by tiansj on 15/2/27.
 */
public class HttpClient {

    private static final int CONNECT_TIME_OUT = 10;
    private static final int WRITE_TIME_OUT = 60;
    private static final int READ_TIME_OUT = 60;
    private static final int MAX_REQUESTS_PER_HOST = 10;
    private static final String TAG = HttpClient.class.getSimpleName();
    private static final String UTF_8 = "UTF-8";
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain;");
    private static OkHttpClient client;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new LoggingInterceptor());
        client = builder.build();
        client.dispatcher().setMaxRequestsPerHost(MAX_REQUESTS_PER_HOST);
    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.i(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

    public static Response get(String url) {
        final Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public static Response getWithHeader(String url) {
        final Request.Builder requestBuild = new Request.Builder().url(url);
        requestBuild.addHeader("Cookie", "; __ysuid=1496998622986rkq; passport_sdk=Android1.3.3.1; cna=31DBEYBBsDoCAQ4XK1LU2w2n; __ayft=1496998623008; __aysid=1497255834985hDJ; __arpvid=1497255834992FVHxPg-1497255835707; __ayscnt=2; __aypstp=2; __ayspstp=1; isg=AtnZ9ECB5HZolbjiJKHRsb2p48ezZs0YgVSepvuOVYB_AvmUQ7bd6EcQVP6L; __ysuid=1496998622986rkq; passport_sdk=Android1.3.3.1; cna=31DBEYBBsDoCAQ4XK1LU2w2n; __ayft=1496998623008; __aysid=1497255834985hDJ; __arpvid=1497255834992FVHxPg-1497255835707; __ayscnt=2; __aypstp=2; __ayspstp=1; isg=AtnZ9ECB5HZolbjiJKHRsb2p48ezZs0YgVSepvuOVYB_AvmUQ7bd6EcQVP6L");
        requestBuild.addHeader("x-appkey", "23570660");
        requestBuild.addHeader("x-mini-wua", "HHnB_d71VNwDWY9TB6W8rIk8lRuZNxz1N3LJq4uKygF79GGUbcf0dLFMadeDEKz%2BVEdnPGQaZqvJ071pIKsxfT5KJ6YNZgbDl035qm5NGqX03vwo%3D");
        requestBuild.addHeader("x-c-traceid", "V3PPEzbapRcDACcQGwD4Riyp14972558711210008111006");
        requestBuild.addHeader("x-features", "27");
        requestBuild.addHeader("x-t", String.valueOf(System.currentTimeMillis()/1000));
        requestBuild.addHeader("x-pv", "5.1");
        requestBuild.addHeader("user-agent", "MTOPSDK%2F3.0.2-RC+%28Android%3B6.0%3BMeizu%3BPRO+6%29");
        requestBuild.addHeader("f-refer", "mtop");
        requestBuild.addHeader("x-ttid", "228200%40youku_android_6.7.1.3");
        requestBuild.addHeader("x-utdid", "V3PPEzbapRcDACcQGwD4Riyp");
        requestBuild.addHeader("x-umt", "g6Y01u4GXxM%2FUBp92nBr2Q5iV5p8%2FPEn");
        requestBuild.addHeader("x-sign", "ab250800809aa43d9669236a17907d8131f4da9a7330cfb886");
         final Request request = requestBuild.build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public static void get(String url, Map<String, String> param, final HttpResponseHandler handler) {

        if (param != null && param.size() > 0) {
            url = url + "?" + mapToQueryString(param);
        }
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    Object apiResponse = getRestApiResponse(response.body().string(), handler.clazz);
                    handler.sendSuccessMessage(apiResponse);
                } catch (Exception e) {
                    handler.sendFailureMessage(call.request(), e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendFailureMessage(call.request(), e);
            }
        });
    }

    public static void post(String url, Map<String, String> param, final HttpResponseHandler handler) {
        String paramStr = "";
        if (param != null && param.size() > 0) {
            paramStr = url += mapToQueryString(param);
            url = url + "?" + paramStr;
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE, paramStr);
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {

                    Object apiResponse = getRestApiResponse(response.body().string(), handler.clazz);
                    handler.sendSuccessMessage(apiResponse);
                } catch (Exception e) {
                    handler.sendFailureMessage(call.request(), e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendFailureMessage(call.request(), e);
            }
        });
    }


    private static Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return (Class) getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return (Class) type;
        }
    }

    private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }

    private static Object getRestApiResponse(String responseBody, Class clazz) throws Exception {
        if (!isJsonString(responseBody)) {
            throw new Exception("server response not json string (response = " + responseBody + ")");
        }
        Object apiResponse = JSON.parseObject(responseBody, clazz);
        if (apiResponse == null) {
            throw new Exception("server error (response = " + responseBody + ")");
        }

        return apiResponse;
    }

    private static boolean isJsonString(String responseBody) {
        return !TextUtils.isEmpty(responseBody) && (responseBody.startsWith("{") && responseBody.endsWith("}"));
    }

    public static String mapToQueryString(Map<String, String> map) {
        StringBuilder string = new StringBuilder();
        /*if(map.size() > 0) {
            string.append("?");
        }*/
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                string.append(entry.getKey());
                string.append("=");
                string.append(URLEncoder.encode(entry.getValue(), UTF_8));
                string.append("&");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string.toString();
    }

    //*************************************************************//
    public static final int PAGE_SIZE = 30;
    private static final String HTTP_DOMAIN = "http://sye.zhongsou.com/ent/rest";
    private static final String SHOP_RECOMMEND = "dpSearch.recommendShop"; // 推荐商家

    public static void getRecommendShops(SearchParam param, HttpResponseHandler httpResponseHandler) {
        param.setLat(39.982314);
        param.setLng(116.409671);
        param.setCity("beijing");
        param.setPsize(PAGE_SIZE);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("city", param.getCity());
        params.put("lat", param.getLat());
        params.put("lng", param.getLng());
        params.put("pno", param.getPno());
        params.put("psize", param.getPsize());
        String paramStr = JSON.toJSONString(param);
        paramStr = Base64.encodeToString(paramStr.getBytes(), Base64.DEFAULT);

        HashMap<String, String> rq = new HashMap<>();
        rq.put("m", SHOP_RECOMMEND);
        rq.put("p", paramStr);
//        String url = HTTP_DOMAIN + "?" + URLEncodedUtils.format(rq, UTF_8);
        get(HTTP_DOMAIN, rq, httpResponseHandler);
    }
}
