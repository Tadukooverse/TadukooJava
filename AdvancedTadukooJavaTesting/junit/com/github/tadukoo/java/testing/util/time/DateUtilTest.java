package com.github.tadukoo.java.testing.util.time;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class DateUtilTest extends JavaClassParsingTest{
	
	public DateUtilTest(){
		super("""
				package com.github.tadukoo.util.time;
				
				import java.time.LocalDate;
				import java.time.Month;
				import java.time.ZoneId;
				import java.util.Date;
				
				/**
				 * Date Util provides utilities for dealing with {@link Date}s.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.2.1
				 */
				public final class DateUtil{
				\t
					/** Not allowed to make a DateUtil */
					private DateUtil(){ }
				\t
					/**
					 * Creates a {@link Date} with the given month, day, and year.
					 *\s
					 * @param month The {@link Month}
					 * @param day The day of the month
					 * @param year The year
					 * @return A {@link Date} for the given month, day, and year
					 */
					public static Date createDate(Month month, int day, int year){
						return convertToDate(LocalDate.of(year, month, day));
					}
				\t
					/**
					 * Creates a {@link Date} with the given month, day, and year.
					 *\s
					 * @param month The month as a string
					 * @param day The day of the month
					 * @param year The year
					 * @return A {@link Date} for the given month, day, and year
					 */
					public static Date createDate(String month, int day, int year){
						return createDate(MonthUtil.parseFromString(month), day, year);
					}
				\t
					/**
					 * Creates a {@link Date} with the given month, day, and year.
					 *\s
					 * @param month The month as an integer (1 = January)
					 * @param day The day of the month
					 * @param year The year
					 * @return A {@link Date} for the given month, day, and year
					 */
					public static Date createDate(int month, int day, int year){
						return convertToDate(LocalDate.of(year, month, day));
					}
				\t
					/**
					 * Converts the given {@link LocalDate} to a {@link Date}.
					 *\s
					 * @param localDate The {@link LocalDate} to be converted
					 * @return The {@link Date} created from the {@link LocalDate}
					 */
					public static Date convertToDate(LocalDate localDate){
						return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
					}
				\t
					/**
					 * Converts the given {@link Date} to a {@link LocalDate}.
					 *\s
					 * @param date The {@link Date} to be converted
					 * @return The {@link LocalDate} created from the {@link Date}
					 */
					public static LocalDate convertToLocalDate(Date date){
						return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.time")
				.importName("java.time.LocalDate", false)
				.importName("java.time.Month", false)
				.importName("java.time.ZoneId", false)
				.importName("java.util.Date", false)
				.javadoc(EditableJavadoc.builder()
						.content("Date Util provides utilities for dealing with {@link Date}s.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.2.1")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("DateUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to make a DateUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("DateUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a {@link Date} with the given month, day, and year.")
								.param("month", "The {@link Month}")
								.param("day", "The day of the month")
								.param("year", "The year")
								.returnVal("A {@link Date} for the given month, day, and year")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("Date")
						.name("createDate")
						.parameter("Month month")
						.parameter("int day")
						.parameter("int year")
						.line("return convertToDate(LocalDate.of(year, month, day));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a {@link Date} with the given month, day, and year.")
								.param("month", "The month as a string")
								.param("day", "The day of the month")
								.param("year", "The year")
								.returnVal("A {@link Date} for the given month, day, and year")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("Date")
						.name("createDate")
						.parameter("String month")
						.parameter("int day")
						.parameter("int year")
						.line("return createDate(MonthUtil.parseFromString(month), day, year);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a {@link Date} with the given month, day, and year.")
								.param("month", "The month as an integer (1 = January)")
								.param("day", "The day of the month")
								.param("year", "The year")
								.returnVal("A {@link Date} for the given month, day, and year")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("Date")
						.name("createDate")
						.parameter("int month")
						.parameter("int day")
						.parameter("int year")
						.line("return convertToDate(LocalDate.of(year, month, day));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Converts the given {@link LocalDate} to a {@link Date}.")
								.param("localDate", "The {@link LocalDate} to be converted")
								.returnVal("The {@link Date} created from the {@link LocalDate}")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("Date")
						.name("convertToDate")
						.parameter("LocalDate localDate")
						.line("return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Converts the given {@link Date} to a {@link LocalDate}.")
								.param("date", "The {@link Date} to be converted")
								.returnVal("The {@link LocalDate} created from the {@link Date}")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("LocalDate")
						.name("convertToLocalDate")
						.parameter("Date date")
						.line("return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();")
						.build())
				.build());
	}
}
