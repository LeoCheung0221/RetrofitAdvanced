package com.tufusi.mustmasterinject.inject;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 鼠夏目 on 2020/8/17.
 *
 * @author 鼠夏目
 * @description 注解点击事件
 * @see
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Event(listener = View.OnClickListener.class, setOnClickListener = "setOnClickListener", methodName = "onClick")
public @interface OnClick {
    int[] value();
}
