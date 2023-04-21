package com.github.tadukoo.java.packagedeclaration;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder used to build a new {@link JavaPackageDeclaration}. It takes the following parameters:
 *
 * <table>
 *     <caption>Java Package Declaration Parameters</caption>
 *     <tr>
 *         <th>Parameter Name</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>packageName</td>
 *         <td>The name of the package in the declaration</td>
 *         <td>Required</td>
 *     </tr>
 * </table>
 *
 * @param <PackageDeclaration> The type of {@link JavaPackageDeclaration} that will be returned by the builder
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class JavaPackageDeclarationBuilder<PackageDeclaration extends JavaPackageDeclaration>{
	
	/** The name of the package in the declaration */
	protected String packageName = null;
	
	/** Not allowed to instantiate outside of subclasses */
	protected JavaPackageDeclarationBuilder(){ }
	
	/**
	 * @param packageName The name of the package in the declaration
	 * @return this, to continue building
	 */
	public JavaPackageDeclarationBuilder<PackageDeclaration> packageName(String packageName){
		this.packageName = packageName;
		return this;
	}
	
	/**
	 * Checks for any errors in the set parameters
	 */
	private void checkForErrors(){
		List<String> errors = new ArrayList<>();
		
		if(StringUtil.isBlank(packageName)){
			errors.add("packageName is required!");
		}
		
		if(!errors.isEmpty()){
			throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
		}
	}
	
	/**
	 * Builds a new {@link JavaPackageDeclaration package declaration} using the set parameters,
	 * after checking for errors
	 *
	 * @return The newly built {@link JavaPackageDeclaration package declaration}
	 */
	public PackageDeclaration build(){
		checkForErrors();
		
		return constructPackageDeclaration();
	}
	
	/**
	 * @return A newly constructed {@link JavaPackageDeclaration package declaration}
	 */
	protected abstract PackageDeclaration constructPackageDeclaration();
}
