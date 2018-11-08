package com.lhy.jdk8;

import java.util.Optional;

import org.junit.Test;

/**
 * 一、Optional容器类：用于尽量避免空指针异常 Optional.of(T t)：创建一个Optional实例。
 * Optional.empty()：创建一个空的Optional实例。 Optional.ofNullable(T
 * t)：若t不为null，创建Optional实例否则创建空实例。 ispresent()：判断是否包含值。 orElse(T t) :
 * 如果调用对象包含值，返回该值，否则返回t orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 *
 */
public class TestOptional {

	@Test
	public void test01() {
		Optional<Employee> op = Optional
				.of(new Employee(101, "张三", 18, 9999.99));

		Optional<String> op2 = op.map(Employee::getName);
		System.out.println(op2.get());

		Optional<String> op3 = op.flatMap((e) -> Optional.of(e.getName()));
		System.out.println(op3.get());
	}

	@Test
	public void test02(){
		Optional<Employee> op = Optional.ofNullable(new Employee());
		
		if(op.isPresent()){
			System.out.println(op.get());
		}
		
		Employee emp = op.orElse(new Employee("张三"));
		System.out.println(emp);
		
		Employee emp2 = op.orElseGet(() -> new Employee());
		System.out.println(emp2);
	}
}
