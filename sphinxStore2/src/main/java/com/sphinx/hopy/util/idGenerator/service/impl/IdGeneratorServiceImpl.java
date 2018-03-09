package com.sphinx.hopy.util.idGenerator.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sphinx.hopy.util.idGenerator.IdGenerator;
import com.sphinx.hopy.util.idGenerator.dao.IdGeneratorDao;
import com.sphinx.hopy.util.idGenerator.service.IdGeneratorService;

@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {

	@Autowired
	private IdGeneratorDao idGeneratorDao;
	
	private int defaultNum = 10;
	
	@Override
	public void insertExtraId(IdGenerator idGenerator) {
		String lastId = idGeneratorDao.getLastId(idGenerator.getTableName());
		//만약 하나도 없으면 예외처리 해줘야해
		if(lastId == null) {
			lastId = idGenerator.getPrefix();
			for( ; lastId.length() < idGenerator.getIdLength(); ) {
				lastId += "0";
			}
		}
		List<String> idList = generateId(lastId, defaultNum, idGenerator);
		for(String id : idList) {
			idGeneratorDao.insertId(id, idGenerator.getTableName());
		}
	}

	@Override
	public void insertExtraId(int number, IdGenerator idGenerator) {
		String lastId = idGeneratorDao.getLastId(idGenerator.getTableName());
		List<String> idList = generateId(lastId, number, idGenerator);
		for(String id : idList) {
			idGeneratorDao.insertId(id, idGenerator.getTableName());
		}
	}

	@Override
	public String getUsableId(IdGenerator idGenerator) {
		//쓸수있는 id하나 가져오구
		String usableId = 
				idGeneratorDao.getUsableMinId(idGenerator.getTableName());
		if(usableId == null) {
			//만약 다 썼으면
			insertExtraId(idGenerator);//새로 채워줄게요
			//아 새로 채우고 받아와야죠
			usableId = 
					idGeneratorDao.getUsableMinId(idGenerator.getTableName());
		}
		//쟤는 이제 썼다고 표시합니당
		idGeneratorDao.updateId(usableId, true, idGenerator.getTableName());
		return usableId;
	}

	@Override
	public void returnUsedId(String id, IdGenerator idGenerator) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public List<String> generateId(String lastId, int num, IdGenerator idGenerator){
		List<String> idList = new ArrayList<>();
		//좀 복잡해보이니까 예시로
		//lastId = "PRO_000001"이라고 생각해봅시당
		
		for(int cnt=0; cnt<num; cnt++) {
			//default로 10개 id를 생성합니당
			String lastIdNumStr = lastId.substring(
					idGenerator.getPrefix().length());//접두떼고니까
			//lastIdNumStr = "000001"이 되겠죠
			int lastIdNum = Integer.parseInt(lastIdNumStr);
			// lastIdNum = 1;이 됩니당
			lastIdNum++;//단순히 1증가 시켜줄게요
			
			int numLength = idGenerator.getIdLength() - 
					idGenerator.getPrefix().length();
			//숫자의 갯수 = id전체의 길이 - 접두사의 길이
			int numRange = (int) Math.pow(10, numLength);
			//그러므로 숫자의 범위 = 10^숫자 갯수
			
			if(lastIdNum > numRange) {
				//id가 넘많이 생성되서 id길이가 최대10자리론 이제 못만드는데
				//이거 나중에 뭐 예외처리 해주자고요
			}else {
				StringBuffer generateId = new StringBuffer(
						Integer.toString(lastIdNum));
				//generateId = "2"
				for( ; generateId.length()<numLength; ) {
					generateId.insert(0, "0");
					//0을 계속 채웁니당
					//generateId = "000002"
				}
				//0을 다 채웠으면 접두 붙여주고요
				generateId.insert(0, idGenerator.getPrefix());
				//idList에 만든친구를 넣어주고
				idList.add(generateId.toString());
				//이제 또 새로 만들어야하는데 방금만든 친구가 마지막이 되겠죠?
				lastId = generateId.toString();
			}
		}
		return idList;
	}


}
