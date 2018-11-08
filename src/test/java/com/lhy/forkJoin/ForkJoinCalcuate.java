package com.lhy.forkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * 使用ForkJoin必须继承RecursiveTask类类型指的是compute方法的返回值类型 需求：
 * 1到10000000000的累加，每一次fork的临界值不小于等于10000就继续fork
 * 
 * @author admin
 *
 */
public class ForkJoinCalcuate extends RecursiveTask<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForkJoinCalcuate() {
		super();
	}

	public ForkJoinCalcuate(Long startVal, Long endVal) {
		super();
		this.startVal = startVal;
		this.endVal = endVal;
	}

	/**
	 * 起始值与结束值
	 */
	private Long startVal, endVal;

	/**
	 * 临界值
	 */
	private static final Long THRESHOLD = 10000L;

	@Override
	protected Long compute() {
		Long length = endVal - startVal;
		if (length <= THRESHOLD) {
			Long sum = 0L;
			for (Long i = startVal; i <= endVal; i++) {
				sum += i;
			}
			return sum;
		} else {
			/**
			 * 每一次fork产生两个子fork，将在startVal与endVal的中间取中间值作为第一个fork的endVal，第二个fork的startVal+1。
			 */
			Long middle = (startVal + endVal) / 2;
			ForkJoinCalcuate f1 = new ForkJoinCalcuate(startVal, middle);
			f1.fork(); // 将该子任务放至线程队列

			ForkJoinCalcuate f2 = new ForkJoinCalcuate(middle + 1, endVal);
			f2.fork();

			// join各个子任务结果集返回
			return f1.join() + f2.join();
		}
	}
}