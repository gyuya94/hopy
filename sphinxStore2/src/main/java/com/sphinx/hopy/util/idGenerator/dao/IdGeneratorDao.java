package com.sphinx.hopy.util.idGenerator.dao;

public interface IdGeneratorDao {
	/** 쓰고 있는지 안쓰고있는지 상관없이 그냥 맨 마지막애를 가져옴
	 * 새로 추가하려면 맨 마지막거 알아와야하니 */
	public String getLastId(String tableName);
	
	/** 얘는 조금 다른게 쓸수있는 id를 하나 가져오는거야*/
	public String getUsableMinId(String tableName);

	public void insertId(String id, String tableName);

	public void updateId(String id, boolean occupancy, String tableName);

}
