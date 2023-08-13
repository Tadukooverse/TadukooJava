package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavaClassTest extends DefaultJavaClassTest<RegularJavaClassTest.TestJavaClass>{
	
	public static class TestJavaClass extends JavaClass{
		
		private TestJavaClass(
				boolean editable, boolean isInnerClass,
				JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
				Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal, String className,
				String superClassName, List<String> implementsInterfaceNames,
				List<JavaSingleLineComment> singleLineComments, List<JavaMultiLineComment> multiLineComments,
				List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods,
				List<Pair<JavaCodeTypes, String>> innerElementsOrder){
			super(editable, isInnerClass, packageDeclaration, importStatements,
					javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal, className,
					superClassName, implementsInterfaceNames,
					singleLineComments, multiLineComments,
					innerClasses, fields, methods,
					innerElementsOrder);
		}
		
		public static TestJavaClassBuilder builder(){
			return new TestJavaClassBuilder(false);
		}
	}
	
	public static class TestJavaClassBuilder extends JavaClassBuilder<TestJavaClass>{
		
		private final boolean editable;
		
		private TestJavaClassBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected JavaPackageDeclarationBuilder<?> getPackageDeclarationBuilder(){
			return UneditableJavaPackageDeclaration.builder();
		}
		
		@Override
		protected JavaImportStatementBuilder<?> getImportStatementBuilder(){
			return UneditableJavaImportStatement.builder();
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaClass constructClass(){
			return new TestJavaClass(editable, isInnerClass, packageDeclaration, importStatements,
					javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal, className,
					superClassName, implementsInterfaceNames,
					singleLineComments, multiLineComments,
					innerClasses, fields, methods,
					innerElementsOrder);
		}
	}
	
	public RegularJavaClassTest(){
		super(TestJavaClass::builder, UneditableJavaPackageDeclaration::builder, UneditableJavaImportStatement::builder,
				UneditableJavaAnnotation::builder, UneditableJavadoc::builder, UneditableJavaSingleLineComment::builder,
				UneditableJavaMultiLineComment::builder, UneditableJavaField::builder, UneditableJavaMethod::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(clazz.isEditable());
	}
	
	@Test
	public void testIsEditableTrue(){
		clazz = new TestJavaClassBuilder(true)
				.className(className)
				.build();
		assertTrue(clazz.isEditable());
	}
}
