package jvx330.mod003.ex2;

public class Dept {
	private String deptName;
	private String deptAddr;
	
	public Dept() {
		
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptAddr() {
		return deptAddr;
	}

	public void setDeptAddr(String deptAddr) {
		this.deptAddr = deptAddr;
	}

	@Override
	public String toString() {
		return "Dept [deptName=" + deptName + ", deptAddr=" + deptAddr + "]";
	}
	
	
}
