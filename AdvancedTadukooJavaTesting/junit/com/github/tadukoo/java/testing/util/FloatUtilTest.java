package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class FloatUtilTest extends JavaClassParsingTest{
	
	public FloatUtilTest(){
		super("""
				package com.github.tadukoo.util;
				
				import java.util.List;
				
				/**
				 * Util functions for dealing with floats.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version 0.1-Alpha-SNAPSHOT
				 */
				public final class FloatUtil{
				\t
					/** Not allowed to create a FloatUtil */
					private FloatUtil(){ }
				\t
					/**
					 * Converts a List of Floats into an array of floats.
					 *\s
					 * @param floatList The List of Floats to convert to an array
					 * @return An array containing the floats from the List
					 */
					public static float[] convertListToArray(List<Float> floatList){
						float[] floatArray = new float[floatList.size()];
						for(int i = 0; i < floatList.size(); i++){
							floatArray[i] = floatList.get(i);
						}
						return floatArray;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util")
				.importName("java.util.List", false)
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with floats.")
						.author("Logan Ferree (Tadukoo)")
						.version("0.1-Alpha-SNAPSHOT")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("FloatUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to create a FloatUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("FloatUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Converts a List of Floats into an array of floats.")
								.param("floatList", "The List of Floats to convert to an array")
								.returnVal("An array containing the floats from the List")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("float[]")
						.name("convertListToArray")
						.parameter("List<Float>", "floatList")
						.line("float[] floatArray = new float[floatList.size()];")
						.line("for(int i = 0; i < floatList.size(); i++){")
						.line("\tfloatArray[i] = floatList.get(i);")
						.line("}")
						.line("return floatArray;")
						.build())
				.build());
	}
}
