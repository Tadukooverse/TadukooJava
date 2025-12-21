package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.classtype.JavaClassType;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Java Class is used to represent a class in Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.7
 * @since Alpha v.0.2 (as old version that is now more like UneditableJavaClass), Alpha v.0.4 (as newer version)
 */
public abstract class JavaClass extends JavaClassType{
	
	/**
	 * Constructs a new Java Class with the given parameters
	 *
	 * @param editable Whether this class is editable or not
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
	 * @param extendsTypes The name of the class this one extends (can be null), along with type parameters to form a {@link JavaType}
	 * @param implementsInterfaceNames The names of interfaces this class implements, along with type parameters to form a {@link JavaType}
	 * @param staticCodeBlocks The {@link JavaStaticCodeBlock static code blocks} inside the class
	 * @param singleLineComments The {@link JavaSingleLineComment single-line comments} inside the class
	 * @param multiLineComments The {@link JavaMultiLineComment multi-line comments} inside the class
	 * @param innerClasses Inner {@link JavaClass classes} inside the class
	 * @param fields The {@link JavaField fields} on the class
	 * @param methods The {@link JavaMethod methods} in the class
	 * @param innerElementsOrder The order of the elements inside the class
	 */
	protected JavaClass(
			boolean editable, boolean isInnerClass,
			JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal, JavaType className,
			List<JavaType> extendsTypes, List<JavaType> implementsInterfaceNames,
			List<JavaStaticCodeBlock> staticCodeBlocks,
			List<JavaSingleLineComment> singleLineComments, List<JavaMultiLineComment> multiLineComments,
			List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods,
			List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		super(editable, isInnerClass,
				packageDeclaration, importStatements,
				javadoc, annotations,
				visibility, isAbstract, isStatic, isFinal,
				JavaCodeTypes.CLASS, className,
				extendsTypes, implementsInterfaceNames,
				staticCodeBlocks,
				singleLineComments, multiLineComments,
				innerClasses, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(),
				fields, methods,
				innerElementsOrder);
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean isAbstract(){
		return super.isAbstract();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean isFinal(){
		return super.isFinal();
	}
	
	/**
	 * @return The class (as a {@link JavaType}) that this class extends, or null
	 */
	public JavaType getExtendsType(){
		if(extendsTypes.isEmpty()){
			return null;
		}else{
			return extendsTypes.get(0);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public List<JavaType> getImplementsTypes(){
		return super.getImplementsTypes();
	}
	
	/** {@inheritDoc} */
	@Override
	public List<JavaStaticCodeBlock> getStaticCodeBlocks(){
		return super.getStaticCodeBlocks();
	}
	
	/** {@inheritDoc} */
	@Override
	public List<JavaField> getFields(){
		return super.getFields();
	}
	
	/** {@inheritDoc} */
	@Override
	public Map<String, JavaField> getFieldsMap(){
		return super.getFieldsMap();
	}
	
	/** {@inheritDoc} */
	@Override
	public List<JavaMethod> getMethods(){
		return super.getMethods();
	}
	
	/** {@inheritDoc} */
	@Override
	public Map<String, JavaMethod> getMethodsMap(){
		return super.getMethodsMap();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherClass){
		if(otherClass instanceof JavaClass clazz){
			return StringUtil.equals(toString(), clazz.toString());
		}else{
			return false;
		}
	}
}
