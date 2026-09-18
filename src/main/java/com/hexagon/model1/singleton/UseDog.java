package com.hexagon.model1.singleton;

public class UseDog {

	public static void main(String[] args) {
		// 공격자 - 방해하겨
		
		Dog d1 = Dog.getInstance();
		Dog d2 = Dog.getInstance();
		Dog d3 = Dog.getInstance();
		Dog d4 = Dog.getInstance();
		Dog d5 = Dog.getInstance();
		Dog d6 = Dog.getInstance();

		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d3);
		System.out.println(d4);
		System.out.println(d5);
		System.out.println(d6);
	}

}
