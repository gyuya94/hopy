package jvx330.mod003.ex2;

public class Foo {
	private Bar bar;
	
	public Foo() {
		System.out.println("Foo() is called");
		
	}
	
	public Foo(Bar bar) {
		this.bar = bar;
		System.out.println("Foo(bar) is called");
	}
	
	public String getRelationship() {
		return "Foo has relationship with" + bar;
	}
	
	public void setBar(Bar bar) {
		this.bar = bar;
	}
}
