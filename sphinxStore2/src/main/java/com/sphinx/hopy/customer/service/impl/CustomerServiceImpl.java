package com.sphinx.hopy.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sphinx.hopy.customer.dao.CustomerDao;
import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.exception.NonExistCustomerException;
import com.sphinx.hopy.customer.service.CustomerService;
import com.sphinx.hopy.util.idGenerator.IdGenerator;
import com.sphinx.hopy.util.idGenerator.service.IdGeneratorService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private IdGeneratorService idGeneratorService;
	private IdGenerator idGenerator;
	
	@Override
	public void addCustomer(Customer customer) {
		//사용자가 입력하지않은, 우리가 만들어준 정보를 여기서 넣어줍시당
		//customer.setCustomerId(getNextCustomerId());
		String usableId = idGeneratorService.getUsableId(idGenerator);
		customer.setCustomerId(usableId);
		
		customer.setIsAdmin(false);
		//joinDate는 sql구문에 그냥 current_date로 박아버립시당
		
		customerDao.addCustomer(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerDao.updateCustomer(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customerDao.deleteCustomer(customer);
	}

	@Override
	public Customer getCustomerBySphinxId(String sphinxId) throws NonExistCustomerException {
		return customerDao.getCustomerBySphinxId(sphinxId);
	}

	@Override
	public Customer getCustomerByCustomerId(String customerId) {
		//그런데 이 함수에서 리턴되는게 customer객체 1개뿐인걸까
		//만약 hoho가 del로 되있다면 새로운 유저는 hoho라는 customerId를 쓸수있
		//아 맞네 그럼 여기서 아예 del조건을 살아있는 친구만 하면 
		//1개만 받아올수 있어
		//대신 insert할때 customerId가 unique가 아니고
		//넣어줄때 그 customerId가 del인지 아닌지도 체크를 해야겠지

		return customerDao.getCustomerByCustomerId(customerId);
	}
	
/*	@Override
	public String getNextCustomerId() {
		String lastCustomerId = customerDao.getNextCustomerId();
		//일단 MAX(customerId)에서 +1해줄건데 이건 나중에
		//모듈화된 친구로 바꿔줘야겠다
		
		// 'CUST000001'이런식으로 할거니 뭐 지금은 대충
		String tag = "CUST";
		int idLength = 10;

		if (lastCustomerId == null) {
			// 지금 마지막으로 저장된게 하나도없음
			// =지금 넣는 얘가 젤 처음인 얘인거니까
			lastCustomerId = tag + "000000";
		}
		// 뒤에 7개 숫자 잘라와서 변환하고 1더하고 하는 내용들
		String strNextCustomerId = lastCustomerId
				.substring(tag.length());

		int nextCustomerId = Integer.parseInt(strNextCustomerId);
		nextCustomerId++;
		strNextCustomerId = tag + Integer.toString(nextCustomerId);

		StringBuffer tempString = new StringBuffer(strNextCustomerId);

		for (; tempString.length() < idLength;) {
			tempString.insert(tag.length(), "0");
		}
		return tempString.toString();
	}*/

	public void init() {
		idGenerator = new IdGenerator();
		idGenerator.setPrefix("CUST");
		idGenerator.setIdLength(10);
		idGenerator.setTableName("customerIdTable");
	}
}
