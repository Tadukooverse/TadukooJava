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
import com.github.tadukoo.java.parsing.FullJavaParser;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a class in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 * @since Alpha v.0.4
 */
public class EditableJavaClass extends JavaClass{
	
	/**
	 * A builder used to make an {@link EditableJavaClass}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 * @since Alpha v.0.4
	 * @see JavaClassBuilder
	 */
	public static class EditableJavaClassBuilder extends JavaClassBuilder<EditableJavaClass>{
		
		/** Not allowed to instantiate outside {@link EditableJavaClass} */
		private EditableJavaClassBuilder(){
			super();
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
		protected List<String> checkForSpecificErrors(){
			List<String> errors = new ArrayList<>();
			
			// Package Declaration can't be uneditable
			if(packageDeclaration != null && !packageDeclaration.isEditable()){
				errors.add("package declaration is not editable in this editable JavaClass");
			}
			
			// Import Statements can't be uneditable
			for(JavaImportStatement importStatement: importStatements){
				if(!importStatement.isEditable()){
					errors.add("some import statements are not editable in this editable JavaClass");
					break;
				}
			}
			
			// Javadoc can't be uneditable
			if(javadoc != null && !javadoc.isEditable()){
				errors.add("javadoc is not editable in this editable JavaClass");
			}
			
			// Annotations can't be uneditable
			for(JavaAnnotation annotation: annotations){
				if(!annotation.isEditable()){
					errors.add("some annotations are not editable in this editable JavaClass");
					break;
				}
			}
			
			// Static Code Blocks can't be uneditable
			for(JavaStaticCodeBlock staticCodeBlock: staticCodeBlocks){
				if(!staticCodeBlock.isEditable()){
					errors.add("some static code blocks are not editable in this editable JavaClass");
					break;
				}
			}
			
			// Single-Line Comments can't be uneditable
			for(JavaSingleLineComment singleLineComment: singleLineComments){
				if(!singleLineComment.isEditable()){
					errors.add("some single-line comments are not editable in this editable JavaClass");
					break;
				}
			}
			
			// Multi-Line Comments can't be uneditable
			for(JavaMultiLineComment multiLineComment: multiLineComments){
				if(!multiLineComment.isEditable()){
					errors.add("some multi-line comments are not editable in this editable JavaClass");
					break;
				}
			}
			
			// Inner Classes can't be uneditable
			for(JavaClass clazz: innerClasses){
				if(!clazz.isEditable()){
					errors.add("some inner classes are not editable in this editable JavaClass");
					break;
				}
			}
			
			// Fields can't be uneditable
			for(JavaField field: fields){
				if(!field.isEditable()){
					errors.add("some fields are not editable in this editable JavaClass");
					break;
				}
			}
			
			// Methods can't be uneditable
			for(JavaMethod method: methods){
				if(!method.isEditable()){
					errors.add("some methods are not editable in this editable JavaClass");
				}
			}
			
			return errors;
		}
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaClass constructClass(){
			return new EditableJavaClass(isInnerClass, packageDeclaration, importStatements,
					javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal, className,
					superClassName, implementsInterfaceNames,
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
	 * @param superClassName The name of the class this one extends (can be null),
	 * along with type parameters to form a {@link JavaType}
	 * @param implementsInterfaceNames The names of interfaces this class implements,
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
			JavaType className, JavaType superClassName, List<JavaType> implementsInterfaceNames,
			List<JavaStaticCodeBlock> staticCodeBlocks,
			List<JavaSingleLineComment> singleLineComments, List<JavaMultiLineComment> multiLineComments,
			List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods,
			List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		super(true, isInnerClass, packageDeclaration, importStatements,
				javadoc, annotations,
				visibility, isAbstract, isStatic, isFinal, className,
				superClassName, implementsInterfaceNames,
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
	
	/**
	 * @param isInnerClass Whether this is an inner class or not
	 */
	public void setInnerClass(boolean isInnerClass){
		this.isInnerClass = isInnerClass;
	}
	
	/**
	 * @param packageDeclaration The {@link JavaPackageDeclaration package declaration} of the class
	 */
	public void setPackageDeclaration(JavaPackageDeclaration packageDeclaration){
		if(!packageDeclaration.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable package declaration");
		}
		this.packageDeclaration = packageDeclaration;
	}
	
	/**
	 * @param packageName The package name of the class, which gets put in a {@link JavaPackageDeclaration}
	 */
	public void setPackageName(String packageName){
		packageDeclaration = EditableJavaPackageDeclaration.builder()
				.packageName(packageName)
				.build();
	}
	
	/**
	 * @param importStatement An {@link JavaImportStatement import statement} of this class to be added
	 */
	public void addImportStatement(JavaImportStatement importStatement){
		if(!importStatement.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable import statements");
		}
		importStatements.add(importStatement);
	}
	
	/**
	 * @param importStatements {@link JavaImportStatement import statements} of this class to be added
	 */
	public void addImportStatements(List<JavaImportStatement> importStatements){
		for(JavaImportStatement importStatement: importStatements){
			if(!importStatement.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable import statements");
			}
		}
		this.importStatements.addAll(importStatements);
	}
	
	/**
	 * @param importStatements The {@link JavaImportStatement import statements} of this class
	 */
	public void setImportStatements(List<JavaImportStatement> importStatements){
		for(JavaImportStatement importStatement: importStatements){
			if(!importStatement.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable import statements");
			}
		}
		this.importStatements = importStatements;
	}
	
	/**
	 * @param importName The name of an import to add to the class (becomes a {@link JavaImportStatement})
	 * @param isStatic Whether the import is static or not
	 */
	public void addImportName(String importName, boolean isStatic){
		importStatements.add(EditableJavaImportStatement.builder()
				.isStatic(isStatic).importName(importName)
				.build());
	}
	
	/**
	 * @param importNames The names of imports to add to the class (become {@link JavaImportStatement import statements})
	 * @param isStatic Whether the imports are static or not
	 */
	public void addImportNames(List<String> importNames, boolean isStatic){
		for(String importName: importNames){
			importStatements.add(EditableJavaImportStatement.builder()
					.isStatic(isStatic).importName(importName)
					.build());
		}
	}
	
	/**
	 * @param importNames The import names to set as the only imports on the class (they become {@link JavaImportStatement import statements})
	 * @param isStatic Whether the imports are static or not
	 */
	public void setImportNames(List<String> importNames, boolean isStatic){
		importStatements = new ArrayList<>();
		addImportNames(importNames, isStatic);
	}
	
	/**
	 * @param javadoc The {@link Javadoc} for the class
	 */
	public void setJavadoc(Javadoc javadoc){
		if(!javadoc.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable Javadoc");
		}
		this.javadoc = javadoc;
	}
	
	/**
	 * @param annotation An {@link JavaAnnotation annotation} to be added to the class - must be editable
	 */
	public void addAnnotation(JavaAnnotation annotation){
		if(!annotation.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable Java Annotations");
		}
		annotations.add(annotation);
	}
	
	/**
	 * @param annotations {@link JavaAnnotation annotations} to be added to the class - must be editable
	 */
	public void addAnnotations(List<JavaAnnotation> annotations){
		for(JavaAnnotation annotation: annotations){
			if(!annotation.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable Java Annotations");
			}
		}
		this.annotations.addAll(annotations);
	}
	
	/**
	 * @param annotations The {@link JavaAnnotation annotations} on the class - must be editable
	 */
	public void setAnnotations(List<JavaAnnotation> annotations){
		for(JavaAnnotation annotation: annotations){
			if(!annotation.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable Java Annotations");
			}
		}
		this.annotations = annotations;
	}
	
	/**
	 * @param visibility The {@link Visibility} of the class
	 */
	public void setVisibility(Visibility visibility){
		this.visibility = visibility;
	}
	
	/**
	 * @param isAbstract Whether this is an abstract class or not
	 */
	public void setAbstract(boolean isAbstract){
		this.isAbstract = isAbstract;
	}
	
	/**
	 * @param isStatic Whether this is a static class or not
	 */
	public void setStatic(boolean isStatic){
		this.isStatic = isStatic;
	}
	
	/**
	 * @param isFinal Whether this is a final class or not
	 */
	public void setFinal(boolean isFinal){
		this.isFinal = isFinal;
	}
	
	/**
	 * @param className The name of the class, along with type parameters to form a {@link JavaType}
	 */
	public void setClassName(JavaType className){
		this.className = className;
	}
	
	/**
	 * @param classNameText The text to parse of the name of the class,
	 * along with type parameters to form a {@link JavaType}
	 */
	public void setClassName(String classNameText){
		this.className = FullJavaParser.parseJavaType(classNameText);
	}
	
	/**
	 * @param superClassName The name of the class this one extends (can be null),
	 * along with type parameters to form a {@link JavaType}
	 */
	public void setSuperClassName(JavaType superClassName){
		this.superClassName = superClassName;
	}
	
	/**
	 * @param superClassNameText The text to parse of the name of the class this one extends (can be null),
	 * along with type parameters to form a {@link JavaType}
	 */
	public void setSuperClassName(String superClassNameText){
		this.superClassName = FullJavaParser.parseJavaType(superClassNameText);
	}
	
	/**
	 * @param implementsInterfaceName The name of an interfaces this class implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	public void addImplementsInterfaceName(JavaType implementsInterfaceName){
		implementsInterfaceNames.add(implementsInterfaceName);
	}
	
	/**
	 * @param implementsInterfaceNameText The text for the name of an interfaces this class implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	public void addImplementsInterfaceName(String implementsInterfaceNameText){
		implementsInterfaceNames.add(FullJavaParser.parseJavaType(implementsInterfaceNameText));
	}
	
	/**
	 * @param implementsInterfaceNames The names of interfaces this class implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	public void addImplementsInterfaceNames(List<JavaType> implementsInterfaceNames){
		this.implementsInterfaceNames.addAll(implementsInterfaceNames);
	}
	
	/**
	 * @param implementsInterfaceNameTexts The text of the names of interfaces this class implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	public void addImplementsInterfaceNameTexts(List<String> implementsInterfaceNameTexts){
		this.implementsInterfaceNames.addAll(implementsInterfaceNameTexts.stream()
				.map(FullJavaParser::parseJavaType).toList());
	}
	
	/**
	 * @param implementsInterfaceNames The names of interfaces this class implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	public void setImplementsInterfaceNames(List<JavaType> implementsInterfaceNames){
		this.implementsInterfaceNames = implementsInterfaceNames;
	}
	
	/**
	 * @param implementsInterfaceNameTexts The text for the names of interfaces this class implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	public void setImplementsInterfaceNameTexts(List<String> implementsInterfaceNameTexts){
		this.implementsInterfaceNames = implementsInterfaceNameTexts.stream()
				.map(FullJavaParser::parseJavaType).collect(Collectors.toList());
	}
	
	/**
	 * @param staticCodeBlock A {@link JavaStaticCodeBlock static code block} to be added inside the class
	 * - must be editable
	 */
	public void addStaticCodeBlock(JavaStaticCodeBlock staticCodeBlock){
		if(!staticCodeBlock.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable static code blocks");
		}
		staticCodeBlocks.add(staticCodeBlock);
	}
	
	/**
	 * @param staticCodeBlocks {@link JavaStaticCodeBlock static code blocks} to be added inside the class
	 * - must be editable
	 */
	public void addStaticCodeBlocks(List<JavaStaticCodeBlock> staticCodeBlocks){
		for(JavaStaticCodeBlock staticCodeBlock: staticCodeBlocks){
			if(!staticCodeBlock.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable static code blocks");
			}
		}
		this.staticCodeBlocks.addAll(staticCodeBlocks);
	}
	
	/**
	 * @param staticCodeBlocks The {@link JavaStaticCodeBlock static code blocks} inside the class
	 * - must be editable
	 */
	public void setStaticCodeBlocks(List<JavaStaticCodeBlock> staticCodeBlocks){
		for(JavaStaticCodeBlock staticCodeBlock: staticCodeBlocks){
			if(!staticCodeBlock.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable static code blocks");
			}
		}
		this.staticCodeBlocks = staticCodeBlocks;
	}
	
	/**
	 * @param singleLineComment A {@link JavaSingleLineComment single-line comment} to be added inside the class
	 * - must be editable
	 */
	public void addSingleLineComment(JavaSingleLineComment singleLineComment){
		if(!singleLineComment.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable single-line comments");
		}
		singleLineComments.add(singleLineComment);
	}
	
	/**
	 * @param singleLineComments {@link JavaSingleLineComment single-line comments} to be added inside the class
	 * - must be editable
	 */
	public void addSingleLineComments(List<JavaSingleLineComment> singleLineComments){
		for(JavaSingleLineComment singleLineComment: singleLineComments){
			if(!singleLineComment.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable single-line comments");
			}
		}
		this.singleLineComments.addAll(singleLineComments);
	}
	
	/**
	 * @param singleLineComments {@link JavaSingleLineComment single-line comments} inside the class - must be editable
	 */
	public void setSingleLineComments(List<JavaSingleLineComment> singleLineComments){
		for(JavaSingleLineComment singleLineComment: singleLineComments){
			if(!singleLineComment.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable single-line comments");
			}
		}
		this.singleLineComments = singleLineComments;
	}
	
	/**
	 * @param multiLineComment A {@link JavaMultiLineComment multi-line comment} to be added inside the class
	 * - must be editable
	 */
	public void addMultiLineComment(JavaMultiLineComment multiLineComment){
		if(!multiLineComment.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable multi-line comments");
		}
		multiLineComments.add(multiLineComment);
	}
	
	/**
	 * @param multiLineComments {@link JavaMultiLineComment multi-line comments} to be added inside the class
	 * - must be editable
	 */
	public void addMultiLineComments(List<JavaMultiLineComment> multiLineComments){
		for(JavaMultiLineComment multiLineComment: multiLineComments){
			if(!multiLineComment.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable multi-line comments");
			}
		}
		this.multiLineComments.addAll(multiLineComments);
	}
	
	/**
	 * @param multiLineComments {@link JavaMultiLineComment multi-line comments} inside the class - must be editable
	 */
	public void setMultiLineComments(List<JavaMultiLineComment> multiLineComments){
		for(JavaMultiLineComment multiLineComment: multiLineComments){
			if(!multiLineComment.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable multi-line comments");
			}
		}
		this.multiLineComments = multiLineComments;
	}
	
	/**
	 * @param innerClass An inner {@link JavaClass class} to be added inside the class - must be editable
	 */
	public void addInnerClass(JavaClass innerClass){
		if(!innerClass.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable inner classes");
		}
		innerClasses.add(innerClass);
	}
	
	/**
	 * @param innerClasses Inner {@link JavaClass classes} to be added inside the class - must be editable
	 */
	public void addInnerClasses(List<JavaClass> innerClasses){
		for(JavaClass innerClass: innerClasses){
			if(!innerClass.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable inner classes");
			}
		}
		this.innerClasses.addAll(innerClasses);
	}
	
	/**
	 * @param innerClasses Inner {@link JavaClass classes} inside the class - must be editable
	 */
	public void setInnerClasses(List<JavaClass> innerClasses){
		for(JavaClass innerClass: innerClasses){
			if(!innerClass.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable inner classes");
			}
		}
		this.innerClasses = innerClasses;
	}
	
	/**
	 * @param field A {@link JavaField field} to be added to the class - must be editable
	 */
	public void addField(JavaField field){
		if(!field.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable Java Fields");
		}
		fields.add(field);
	}
	
	/**
	 * @param fields {@link JavaField fields} to be added to the class - must be editable
	 */
	public void addFields(List<JavaField> fields){
		for(JavaField field: fields){
			if(!field.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable Java Fields");
			}
		}
		this.fields.addAll(fields);
	}
	
	/**
	 * @param fields The {@link JavaField fields} on the class - must be editable
	 */
	public void setFields(List<JavaField> fields){
		for(JavaField field: fields){
			if(!field.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable Java Fields");
			}
		}
		this.fields = fields;
	}
	
	/**
	 * @param method A {@link JavaMethod method} to add to the class - must be editable
	 */
	public void addMethod(JavaMethod method){
		if(!method.isEditable()){
			throw new IllegalArgumentException("editable Java Class requires editable Java Methods");
		}
		methods.add(method);
	}
	
	/**
	 * @param methods {@link JavaMethod methods} to add to the class - must be editable
	 */
	public void addMethods(List<JavaMethod> methods){
		for(JavaMethod method: methods){
			if(!method.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable Java Methods");
			}
		}
		this.methods.addAll(methods);
	}
	
	/**
	 * @param methods The {@link JavaMethod methods} in the class - must be editable
	 */
	public void setMethods(List<JavaMethod> methods){
		for(JavaMethod method: methods){
			if(!method.isEditable()){
				throw new IllegalArgumentException("editable Java Class requires editable Java Methods");
			}
		}
		this.methods = methods;
	}
	
	/**
	 * @param innerElementsOrder The order of the elements inside the class
	 */
	public void setInnerElementsOrder(List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		this.innerElementsOrder = innerElementsOrder;
	}
}
