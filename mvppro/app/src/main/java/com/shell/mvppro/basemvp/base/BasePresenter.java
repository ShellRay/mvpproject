package com.shell.mvppro.basemvp.base;


import com.shell.mvppro.basemvp.inject.InjectModel;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 基类presenter，封装presenter公共方法
 */
public class BasePresenter<V extends BaseView> {

    private WeakReference<V> mView;
    private V proxyView;
    private List<BaseModel> modelList = new ArrayList<>();
    /**
     * 绑定view
     */
    public void attach(V view) {
        this.mView = new WeakReference<>(view);
        proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        if (mView == null || mView.get() == null) {//统一判空处理
                            throw new NullPointerException("view is null");
                        }
                        return method.invoke(mView.get(), objects);
                    }
                });

        //一对一的情况创建model,需要在类上添加一个泛型
//        Type type = this.getClass().getGenericSuperclass();
//        Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
//        Class clazz = (Class) arguments[1];//获取的是BasePresenter上的第二个泛型
//        try {
//            mModel = (M)clazz.newInstance();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
        //一对多创建model
        injectModel();

        regist();

    }

    /**
     * 注入model
     */
    private void injectModel() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectModel annotation = field.getAnnotation(InjectModel.class);
            if(annotation != null){
                Class<? extends BaseModel> clazz = (Class<? extends BaseModel>) field.getType();
                try {
                    BaseModel model = clazz.newInstance();
                    modelList.add(model);
                    field.setAccessible(true);
                    field.set(this,model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解绑view
     */
    public void detach() {
        this.mView = null;

        unRegist();
    }

    /**
     * 返回view
     */
    public V getView() {

        return proxyView;
    }


    public void regist(){
        if(modelList.isEmpty())return;
        for (BaseModel baseModel : modelList) {
//            EventBusManager.getInstance().register(baseModel);
        }
    }

    public void unRegist(){
        if (modelList.isEmpty())return;
        for (BaseModel baseModel : modelList) {
//            EventBusManager.getInstance().unregister(baseModel);
        }
    }
}
