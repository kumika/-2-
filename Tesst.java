package AOP;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Tesst {

	@Test
	public void  tessss(){
		
		String xml = "AOP/beans.xml";
		ApplicationContext app = new ClassPathXmlApplicationContext(xml);
		
		
		//获得代理类
		UserService us = (UserService) app.getBean("USerServiceId");
		us.add();
		us.modify();
		us.delete();
	}
	
}
