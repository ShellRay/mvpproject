package com.shell.mvppro.basemvp.proxy;



import com.shell.mvppro.basemvp.base.BasePresenter;
import com.shell.mvppro.basemvp.base.BaseView;
import com.shell.mvppro.basemvp.inject.InjectPresenter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IMVPProxyImpl<V extends BaseView> implements IMVPProxy {
    //保存绑定的presenter，用于解绑
    private List<BasePresenter> presenters = new ArrayList<>();
    private V mView;

    public IMVPProxyImpl(V view) {
        this.mView = view;
    }

    @Override
    public void bindAndCreatePresenter() {
        //获取属性
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取标记
            InjectPresenter annotation = field.getAnnotation(InjectPresenter.class);
            //判断是否是标记的属性
            if (annotation != null) {
                //获取属性presenter的class
                Class<? extends BasePresenter> presenterClazz = (Class<? extends BasePresenter>) field.getType();

                //检查是否是presenter，规避因泛型擦除导致检测不到非presenter创建对象的问题
                if (!BasePresenter.class.isAssignableFrom(presenterClazz)) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(presenterClazz.getSimpleName())
                            .append(" in ")
                            .append(mView.getClass().getSimpleName())
                            .append(" is not presenter");
                    throw new RuntimeException(builder.toString());
                }
                BasePresenter presenter = null;
                try {
                    //创建对象
                    presenter = presenterClazz.newInstance();
                    if (presenter != null) {
                        //绑定
                        presenter.attach(mView);
                        field.setAccessible(true);
                        //注入(设置值)
                        field.set(mView, presenter);
                        presenters.add(presenter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkViewimplements(presenter);
            }
        }
    }

    /**
     * 检测View层是否实现了BaseView接口
     * @param basePresenter
     */
    private void checkViewimplements(BasePresenter basePresenter) {
        //获取BasePresenter上的View泛型即BaseView
        Type type = basePresenter.getClass().getGenericSuperclass();
        if(!(type instanceof ParameterizedType)){
            StringBuilder builder = new StringBuilder();
            builder.append(basePresenter.getClass().getName())
                    .append(" 没有给BasePresenter后面指定View的泛型");
            throw new RuntimeException(builder.toString());
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        Class viewClazz = (Class) types[0];
        //拿到View层的所有接口
        Class<?>[] interfaces = mView.getClass().getInterfaces();
        //判断View层是否实现了BaseView接口
        boolean hasImplements = false;
        for (Class<?> anInterface : interfaces) {
            if(viewClazz.isAssignableFrom(anInterface)){
                hasImplements = true;
                break;
            }
        }
        if(!hasImplements){// 未实现，抛出异常
            StringBuilder builder = new StringBuilder();
            builder.append(mView.getClass().getSimpleName())
                    .append(" must implements ")
                    .append(viewClazz.getName());
            throw new RuntimeException(builder.toString());
        }
    }

    @Override
    public void unbindPresenter() {
        if (presenters != null && !presenters.isEmpty()) {
            for (BasePresenter presenter : presenters) {
                presenter.detach();
            }
        }

        mView = null;
    }
}
