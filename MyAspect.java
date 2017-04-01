package AOP;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * 切面类中确定通知，需要实现不同接口，接口就是规范，从而就确定方法名称。
 * * 采用“环绕通知” MethodInterceptor
 *
 */
public class MyAspect implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation mo) throws Throwable {

		System.out.println("前途迷茫，但是路很多，只是走的人少罢了");
		//手动执行目标方法
		Object obj = mo.proceed();
		
		System.out.println("前途迷茫，光影冉然，踏实做事，一路向前");
		
		
		return obj;
	}
	
}
