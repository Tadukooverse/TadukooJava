package com.github.tadukoo.java.testing.util.time;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class MonthUtilTest extends JavaClassParsingTest{
	
	public MonthUtilTest(){
		super("""
				package com.github.tadukoo.util.time;
				
				import com.github.tadukoo.util.StringUtil;
				
				import java.time.Month;
				import java.time.format.TextStyle;
				import java.util.Locale;
				
				/**
				 * Month Util provides utilities for dealing with {@link Month}s.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.2.1
				 */
				public final class MonthUtil{
				\t
					/** Can't create a MonthUtil */
					private MonthUtil(){ }
				\t
					/**
					 * Parses a {@link Month} from the given string. May return null if none match.
					 *\s
					 * @param str The string to be parsed into a {@link Month}
					 * @return The {@link Month} matching the given string, or null
					 */
					public static Month parseFromString(String str){
						for(Month month: Month.values()){
							if(StringUtil.equalsIgnoreCase(str, month.toString())){
								return month;
							}
						}
						return null;
					}
				\t
					/**
					 * Converts the given {@link Month} to its full, capitalized, US-Locale string. This is an
					 * easy way instead of having to call {@link Month#getDisplayName(TextStyle, Locale)} all the time.
					 *\s
					 * @param month The {@link Month} to get as a string
					 * @return The full string of the {@link Month}
					 */
					public static String asString(Month month){
						return month.getDisplayName(TextStyle.FULL, Locale.US);
					}
				\t
					/**
					 * Creates a String array of the {@link Month}s.
					 *\s
					 * @return A String array of the {@link Month}s
					 */
					public static String[] asStringArray(){
						String[] monthStrings = new String[12];
						int i = 0;
						for(Month month: Month.values()){
							monthStrings[i] = asString(month);
							i++;
						}
						return monthStrings;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.time")
				.importName("com.github.tadukoo.util.StringUtil", false)
				.importName("java.time.Month", false)
				.importName("java.time.format.TextStyle", false)
				.importName("java.util.Locale", false)
				.javadoc(EditableJavadoc.builder()
						.content("Month Util provides utilities for dealing with {@link Month}s.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.2.1")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("MonthUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Can't create a MonthUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("MonthUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Parses a {@link Month} from the given string. May return null if none match.")
								.param("str", "The string to be parsed into a {@link Month}")
								.returnVal("The {@link Month} matching the given string, or null")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("Month")
						.name("parseFromString")
						.parameter("String", "str")
						.line("for(Month month: Month.values()){")
						.line("\tif(StringUtil.equalsIgnoreCase(str, month.toString())){")
						.line("\t\treturn month;")
						.line("\t}")
						.line("}")
						.line("return null;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Converts the given {@link Month} to its full, capitalized, US-Locale string. This is an")
								.content("easy way instead of having to call {@link Month#getDisplayName(TextStyle, Locale)} all the time.")
								.param("month", "The {@link Month} to get as a string")
								.returnVal("The full string of the {@link Month}")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String")
						.name("asString")
						.parameter("Month", "month")
						.line("return month.getDisplayName(TextStyle.FULL, Locale.US);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a String array of the {@link Month}s.")
								.returnVal("A String array of the {@link Month}s")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String[]")
						.name("asStringArray")
						.line("String[] monthStrings = new String[12];")
						.line("int i = 0;")
						.line("for(Month month: Month.values()){")
						.line("\tmonthStrings[i] = asString(month);")
						.line("\ti++;")
						.line("}")
						.line("return monthStrings;")
						.build())
				.build());
	}
}
