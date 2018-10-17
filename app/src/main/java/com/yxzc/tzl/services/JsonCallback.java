/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yxzc.tzl.services;

import android.app.Activity;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.orhanobut.logger.Logger;
import com.yxzc.tzl.beans.BaseBean;
import com.yxzc.tzl.utils.JsonUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/25 17:43
 * @E-mail: 13967189624@163.com
 * @Description:依次回调 onStart --> convertResponse(子线程) --> onSuccess --> finish(主线程)
 */
public abstract class JsonCallback<T extends BaseBean> extends AbsCallback<T> {

    protected Activity mActivity;
    protected List<String> mSuccess = new ArrayList<>();

    public JsonCallback() {
        // TODO: 2018/9/27 no param
    }

    public JsonCallback(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // TODO: 2018/9/25
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
//        request.headers("header1", "HeaderValue1")//
//                .params("params1", "ParamsValue1")//
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        Class<T> clazz = (Class<T>) type;
        T data = JsonUtils.parseT(body.string(), clazz);
        Logger.t("JSON").d("convertResponse: ==" + clazz.getName() + "==" + clazz);
        //根据服务端约定，具体处理
        int code = data.getError_code();
        if (code == 0) {
            //noinspection unchecked
            return data;
        } else if (code == 402) {
            throw new IllegalStateException("用户授权信息无效");
        } else if (code == 401) {
            startLogin();
            throw new IllegalStateException("用户收取信息已过期");
        } else {
            //直接将服务端的错误信息抛出，onError中可以获取
            throw new IllegalStateException("错误代码：" + code + "，错误信息：" + data.getReason());
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    private void startLogin() {
        if (mActivity != null) {
            // TODO: 2018/9/27  
        }
    }
}
