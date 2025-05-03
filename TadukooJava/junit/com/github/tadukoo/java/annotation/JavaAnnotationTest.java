package com.github.tadukoo.java.annotation;

import com.github.tadukoo.java.BaseJavaCodeTypeTest;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.Function;
import com.github.tadukoo.util.functional.supplier.Supplier;
import com.github.tadukoo.util.map.MapUtil;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaAnnotationTest extends BaseJavaCodeTypeTest<JavaAnnotation>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(UneditableJavaAnnotation.builder()
						.name("Test")
						.build(), false,
						(Function<UneditableJavaAnnotation, Boolean>) UneditableJavaAnnotation::isEditable),
				Arguments.of(EditableJavaAnnotation.builder()
						.name("Test")
						.build(), true,
						(Function<EditableJavaAnnotation, Boolean>) EditableJavaAnnotation::isEditable)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Get Type
				Pair.of(
						builders -> JavaCodeTypes.ANNOTATION,
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build()
								.getJavaCodeType()
				),
				// Default Parameters
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build()
								.getParameters()
				),
				// Copy
				Pair.of(
						builders ->  builders.annotationBuilder().get()
								.name("Test").canonicalName("something.Test")
								.parameter("test", "true")
								.parameter("derp", "false")
								.build(),
						builders -> builders.annotationBuilder().get()
								.copy( builders.annotationBuilder().get()
										.name("Test").canonicalName("something.Test")
										.parameter("test", "true")
										.parameter("derp", "false")
										.build())
								.build()
				),
				// Name
				Pair.of(
						builders -> "Test",
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build()
								.getName()
				),
				// Canonical Name
				Pair.of(
						builders -> "something.Test",
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.canonicalName("something.Test")
								.build()
								.getCanonicalName()
				),
				// Add Parameter Pieces
				Pair.of(
						builders -> ListUtil.createList(
								Pair.of("test", "true"), Pair.of("derp", "false")
						),
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameter("test", "true")
								.parameter("derp", "false")
								.build()
								.getParameters()
				),
				// Add Parameter Pair
				Pair.of(
						builders -> ListUtil.createList(
								Pair.of("test", "true"), Pair.of("derp", "false")
						),
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameter(Pair.of("test", "true"))
								.parameter(Pair.of("derp", "false"))
								.build()
								.getParameters()
				),
				// Set Parameters
				Pair.of(
						builders -> ListUtil.createList(
								Pair.of("test", "true"), Pair.of("derp", "false")
						),
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameters(ListUtil.createList(
										Pair.of("test", "true"), Pair.of("derp", "false")))
								.build()
								.getParameters()
				),
				// Get Parameters Map
				Pair.of(
						builders -> true,
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build()
								.getParametersMap()
								.isEmpty()
				),
				// Get Parameters Map Single Param
				Pair.of(
						builders -> MapUtil.createMap(Pair.of("test", "something")),
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameter("test", "something")
								.build()
								.getParametersMap()
				),
				// Get Parameters Map Multiple Params
				Pair.of(
						builders -> MapUtil.createMap(Pair.of("test", "something"),
								Pair.of("derp", "something else")),
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameter("test", "something")
								.parameter("derp", "something else")
								.build()
								.getParametersMap()
				),
				// Equals
				Pair.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build(),
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Not Equal
				Pair.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build(),
						builders -> builders.annotationBuilder().get()
								.name("Testing")
								.build()
				),
				// Not Equal Canonical Name
				Pair.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build(),
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.canonicalName("something.here")
								.build()
				),
				// Different Type
				Pair.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build(),
						builders -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<Function<Builders, JavaAnnotation>, String, Function<SimpleClassNames, String>>>
				annotationMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.build(),
						"@Test",
						classNames -> classNames.annotationSimpleClassName() + """
								.builder()
										.name("Test")
										.build()"""
				),
				// Single parameter
				Triple.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameter("test", "true")
								.build(),
						"@Test(test = true)",
						classNames -> classNames.annotationSimpleClassName() + """
								.builder()
										.name("Test")
										.parameter("test", "true")
										.build()"""
				),
				// Value parameter
				Triple.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameter("value", "true")
								.build(),
						"@Test(true)",
						classNames -> classNames.annotationSimpleClassName() + """
								.builder()
										.name("Test")
										.parameter("value", "true")
										.build()"""
				),
				// Multiple parameters
				Triple.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameter("test", "true")
								.parameter("derp", "String.class")
								.build(),
						"@Test(test = true, derp = String.class)",
						classNames -> classNames.annotationSimpleClassName() + """
								.builder()
										.name("Test")
										.parameter("test", "true")
										.parameter("derp", "String.class")
										.build()"""
				),
				// Multiple parameters, one is value
				Triple.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.parameter("value", "true")
								.parameter("derp", "String.class")
								.build(),
						"@Test(value = true, derp = String.class)",
						classNames -> classNames.annotationSimpleClassName() + """
								.builder()
										.name("Test")
										.parameter("value", "true")
										.parameter("derp", "String.class")
										.build()"""
				),
				// Canonical Name
				Triple.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.canonicalName("some.canon.name")
								.build(),
						"@Test",
						classNames -> classNames.annotationSimpleClassName() + """
								.builder()
										.name("Test")
										.canonicalName("some.canon.name")
										.build()"""
				),
				// Everything
				Triple.of(
						builders -> builders.annotationBuilder().get()
								.name("Test")
								.canonicalName("some.canon.name")
								.parameter("test", "5")
								.parameter("something", "42")
								.build(),
						"@Test(test = 5, something = 42)",
						classNames -> classNames.annotationSimpleClassName() + """
								.builder()
										.name("Test")
										.canonicalName("some.canon.name")
										.parameter("test", "5")
										.parameter("something", "42")
										.build()"""
				)
		);
		
		return annotationMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<Function<Builders, Supplier<? extends JavaAnnotation>>, String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Missing Name
				Pair.of(
						builders -> () -> builders.annotationBuilder().get()
								.build(),
						"Must specify name!"
				)
		);
		
		return builderFuncsAndErrorMessages.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight())));
	}
	
	/*
	 * Editable Tests
	 */
	
	@Test
	public void testSetName(){
		EditableJavaAnnotation annotation = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		assertEquals("Test", annotation.getName());
		annotation.setName("Derp");
		assertEquals("Derp", annotation.getName());
	}
	
	@Test
	public void testSetCanonicalName(){
		EditableJavaAnnotation annotation = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		assertTrue(StringUtil.isBlank(annotation.getCanonicalName()));
		annotation.setCanonicalName("something.Test");
		assertEquals("something.Test", annotation.getCanonicalName());
	}
	
	@Test
	public void testAddParameterPieces(){
		EditableJavaAnnotation annotation = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		assertEquals(new ArrayList<>(), annotation.getParameters());
		annotation.addParameter("test", "true");
		assertEquals(ListUtil.createList(Pair.of("test", "true")), annotation.getParameters());
		annotation.addParameter("derp", "String.class");
		assertEquals(ListUtil.createList(Pair.of("test", "true"), Pair.of("derp", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testAddParameterPair(){
		EditableJavaAnnotation annotation = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		assertEquals(new ArrayList<>(), annotation.getParameters());
		annotation.addParameter(Pair.of("test", "true"));
		assertEquals(ListUtil.createList(Pair.of("test", "true")), annotation.getParameters());
		annotation.addParameter(Pair.of("derp", "String.class"));
		assertEquals(ListUtil.createList(Pair.of("test", "true"), Pair.of("derp", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testAddParameters(){
		EditableJavaAnnotation annotation = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		Pair<String, String> param1 = Pair.of("test", "true");
		Pair<String, String> param2 = Pair.of("derp", "String.class");
		Pair<String, String> param3 = Pair.of("yep", "false");
		Pair<String, String> param4 = Pair.of("nah", "\"\"");
		assertEquals(new ArrayList<>(), annotation.getParameters());
		annotation.addParameters(ListUtil.createList(param1, param2));
		assertEquals(ListUtil.createList(param1, param2), annotation.getParameters());
		annotation.addParameters(ListUtil.createList(param3, param4));
		assertEquals(ListUtil.createList(param1, param2, param3, param4), annotation.getParameters());
	}
	
	@Test
	public void testSetParameters(){
		EditableJavaAnnotation annotation = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		Pair<String, String> param1 = Pair.of("test", "true");
		Pair<String, String> param2 = Pair.of("derp", "String.class");
		Pair<String, String> param3 = Pair.of("yep", "false");
		Pair<String, String> param4 = Pair.of("nah", "\"\"");
		assertEquals(new ArrayList<>(), annotation.getParameters());
		annotation.setParameters(ListUtil.createList(param1, param2));
		assertEquals(ListUtil.createList(param1, param2), annotation.getParameters());
		annotation.setParameters(ListUtil.createList(param3, param4));
		assertEquals(ListUtil.createList(param3, param4), annotation.getParameters());
	}
}
