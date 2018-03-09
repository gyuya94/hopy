package jvx330.mod003.ex4;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ByTypeClient {
	private NamingService namingService;

	public NamingService getNamingService() {
		return namingService;
	}

	public void setNamingService(NamingService namingService) {
		this.namingService = namingService;
	}
	
	public void service(String name) {
		Object o = namingService.lookup(name);
		System.out.println(o);
	}
	
	public static void main(String[] args) {
		String config = "jvx330/mod003/ex4/byType.xml";
		AbstractApplicationContext context = new GenericXmlApplicationContext(config);
		
		//신기하당 Spring에서는 이런식으로 이제 객체를 만든?받아온대
		ByTypeClient byTypeClient = context.getBean("byTypeClient", ByTypeClient.class);
		System.out.println("-------------");
		byTypeClient.service("멍멍");
		
		//예전 방식
		/*ByTypeClient byTypeClient2 = new ByTypeClient();
		NamingService namingService = new DnsNamingService();
		byTypeClient2.setNamingService(namingService);*/
		
		context.close();
	}
}
