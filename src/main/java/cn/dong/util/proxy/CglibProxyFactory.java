package cn.dong.util.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyFactory implements MethodInterceptor {

	private Object delegate;
	
	public Object bind(Object delegate){
		this.delegate = delegate;
		
		Enhancer hancer = new Enhancer();
		//
		hancer.setSuperclass(delegate.getClass());
		//
		hancer.setCallback(this);
		return hancer.create();
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		//PerformanceUtil.startPerformance();
		Object o = proxy.invoke(this.delegate, args);
		//PerformanceUtil.endPerformance();
		return o;
	}
}
