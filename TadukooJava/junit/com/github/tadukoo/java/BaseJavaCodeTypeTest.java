package com.github.tadukoo.java;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineCommentBuilder;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineCommentBuilder;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.field.JavaFieldBuilder;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.importstatement.*;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.JavaClassBuilder;
import com.github.tadukoo.java.javaclass.UneditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.JavadocBuilder;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.method.*;
import com.github.tadukoo.java.packagedeclaration.*;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.Function;
import com.github.tadukoo.util.functional.supplier.Supplier;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseJavaCodeTypeTest<CodeType extends JavaCodeType>{
	
	protected abstract Stream<Arguments> getEditableData();
	
	@ParameterizedTest
	@MethodSource("getEditableData")
	public void testIsEditable(CodeType type, boolean editable, Function<CodeType, Boolean> editableFunc){
		assertEquals(editable, editableFunc.apply(type));
	}
	
	protected abstract Stream<Arguments> getEqualsData();
	
	@ParameterizedTest
	@MethodSource("getEqualsData")
	public void testEquals(Object expectedObject, Object actualObject){
		assertEquals(expectedObject, actualObject);
	}
	
	protected abstract Stream<Arguments> getNotEqualsData();
	
	@ParameterizedTest
	@MethodSource("getNotEqualsData")
	public void testNotEquals(Object expectedObject, Object actualObject){
		assertNotEquals(expectedObject, actualObject);
	}
	
	protected abstract Stream<Arguments> getStringData();
	
	@ParameterizedTest
	@MethodSource("getStringData")
	public void testToString(CodeType type, String expectedText, String ignored){
		assertEquals(expectedText, type.toString());
	}
	
	@ParameterizedTest
	@MethodSource("getStringData")
	public void testToBuilderCode(CodeType type, String ignored, String builderCode){
		assertEquals(builderCode, type.toBuilderCode());
	}
	
	protected Stream<Arguments> getBuilderErrorData(){
		return Stream.of(
				Arguments.of((Supplier<CodeType>) () -> null, null)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getBuilderErrorData")
	public void testBuilderError(Supplier<CodeType> buildFunc, String errorText){
		// Skip for cases where we don't have real tests
		if(StringUtil.isBlank(errorText)){
			return;
		}
		try{
			buildFunc.get();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals(errorText, e.getMessage());
		}
	}
	
	/*
	 * Package Declaration Info
	 */
	
	protected static class TestJavaPackageDeclaration extends JavaPackageDeclaration{
		
		private TestJavaPackageDeclaration(boolean editable, String packageName){
			super(editable, packageName);
		}
		
		public static TestJavaPackageDeclarationBuilder builder(){
			return new TestJavaPackageDeclarationBuilder(false);
		}
	}
	
	protected static class TestJavaPackageDeclarationBuilder extends JavaPackageDeclarationBuilder<TestJavaPackageDeclaration>{
		
		private final boolean editable;
		
		private TestJavaPackageDeclarationBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaPackageDeclaration constructPackageDeclaration(){
			return new TestJavaPackageDeclaration(editable, packageName);
		}
	}
	
	protected static final List<Supplier<JavaPackageDeclarationBuilder<? extends JavaPackageDeclaration>>> packageDeclarationBuilders =
			ListUtil.createList(
					UneditableJavaPackageDeclaration::builder,
					EditableJavaPackageDeclaration::builder,
					TestJavaPackageDeclaration::builder
			);
	protected static final List<String> packageDeclarationSimpleClassNames = ListUtil.createList(
			UneditableJavaPackageDeclaration.class.getSimpleName(),
			EditableJavaPackageDeclaration.class.getSimpleName(),
			TestJavaPackageDeclaration.class.getSimpleName()
	);
	
	/*
	 * Import Statement Info
	 */
	
	protected static class TestJavaImportStatement extends JavaImportStatement{
		
		private TestJavaImportStatement(boolean editable, boolean isStatic, String importName){
			super(editable, isStatic, importName);
		}
		
		public static TestJavaImportStatementBuilder builder(){
			return new TestJavaImportStatementBuilder(false);
		}
	}
	
	protected static class TestJavaImportStatementBuilder extends JavaImportStatementBuilder<TestJavaImportStatement>{
		
		private final boolean editable;
		
		private TestJavaImportStatementBuilder(boolean editable){
			super();
			this.editable = editable;
		}
		
		@Override
		protected TestJavaImportStatement constructImportStatement(){
			return new TestJavaImportStatement(editable, isStatic, importName);
		}
	}
	
	protected static final List<Supplier<JavaImportStatementBuilder<? extends JavaImportStatement>>> importStatementBuilders =
			ListUtil.createList(
					UneditableJavaImportStatement::builder,
					EditableJavaImportStatement::builder,
					TestJavaImportStatement::builder
			);
	protected static final List<String> importStatementSimpleClassNames = ListUtil.createList(
			UneditableJavaImportStatement.class.getSimpleName(),
			EditableJavaImportStatement.class.getSimpleName(),
			TestJavaImportStatement.class.getSimpleName()
	);
	
	/*
	 * Single Line Comment Info
	 */
	
	protected static class TestJavaSingleLineComment extends JavaSingleLineComment{
		
		protected TestJavaSingleLineComment(boolean editable, String content){
			super(editable, content);
		}
		
		public static TestJavaSingleLineCommentBuilder builder(){
			return new TestJavaSingleLineCommentBuilder(false);
		}
	}
	
	protected static class TestJavaSingleLineCommentBuilder extends JavaSingleLineCommentBuilder<TestJavaSingleLineComment>{
		
		private final boolean editable;
		
		private TestJavaSingleLineCommentBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaSingleLineComment constructSingleLineComment(){
			return new TestJavaSingleLineComment(editable, content);
		}
	}
	
	protected static final List<Supplier<JavaSingleLineCommentBuilder<? extends JavaSingleLineComment>>> singleLineCommentBuilders =
			ListUtil.createList(
					UneditableJavaSingleLineComment::builder,
					EditableJavaSingleLineComment::builder,
					TestJavaSingleLineComment::builder
			);
	protected static final List<String> singleLineCommentSimpleClassNames = ListUtil.createList(
			UneditableJavaSingleLineComment.class.getSimpleName(),
			EditableJavaSingleLineComment.class.getSimpleName(),
			TestJavaSingleLineComment.class.getSimpleName()
	);
	
	/*
	 * Multi Line Comment Info
	 */
	
	protected static class TestJavaMultiLineComment extends JavaMultiLineComment{
		
		protected TestJavaMultiLineComment(boolean editable, List<String> content){
			super(editable, content);
		}
		
		public static TestJavaMultiLineCommentBuilder builder(){
			return new TestJavaMultiLineCommentBuilder(false);
		}
	}
	
	protected static class TestJavaMultiLineCommentBuilder extends JavaMultiLineCommentBuilder<TestJavaMultiLineComment>{
		
		private final boolean editable;
		
		private TestJavaMultiLineCommentBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaMultiLineComment constructComment(){
			return new TestJavaMultiLineComment(editable, content);
		}
	}
	
	protected static final List<Supplier<JavaMultiLineCommentBuilder<? extends JavaMultiLineComment>>> multiLineCommentBuilders =
			ListUtil.createList(
					UneditableJavaMultiLineComment::builder,
					EditableJavaMultiLineComment::builder,
					TestJavaMultiLineComment::builder
			);
	protected static final List<String> multiLineCommentSimpleClassNames = ListUtil.createList(
			UneditableJavaMultiLineComment.class.getSimpleName(),
			EditableJavaMultiLineComment.class.getSimpleName(),
			TestJavaMultiLineComment.class.getSimpleName()
	);
	
	/*
	 * Javadoc Info
	 */
	
	protected static class TestJavadoc extends Javadoc{
		
		protected TestJavadoc(
				boolean editable, boolean condensed, List<String> content, String author, String version, String since,
				List<Pair<String, String>> params, String returnVal, List<Pair<String, String>> throwsInfos){
			super(editable, condensed, content, author, version, since, params, returnVal, throwsInfos);
		}
		
		public static TestJavadocBuilder builder(){
			return new TestJavadocBuilder(false);
		}
	}
	
	protected static class TestJavadocBuilder extends JavadocBuilder<TestJavadoc>{
		
		private final boolean editable;
		
		public TestJavadocBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavadoc constructJavadoc(){
			return new TestJavadoc(editable, condensed, content, author, version, since, params, returnVal, throwsInfos);
		}
	}
	
	protected static final List<Supplier<JavadocBuilder<? extends Javadoc>>> javadocBuilders =
			ListUtil.createList(
					UneditableJavadoc::builder,
					EditableJavadoc::builder,
					TestJavadoc::builder
			);
	
	protected static final List<String> javadocSimpleClassNames = ListUtil.createList(
			UneditableJavadoc.class.getSimpleName(),
			EditableJavadoc.class.getSimpleName(),
			TestJavadoc.class.getSimpleName()
	);
	
	/*
	 * Java Annotation Info
	 */
	
	protected static class TestJavaAnnotation extends JavaAnnotation{
		private TestJavaAnnotation(boolean editable, String name, String canonicalName, List<Pair<String, String>> parameters){
			super(editable, name, canonicalName, parameters);
		}
		
		public static TestJavaAnnotationBuilder builder(){
			return new TestJavaAnnotationBuilder(false);
		}
	}
	
	protected static class TestJavaAnnotationBuilder extends JavaAnnotationBuilder<TestJavaAnnotation>{
		
		private final boolean editable;
		
		private TestJavaAnnotationBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaAnnotation constructAnnotation(){
			return new TestJavaAnnotation(editable, name, canonicalName, parameters);
		}
	}
	
	protected static final List<Supplier<JavaAnnotationBuilder<? extends JavaAnnotation>>> annotationBuilders =
			ListUtil.createList(
					UneditableJavaAnnotation::builder,
					EditableJavaAnnotation::builder,
					TestJavaAnnotation::builder
			);
	protected static final List<String> annotationSimpleClassNames = ListUtil.createList(
			UneditableJavaAnnotation.class.getSimpleName(),
			EditableJavaAnnotation.class.getSimpleName(),
			TestJavaAnnotation.class.getSimpleName()
	);
	
	/*
	 * Field Info
	 */
	
	protected static class TestJavaField extends JavaField{
		
		protected TestJavaField(
				boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, boolean isFinal,
				JavaType type, String name, String value){
			super(editable, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
		
		public static TestJavaFieldBuilder builder(){
			return new TestJavaFieldBuilder(false);
		}
	}
	
	protected static class TestJavaFieldBuilder extends JavaFieldBuilder<TestJavaField>{
		
		private final boolean editable;
		
		public TestJavaFieldBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaField constructField(){
			return new TestJavaField(editable, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
	}
	
	protected static final List<Supplier<JavaFieldBuilder<? extends JavaField>>> fieldBuilders =
			ListUtil.createList(
					UneditableJavaField::builder,
					EditableJavaField::builder,
					TestJavaField::builder
			);
	
	protected static final List<String> fieldSimpleClassNames = ListUtil.createList(
			UneditableJavaField.class.getSimpleName(),
			EditableJavaField.class.getSimpleName(),
			TestJavaField.class.getSimpleName()
	);
	
	/*
	 * Method Info
	 */
	
	protected static class TestJavaMethod extends JavaMethod{
		
		private TestJavaMethod(
				boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal,
				JavaType returnType, String name,
				List<JavaParameter> parameters, List<String> throwTypes, List<String> lines){
			super(editable, javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal,
					returnType, name,
					parameters, throwTypes, lines);
		}
		
		public static TestJavaMethodBuilder builder(){
			return new TestJavaMethodBuilder(false);
		}
	}
	
	protected static class TestJavaMethodBuilder extends JavaMethodBuilder<TestJavaMethod>{
		
		private final boolean editable;
		
		private TestJavaMethodBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaMethod constructMethod(){
			return new TestJavaMethod(editable, javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal,
					returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	protected static final List<Supplier<JavaMethodBuilder<? extends JavaMethod>>> methodBuilders =
			ListUtil.createList(
					UneditableJavaMethod::builder,
					EditableJavaMethod::builder,
					TestJavaMethod::builder
			);
	
	protected static final List<String> methodSimpleClassNames = ListUtil.createList(
			UneditableJavaMethod.class.getSimpleName(),
			EditableJavaMethod.class.getSimpleName(),
			TestJavaMethod.class.getSimpleName()
	);
	
	/*
	 * Class Info
	 */
	
	protected static class TestJavaClass extends JavaClass{
		
		private TestJavaClass(
				boolean editable, boolean isInnerClass,
				JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
				Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal,
				JavaType className, JavaType superClassName, List<JavaType> implementsInterfaceNames,
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
	
	protected static class TestJavaClassBuilder extends JavaClassBuilder<TestJavaClass>{
		
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
		protected JavaSingleLineCommentBuilder<?> getSingleLineCommentBuilder(){
			return UneditableJavaSingleLineComment.builder();
		}
		
		@Override
		protected JavaMultiLineCommentBuilder<?> getMultiLineCommentBuilder(){
			return UneditableJavaMultiLineComment.builder();
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
	
	protected static final List<Supplier<JavaClassBuilder<? extends JavaClass>>> classBuilders =
			ListUtil.createList(
					UneditableJavaClass::builder,
					EditableJavaClass::builder,
					TestJavaClass::builder
			);
	
	protected static final List<String> classSimpleClassNames = ListUtil.createList(
			UneditableJavaClass.class.getSimpleName(),
			EditableJavaClass.class.getSimpleName(),
			TestJavaClass.class.getSimpleName()
	);
}
