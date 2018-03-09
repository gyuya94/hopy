package com.sphinx.hopy.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.sphinx.hopy.util.idGenerator.IdGenerator;
import com.sphinx.hopy.util.idGenerator.dao.IdGeneratorDao;
import com.sphinx.hopy.util.idGenerator.service.IdGeneratorService;
import com.sphinx.hopy.util.idGenerator.service.impl.IdGeneratorServiceImpl;

public class IdGeneratorTest {

	public static void main(String[] args) {
		String config = "com/sphinx/hopy/test/test.xml";
		AbstractApplicationContext context = new GenericXmlApplicationContext(
				config);
		IdGeneratorDao idGeneratorDao = context.getBean(
				"idGeneratorDao", IdGeneratorDao.class);
		IdGeneratorService idGeneratorService = context.getBean(
				"idGeneratorService", IdGeneratorService.class);
		IdGenerator idGenerator = new IdGenerator("TEST", 10, "testTable");
/*		
		System.out.println("--------getLastId-----------");
		String lastId = idGeneratorDao.getLastId(idGenerator.getTableName());
		System.out.println(lastId);
		
		System.out.println("-------idGeneratorDao.insertId------------");
		idGeneratorDao.insertId("P_0004_BLK", idGenerator.getTableName());
		lastId = idGeneratorDao.getLastId(idGenerator.getTableName());
		System.out.println(lastId);
		
		System.out.println("-------getUsableMinId---------");
		String usableId = idGeneratorDao.getUsableMinId(idGenerator.getTableName());
		System.out.println(usableId);
		
		System.out.println("-------updateId---------");
		idGeneratorDao.updateId("PROD000001", true, idGenerator.getTableName());
		usableId = idGeneratorDao.getUsableMinId(idGenerator.getTableName());
		System.out.println(usableId);
		*/
		System.out.println("-------getUsableId---------");
		String usableId = idGeneratorService.getUsableId(idGenerator);
		System.out.println(usableId);
		
		System.out.println("-------insertExtraId---------");
		//idGeneratorService.insertExtraId();
		
		String usableId2 = idGeneratorService.getUsableId(idGenerator);
		System.out.println(usableId2);
		
		
	}
	

}
