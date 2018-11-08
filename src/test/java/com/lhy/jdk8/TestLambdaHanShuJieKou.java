package com.lhy.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * Java8内置的四大核心函数接口
 * Consumert<T> ：消费型接口
 * 			void accept(T t);
 * Supplier<T> ：供给型接口
 * 			T get();
 * Function<T,R> ：函数型接口
 * 			R apply(T t);
 * Predicate<T> ：断言型接口
 * 			boolean test(T t);
 */
public class TestLambdaHanShuJieKou {

	
	/**
	 * 断言型接口
	 */
	@Test
	public void test4(){
		List<String> list = Arrays.asList("aa","bbb","ccc","dddd");
		list = filterStr(list, (s) -> s.length() > 2);
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	public List<String> filterStr(List<String> list, Predicate<String> pre){
		List<String> strList = new ArrayList<String>();
		for (String string : list) {
			if(pre.test(string)){
				strList.add(string);
			}
		}
		return strList;
	}
	
	
	/**
	 * Function<T,R>函数型接口
	 */
	@Test
	public void test3(){
		String str = strHandler("\t\t\t abc", (a) -> a.trim());
		System.out.println(str);
		
		String subStr = strHandler(str, (a) -> a.substring(0, 2));
		System.out.println(subStr);
		
	}
	
	public String strHandler(String str,Function<String, String> fun){
		return fun.apply(str);
	}
	
	
	/**
	 * Supplier<T>供给型接口
	 */
	@Test
	public void test2(){
		List<Integer> list = getNumList(10, () -> (int)(Math.random() * 10));
		list.stream().forEach((e) -> System.out.println(e));
	}
	
	public List<Integer> getNumList(Integer num, Supplier<Integer> sup){
		
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			list.add(sup.get());
		}
		return list;
	}
	
	
	/**
	 * 消费型接口
	 */
	@Test
	public void test1(){
		happy(500d, (e) -> System.out.println("吃饭消费"+e+"元"));
	}
	
	public void happy(Double money,Consumer<Double> con){
		con.accept(money);
	}
}
