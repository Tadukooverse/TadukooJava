package com.github.tadukoo.java.importstatement;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder used to build a {@link JavaImportStatement}. It takes the following parameters:
 *
 * <table>
 *     <caption>Java Import Statement Parameters</caption>
 *     <tr>
 *         <th>Parameter Name</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>isStatic</td>
 *         <td>Whether the import statement is static or not</td>
 *         <td>false</td>
 *     </tr>
 *     <tr>
 *         <td>importName</td>
 *         <td>The name used in the import</td>
 *         <td>Required</td>
 *     </tr>
 * </table>
 *
 * @param <ImportStatementType> The type of {@link JavaImportStatement} being built
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class JavaImportStatementBuilder<ImportStatementType extends JavaImportStatement>{
	
	/** Whether the import statement is static or not */
	protected boolean isStatic = false;
	/** The name used in the import */
	protected String importName = null;
	
	/** Not allowed to instantiate outside subclasses */
	protected JavaImportStatementBuilder(){ }
	
	/**
	 * Copies the settings from the given {@link JavaImportStatement import statement} into this builder
	 *
	 * @param importStatement The {@link JavaImportStatement import statement} to copy settings from
	 * @return this, to continue building
	 */
	public JavaImportStatementBuilder<ImportStatementType> copy(JavaImportStatement importStatement){
		this.isStatic = importStatement.isStatic();
		this.importName = importStatement.getImportName();
		return this;
	}
	
	/**
	 * Sets the import statement to be static
	 *
	 * @return this, to continue building
	 */
	public JavaImportStatementBuilder<ImportStatementType> isStatic(){
		isStatic = true;
		return this;
	}
	
	/**
	 * @param isStatic Whether the import statement is static or not
	 * @return this, to continue building
	 */
	public JavaImportStatementBuilder<ImportStatementType> isStatic(boolean isStatic){
		this.isStatic = isStatic;
		return this;
	}
	
	/**
	 * @param importName The name used in the import
	 * @return this, to continue building
	 */
	public JavaImportStatementBuilder<ImportStatementType> importName(String importName){
		this.importName = importName;
		return this;
	}
	
	/**
	 * Checks for any errors in the set parameters
	 */
	private void checkForErrors(){
		List<String> errors = new ArrayList<>();
		
		// import name is required
		if(StringUtil.isBlank(importName)){
			errors.add("importName is required!");
		}
		
		// Report any errors
		if(!errors.isEmpty()){
			throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
		}
	}
	
	/**
	 * Builds a new {@link JavaImportStatement} using the set parameters, after checking for any errors
	 *
	 * @return The newly built {@link JavaImportStatement}
	 */
	public ImportStatementType build(){
		checkForErrors();
		
		return constructImportStatement();
	}
	
	/**
	 * @return A newly constructed {@link JavaImportStatement} using the set parameters
	 */
	protected abstract ImportStatementType constructImportStatement();
}
