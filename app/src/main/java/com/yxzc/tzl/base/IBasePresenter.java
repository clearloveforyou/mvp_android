package com.yxzc.tzl.base;

/**
 * @Author: HouShengLi
 * @Time: 2018/10/15 15:15
 * @E-mail: 13967189624@163.com
 * @Description: 为业务处理层，既能调用UI逻辑，又能请求数据，该层为纯Java类
 */
public interface IBasePresenter<V extends IBaseView> {

    void onAttachView(V view);

    void onDetachView();

}
