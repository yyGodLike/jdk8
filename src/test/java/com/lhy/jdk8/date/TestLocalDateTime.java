package com.lhy.jdk8.date;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Set;

import org.junit.Test;

/**
 * Jdk1.8日期类是一套全新的API，是不可变线程安全的。
 * 1.8的日期API是JSR-310的实现，并且是工作在ISO-8601日历系统基础上的，但我们也可以在非ISO的日历上。
 * JDK8的日期API大致分为以下几个包：
 * java.time包：JDK8中的基础包，所有常用的基础类都是这个包的一部分，如LocalDate，LocalTime，LocalDateTime等等，所有这些类都是不可变且线程安全的。
 * java.time.chrono包：这个包为非ISO的日历系统定义了一些API，我们可以在借助这个包中的一些类扩展我们自己的日历系统。
 * java.time.format包：这个包很明显了，格式化和解析日期时间对象，一般java.time包中的类都差不多能满足我们的需求了，如果有需要，可以调用这个包下的类自定义解析方式。
 * java.time.temporal包：这个包很有意思，封装了一些获取某个特定日期和时间的接口，比如某月的第一天或最后一天，并且这些方法都是属于特别好认的方法。
 * java.time.zone包：这个包就是时区相关的类。
 * ISO-8601简单阐述：
 * 	ISO-8601：国际标准化组织制定的日期和时间的表示方法，全程为"数据存储和交换形式,信息交换,日期和时间的表示方法"简称为ISO-8601。
 * 	日的表示：小时、分和秒都用2位数表示，对UTC时间最后加一个大写字母Z，其他时区用实际时间加时差表示。如UTC时间下午2点30分5秒表示为
 * 	14:30:05Z或143005Z，当时的北京时间表示为22:30:05+08:00或223005+0800，也可以简化成223005+08。
 * 	日期和时间的组合表示：合并表示时，要在时间前面加一大写字母T，如要表示北京时间2004年5月3日下午5点30分8秒，
 * 	可以写成2004-05-03T17:30:08+08:00或20040503T173008+08。
 * 
 */
public class TestLocalDateTime {

	/**
	 * LocalDate：get 年月日 
	 * 默认使用亚洲上海时区获取时间转换
	 */
	@Test
	public void testDate() {

		//获取当前日期
		LocalDate localDate1 = LocalDate.now();
		System.out.println(localDate1);
		//自定义构建日期
		LocalDate localDate2 = LocalDate.of(2018, 1, 20);
		//字符串转日期
		LocalDate localDate3 = LocalDate.parse("2018-10-25");
		System.out.println(localDate2);
		//获取本月第一天
		LocalDate firstDayOfMonth = localDate1.with(TemporalAdjusters.firstDayOfMonth());
		System.out.println(firstDayOfMonth);
		//获取本月第二天，想获取第几天就传几
		LocalDate secondDayOfMonth = localDate1.withDayOfMonth(2);
		//获取本月最后一天
		LocalDate lastDayMonth = localDate1.with(TemporalAdjusters.lastDayOfMonth());
		//year+1 年+
		System.out.println(localDate1.plusYears(1));
		//Month+1 月+
		System.out.println(localDate1.plusMonths(1));
		//day+1 天+
		System.out.println(localDate1.plusDays(1));
		//Weeks+1 加一个星期其实就是day+7
		System.out.println(localDate1.plusWeeks(1));
		//获取本年的第多少天的日期
		LocalDate yesDay = localDate1.withDayOfYear(234);
		System.out.println(yesDay);
		/**
		 * 计算两个日期相差的天数
		 * 比如
		 * 2018-10-10.until(2018-10-15) 是第一个时间与第二个时间相差多少天，这个比是相差5天
		 * 2018-10-15.until(2018-10-10) 是第一个时间与第二个时间相差多少天，这个比是相差-5天
		 */
		System.out.println(localDate1.until(localDate3, ChronoUnit.DAYS));
		//计算两个日期间的周数(7天)
		System.out.println(localDate3.until(localDate1, ChronoUnit.WEEKS));
	}
	
	/**
	 * LocalTime：get 时分秒毫秒 
	 */
	@Test
	public void testTime(){
		
		//获取当前时间，包含毫秒数
		LocalTime localTime = LocalTime.now();
		System.out.println(localTime);
		//自定义构建时间
		LocalTime localTime2 = LocalTime.of(20, 14, 20);
		//获取当前时间，不包含毫秒数
		localTime = localTime.withNano(0);
		//字符串转为时间
		localTime = LocalTime.parse("22:22:22");
	}
	
	/**
	 * LocalDate，LocalTime，LocalDateTime这三个类基本上处理了大部分的日期，时间。
	 * 而与这三个类经常结合使用的还有如下几个类：Year,Month,YearMonth,MonthDay,DayOfWeek。
	 * 并且，我们在LocalDate相关类的操作也可以通过Year，Month等来实现
	 * Year year =Year.now();
	 * System.out.println(year.getValue()); // 2018
	 */
	
