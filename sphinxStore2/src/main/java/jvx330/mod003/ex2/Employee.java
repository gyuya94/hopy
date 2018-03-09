package jvx330.mod003.ex2;

public class Employee {
	private String name;
	private Dept dept;
	private double salary;
	private Date regDate;
	
	public Employee(String name, Dept dept, double salary, Date regDate) {
		super();
		this.name = name;
		this.dept = dept;
		this.salary = salary;
		this.regDate = regDate;
	}

	public String getDetails() {
		return "Employee [name=" + name + ", dept=" + dept + ", salary="
				+ salary + ", regDate=" + regDate + "]";
	}
	
	
}
