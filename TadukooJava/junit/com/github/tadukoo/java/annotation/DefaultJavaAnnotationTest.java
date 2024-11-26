package com.github.tadukoo.java.annotation;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class DefaultJavaAnnotationTest<AnnotationType extends JavaAnnotation>{
	
	private final Class<AnnotationType> clazz;
	private final ThrowingSupplier<JavaAnnotationBuilder<AnnotationType>, NoException> builder;
	protected String name;
	protected AnnotationType annotation;
	
	protected DefaultJavaAnnotationTest(
			Class<AnnotationType> clazz, ThrowingSupplier<JavaAnnotationBuilder<AnnotationType>, NoException> builder){
		this.clazz = clazz;
		this.builder = builder;
	}
	
	@BeforeEach
	public void setup(){
		name = "Test";
		annotation = builder.get().name(name).build();
	}
	
	@Test
	public void testGetType(){
		assertEquals(JavaCodeTypes.ANNOTATION, annotation.getJavaCodeType());
	}
	
	@Test
	public void testBuilderDefaultParameters(){
		assertEquals(new ArrayList<>(), annotation.getParameters());
	}
	
	@Test
	public void testBuilderCopy(){
		JavaAnnotation otherAnnotation = builder.get()
				.name(name).canonicalName("something.Test")
				.parameter("test", "true")
				.parameter("derp", "false")
				.build();
		annotation = builder.get()
				.copy(otherAnnotation)
				.build();
		assertEquals(annotation, otherAnnotation);
		assertEquals(annotation.getCanonicalName(), otherAnnotation.getCanonicalName());
	}
	
	@Test
	public void testBuilderName(){
		assertEquals(name, annotation.getName());
	}
	
	@Test
	public void testBuilderCanonicalName(){
		annotation = builder.get()
				.name(name)
				.canonicalName("something.Test")
				.build();
		assertEquals("something.Test", annotation.getCanonicalName());
	}
	
	@Test
	public void testBuilderAddParameterPieces(){
		annotation = builder.get()
				.name(name)
				.parameter("test", "true")
				.parameter("derp", "false")
				.build();
		assertEquals(ListUtil.createList(
				Pair.of("test", "true"), Pair.of("derp", "false")
		), annotation.getParameters());
	}
	
	@Test
	public void testBuilderAddParameterPair(){
		annotation = builder.get()
				.name(name)
				.parameter(Pair.of("test", "true"))
				.parameter(Pair.of("derp", "false"))
				.build();
		assertEquals(ListUtil.createList(
				Pair.of("test", "true"), Pair.of("derp", "false")
		), annotation.getParameters());
	}
	
	@Test
	public void testBuilderSetParameters(){
		annotation = builder.get()
				.name(name)
				.parameters(ListUtil.createList(
				Pair.of("test", "true"), Pair.of("derp", "false")))
				.build();
		assertEquals(ListUtil.createList(
				Pair.of("test", "true"), Pair.of("derp", "false")
		), annotation.getParameters());
	}
	
	@Test
	public void testBuilderMissingName(){
		try{
			annotation = builder.get().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify name!", e.getMessage());
		}
	}
	
	@Test
	public void testGetParametersMap(){
		Map<String, String> parameters = annotation.getParametersMap();
		assertTrue(parameters.isEmpty());
	}
	
	@Test
	public void testGetParametersMapSingleParam(){
		annotation = builder.get()
				.name(name)
				.parameter("test", "something")
				.build();
		Map<String, String> parameters = annotation.getParametersMap();
		assertEquals(1, parameters.size());
		assertEquals("something", parameters.get("test"));
	}
	
	@Test
	public void testGetParametersMapMultipleParams(){
		annotation = builder.get()
				.name(name)
				.parameter("test", "something")
				.parameter("derp", "something else")
				.build();
		Map<String, String> parameters = annotation.getParametersMap();
		assertEquals(2, parameters.size());
		assertEquals("something", parameters.get("test"));
		assertEquals("something else", parameters.get("derp"));
	}
	
	@Test
	public void testToString(){
		assertEquals("@Test", annotation.toString());
	}
	
	@Test
	public void testToStringSingleParameter(){
		annotation = builder.get()
				.name(name)
				.parameter("test", "true")
				.build();
		assertEquals("@Test(test = true)", annotation.toString());
	}
	
	@Test
	public void testToStringValueParameter(){
		annotation = builder.get()
				.name(name)
				.parameter("value", "true")
				.build();
		assertEquals("@Test(true)", annotation.toString());
	}
	
	@Test
	public void testToStringMultipleParameters(){
		annotation = builder.get()
				.name(name)
				.parameter("test", "true")
				.parameter("derp", "String.class")
				.build();
		assertEquals("@Test(test = true, derp = String.class)", annotation.toString());
	}
	
	@Test
	public void testToStringMultipleParametersOneIsValue(){
		annotation = builder.get()
				.name(name)
				.parameter("value", "true")
				.parameter("derp", "String.class")
				.build();
		assertEquals("@Test(value = true, derp = String.class)", annotation.toString());
	}
	
	/*
	 * Test Equals
	 */
	
	@Test
	public void testEquals(){
		AnnotationType otherAnnotation = builder.get().name(name).build();
		assertEquals(annotation, otherAnnotation);
	}
	
	@Test
	public void testEqualsNotEqual(){
		AnnotationType otherAnnotation = builder.get().name("Testing").build();
		assertNotEquals(annotation, otherAnnotation);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(annotation, "testing");
	}
	
	@Test
	public void testToBuilderCode(){
		annotation = builder.get()
				.name(name)
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.name(\"" + name + "\")\n" +
				"\t\t.build()", annotation.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithCanonicalName(){
		annotation = builder.get()
				.name(name)
				.canonicalName("some.canon.name")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.name(\"" + name + "\")\n" +
				"\t\t.canonicalName(\"some.canon.name\")\n" +
				"\t\t.build()", annotation.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithOneParameter(){
		annotation = builder.get()
				.name(name)
				.parameter("test", "5")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.name(\"" + name + "\")\n" +
				"\t\t.parameter(\"test\", \"5\")\n" +
				"\t\t.build()", annotation.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithTwoParameters(){
		annotation = builder.get()
				.name(name)
				.parameter("test", "5")
				.parameter("something", "42")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.name(\"" + name + "\")\n" +
				"\t\t.parameter(\"test\", \"5\")\n" +
				"\t\t.parameter(\"something\", \"42\")\n" +
				"\t\t.build()", annotation.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithEverything(){
		annotation = builder.get()
				.name(name)
				.canonicalName("some.canon.name")
				.parameter("test", "5")
				.parameter("something", "42")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.name(\"" + name + "\")\n" +
				"\t\t.canonicalName(\"some.canon.name\")\n" +
				"\t\t.parameter(\"test\", \"5\")\n" +
				"\t\t.parameter(\"something\", \"42\")\n" +
				"\t\t.build()", annotation.toBuilderCode());
	}
}
