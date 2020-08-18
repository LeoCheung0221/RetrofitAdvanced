package com.tufusi.mustmasterinject.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 鼠夏目 on 2020/8/17.
 *
 * @author 鼠夏目
 * @description InjectView 用于注入View  用来代替findViewById
 *
 * @see
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
    int value(); //控件的ID
}
