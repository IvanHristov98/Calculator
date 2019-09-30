package com.calculator.web.aspects.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DbInteractionLogger implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("Making a database transaction.");
		
		return invocation.proceed();
	}
}
