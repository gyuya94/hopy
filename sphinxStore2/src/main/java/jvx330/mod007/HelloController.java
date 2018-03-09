package jvx330.mod007;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller //이제 extends안하고 어노테이션으로 써줍니당
public class HelloController {

	@RequestMapping("/mod007/sayHello")
	//저런 데이터 타입도 있나봐요
	public ModelAndView sayHello() {
		String greeting = "Hello! Spring MVC";
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("mod007/hello");
		mav.addObject("greeting", greeting);
		//뭐 request.setAttribute()랑 같은 의미이긴한데
		//servlet의 그런거를 지우겠다는 거죠
		
		return mav;
	}
}
