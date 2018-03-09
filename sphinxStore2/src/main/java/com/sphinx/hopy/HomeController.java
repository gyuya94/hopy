package com.sphinx.hopy;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController implements ApplicationContextAware {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private CategoryService categoryService;
	List<Category> rootCategoryList = null;
	
	private WebApplicationContext context;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	//처음 화면
	public String home(Locale locale, Model model) {
		//강사님이 만든 기본 서버에 날짜찍어주는 친구
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		//움 root를 계속 하고있는데 1번만 받아올수있도록 합시당
		if(rootCategoryList == null) {
			//처음이면 한번만 하도록
			rootCategoryList = new ArrayList<>();//공간 할당해주고
			rootCategoryList = categoryService.getRootCategoryList();
			//아 applicationScope쓰려면 이런식으로 해야하는구낭
			context.getServletContext().setAttribute(
					"rootCategoryList", rootCategoryList);
		}

		//히희 접속 ip털어봐야징
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();
        System.out.println(ip);
        
		return "home";
	}
	
	//ApplicationContextAware이 인터페이스라서
		//이 메소드를 정의해줘야함
		@Override
		public void setApplicationContext(ApplicationContext context)
				throws BeansException {
			//context가 ApplicationContext context이 변수로 자기를 집어넣어서
			//컴포넌트(여기서는 JoinCustomerController인 Bean)가 
			//자기가 놀고있는 컨텍스트를 가져오는거지
			
			this.context = (WebApplicationContext) context;
			//WebApplicationContext는 ApplicationContext의 자식이야
			//우린 지금 그냥 main써서 하는게 아니라
			//톰캣올려서 하는 웹에거 하는거니까 WebAppContext로 하는거고
			//그래서 나중에 applicationScope를 쓰고싶다면
			//((WebApplicationContext)context).setAttribute("냥냥");
			//이런식으로 하면 된다고하는데 왜 안될까
			//암튼 그렇대요
			
		}
	
}
