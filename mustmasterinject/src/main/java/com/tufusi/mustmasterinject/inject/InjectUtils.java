package com.tufusi.mustmasterinject.inject;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 鼠夏目 on 2020/8/17.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class InjectUtils {

    /**
     * 通过反射找出findViewById  取出注解对象，并赋值
     */
    public static void injectView(Activity activity) {
        if (activity == null) {
            return;
        }

        Class<? extends Activity> activityClass = activity.getClass();
        Field[] declaredFields = activityClass.getDeclaredFields();

        // 遍历类中的所有成员变量
        for (Field field : declaredFields) {
            // 找出命中注解@InjectView 的成员变量
            if (field.isAnnotationPresent(InjectView.class)) {
                // 取出指定的注解对象
                InjectView annotation = field.getAnnotation(InjectView.class);
                // 获取注解值，即控件ID
                int value = annotation.value();

                try {
                    // 找到 findViewById 方法
                    Method method = activityClass.getDeclaredMethod("findViewById", int.class);
                    // 方法设置访问权限
                    method.setAccessible(true);
                    /*
                     *  将方法委托给 activity 和 控件ID
                     *  @param obj  调用底层方法的对象
                     *  @param args 用于方法调用的参数
                     */
                    Object view = method.invoke(activity, value);
                    field.set(activity, view);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过反射找出点击事件监听器 ，并注册
     */
    public static void injectEvent(Activity activity) {
        if (activity == null) {
            return;
        }

        Class<? extends Activity> activityClass = activity.getClass();
        Method[] methods = activityClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                OnClick onClick = method.getAnnotation(OnClick.class);
                int[] componentIds = onClick.value();

                Event event = onClick.annotationType().getAnnotation(Event.class);
                Class listener = event.listener();
                String listenerName = event.setOnClickListener();
                String methodName = event.methodName();

                // 创建InvocationHandler 及动态代理 处理 点击事件
                ProxyHandler proxyHandler = new ProxyHandler(activity);
                proxyHandler.setMethod(methodName, method);

                try {
                    Object proxyInstance = Proxy.newProxyInstance(listener.getClassLoader(), new Class[]{listener}, proxyHandler);
                    for (int id : componentIds) {
                        Method findViewById = activityClass.getMethod("findViewById", int.class);
                        findViewById.setAccessible(true);
                        View view = (View) findViewById.invoke(activity, id);
                        // 根据 listener方法名 和 event 方法参数
                        Method setOnClickListener = view.getClass().getMethod(listenerName, listener);
                        setOnClickListener.setAccessible(true);
                        setOnClickListener.invoke(view, proxyInstance);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
