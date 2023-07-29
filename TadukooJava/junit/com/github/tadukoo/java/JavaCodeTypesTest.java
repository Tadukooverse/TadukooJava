package com.github.tadukoo.java;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.github.tadukoo.java.JavaCodeTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaCodeTypesTest{
	
	// The below warnings aren't really valid for this, looks like JUnit is not properly detecting
	// this abstract class is inherited later
	@SuppressWarnings({"JUnitMalformedDeclaration", "JUnit3StyleTestMethodInJUnit4Class", "unused"})
	private static abstract class BaseJavaCodeTypesTest{
		private final JavaCodeTypes type;
		private final String toString;
		private final Class<?> javaTypeClass;
		private final String standardName;
		
		protected BaseJavaCodeTypesTest(JavaCodeTypes type, String toString, Class<?> javaTypeClass, String standardName){
			this.type = type;
			this.toString = toString;
			this.javaTypeClass = javaTypeClass;
			this.standardName = standardName;
		}
		
		@Test
		public void testToString(){
			assertEquals(toString, type.toString());
		}
		
		@Test
		public void testJavaTypeClass(){
			assertEquals(javaTypeClass, type.getJavaTypeClass());
		}
		
		@Test
		public void testStandardName(){
			assertEquals(standardName, type.getStandardName());
		}
	}
	
	@Nested
	public class UNKNOWNTest extends BaseJavaCodeTypesTest{
		public UNKNOWNTest(){
			super(UNKNOWN, "UNKNOWN", null, "unknown");
		}
	}
	
	@Nested
	public class PACKAGE_DECLARATIONTest extends BaseJavaCodeTypesTest{
		public PACKAGE_DECLARATIONTest(){
			super(PACKAGE_DECLARATION,
					"PACKAGE_DECLARATION", JavaPackageDeclaration.class, "package declaration");
		}
	}
	
	@Nested
	public class IMPORT_STATEMENTTest extends BaseJavaCodeTypesTest{
		public IMPORT_STATEMENTTest(){
			super(IMPORT_STATEMENT,
					"IMPORT_STATEMENT", JavaImportStatement.class, "import statement");
		}
	}
	
	@Nested
	public class JAVADOCTest extends BaseJavaCodeTypesTest{
		public JAVADOCTest(){
			super(JAVADOC,
					"JAVADOC", Javadoc.class, "Javadoc");
		}
	}
	
	@Nested
	public class MULTI_LINE_COMMENTTest extends BaseJavaCodeTypesTest{
		public MULTI_LINE_COMMENTTest(){
			super(MULTI_LINE_COMMENT,
					"MULTI_LINE_COMMENT", JavaMultiLineComment.class, "multi-line comment");
		}
	}
	
	@Nested
	public class SINGLE_LINE_COMMENTTest extends BaseJavaCodeTypesTest{
		public SINGLE_LINE_COMMENTTest(){
			super(SINGLE_LINE_COMMENT,
					"SINGLE_LINE_COMMENT", JavaSingleLineComment.class, "single-line comment");
		}
	}
	
	@Nested
	public class ANNOTATIONTest extends BaseJavaCodeTypesTest{
		public ANNOTATIONTest(){
			super(ANNOTATION,
					"ANNOTATION", JavaAnnotation.class, "annotation");
		}
	}
	
	@Nested
	public class TYPE_WITH_MODIFIERSTest extends BaseJavaCodeTypesTest{
		public TYPE_WITH_MODIFIERSTest(){
			super(TYPE_WITH_MODIFIERS,
					"TYPE_WITH_MODIFIERS", null, "type with modifiers");
		}
	}
	
	@Nested
	public class FIELDTest extends BaseJavaCodeTypesTest{
		public FIELDTest(){
			super(FIELD,
					"FIELD", JavaField.class, "field");
		}
	}
	
	@Nested
	public class METHODTest extends BaseJavaCodeTypesTest{
		public METHODTest(){
			super(METHOD,
					"METHOD", JavaMethod.class, "method");
		}
	}
	
	@Nested
	public class CLASSTest extends BaseJavaCodeTypesTest{
		public CLASSTest(){
			super(CLASS,
					"CLASS", JavaClass.class, "class");
		}
	}
}
