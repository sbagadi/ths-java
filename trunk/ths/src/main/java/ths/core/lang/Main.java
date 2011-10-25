package ths.core.lang;

public class Main {

	public static <T> void test(T[] array) {
		System.out.println(array.length);
	}
	
	public static void test2(Object obj) {
		System.out.println(obj.getClass().getName());
	}
	
	public static void main(String[] args) {
		
		/*
		int[] a = {1, 2};
		Integer[] b = {1, 2};
		test(a);
		*/
		
		Integer[] b = {1, 2};
		test2(b);
	}
}
