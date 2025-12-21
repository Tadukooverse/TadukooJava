package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.classtype.JavaClassTypeBuilder;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Class Builder is used to create a {@link JavaClass}. It has the following parameters:
 *
 * <table>
 *     <caption>Java Class Parameters</caption>
 *     <tr>
 *         <th>Parameter</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>isInnerType</td>
 *         <td>Whether the class is an inner class or not</td>
 *         <td>Defaults to false</td>
 *     </tr>
 *     <tr>
 *         <td>packageDeclaration</td>
 *         <td>The {@link JavaPackageDeclaration package declaration} of the class</td>
 *         <td>Defaults to null</td>
 *     </tr>
 *     <tr>
 *         <td>importStatements</td>
 *         <td>The {@link JavaImportStatement import statements} of the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>javadoc</td>
 *         <td>The {@link Javadoc} for the class</td>
 *         <td>Defaults to null</td>
 *     </tr>
 *     <tr>
 *         <td>annotations</td>
 *         <td>The {@link JavaAnnotation annotations} on the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>visibility</td>
 *         <td>The {@link Visibility} of the class</td>
 *         <td>{@link Visibility#NONE}</td>
 *     </tr>
 *     <tr>
 *         <td>isAbstract</td>
 *         <td>Whether the class is abstract or not</td>
 *         <td>false</td>
 *     </tr>
 *     <tr>
 *         <td>isStatic</td>
 *         <td>Whether the class is static or not</td>
 *         <td>false</td>
 *     </tr>
 *     <tr>
 *         <td>isFinal</td>
 *         <td>Whether the class is final or not</td>
 *         <td>false</td>
 *     </tr>
 *     <tr>
 *         <td>name</td>
 *         <td>The name of the class</td>
 *         <td>Required</td>
 *     </tr>
 *     <tr>
 *         <td>extendsType</td>
 *         <td>The name of the class this one extends (can be null)</td>
 *         <td>null</td>
 *     </tr>
 *     <tr>
 *         <td>implementsTypes</td>
 *         <td>The names of interfaces this class implements as {@link JavaType types}</td>
 *         <td>An empty List</td>
 *     </tr>
 *     <tr>
 *         <td>staticCodeBlocks</td>
 *         <td>The {@link JavaStaticCodeBlock static code blocks} inside the class</td>
 *         <td>An empty List</td>
 *     </tr>
 *     <tr>
 *         <td>singleLineComments</td>
 *         <td>The {@link JavaSingleLineComment single-line comments} inside the class</td>
 *         <td>An empty List</td>
 *     </tr>
 *     <tr>
 *         <td>multiLineComments</td>
 *         <td>The {@link JavaMultiLineComment multi-line comments} inside the class</td>
 *         <td>An empty List</td>
 *     </tr>
 *     <tr>
 *         <td>innerClasses</td>
 *         <td>Inner {@link JavaClass classes} inside the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>fields</td>
 *         <td>The {@link JavaField fields} on the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>methods</td>
 *         <td>The {@link JavaMethod methods} in the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>innerElementsOrder</td>
 *         <td>The order of the elements inside the class</td>
 *         <td>The order they were added in, Required if there are comments</td>
 *     </tr>
 * </table>
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.7
 * @since Alpha v.0.2 (in JavaClass), Alpha v.0.4 (as a separate class)
 */
public abstract class JavaClassBuilder<ClassType extends JavaClass> extends JavaClassTypeBuilder<ClassType>{
	
	/**
	 * Constructs a new {@link JavaClassBuilder}
	 */
	protected JavaClassBuilder(){ }
	
	/**
	 * Copies the settings from the given {@link JavaClass class} to this builder
	 *
	 * @param clazz The {@link JavaClass class} to copy settings from
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> copy(JavaClass clazz){
		return (JavaClassBuilder<ClassType>) super.copy(clazz);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> isInnerType(boolean isInnerType){
		return (JavaClassBuilder<ClassType>) super.isInnerType(isInnerType);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> innerType(){
		return (JavaClassBuilder<ClassType>) super.innerType();
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> packageDeclaration(JavaPackageDeclaration packageDeclaration){
		return (JavaClassBuilder<ClassType>) super.packageDeclaration(packageDeclaration);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> packageName(String packageName){
		return (JavaClassBuilder<ClassType>) super.packageName(packageName);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> importStatement(JavaImportStatement importStatement){
		return (JavaClassBuilder<ClassType>) super.importStatement(importStatement);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> importStatements(List<JavaImportStatement> importStatements){
		return (JavaClassBuilder<ClassType>) super.importStatements(importStatements);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> importName(String importName, boolean isStatic){
		return (JavaClassBuilder<ClassType>) super.importName(importName, isStatic);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> importNames(List<String> importNames, boolean isStatic){
		return (JavaClassBuilder<ClassType>) super.importNames(importNames, isStatic);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> javadoc(Javadoc javadoc){
		return (JavaClassBuilder<ClassType>) super.javadoc(javadoc);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> annotations(List<JavaAnnotation> annotations){
		return (JavaClassBuilder<ClassType>) super.annotations(annotations);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> annotation(JavaAnnotation annotation){
		return (JavaClassBuilder<ClassType>) super.annotation(annotation);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> visibility(Visibility visibility){
		return (JavaClassBuilder<ClassType>) super.visibility(visibility);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> isAbstract(){
		return (JavaClassBuilder<ClassType>) super.isAbstract();
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> isAbstract(boolean isAbstract){
		return (JavaClassBuilder<ClassType>) super.isAbstract(isAbstract);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> isStatic(){
		return (JavaClassBuilder<ClassType>) super.isStatic();
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> isStatic(boolean isStatic){
		return (JavaClassBuilder<ClassType>) super.isStatic(isStatic);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> isFinal(){
		return (JavaClassBuilder<ClassType>) super.isFinal();
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> isFinal(boolean isFinal){
		return (JavaClassBuilder<ClassType>) super.isFinal(isFinal);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> name(JavaType name){
		return (JavaClassBuilder<ClassType>) super.name(name);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> name(String nameText){
		return (JavaClassBuilder<ClassType>) super.name(nameText);
	}
	
	/**
	 * @param extendsType The name of the class this one extends (can be null),
	 * along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> extendsType(JavaType extendsType){
		return (JavaClassBuilder<ClassType>) super.extendsTypes(ListUtil.createList(extendsType));
	}
	
	/**
	 * @param extendsTypeText The text to parse for the name of the class this one extends (can be null),
	 * along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> extendsType(String extendsTypeText){
		return (JavaClassBuilder<ClassType>) super.extendsTypesTexts(ListUtil.createList(extendsTypeText));
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> implementsTypes(List<JavaType> implementsTypes){
		return (JavaClassBuilder<ClassType>) super.implementsTypes(implementsTypes);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> implementsTypeTexts(List<String> implementsTypeTexts){
		return (JavaClassBuilder<ClassType>) super.implementsTypeTexts(implementsTypeTexts);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> implementsType(JavaType implementsType){
		return (JavaClassBuilder<ClassType>) super.implementsType(implementsType);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> implementsType(String implementsTypeText){
		return (JavaClassBuilder<ClassType>) super.implementsType(implementsTypeText);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> staticCodeBlocks(List<JavaStaticCodeBlock> staticCodeBlocks){
		return (JavaClassBuilder<ClassType>) super.staticCodeBlocks(staticCodeBlocks);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> staticCodeBlock(JavaStaticCodeBlock staticCodeBlock){
		return (JavaClassBuilder<ClassType>) super.staticCodeBlock(staticCodeBlock);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> staticCodeBlock(List<String> lines){
		return (JavaClassBuilder<ClassType>) super.staticCodeBlock(lines);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> singleLineComments(List<JavaSingleLineComment> singleLineComments){
		return (JavaClassBuilder<ClassType>) super.singleLineComments(singleLineComments);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> singleLineComment(JavaSingleLineComment singleLineComment){
		return (JavaClassBuilder<ClassType>) super.singleLineComment(singleLineComment);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> singleLineComment(String singleLineComment){
		return (JavaClassBuilder<ClassType>) super.singleLineComment(singleLineComment);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> multiLineComments(List<JavaMultiLineComment> multiLineComments){
		return (JavaClassBuilder<ClassType>) super.multiLineComments(multiLineComments);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> multiLineComment(JavaMultiLineComment multiLineComment){
		return (JavaClassBuilder<ClassType>) super.multiLineComment(multiLineComment);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> multiLineComment(String... multiLineComment){
		return (JavaClassBuilder<ClassType>) super.multiLineComment(multiLineComment);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> innerClasses(List<JavaClass> innerClasses){
		return (JavaClassBuilder<ClassType>) super.innerClasses(innerClasses);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> innerClass(JavaClass innerClass){
		return (JavaClassBuilder<ClassType>) super.innerClass(innerClass);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> fields(List<JavaField> fields){
		return (JavaClassBuilder<ClassType>) super.fields(fields);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> field(JavaField field){
		return (JavaClassBuilder<ClassType>) super.field(field);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> methods(List<JavaMethod> methods){
		return (JavaClassBuilder<ClassType>) super.methods(methods);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> method(JavaMethod method){
		return (JavaClassBuilder<ClassType>) super.method(method);
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaClassBuilder<ClassType> innerElementsOrder(List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		return (JavaClassBuilder<ClassType>) super.innerElementsOrder(innerElementsOrder);
	}
	
	/** {@inheritDoc} */
	@Override
	protected List<String> checkForTypeSpecificErrors(){
		List<String> errors = new ArrayList<>();
		
		// Class can only have 1 extends type
		if(ListUtil.isNotBlank(extendsTypes) && extendsTypes.size() > 1){
			errors.add("class can only have one extendsType!");
		}
		
		return errors;
	}
}
