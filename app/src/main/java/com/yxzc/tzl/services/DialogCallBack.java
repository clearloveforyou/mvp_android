package com.yxzc.tzl.services;

import com.lzy.okgo.request.base.Request;
import com.yxzc.tzl.beans.BaseBean;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.services
 * @Author: HSL
 * @Time: 2018/10/16 16:41
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public abstract class DialogCallBack<T extends BaseBean> extends JsonCallback<T> {

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // TODO: 2018/10/16
    }
}
