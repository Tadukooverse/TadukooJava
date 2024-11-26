package com.github.tadukoo.java.packagedeclaration;

/**
 * Represents a {@link JavaPackageDeclaration} that can't be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class UneditableJavaPackageDeclaration extends JavaPackageDeclaration{
	
	/**
	 * A builder used to make an {@link UneditableJavaPackageDeclaration}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @see JavaPackageDeclarationBuilder
	 */
	public static class UneditableJavaPackageDeclarationBuilder extends JavaPackageDeclarationBuilder<UneditableJavaPackageDeclaration>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaPackageDeclaration} */
		private UneditableJavaPackageDeclarationBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected UneditableJavaPackageDeclaration constructPackageDeclaration(){
			return new UneditableJavaPackageDeclaration(packageName);
		}
	}
	
	/**
	 * Constructs a new {@link JavaPackageDeclaration package declaration} using the given parameters
	 *
	 * @param packageName The name of the package in the declaration
	 */
	private UneditableJavaPackageDeclaration(String packageName){
		super(false, packageName);
	}
	
	/**
	 * @return A new {@link UneditableJavaPackageDeclarationBuilder builder} to use to build a {@link UneditableJavaPackageDeclaration}
	 */
	public static UneditableJavaPackageDeclarationBuilder builder(){
		return new UneditableJavaPackageDeclarationBuilder();
	}
}
