package com.sphinx.hopy.customer.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.service.CustomerService;

@Controller
public class UpdateCustomerController implements ApplicationContextAware{
	@Autowired
	private CustomerService customerService;
	// Autowired는 상관없는데 @Service이거는 각 구현체마다 달아줘야해
	private WebApplicationContext context;
	private List<String> phoneNum1 = null;

	@RequestMapping(value = "/customer/auth/update", method = RequestMethod.GET)
	public ModelAndView updateForm(Customer customer) {
		ModelAndView mav = new ModelAndView();
		String CONTENT = "customer/auth/update_form.jsp";
		mav.addObject("CONTENT", CONTENT);
		mav.setViewName("template");
		return mav;
	}

	@RequestMapping(value = "/customer/auth/update", method = RequestMethod.POST)
	public ModelAndView update(Customer modifiedCustomer,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		//역시 덜차있넹 그럼 session에 로그인 되있는 친구로 델꼬옵시당
		Customer originCustomer = (Customer) session.getAttribute("customer");
		//수정된 값으로 덮어씌웁시당
		//originCustomer.setSphinxId(modifiedCustomer.getSphinxId());
		originCustomer.setPassword(modifiedCustomer.getPassword());
		originCustomer.setName(modifiedCustomer.getName());
		originCustomer.setAddress(modifiedCustomer.getAddress());
		originCustomer.setPhone(modifiedCustomer.getPhone());
		
		customerService.updateCustomer(originCustomer);
		mav.addObject("customer", originCustomer);
		///////////////////////////////////////////////////

		String CONTENT = "customer/auth/view_detail.jsp";
		mav.addObject("CONTENT", CONTENT);
		mav.setViewName("template");
		return mav;
	}
	
	//관리자 승격
	@RequestMapping(value = "/customer/auth/update_admin", method = RequestMethod.GET)
	public String updateAdmin(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("customer");
		customer.setIsAdmin(true);
		//여기서 db에 값도 변경해줘야하는데 깜빡했네
		customerService.updateCustomer(customer);
		session.setAttribute("customer", customer);
		model.addAttribute("customer", customer);
		String CONTENT = "customer/auth/view_detail.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	// 이런식으로 넣어두면 jsp갈때 알아서 해준대여!
	@ModelAttribute("phoneNum1")
	public List<String> referenceData() {
		// 하드코딩 말구 파일로 함 읽어봅시당
		/*
		 * phoneNum1.add("010"); phoneNum1.add("011"); phoneNum1.add("012");
		 */
		if (phoneNum1 != null) {
			// 읽어온 자료가 있다는거니까
			return phoneNum1;
		} else {
			phoneNum1 = new ArrayList<>();
			BufferedReader br = null;
			try {
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

	
	// ApplicationContextAware이 인터페이스라서
	// 이 메소드를 정의해줘야함
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = (WebApplicationContext) context;
	}
}
