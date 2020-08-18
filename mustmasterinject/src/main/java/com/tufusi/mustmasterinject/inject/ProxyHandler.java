package com.tufusi.mustmasterinject.inject;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by 鼠夏目 on 2020/8/17.
 *
 * @author 鼠夏目
 * @description 实现InvocationHandler，用来自定义代理
 * @see
 */
public class ProxyHandler implements InvocationHandler {

    private Object mTarget;
    private HashMap<String, Method> methodHashMap = new HashMap<>();

    public ProxyHandler(Object activity) {
        mTarget = activity;
    }

    /**
     * 委托调用方法
     *
     * @param proxy  调用方法的代理实例
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("ProxyHandler", "method name: " + method.getName() + " , " + "args: " + Arrays.toString(args));
        Method matchMethod = methodHashMap.get(method.getName());
        if (matchMethod != null) {
            return matchMethod.invoke(mTarget, args);
        }
        return null;
    }

    public void setMethod(String methodName, Method method) {
        methodHashMap.put(methodName, method);
    }
}
