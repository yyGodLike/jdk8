package com.lhy.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.text.html.Option;

import org.junit.Test;

import com.lhy.jdk8.Employee.Color;
import com.lhy.jdk8.Employee.Status;

/**
 * Stream的操作步骤 1、创建Stream 2、中间操作 3、终止操作
 */
public class TestStreamaAPI {

	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 18, 6666.66, Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Status.FREE), 
			new Employee(101, "张三1", 18, 9999.99, Status.FREE), 
			new Employee(101, "张三2", 18, 9999.99, Status.FREE), 
			new Employee(103, "王五", 28,3333.33, Status.VOCATION), 
			new Employee(104, "赵六", 8, 7777.77, Status.VOCATION),
			new Employee(105, "田七", 38, 5555.55, Status.BUSY)
	);

	// 1.创建Stream
	@Test
	public void test01() {

		// 1.Collection提供了两个方法，stream()与parallelStream()
		// 获取顺序流
		Stream<Employee> stream = emps.stream();
		// 获取并行流，forEach线程不安全，可以通过collect,reduce操作来满足线程安全
		Stream<Employee> parallelStream = emps.parallelStream();

		// 2.通过arrays中的stream()获取一个数组流
		Integer[] nums = new Integer[10];
		Stream<Integer> stream2 = Arrays.stream(nums);

		// 3.通过Stream类的静态方法of()
		Stream<Integer> stream3 = Stream.of(1, 2, 3, 4, 5);
		stream3.forEach(System.out::println);

		// 4.创建无限流,只显示前10条
		Stream.iterate(0, (e) -> e + 2).limit(10).forEach(System.out::println);

		// 迭代生成
		Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
		stream4.forEach(System.out::println);
	}

	/**
	 * 筛选与切片 filter：接受Lambda，从流中排除某些元素。 
	 * limit(n)：截断流，使其元素不超过给定的数量。
	 * skip(n)：跳过元素，返回一个扔掉了的n个元素流，若流中的元素不足n则返回null，可以与limit(n)查看分页效果。
	 * distinct：筛选，通过流所生成元素的hashcode()与equals()去除重复元素，如果Stream的类型是Javabean
	 * 则需要重写Javabean的hashcode()与equals()。
	 */
	@Test
	public void test02() {
		// forEach为终止操作，只有做了终止操作，所有的中间操作才会一次性全部执行，称为"惰性求值"
		emps.stream()
			.filter((e) -> {
					System.out.println("中间处理操作");
					return e.getAge() > 20;
				}
			)
			.skip(0)
			.limit(5)
			.distinct()
			.forEach(System.out::println);

	}

	/**
	 * 映射 map接受Lambda，将元素转换成其他形式处理或提取该元素，map接受一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
	 * flatMap接受一个函数作为参数，将流中的每个值都换成另一个流，最后把所有流都连接成一个流
	 */
	@Test
	public void test03() {

		// 提取
		emps.stream()
		// .map((e) -> e.getName())
				.map(Employee::getName).forEach(System.out::println);

		List<String> strList = Arrays.asList("aa", "bb", "cc", "dd");

		// 转换成它形式
		strList.stream()
		// .map((e) -> e.toUpperCase())
				.map(String::toLowerCase).forEach(System.out::println);

		System.out.println("-----------------------------------");
		/**
		 * 测试flatMap 
		 * 先使用map映射，map就是将一个流添加到另外一个流中， flatMap是将流中的值添加到另外一个流中
		 */
		Stream<Stream<Character>> stream2 = strList.stream().map(
				TestStreamaAPI::filterCharacter);
		stream2.forEach((sm) -> {
			sm.forEach(System.out::println);
		});
		System.out.println("-----------------------------------");
		Stream<Character> stream3 = strList.stream().flatMap(
				TestStreamaAPI::filterCharacter);
		stream3.forEach(System.out::println);

	}

	public static Stream<Character> filterCharacter(String str) {
		List<Character> list = new ArrayList<Character>(str.length());
		for (Character ch : str.toCharArray()) {
			list.add(ch);
		}
		return list.stream();
	}
	
	/**
	 * 排序
	 * sorted()--自然排序使用的Comparable类
	 * sorted(Comparator com)--自定义排序使用的Compartor类
	 */
	@Test
	public void test04(){
		List<String> list = Arrays.asList("bb","dd","cc","aa");
		list.stream()
			.sorted()
			.forEach(System.out::println);
		System.out.println("-----------------------------------");
		emps.stream()
			.sorted((e1,e2) ->{
				if(e1.getAge() == e2.getAge())
					return e1.getName().compareTo(e2.getName());
				else
					return -Integer.compare(e1.getAge(), e2.getAge()); //加上-就是降序
			})
			.forEach(System.out::println);
	}
	
	
	/**
	 * 终止操作
	 * allMatch:检查是否匹配所有元素
	 * anyMatch:检查是否至少匹配一个元素
	 * noneMatch:返回第一个元素
	 * findAny:返回当前流中的任意元素
	 * findFirst:返回第一个元素
	 * count:返回流中元素的总数
	 * max:返回流中元素的最大值
	 * min:返回流中元素的最小值
	 */
	@Test
	public void test05(){
		
		Boolean b1 = emps.stream().allMatch((e) -> e.getStatus().equals(Status.BUSY));
		System.out.println(b1);
		
		Boolean b2 = emps.stream().anyMatch((e) -> e.getStatus().equals(Status.FREE));
		System.out.println(b2);
		
		Boolean b3 = emps.stream().noneMatch((e) -> e.getStatus().equals(Status.FREE));
		System.out.println(b3);
		
		//返回任意一条可以使用并行流，多线程匹配，谁先匹配到就返回谁。
		//串行流是按顺序找的
		Optional<Employee> op2 = emps.parallelStream()
									.filter((e) -> e.getAge() == 18)
									.findAny();
		System.out.println(op2.get());
		
		Optional<Employee> op = emps.stream()
				.sorted((e1,e2) -> -Double.compare(e1.getSalary(), e2.getSalary()))
				.findFirst();
		System.out.println(op.get());
		
		Long count = emps.stream().filter((e) -> e.getAge() == 18).count();
		System.out.println(count);
		
		//获取最高工资的值，先提取在统计
		Optional<Double> op3 = emps.stream()
				.map(Employee::getSalary)
				.max(Double::compare);
		System.out.println(op3.get());
		//统计一个集合中工资最高的员工(Employee)对象
		Optional<Employee> op4 = emps.stream().max((e1,e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
		System.out.println(op4.get());
		
		//获取最低工资的值，先提取在统计
		Optional<Double> op5 = emps.stream()
				.map(Employee::getSalary)
				.min(Double::compare);
		System.out.println(op5.get());
		//统计一个集合中工资最低的员工(Employee)对象
		Optional<Employee> op6 = emps.stream().min((e1,e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
		System.out.println(op6.get());
	}
	
	//注意：流(Stream)进行了终止操作后，不能再次使用
	@Test
	public void test06(){
		Stream<Employee> stream = emps.stream()
		 .filter((e) -> e.getStatus().equals(Status.FREE));
		
		System.out.println(stream.count());
		
		stream.map(Employee::getSalary)
			.max(Double::compare);
	}
	
	
	/**
	 * 归约
	 * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) 
	 * ——可以将流中元素反复结合起来，得到一个值。
	 */
	@Test
	public void test07(){
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
		/**
		 * (0, (x , y) -> x + y) 解释：
		 *    x     y
		 *    0+1 + 2
		 *    3   + 3
		 *    6   + 4
		 *    10  + 5
		 *    15  + 6
		 *    21  + 7
		 * 这样子每次传值相加
		 */
		Integer num = list.stream().reduce(0, (x , y) -> x + y);
		System.out.println(num);
		
		/**
		 * 以下返回类型为Optional是因为emps集合数据可能为空，为啥上面累加返回的是基本数据类型是因为传入了起始值是0开始累加就算集合为空也会是0。
		 * Double的sum方法是jdk1.8的
		 */
		Optional<Double> op = emps.stream()
				.map(Employee::getSalary)
				.reduce(Double::sum);
		System.out.println(op.get());
	}
	
	
	/**
	 * collect——将流转换成其它形式，接受一个Collector接口的实现，用于给Stream中的元素做汇总的方法
	 */
	@Test
	public void test08(){
		
		List<String> list = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toList());
		list.forEach(System.out::println);
		
		System.out.println("-----------------------------------");
		
		Set<String> set = emps.stream()
				.map(Employee::getName)
				.collect(Collectors.toSet());
		set.forEach(System.out::println);
		
		System.out.println("-----------------------------------");
		
		HashSet<String> hs = emps.stream()
					.map(Employee::getName)
					.collect(Collectors.toCollection(HashSet::new));
		System.out.println(hs);
		
		System.out.println("-----------------------------------");
		
		Optional<Double> max = emps.stream()
					.map(Employee::getSalary)
					//.collect(Collectors.maxBy((e,q) -> Double.compare(e, q)));
					.collect(Collectors.maxBy(Double::compareTo));
		System.out.println(max.get());
		
		System.out.println("-----------------------------------");
		
		Optional<Double> max2 = emps.stream()
				.map(Employee::getSalary)
				.collect(Collectors.maxBy(Double::compare));
		System.out.println(max2.get());
		
		Optional<Employee> op = emps.stream()
			.collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
		
		System.out.println(op.get());
		
		Double sum = emps.stream()
			.collect(Collectors.summingDouble(Employee::getSalary));
		System.out.println(sum);
		
		Double avg = emps.stream()
			.collect(Collectors.averagingDouble(Employee::getSalary));
		System.out.println(avg);
		
		Long count = emps.stream()
			.collect(Collectors.counting());
		System.out.println(count);
		
		System.out.println("--------------------------------------------");
		
		DoubleSummaryStatistics dss = emps.stream()
			.collect(Collectors.summarizingDouble(Employee::getSalary));
		System.out.println(dss.getMax());
		
	}
	
	/**
	 * 分组
	 */
	@Test
	public void test09(){
		Map<Status, List<Employee>> map = emps.stream()
				.collect(Collectors.groupingBy(Employee::getStatus));
		System.out.println(map);
	}
	
	/**
	 * 多级分组
	 */
	@Test
	public void test10(){
		Map<Status, Map<String, List<Employee>>> map = emps.stream()
				.collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
					if(((Employee) e).getAge() >= 60)
						return "老年";
					else if(((Employee) e).getAge() >= 35)
						return "中年";
					else
						return "成年";
				})));
			
			System.out.println(map);
	}
	
	/**
	 * 分区
	 */
	@Test
	public void test11(){
		Map<Boolean, List<Employee>> map = emps.stream()
				.collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
			System.out.println(map);
	}
	
}
