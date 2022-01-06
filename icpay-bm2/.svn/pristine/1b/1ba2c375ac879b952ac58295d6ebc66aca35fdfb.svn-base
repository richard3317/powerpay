package com.icpay.payment.bm.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动将请求参数中，字首为"_QRY_"的参数，放到 qryParamMap。<br/><br/>
 * 当 Controller中的方法加上此标记，则会将request.getParameterMap()中的内容过滤所有字首为 "_QRY_"的参数自动加入查询参数列表(Query Map)，
 * Query Map 会以 _QRY_PARAM_MAP 为键值放在 request Attributes 之中。<br/>
 * 请参阅: {@link com.icpay.payment.bm.web.interceptor.ParamMapInterceptor}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QryMethod {

}
