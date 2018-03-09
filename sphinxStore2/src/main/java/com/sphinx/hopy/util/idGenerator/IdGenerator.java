package com.sphinx.hopy.util.idGenerator;

public class IdGenerator {
	private String prefix = "";
	private int idLength = 10;
	private String tableName = "";

	public IdGenerator(String prefix, int idLength, String tableName) {
		super();
		this.prefix = prefix;
		this.idLength = idLength;
		this.tableName = tableName;
	}

	public IdGenerator() {
		super();
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getIdLength() {
		return idLength;
	}

	public void setIdLength(int idLength) {
		this.idLength = idLength;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "IdGenerator [prefix=" + prefix + ", idLength=" + idLength
				+ ", tableName=" + tableName + "]";
	}

}
