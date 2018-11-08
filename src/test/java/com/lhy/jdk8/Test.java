package com.lhy.jdk8;

import java.util.Arrays;
import java.util.List;

import com.lhy.jdk8.Employee.Status;

public class Test {
	public static void main(String[] args) {
		List<Employee> emps = Arrays.asList(
				new Employee(102, "李四", 18, 6666.66, Status.BUSY),
				new Employee(101, "张三", 18, 9999.99, Status.FREE)
		);
		
		List<Employee> emps2 = Arrays.asList(
				new Employee(102, "李四", 18, 6666.66, Status.BUSY),
				new Employee(101, "张三", 18, 9999.99, Status.FREE)
		);
		
		for (Employee employee : emps2) {
			if(emps.contains(employee)){
				System.out.println("存在");
			}
		}
		
	}
}