	/**
	 * LocalDateTime：get 年月日时分秒毫秒，与原先的java.util.Date很像
	 * 注意点：
	 * 		LocalDateTime默认格式是2018-10-20T18:16:22.223这个格式，跟我们经常使用的格式不太符合，所以需要指定格式。
	 * 		DateTimeFormatter本身提供了许多静态格式常量都是些ISO开头的，如果不满足还可以通过.ofPattern(格式)自定义。
	 * 		直接打印LocalDateTime对象输出的时间是带T的，是因为LocalDateTime对象的toString返回值就是日期+T+时间。
	 */
	@Test
	public void testDateTime(){
		
		//获取当前年月日时分秒
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		// 自定义构建一个时间
		LocalDateTime ldt2 = LocalDateTime.of(2016, 11, 21, 10, 10, 10);
		System.out.println(ldt2);
		ldt2 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
		//格式化当前时间
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		System.out.println(formatter.format(ldt));
		//LocalDateTime.now().with*(num) 可以直接设置年月日时分秒
		System.out.println(LocalDateTime.now().withDayOfMonth(2));

		System.out.println("年:" + ldt.getYear());
		System.out.println("月:" + ldt.getMonthValue());
		System.out.println("日:" + ldt.getDayOfMonth());
		System.out.println("时:" + ldt.getHour());
		System.out.println("分:" + ldt.getMinute());
		System.out.println("秒:" + ldt.getSecond());
	}
	
	
	/**
	 * 操作TemporalAdjusters类时间校正器
	 */
	@Test
	public void testTemporalAdjusters(){
		LocalDate localDate = LocalDate.now();
		// 1. 本月第一天
		LocalDate firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
		// 2. 本月最后一天
		LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
		// 3. 本年第一天
		LocalDate firstDayOfYear = localDate.with(TemporalAdjusters.firstDayOfYear());
		// 4. 下个月第一天
		LocalDate firstDayOfNextMonth = localDate.with(TemporalAdjusters.firstDayOfNextMonth());
		// 5. 本年度最后一天
		LocalDate lastDayOfYear = localDate.with(TemporalAdjusters.lastDayOfYear());

		System.out.println(firstDayOfMonth);
		System.out.println(lastDayOfMonth);
		System.out.println(firstDayOfYear);
		System.out.println(firstDayOfNextMonth);
		System.out.println(lastDayOfYear);
		
		// 下一个周日是几号
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime ldt3 = localDateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		System.out.println(ldt3);

		// 自定义：下一个工作日
		LocalDateTime ldt5 = localDateTime.with((l) -> {
			LocalDateTime ldt4 = (LocalDateTime) l;

			DayOfWeek dow = ldt4.getDayOfWeek();

			if (dow.equals(DayOfWeek.FRIDAY)) {
				return ldt4.plusDays(3);
			} else if (dow.equals(DayOfWeek.SATURDAY)) {
				return ldt4.plusDays(2);
			} else {
				return ldt4.plusDays(1);
			}
		});
	}
	
	

	/**
	 * Instant：时间戳。(使用Unix元年 1970年1月1日00:00:00所经历的毫秒值) 默认使用UTC时区
	 */
	@Test
	public void test02() {

		Instant instant = Instant.now();
		System.out.println(instant);

		// ZoneOffset时区偏移量->加8小时
		OffsetDateTime odt = instant.atOffset(ZoneOffset.ofHours(8));
		System.out.println(odt);

		// 从1970年1月1日00:00:00加5秒
		Instant ins2 = Instant.ofEpochSecond(5);
		System.out.println(ins2);
	}

	/**
	 * Duration:用于计算两个"时间"间隔 
	 * Period:用户计算两个"日期"间隔
	 */
	@Test
	public void test03() throws InterruptedException {

		Instant ins1 = Instant.now();
		Thread.sleep(1000);
		Instant ins2 = Instant.now();
		System.out.println("所耗费时间为：" + Duration.between(ins1, ins2));

		System.out.println("-------------------------------------------");

		LocalDate ld1 = LocalDate.now();
		LocalDate ld2 = LocalDate.of(2011, 1, 1);

		Period pe = Period.between(ld2, ld1);
		System.out.println(pe.getYears());
		System.out.println(pe.getMonths());
		System.out.println(pe.getDays());
	}

	// 5. DateTimeFormatter : 解析和格式化日期或时间
	@Test
	public void test05() {
		// DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
		DateTimeFormatter dtf = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime ldt = LocalDateTime.now();
		String strDate = ldt.format(dtf);

		System.out.println(strDate);

		LocalDateTime newLdt = ldt.parse(strDate, dtf);
		System.out.println(newLdt);
	}

	/**
	 * Date转LocalDateTime,LocalDate,LocalTime
	 */
	@Test
	public void test06() {
		Date date = new Date();
		System.out.println("current date: " + date);

		// Date -> LocalDateTime
		LocalDateTime localDateTime = date.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
		System.out.println("localDateTime by Instant: " + localDateTime);

		// Date -> LocalDate
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		System.out.println("localDate by Instant: " + localDate);

		// Date -> LocalTime
		LocalTime localTime = date.toInstant().atZone(ZoneId.systemDefault())
				.toLocalTime();
		System.out.println("localTime by Instant: " + localTime);
	}

	/**
	 * LocalDateTime,LocalDate,LocalTime转Date
	 */
	@Test
	public void test07() {

		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("localDateTime: " + localDateTime);

		// LocalDateTime -> Date
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault())
				.toInstant());
		System.out.println("LocalDateTime -> current date: " + date);

		// LocalDate -> Date，时间默认都是00
		LocalDate localDate = LocalDate.now();
		date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
				.toInstant());
		System.out.println("LocalDate -> current date: " + date);
	}

	/**
	 * ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
	 */
	@Test
	public void test08() {
		//获取支持的所有时区
		Set<String> set = ZoneId.getAvailableZoneIds();
		set.forEach(System.out::println);
		
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
		System.out.println(ldt);

		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
		System.out.println(zdt);
	}

}
