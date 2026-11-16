package org.route.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.route.model.Governorate;
import org.route.model.ShoppingCart;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditLogAspect {


    @Before("execution(* org.route.Service.CheckoutService.placeOrder(org.route.Model.ShoppingCart, org.route.Model.Governorate))")
    public void logOrderAttempt(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length >= 2 && args[0] instanceof ShoppingCart cart && args[1] instanceof Governorate gov) {
            System.out.println("[AuditLog] Placing order for Cart Number: " + cart.getCartNumber() + " to Governorate: " + gov);
        }
    }
}