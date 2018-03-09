package com.sphinx.hopy.customer.web.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.exception.NonExistCustomerException;
import com.sphinx.hopy.customer.service.CustomerService;

@Controller
public class JoinCustomerController implements ApplicationContextAware {
	@Autowired
	private CustomerService customerService;
	// Autowired는 상관없는데 @Service이거는 각 구현체마다 달아줘야해
	private WebApplicationContext context;
	private List<String> phoneNum1 = null;

	
/*	@RequestMapping(value = "/customer/auth/join", method = RequestMethod.GET)
	public ModelAndView joinForm(Customer customer) {
		ModelAndView mav = new ModelAndView();
		String CONTENT = "customer/auth/join_SpringForm.jsp";
		mav.addObject("CONTENT", CONTENT);
		mav.setViewName("template");// 보여줄 페이지
		return mav;
	}*/
	//위에 친구는 그냥 form을 쓸때고
	//이제 스프링이 제공하는 form태그를 이용해서 받아볼거에여
	@RequestMapping(value = "/customer/auth/join", method = RequestMethod.GET)
	public ModelAndView joinForm(@ModelAttribute("customer") Customer customer) {
		ModelAndView mav = new ModelAndView();
		String CONTENT = "customer/auth/join_SpringForm.jsp";
		mav.addObject("CONTENT", CONTENT);
		mav.setViewName("template");// 보여줄 페이지
		return mav;
	}
	

	// 이런식으로 넣어두면 jsp갈때 알아서 해준대여!
	@ModelAttribute("phoneNum1")
	public List<String> referenceData() {
		// 하드코딩 말구 파일로 함 읽어봅시당
		/*
		 * phoneNum1.add("010"); 
		 * phoneNum1.add("011"); 
		 * phoneNum1.add("012");
		 */
		if (phoneNum1 != null) {
			// 읽어온 자료가 있다는거니까
			return phoneNum1;
		} else {
			phoneNum1 = new ArrayList<>();
			BufferedReader br = null;
			try {
				//System.out.println("context는 뭐징? : " +context.getServletContext().getRealPath("/resources/"));
				File file = new File(
						context.getServletContext().getRealPath("/resources/"),
						"phoneNum.txt");
				br = new BufferedReader(new FileReader(file));
				// BufferedReader(Reader)인데 FileReader로 polymorphy라서 걍넣음됨
				String line = br.readLine(); // 먼저 한줄 읽어오는데
				// Buffer는 한줄읽어서 String으로 받아옴

				while (line != null) {// String이니까 암것도없음 null임
					phoneNum1.add(line);// 한줄 넣고
					line = br.readLine();// 다시 또 한줄 읽어서 String저장
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return phoneNum1;
		}
	}

	@RequestMapping(value = "/customer/auth/join", method = RequestMethod.POST)
	public ModelAndView join(Customer customer,
			@RequestParam("phoneNum1") String phoneNum1,
			@RequestParam("phoneNum2") String phoneNum2,
			@RequestParam("phoneNum3") String phoneNum3) {
		ModelAndView mav = new ModelAndView();

		//움 있는지 id중복검사 해야하는거같은뎅
		try {
			customerService.getCustomerBySphinxId(customer.getSphinxId());
			//실패 메세지도 주고싶은데 이거 예쁘게 하는친구 있지싶어
		} catch (NonExistCustomerException e) {
			//존재하지 x? 중복되는게 아니라 customer를 add할수 있단거니까
			customer.setPhone(phoneNum1+phoneNum2+phoneNum3);
			customerService.addCustomer(customer);
			mav.addObject(customer);
			///////////////////////////////////////////////////
			String CONTENT = "customer/auth/join_result.jsp";
			mav.addObject("CONTENT", CONTENT);
			mav.setViewName("template");
			return mav;
		}
		//저 id가 존재한다면 실패한거니 다시 가입페이지로감
		String CONTENT = "customer/auth/join_SpringForm.jsp";
		mav.addObject("CONTENT", CONTENT);
		mav.setViewName("template");
		return mav;
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
