package com.github.tadukoo.java.packagedeclaration;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.StringUtil;

/**
 * Represents a package declaration in Java
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class JavaPackageDeclaration implements JavaCodeType{
	
	/** Whether this {@link JavaPackageDeclaration package declaration} is editable or not */
	private final boolean editable;
	/** The name of the package in this declaration */
	protected String packageName;
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.PACKAGE_DECLARATION;
	}
	
	/**
	 * Constructs a new {@link JavaPackageDeclaration package declaration} using the given parameters
	 *
	 * @param editable Whether this package declaration is editable or not
	 * @param packageName The name of the package in the declaration
	 */
	protected JavaPackageDeclaration(boolean editable, String packageName){
		this.editable = editable;
		this.packageName = packageName;
	}
	
	/**
	 * @return Whether this {@link JavaPackageDeclaration package declaration} is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The name of the package in this declaration
	 */
	public String getPackageName(){
		return packageName;
	}
	
	/**
	 * @return The Java code represented by this {@link JavaPackageDeclaration package declaration}
	 */
	@Override
	public String toString(){
		return PACKAGE_TOKEN + " " + packageName + SEMICOLON;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o){
		if(o instanceof JavaPackageDeclaration packageDeclaration){
			return StringUtil.equals(toString(), packageDeclaration.toString());
		}else{
			return false;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		return this.getClass().getSimpleName() + ".builder()" +
				NEWLINE_WITH_2_TABS + ".packageName(\"" + packageName + "\")" +
				NEWLINE_WITH_2_TABS + ".build()";
	}
}
