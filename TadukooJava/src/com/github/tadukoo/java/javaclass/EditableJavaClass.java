package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.code.staticcodeblock.EditableJavaStaticCodeBlock;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlockBuilder;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineCommentBuilder;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineCommentBuilder;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.7
 * @since Alpha v.0.4
 */
public class EditableJavaClass extends JavaClass{
	
	/**
	 * A builder used to make an {@link EditableJavaClass}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.7
	 * @since Alpha v.0.4
	 * @see JavaClassBuilder
	 */
	public static class EditableJavaClassBuilder extends JavaClassBuilder<EditableJavaClass>{
		
		/** Not allowed to instantiate outside {@link EditableJavaClass} */
		private EditableJavaClassBuilder(){
			super();
			isEditable = true;
		}
		
		/** {@inheritDoc} */
		@Override
		protected JavaPackageDeclarationBuilder<?> getPackageDeclarationBuilder(){
			return EditableJavaPackageDeclaration.builder();
		}
		
		/** {@inheritDoc} */
		@Override
		protected JavaImportStatementBuilder<?> getImportStatementBuilder(){
			return EditableJavaImportStatement.builder();
		}
		
		/** {@inheritDoc} */
		@Override
		protected JavaStaticCodeBlockBuilder<?> getStaticCodeBlockBuilder(){
			return EditableJavaStaticCodeBlock.builder();
		}
		
		/** {@inheritDoc} */
		@Override
		protected JavaSingleLineCommentBuilder<?> getSingleLineCommentBuilder(){
			return EditableJavaSingleLineComment.builder();
		}
		
		/** {@inheritDoc} */
		@Override
		protected JavaMultiLineCommentBuilder<?> getMultiLineCommentBuilder(){
			return EditableJavaMultiLineComment.builder();
		}
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaClass constructClass(){
			return new EditableJavaClass(isInnerType, packageDeclaration, importStatements,
					javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal, name,
					extendsTypes, implementsTypes,
					staticCodeBlocks,
					singleLineComments, multiLineComments,
					innerClasses, fields, methods,
					innerElementsOrder);
		}
	}
	
	/**
	 * Constructs a new Java Class with the given parameters
	 *
	 * @param isInnerClass Whether this is an inner class or not
	 * @param packageDeclaration The {@link JavaPackageDeclaration package declaration} of the class
	 * @param importStatements The {@link JavaImportStatement import statements} of the class
	 * @param javadoc The {@link Javadoc} for the class
	 * @param annotations The {@link JavaAnnotation annotations} on the class
	 * @param visibility The {@link Visibility} of the class
	 * @param isAbstract Whether this is an abstract class or not
	 * @param isStatic Whether this is a static class or not
	 * @param isFinal Whether this is a final class or not
	 * @param className The name of the class, along with type parameters to form a {@link JavaType}
	 * @param extendsTypes The name of the class this one extends (can be null),
	 * along with type parameters to form a {@link JavaType}
	 * @param implementsTypes The names of interfaces this class implements,
	 * along with type parameters to form a {@link JavaType}
	 * @param staticCodeBlocks The {@link JavaStaticCodeBlock static code blocks} inside the class
	 * @param singleLineComments The {@link JavaSingleLineComment single-line comments} inside the class
	 * @param multiLineComments The {@link JavaMultiLineComment multi-line comments} inside the class
	 * @param innerClasses Inner {@link JavaClass classes} inside the class
	 * @param fields The {@link JavaField fields} on the class
	 * @param methods The {@link JavaMethod methods} in the class
	 * @param innerElementsOrder The order of the elements inside the class
	 */
	private EditableJavaClass(
			boolean isInnerClass, JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal,
			JavaType className, List<JavaType> extendsTypes, List<JavaType> implementsTypes,
			List<JavaStaticCodeBlock> staticCodeBlocks,
			List<JavaSingleLineComment> singleLineComments, List<JavaMultiLineComment> multiLineComments,
			List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods,
			List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		super(true, isInnerClass, packageDeclaration, importStatements,
				javadoc, annotations,
				visibility, isAbstract, isStatic, isFinal, className,
				extendsTypes, implementsTypes,
				staticCodeBlocks,
				singleLineComments, multiLineComments,
				innerClasses, fields, methods,
				innerElementsOrder);
	}
	
