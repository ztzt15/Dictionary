package com.zt.dictionary.util.netUtil;

import android.content.Context;
import android.util.Log;

import com.yeepay.utils.http.AuthFailureError;
import com.yeepay.utils.http.DefaultRetryPolicy;
import com.yeepay.utils.http.Request;
import com.yeepay.utils.http.RequestQueue;
import com.yeepay.utils.http.Response;
import com.yeepay.utils.http.VolleyError;
import com.yeepay.utils.http.toolbox.JsonObjectRequest;
import com.yeepay.utils.http.toolbox.StringRequest;
import com.yeepay.utils.http.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @user: zt
 * @describe:
 */

public class YeePayBaseRequest {
    private static YeePayBaseRequest baseRequest;
    private static Context myContext;
    private RequestQueue mRequestQueue;
    private String url = "";
    private static final int TIMEOUT_MS = 2500;
    private static final int RETRIES = 1;
    private static final float BACKOFF_MULT = 1.0F;

    private Map<String, String> map = null;


    private YeePayBaseRequest(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mRequestQueue.start();
    }

    public static YeePayBaseRequest getInstance() {
        if (baseRequest == null) {
            throw new NullPointerException("Call init method first.");
        }
        return baseRequest;
    }

    public static YeePayBaseRequest init(Context context) {
        myContext = context;
        if (baseRequest == null) {
            synchronized (YeePayBaseRequest.class) {
                if (baseRequest == null) {
                    baseRequest = new YeePayBaseRequest(context);
                }
            }
        }
        return baseRequest;
    }

    public void getTranslate(String keyWord, YeePayNetRequestCallBack callBack) {
        map = new HashMap<String, String>();
        map.put("action", "FY_BY_ENTER");
        map.put("doctype", "json");
        map.put("keyfrom", "fanyi.web");
        map.put("type", "AUTO");
        map.put("typoResult", "true");
        map.put("ue", "UTF-8");
        map.put("xmlVersion", "1.8");
        map.put("i", keyWord);
        url = "http://fanyi.youdao.com/translate?smartresult=dict&smartresult=rule&smartresult=ugc&sessionFrom=null";
        sendStringRequest(map, url, callBack);
    }

    public void sendJsonRequest(final Map<String, String> map, String url, final YeePayNetRequestCallBack callBack) {
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }


    public void sendStringRequest(final Map<String, String> map, String url, final YeePayNetRequestCallBack callBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                callBack.onResponse(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onErrorResponse(volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("api-version", "1.0");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, RETRIES, BACKOFF_MULT));
        stringRequest.setTag(myContext.getClass().getName());
        Log.d("TAG", myContext.getClass().getName());
        mRequestQueue.add(stringRequest);
    }


    /**
     * 取消同一activity的所有网络请求
     */
    public void cancelOneActivityRequest() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(myContext.getClass().getName());
        }
    }

}
