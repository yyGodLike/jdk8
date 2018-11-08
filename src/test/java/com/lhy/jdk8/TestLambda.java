package com.lhy.jdk8;

import java.util.Comparator;
import java.util.Date;
import java.util.function.Consumer;

import org.junit.Test;

/**
 * 一、Lambda表达式的基础语法，Java8中引入了一个新的操作符"->"该操作符称为箭头操作符或Lambda操作符，
 * 箭头操作符将Lambda表达式拆分为两部分： 左侧：Lambda表达式的参数列表 右侧Lambda表达式所需执行的内容， 即Lambda体。
 * 语法格式一：无参数，无返回值 参考test1()。 语法格式二：有一个参数，无返回值 参考test02()。
 * 语法格式三：若只有一个参数可以省略()不写，参考test02()。
 * 语法格式四：有两个或以上参数，有返回值并且Lambda体中有多条语句，参考test03()。
 * 语法格式五：若Lambda表达式中只有一个语句，return和大括号都可以省略不写，参考test04()。
 * 语法格式六：Lambda表达式的参数列表的数据类型可以省略不写，因为JVM虚拟机通过上下文推断出类型，即"类型推断"
 * 			Comparator<Date> comparator = (x, y) -> x.compareTo(y);
 * Lambda表达式纪要：左右遇一括号省，左侧推断类型省，总之能省则省
 * 二、Lambda表达式需要"函数接口"的支持
 * 	函数式接口：接口中只有一个抽象方法的接口，称为函数式接口，可以使用@FunctionalInterface在class上修饰可以
 * 			检查是否是函数式接口。
 *
 **/
public class TestLambda {

	@Test
	public void test1() {
		/**
		 * 在一个局部内部类中使用到了一个同级别的局部变量， 在jdk1.8之前必须使用final修饰，在jdk1.8之后
		 * 省略final关键字，但是还是一个常量。
		 */
		int num = 0;
		/**
		 * jdk1.8之前的写法，new 一个接口需要实现一个匿名(局部)内部类
		 */
		Runnable r = new Runnable() {

			@Override
			public void run() {
				System.out.println("jdk1.8 before Hello World" + num);
			}
		};
		r.run();

		System.out.println("----------------------------------------");

		/**
		 * jdk1.8的写法
		 */
		Runnable r2 = () -> System.out.println("jdk1.7 later Hello World");
		r2.run();
	}

	@Test
	public void test02() {
		Consumer<String> consumer = x -> System.out.println(x);
		consumer.accept("今天是2018年10月4号下午18:18");
	}

	@Test
	public void test03() {

		Comparator<Integer> comparator = (x, y) -> {
			System.out.println("x: " + x + ",y: " + y);
			return x.compareTo(y);
		};
		System.out.println(comparator.compare(100, 10));
	}

	@Test
	public void test04() {
		Comparator<Date> comparator = (x, y) -> x.compareTo(y);
		System.out.println(comparator.compare(new Date(), new Date()));
	}
	
	/**
	 * 需求：对一个数进行运算
	 */
	@Test
	public void test05(){
		System.out.println(show(100,(x) -> x+100));
	}
	
	public Integer show(Integer num, MyFunction myfun){
		return myfun.getValue(num);
	}
}
