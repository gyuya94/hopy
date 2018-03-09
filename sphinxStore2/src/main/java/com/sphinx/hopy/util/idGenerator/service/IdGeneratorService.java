package com.sphinx.hopy.util.idGenerator.service;

import java.util.List;

import com.sphinx.hopy.util.idGenerator.IdGenerator;

public interface IdGeneratorService {
	/** 여유분의 id를 넣음. default갯수로는 10개*/
	public void insertExtraId(IdGenerator idGenerator);
	
	public void insertExtraId(int number, IdGenerator idGenerator);
	
	/** 쓸수있는 Id를 하나 받아옵니당*/
	public String getUsableId(IdGenerator idGenerator);
	
	/** 다 쓴 id는 반납하자는 건데 아직 여기까진 필요없을거같아요*/
	public void returnUsedId(String id, IdGenerator idGenerator);
	
	/** 진짜루 id들을 생성하는 함수에여*/
	public List<String> generateId(String lastId, int num, IdGenerator idGenerator);
}
