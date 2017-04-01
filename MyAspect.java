package AOP;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * ��������ȷ��֪ͨ����Ҫʵ�ֲ�ͬ�ӿڣ��ӿھ��ǹ淶���Ӷ���ȷ���������ơ�
 * * ���á�����֪ͨ�� MethodInterceptor
 *
 */
public class MyAspect implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation mo) throws Throwable {

		System.out.println("ǰ;��ã������·�ֻܶ࣬���ߵ����ٰ���");
		//�ֶ�ִ��Ŀ�귽��
		Object obj = mo.proceed();
		
		System.out.println("ǰ;��ã����ӰȽȻ��̤ʵ���£�һ·��ǰ");
		
		
		return obj;
	}
	
}
