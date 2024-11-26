package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertPackageDeclarationEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findPackageDeclarationDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestPackageDeclarationTest{
	
	@Test
	public void testFindPackageDeclarationsDifferencesNone(){
		assertEquals(new ArrayList<>(), findPackageDeclarationDifferences(
				EditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build(),
				EditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build()));
	}
	
	@Test
	public void testFindPackageDeclarationDifferencesBothNull(){
		assertEquals(new ArrayList<>(), findPackageDeclarationDifferences(
				null, null
		));
	}
	
	@Test
	public void testFindPackageDeclarationDifferences1Null2Not(){
		assertEquals(ListUtil.createList("One of the package declarations is null, and the other isn't!"), findPackageDeclarationDifferences(
				null,
				EditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build()
		));
	}
	
	@Test
	public void testFindPackageDeclarationDifferences2Null1Not(){
		assertEquals(ListUtil.createList("One of the package declarations is null, and the other isn't!"), findPackageDeclarationDifferences(
				EditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build(),
				null
		));
	}
	
	@Test
	public void testFindPackageDeclarationsDifferencesEditable(){
		assertEquals(ListUtil.createList("Editable is different!"), findPackageDeclarationDifferences(
				EditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build(),
				UneditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build()));
	}
	
	@Test
	public void testFindPackageDeclarationsDifferencesPackageName(){
		assertEquals(ListUtil.createList("Package Name is different!"), findPackageDeclarationDifferences(
				EditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build(),
				EditableJavaPackageDeclaration.builder()
						.packageName("com.derp")
						.build()));
	}
	
	@Test
	public void testFindPackageDeclarationsDifferencesAll(){
		assertEquals(ListUtil.createList("Editable is different!", "Package Name is different!"),
				findPackageDeclarationDifferences(
						EditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						UneditableJavaPackageDeclaration.builder()
								.packageName("com.derp")
								.build()));
	}
	
	@Test
	public void testAssertPackageDeclarationEqualsNone(){
		assertPackageDeclarationEquals(
				EditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build(),
				EditableJavaPackageDeclaration.builder()
						.packageName("com.test")
						.build());
	}
	
	@Test
	public void testAssertPackageDeclarationEqualsBothNull(){
		assertPackageDeclarationEquals(null, null);
	}
	
	@Test
	public void testAssertPackageDeclarationEquals1Null2Not(){
		JavaPackageDeclaration declaration2 = EditableJavaPackageDeclaration.builder()
				.packageName("com.test")
				.build();
		try{
			assertPackageDeclarationEquals(null, declaration2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the package declarations is null, and the other isn't!",
							buildAssertError(null, declaration2)),
					e.getMessage());
		}
	}
	
	@Test
	public void testAssertPackageDeclarationEquals2Null1Not(){
		JavaPackageDeclaration declaration1 = EditableJavaPackageDeclaration.builder()
				.packageName("com.test")
				.build();
		try{
			assertPackageDeclarationEquals(declaration1, null);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the package declarations is null, and the other isn't!",
							buildAssertError(declaration1, null)),
					e.getMessage());
		}
	}
	
	@Test
	public void testAssertPackageDeclarationEqualsEditable(){
		JavaPackageDeclaration declaration1 = EditableJavaPackageDeclaration.builder()
				.packageName("com.test")
				.build();
		JavaPackageDeclaration declaration2 = UneditableJavaPackageDeclaration.builder()
				.packageName("com.test")
				.build();
		try{
			assertPackageDeclarationEquals(declaration1, declaration2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!", buildAssertError(declaration1, declaration2)),
					e.getMessage());
		}
	}
	
	@Test
	public void testAssertPackageDeclarationEqualsPackageName(){
		JavaPackageDeclaration declaration1 = EditableJavaPackageDeclaration.builder()
				.packageName("com.test")
				.build();
		JavaPackageDeclaration declaration2 = EditableJavaPackageDeclaration.builder()
				.packageName("com.derp")
				.build();
		try{
			assertPackageDeclarationEquals(declaration1, declaration2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Package Name is different!", buildAssertError(declaration1, declaration2)),
					e.getMessage());
		}
	}
	
	@Test
	public void testAssertPackageDeclarationEqualsAll(){
		JavaPackageDeclaration declaration1 = EditableJavaPackageDeclaration.builder()
				.packageName("com.test")
				.build();
		JavaPackageDeclaration declaration2 = UneditableJavaPackageDeclaration.builder()
				.packageName("com.derp")
				.build();
		try{
			assertPackageDeclarationEquals(declaration1, declaration2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!\n" +
							"Package Name is different!", buildAssertError(declaration1, declaration2)),
					e.getMessage());
		}
	}
}
