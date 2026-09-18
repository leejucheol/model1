package com.hexagon.modle1.singleton;

public class Dog {
	// singleton을 구현하기 위한 첫단계(외부에서 인스턴스를 직접 생성하는 것을 막자)
	// 생성자를 private으로 묶어 버리자
	private static Dog instance;

	private Dog() {
		
	}
	
	public static Dog getInstance() { // 인스턴스 메서드 벌러
		if(instance== null) {
			instance = new Dog();
		}
		return instance;	
		}
	}
