package com.lhy.forkJoin;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * 测试0-10000000000L累加采用三种不同的方式：forkJoin，for，jdk1.8的并行流+reduce
 * 注意：在数据量小的情况下使用forkjoin的想过并没有for的单线程性能好，因为forkjoin需要拆分再合并，而for不需要，
 * 		数据量大的情况下forkjoin好，for单线程慢。
 * jdk1.8的并行流是底层调用forkjoin。
 * @author admin
 *
 */
public class TestForkJoinCalcuate{

	public static void main(String[] args) {
		
		Long val = 1000000000L;
		
		/***
		 * forkjoin计算
		 */
		Long start1 = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinCalcuate(0L, val);
		Long sum1 = pool.invoke(task);
		System.out.println("forkjoin结算结果:" + sum1);
		Long end1 = System.currentTimeMillis();
		System.out.println("耗时(秒):" + (end1 - start1) / 1000);
		
		System.out.println("----------------------------------------------");
		
		/**
		 * for循环计算，是单线程的
		 * 
		 * jdk1.8的时间获取
		 * Instant start2 = Instant.now();
		 */
		Instant start2 = Instant.now();
		Long sum2 = 0L;
		for(Long i = 0L; i <= val; i++){
			sum2 += i;
		}
		System.out.println("for结算结果:" + sum2);
		Instant end2 = Instant.now();
		System.out.println("耗时(秒):" + Duration.between(start2, end2).getSeconds());
		
		/**
		 * jdk1.8计算
		 * 将流(Stream)切换为并行流或顺序流
		 * 	parallel()并行流
		 * 	.sequential()顺序流
		 */
		/*Instant start3 = Instant.now();
		Long sum3 = LongStream.rangeClosed(0L, val)
						.parallel()
						.sum();
		System.out.println("jdk1.8并行流结算结果:" + sum3);
		Instant end3 = Instant.now();
		System.out.println("耗时(秒):" + Duration.between(start3, end3).getSeconds());*/
		
	}

}