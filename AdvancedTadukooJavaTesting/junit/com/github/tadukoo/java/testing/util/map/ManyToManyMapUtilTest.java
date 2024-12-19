package com.github.tadukoo.java.testing.util.map;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class ManyToManyMapUtilTest extends JavaClassParsingTest{
	
	public ManyToManyMapUtilTest(){
		super("""
				package com.github.tadukoo.util.map;
				
				/**
				 * Util functions for dealing with {@link ManyToManyMap}s.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.2
				 */
				public final class ManyToManyMapUtil{
				\t
					/** Not allowed to make a ManyToManyMapUtil */
					private ManyToManyMapUtil(){ }
				\t
					/**
					 * Checks if the given ManyToManyMap is blank (either null or an empty manyToManyMap).
					 *\s
					 * @param map The ManyToManyMap to check
					 * @return true if the ManyToManyMap is null or empty
					 */
					public static boolean isBlank(ManyToManyMap<?, ?> map){
						return map == null || map.isEmpty();
					}
				\t
					/**
					 * Checks if the given ManyToManyMap is NOT blank (blank = either null or an empty manyToManyMap).
					 *\s
					 * @param map The ManyToManyMap to check
					 * @return true if the ManyToManyMap is not null and not empty
					 */
					public static boolean isNotBlank(ManyToManyMap<?, ?> map){
						return !isBlank(map);
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.map")
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with {@link ManyToManyMap}s.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.2")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("ManyToManyMapUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to make a ManyToManyMapUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("ManyToManyMapUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the given ManyToManyMap is blank (either null or an empty manyToManyMap).")
								.param("map", "The ManyToManyMap to check")
								.returnVal("true if the ManyToManyMap is null or empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isBlank")
						.parameter("ManyToManyMap<?, ?> map")
						.line("return map == null || map.isEmpty();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the given ManyToManyMap is NOT blank (blank = either null or an empty manyToManyMap).")
								.param("map", "The ManyToManyMap to check")
								.returnVal("true if the ManyToManyMap is not null and not empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isNotBlank")
						.parameter("ManyToManyMap<?, ?> map")
						.line("return !isBlank(map);")
						.build())
				.build());
	}
}
