package com.lhy.jdk8;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * 一、方法引用：若Lambda体中的功能，已经有方法提供了实现，可以使用方法引用
 * 			(可以将方法理解为Lambda表达式的另外一种表现形式)
 * 	1、对象的引用::实例方法名
 * 	2、类名::静态方法名
 * 	3、类名::实例方法名
 *	注意：
 *		①.方法引用所引用的方法的参数列表与返回值类型需要与函数式接口的抽象方法的参数列表与返回值类型保持一致。
 *		②.若Lambda的参数列表的第一个参数是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式:ClassName::methodName。
 * 二、构造器引用：构造器的参数列表需要与函数式接口中的方法参数列表保持一致，函数式接口的方法会根据参数自动匹配上构造器方法。
 * 	格式：类名::new
 * 三、数组引用
 * 	格式：类型[]::new
 *	
 */
public class TestmethodRef {
	
	/**
	 * 类名::实例方法名
	 */
	@Test
	public void test01(){
		
		Consumer<String> consumer = (str) -> System.out.println(str);
		consumer.accept("hello");
		
		System.out.println("-----------------------------------------");
		
		Consumer<String> consumer2 = System.out::println;
		consumer2.accept("hello2");
	}
	
	/**
	 * 对象的引用::实例方法名
	 */
	@Test
	public void test02(){
		Employee employee = new Employee(1,"张三", 18,3000.0);
		
		//旧写法
		Supplier<String> sp = () -> employee.getName();
		System.out.println(sp.get());
		
		System.out.println("--------------------------");
		
		//新写法
		Supplier<String> sp2 = employee::getName;
		System.out.println(sp2.get());
	}
	
	/**
	 * 类名::静态方法名
	 */
	@Test
	public void test03(){
		BiFunction<Double, Double, Double> fun = (x,y) -> Math.max(x, y);
		System.out.println(fun.apply(1.5,22.0));
		
		System.out.println("--------------------------");
		
		BiFunction<Double, Double, Double> fun2 = Math::max;
		System.out.println(fun2.apply(5.0, 5.1));
		
	}
	
	/**
	 * 类名::实例方法名
	 */
	@Test
	public void test04(){
		
		BiPredicate<String, String> bp = (x,y) -> x.equals(y);
		System.out.println(bp.test("1", "2"));
		
		System.out.println("--------------------------");
		
		BiPredicate<String, String> bp2 = String::equals;
		System.out.println(bp2.test("a", "a"));
		
		System.out.println("--------------------------");
		
		Function<Employee, String> fun = (e) -> e.show();
		System.out.println(fun.apply(new Employee()));
		
		System.out.println("-----------------------------------------");
		
		Function<Employee, String> fun2 = Employee::show;
		System.out.println(fun2.apply(new Employee()));
	}
	
	/**
	 * 构造器引用
	 */
	@Test
	public void test05(){
		Supplier<Employee> sup = () -> new Employee();
		System.out.println(sup.get());
		
		System.out.println("------------------------------");
		
		Supplier<Employee> sup2 = Employee::new;
		System.out.println(sup2.get());
		
		System.out.println("------------------------------");
		
		Function<String, Employee> fun = Employee::new;
		System.out.println(fun.apply("张三"));
		
		System.out.println("------------------------------");
		
		BiFunction<String, Integer, Employee> dfun = Employee::new;
		System.out.println(dfun.apply("李四", 19));
	}
	
	/**
	 * 数组引用,产出一个数组对象并制定长度
	 */
	@Test
	public void test06(){
		Function<Integer, String[]> fun = (args) -> new String[args];
		String[] strs = fun.apply(10);
		System.out.println(strs.length);
		
		System.out.println("--------------------------");
		
		Function<Integer, Employee[]> fun2 = Employee[] :: new;
		Employee[] emps = fun2.apply(20);
		System.out.println(emps.length);
	}
}
