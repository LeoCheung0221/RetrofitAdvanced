package com.tufusi.mustmasterinject.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 鼠夏目 on 2020/8/17.
 *
 * @author 鼠夏目
 * @description 事件类型
 * @see
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {

    Class listener();

    String setOnClickListener();

    String methodName();

}
