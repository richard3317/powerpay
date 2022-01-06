package com.icpay.payment.mer.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.icpay.payment.common.enums.MerEnums.MerUserRole;

/**
 * 此標記表示該 Controller 仅允许指定角色执行，默认只允许 {@link com.icpay.payment.common.enums.MerEnums.MerUserRole.PaymentUser MerUserRole.PaymentUser}。
 * 此标签仅允许套用于方法上，以下范例指定方法 doBatchWithdraw() 仅允许 MerUserRole.WithdrawUser 及 MerUserRole.SuperUser 执行：
 * <pre>
 * &#64;RolesSessionCheck(roles={MerUserRole.WithdrawUser, MerUserRole.SuperUser})
 * public String doBatchWithdraw(Model model, HttpServletRequest req) {
 *   ...
 * }
 * </pre>
 * @author robin
 */
//@Target({ElementType.METHOD, ElementType.TYPE})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RolesSessionCheck {
	MerUserRole[] roles() default { MerUserRole.PaymentUser};
}
