package cn.dong.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements InvocationHandler {
	
	private Object delegate;

	public Object bind(Object delegate) {
		this.delegate = delegate;
		return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),delegate.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//PerformanceUtil.startPerformance();
		Object result = null;
		try{
			result = method.invoke(delegate, args);
		}
		catch(Exception e){
		}
				//PerformanceUtil.endPerformance();
		return result;
	}
}
