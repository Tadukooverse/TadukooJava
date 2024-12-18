package com.github.tadukoo.java.importstatement;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.StringUtil;

/**
 * Represents an import statement in Java
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class JavaImportStatement implements JavaCodeType{
	
	/** Whether this import statement is editable or not */
	private final boolean editable;
	/** Whether this import is static or not */
	protected boolean isStatic;
	/** The name for the import */
	protected String importName;
	
	/**
	 * Constructs a new {@link JavaImportStatement} using the given parameters
	 *
	 * @param editable Whether this import statement is editable or not
	 * @param isStatic Whether this import is static or not
	 * @param importName The name for the import
	 */
	protected JavaImportStatement(boolean editable, boolean isStatic, String importName){
		this.editable = editable;
		this.isStatic = isStatic;
		this.importName = importName;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.IMPORT_STATEMENT;
	}
	
	/**
	 * @return Whether this import statement is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return Whether this import is static or not
	 */
	public boolean isStatic(){
		return isStatic;
	}
	
	/**
	 * @return The name for the import
	 */
	public String getImportName(){
		return importName;
	}
	
	/**
	 * @return The code represented by this import statement
	 */
	@Override
	public String toString(){
		StringBuilder statement = new StringBuilder(IMPORT_TOKEN).append(' ');
		
		// Add "static" if it's static
		if(isStatic){
			statement.append(STATIC_MODIFIER).append(' ');
		}
		
		// Finish with the import name and semicolon
		statement.append(importName).append(SEMICOLON);
		
		return statement.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o){
		if(o instanceof JavaImportStatement importStatement){
			return StringUtil.equals(toString(), importStatement.toString());
		}else{
			return false;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		// Start builder
		StringBuilder codeString = new StringBuilder(this.getClass().getSimpleName())
				.append(".builder()");
		
		// Add isStatic if the import is static
		if(isStatic){
			codeString.append(NEWLINE_WITH_2_TABS).append(".isStatic()");
		}
		
		// Add import name and finish building
		codeString.append(NEWLINE_WITH_2_TABS).append(".importName(\"").append(importName).append("\")")
				.append(NEWLINE_WITH_2_TABS).append(".build()");
		
		return codeString.toString();
	}
}
