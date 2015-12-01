package com.zt.dictionary.util.netUtil;

import com.yeepay.utils.http.VolleyError;

/**
 * @user: zt
 * @describe:
 */

public interface YeePayNetRequestCallBack {
    void onResponse(Object obj);
    void onErrorResponse(VolleyError volleyError);
}
