package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class CollectionUtilTest extends JavaClassParsingTest{
	
	public CollectionUtilTest(){
		super("""
				package com.github.tadukoo.util;
				
				import java.util.Collection;
				
				/**
				 * Util functions for dealing with {@link Collection Collections}
				 *\s
				 * @author Logan Ferree
				 * @version Beta v.0.5.2
				 */
				public class CollectionUtil{
				\t
					/** Not allowed to create a CollectionUtil */
					private CollectionUtil(){ }
				\t
					/**
					 * Checks if the given {@link Collection} is blank (either null or an empty collection).
					 *\s
					 * @param collection The {@link Collection} to check
					 * @return true if the {@link Collection} is null or empty
					 */
					public static boolean isBlank(Collection<?> collection){
						return collection == null || collection.isEmpty();
					}
				\t
					/**
					 * Checks if the given {@link Collection} is NOT blank (blank = either null or an empty collection).
					 *\s
					 * @param collection The {@link Collection} to check
					 * @return true if the {@link Collection} is not null and not empty
					 */
					public static boolean isNotBlank(Collection<?> collection){
						return !isBlank(collection);
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util")
				.importName("java.util.Collection", false)
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with {@link Collection Collections}")
						.author("Logan Ferree")
						.version("Beta v.0.5.2")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("CollectionUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to create a CollectionUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("CollectionUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the given {@link Collection} is blank (either null or an empty collection).")
								.param("collection", "The {@link Collection} to check")
								.returnVal("true if the {@link Collection} is null or empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isBlank")
						.parameter("Collection<?> collection")
						.line("return collection == null || collection.isEmpty();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the given {@link Collection} is NOT blank (blank = either null or an empty collection).")
								.param("collection", "The {@link Collection} to check")
								.returnVal("true if the {@link Collection} is not null and not empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isNotBlank")
						.parameter("Collection<?> collection")
						.line("return !isBlank(collection);")
						.build())
				.build());
	}
}
