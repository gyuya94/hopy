package jvx330.mod003.ex3;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import jvx330.mod003.ex2.Date;

public class AddressInfoList {
	private List<String> addrList;
	private Map<String, String> testMap;
	private Set<Date> testSet;
	
	
	public List<String> getAddrList() {
		return addrList;
	}

	public void setAddrList(List<String> addrList) {
		this.addrList = addrList;
	}

	public Map<String, String> getTestMap() {
		return testMap;
	}

	public void setTestMap(Map<String, String> testMap) {
		this.testMap = testMap;
	}

	public Set<Date> getTestSet() {
		return testSet;
	}

	public void setTestSet(Set<Date> testSet) {
		this.testSet = testSet;
	}

	public static void main(String[] args) {
		String config = "jvx330/mod003/ex3/mod003_3.xml";
		AbstractApplicationContext context = new GenericXmlApplicationContext(config);
		
		//신기하당 Spring에서는 이런식으로 이제 객체를 만든?받아온대
		AddressInfoList addrInfo = context.getBean("addrInfo", AddressInfoList.class);
		System.out.println("-------------");
		System.out.println(addrInfo);
		
		context.close();
	}

	@Override
	public String toString() {
		return "AddressInfoList [addrList=" + addrList + ", testMap=" + testMap
				+ ", testSet=" + testSet + "]";
	}

	
}
