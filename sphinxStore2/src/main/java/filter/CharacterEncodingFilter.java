package filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 */
//@WebFilter("*.do")//이 방식이 어노테이션
//아니면 web.xml에서 정의 해줘야함
//@WebFilter("*.ho")
public class CharacterEncodingFilter implements Filter {

	private FilterConfig config;	//설정값, web.xml에서
	private String encoding;	
	private String DEFAULT_ENCODING = "UTF-8";

    public CharacterEncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		config = null;
		encoding = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//이제 실제로 실행되는 부분
		try {
			request.setCharacterEncoding(encoding);
		} catch (UnsupportedEncodingException e) {
			encoding = DEFAULT_ENCODING;
			request.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		//초기화 시켜주는 부분
		this.config = fConfig;
		encoding = fConfig.getInitParameter("Character Encoding");
		//web.xml에서 Character Encoding이란 이름의 값을 가져옴
		//getParameter처럼 저것도 같은거 해줌
	}

}
