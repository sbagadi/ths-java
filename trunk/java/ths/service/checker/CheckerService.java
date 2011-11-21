package ths.service.checker;

public class CheckerService {
	
	public static final String DEFAULT_BEAN_NAME = "checkerServices";
	private String name;
	private int age;
	
	public CheckerService() {
		System.out.println("create CheckerService object.");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
