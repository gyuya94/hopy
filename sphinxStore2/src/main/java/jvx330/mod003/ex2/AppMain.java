package jvx330.mod003.ex2;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AppMain {
	public static void main(String[] args) {
		String config = "jvx330/mod003/ex2/mod003_2.xml";
		AbstractApplicationContext context = new GenericXmlApplicationContext(config);
		
		//신기하당 Spring에서는 이런식으로 이제 객체를 만든?받아온대
		Employee employee = context.getBean("employee", Employee.class);
		System.out.println("-------------");
		System.out.println(employee.getDetails());
		
		context.close();
	}

}
