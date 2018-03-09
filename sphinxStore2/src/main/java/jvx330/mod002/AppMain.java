package jvx330.mod002;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AppMain {
	public static void main(String[] args) {
		String config = "jvx330/mod002/mod002.xml";
		AbstractApplicationContext context = new GenericXmlApplicationContext(config);
		
		//신기하당 Spring에서는 이런식으로 이제 객체를 만든?받아온대
		Hello hello = context.getBean("helloBean", Hello.class);
		//name,id가 "helloBean"인 xml을 가져오는데 ,
		//그냥 Object로 가져와서 2번째 인자인 Hello.class로 casting해주겠다
		//적어주는거에여
		System.out.println(hello.sayHello("멈멍"));
		System.out.println("hello "+ hello);
		
		//hello1과 2는 같은것일까요??
		Hello hello2 = context.getBean("helloBean", Hello.class);
		System.out.println("hello2 " + hello2);
		//같네염
		//getInstance()같은거라 그런가
		
		//그리고 미리 만들어둔애라 그런가 수정된거 반영이 좀 느리네
		
		//그럼 다른거로 주고싶으면 어떡하죠??
		//Hello hello3 = context.getb
		//필요하면 뭐 new써도 돼요
		
		context.close();
	}

}
