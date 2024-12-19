package com.github.tadukoo.java.testing.util.map;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class MultiMapUtilTest extends JavaClassParsingTest{
	
	public MultiMapUtilTest(){
		super("""
				package com.github.tadukoo.util.map;
				
				/**
				 * Util functions for dealing with {@link MultiMap}s.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.2
				 */
				public final class MultiMapUtil{
				\t
					/** Not allowed to make a MultiMapUtil */
					private MultiMapUtil(){ }
				\t
					/**
					 * Checks if the given MultiMap is blank (either null or an empty multiMap).
					 *\s
					 * @param map The MultiMap to check
					 * @return true if the MultiMap is null or empty
					 */
					public static boolean isBlank(MultiMap<?, ?> map){
						return map == null || map.isEmpty();
					}
				\t
					/**
					 * Checks if the given MultiMap is NOT blank (blank = either null or an empty multiMap).
					 *\s
					 * @param map The MultiMap to check
					 * @return true if the MultiMap is not null and not empty
					 */
					public static boolean isNotBlank(MultiMap<?, ?> map){
						return !isBlank(map);
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.map")
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with {@link MultiMap}s.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.2")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("MultiMapUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to make a MultiMapUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("MultiMapUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the given MultiMap is blank (either null or an empty multiMap).")
								.param("map", "The MultiMap to check")
								.returnVal("true if the MultiMap is null or empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isBlank")
						.parameter("MultiMap<?, ?> map")
						.line("return map == null || map.isEmpty();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the given MultiMap is NOT blank (blank = either null or an empty multiMap).")
								.param("map", "The MultiMap to check")
								.returnVal("true if the MultiMap is not null and not empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isNotBlank")
						.parameter("MultiMap<?, ?> map")
						.line("return !isBlank(map);")
						.build())
				.build());
	}
}