	/**
	 * @return A new {@link EditableJavaClassBuilder} to use to build a new {@link EditableJavaClass}
	 */
	public static EditableJavaClassBuilder builder(){
		return new EditableJavaClassBuilder();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setInnerType(boolean isInnerType){
		super.setInnerType(isInnerType);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setPackageDeclaration(JavaPackageDeclaration packageDeclaration){
		super.setPackageDeclaration(packageDeclaration);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setPackageName(String packageName){
		super.setPackageName(packageName);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addImportStatement(JavaImportStatement importStatement){
		super.addImportStatement(importStatement);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addImportStatements(List<JavaImportStatement> importStatements){
		super.addImportStatements(importStatements);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setImportStatements(List<JavaImportStatement> importStatements){
		super.setImportStatements(importStatements);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addImportName(String importName, boolean isStatic){
		super.addImportName(importName, isStatic);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addImportNames(List<String> importNames, boolean isStatic){
		super.addImportNames(importNames, isStatic);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setImportNames(List<String> importNames, boolean isStatic){
		super.setImportNames(importNames, isStatic);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setJavadoc(Javadoc javadoc){
		super.setJavadoc(javadoc);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addAnnotation(JavaAnnotation annotation){
		super.addAnnotation(annotation);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addAnnotations(List<JavaAnnotation> annotations){
		super.addAnnotations(annotations);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setAnnotations(List<JavaAnnotation> annotations){
		super.setAnnotations(annotations);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setVisibility(Visibility visibility){
		super.setVisibility(visibility);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setAbstract(boolean isAbstract){
		super.setAbstract(isAbstract);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setStatic(boolean isStatic){
		super.setStatic(isStatic);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setFinal(boolean isFinal){
		super.setFinal(isFinal);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setName(JavaType name){
		super.setName(name);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setName(String nameText){
		super.setName(nameText);
	}
	
	/**
	 * @param extendsType The name of the class this one extends (can be null),
	 * along with type parameters to form a {@link JavaType}
	 */
	public void setExtendsType(JavaType extendsType){
		if(extendsType == null){
			setExtendsTypes(new ArrayList<>());
		}else{
			setExtendsTypes(ListUtil.createList(extendsType));
		}
	}
	
	/**
	 * @param extendsTypeText The text to parse of the name of the class this one extends (can be null),
	 * along with type parameters to form a {@link JavaType}
	 */
	public void setExtendsType(String extendsTypeText){
		if(StringUtil.isBlank(extendsTypeText)){
			setExtendsTypes(new ArrayList<>());
		}else{
			setExtendsTypeTexts(ListUtil.createList(extendsTypeText));
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public void addImplementsType(JavaType implementsType){
		super.addImplementsType(implementsType);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addImplementsType(String implementsTypeText){
		super.addImplementsType(implementsTypeText);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addImplementsTypes(List<JavaType> implementsTypes){
		super.addImplementsTypes(implementsTypes);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addImplementsTypeTexts(List<String> implementsTypeTexts){
		super.addImplementsTypeTexts(implementsTypeTexts);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setImplementsTypes(List<JavaType> implementsTypes){
		super.setImplementsTypes(implementsTypes);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setImplementsTypeTexts(List<String> implementsTypeTexts){
		super.setImplementsTypeTexts(implementsTypeTexts);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addStaticCodeBlock(JavaStaticCodeBlock staticCodeBlock){
		super.addStaticCodeBlock(staticCodeBlock);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addStaticCodeBlocks(List<JavaStaticCodeBlock> staticCodeBlocks){
		super.addStaticCodeBlocks(staticCodeBlocks);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setStaticCodeBlocks(List<JavaStaticCodeBlock> staticCodeBlocks){
		super.setStaticCodeBlocks(staticCodeBlocks);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addSingleLineComment(JavaSingleLineComment singleLineComment){
		super.addSingleLineComment(singleLineComment);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addSingleLineComments(List<JavaSingleLineComment> singleLineComments){
		super.addSingleLineComments(singleLineComments);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setSingleLineComments(List<JavaSingleLineComment> singleLineComments){
		super.setSingleLineComments(singleLineComments);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addMultiLineComment(JavaMultiLineComment multiLineComment){
		super.addMultiLineComment(multiLineComment);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addMultiLineComments(List<JavaMultiLineComment> multiLineComments){
		super.addMultiLineComments(multiLineComments);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setMultiLineComments(List<JavaMultiLineComment> multiLineComments){
		super.setMultiLineComments(multiLineComments);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addInnerClass(JavaClass innerClass){
		super.addInnerClass(innerClass);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addInnerClasses(List<JavaClass> innerClasses){
		super.addInnerClasses(innerClasses);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setInnerClasses(List<JavaClass> innerClasses){
		super.setInnerClasses(innerClasses);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addField(JavaField field){
		super.addField(field);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addFields(List<JavaField> fields){
		super.addFields(fields);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setFields(List<JavaField> fields){
		super.setFields(fields);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addMethod(JavaMethod method){
		super.addMethod(method);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addMethods(List<JavaMethod> methods){
		super.addMethods(methods);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setMethods(List<JavaMethod> methods){
		super.setMethods(methods);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setInnerElementsOrder(List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		super.setInnerElementsOrder(innerElementsOrder);
	}
}
