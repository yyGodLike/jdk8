package com.lhy.jdk8.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 测试jdk1.1与1.8的SimpleDateFormat的区别 
 * jdk1.1的SimpleDateFormat是线程不安全的。 
 * jdk1.8线程安全
 */
public class TestSimpleDateFormat {

	public static void main(String[] args){
		ExecutorService pool = Executors.newFixedThreadPool(10);
		/**
		 * 测试jdk1.1版本的SimpleDateFormat
		 * 开启10个线程同时执行SimpleDateFormat时期格式化，报错了
		 * Exception in thread "main" java.util.concurrent.ExecutionException: java.lang.NumberFormatException: For input string: "1111.E11112E2"
					at java.util.concurrent.FutureTask.report(FutureTask.java:122)
					at java.util.concurrent.FutureTask.get(FutureTask.java:192)
					at com.lhy.jdk8.date.TestSimpleDateFormat.main(TestSimpleDateFormat.java:36)
			Caused by: java.lang.NumberFormatException: For input string: "1111.E11112E2"
			或者
			java.util.concurrent.ExecutionException: java.lang.NumberFormatException: multiple points
				at java.util.concurrent.FutureTask.report(FutureTask.java:122)
				at java.util.concurrent.FutureTask.get(FutureTask.java:192)
				at com.lhy.jdk8.date.TestSimpleDateFormat.main(TestSimpleDateFormat.java:44)
			Caused by: java.lang.NumberFormatException: multiple points
			足以体现是线程不安全
		 */
		/*try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Callable<Date> task = () -> {
				return sdf.parse("20161121");
			};
			List<Future<Date>> reFutures = new ArrayList<>();
			for (int i = 1; i <= 10; i++) {
				reFutures.add(pool.submit(task));
			}
			for (Future<Date> future : reFutures) {
				System.out.println(future.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			pool.shutdown();
		}*/
		
		/**
		 * 通过ThreadLocal解决变量线程安全问题或者通过代码块加锁
		 */
		/*try {
			Callable<Date> task = () -> {
				return DateFormatThreadLocal.convert("20161121");
			};
			List<Future<Date>> reFutures = new ArrayList<>();
			for (int i = 1; i <= 10; i++) {
				reFutures.add(pool.submit(task));
			}
			for (Future<Date> future : reFutures) {
				System.out.println(future.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			pool.shutdown();
		}*/
		/**
		 * jdk1.8 使用LocalDate
		 */
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			Callable<LocalDate> task = () -> {
				return LocalDate.parse("20181011", dtf);
			};
			List<Future<LocalDate>> reFutures = new ArrayList<>();
			for (int i = 1; i <= 10; i++) {
				reFutures.add(pool.submit(task));
			}
			for (Future<LocalDate> future : reFutures) {
				System.out.println(future.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			pool.shutdown();
		}
	}
}
