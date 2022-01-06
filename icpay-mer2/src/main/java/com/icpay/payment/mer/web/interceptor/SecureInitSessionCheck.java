package com.icpay.payment.mer.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此標記为允许显示于安全初始化的资源。
 * 如果尚未安全初始化，则只允许存取SecureInitSessionCheck标记的资源。
 * @author robin
 */
//@Target({ElementType.METHOD, ElementType.TYPE})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecureInitSessionCheck {

}
