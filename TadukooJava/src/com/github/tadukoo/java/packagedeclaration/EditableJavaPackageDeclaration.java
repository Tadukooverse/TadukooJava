package com.github.tadukoo.java.packagedeclaration;

/**
 * Represents a {@link JavaPackageDeclaration} that can be edited
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class EditableJavaPackageDeclaration extends JavaPackageDeclaration{
	
	/**
	 * A builder to use to build an {@link EditableJavaPackageDeclaration}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @see JavaPackageDeclarationBuilder
	 */
	public static class EditableJavaPackageDeclarationBuilder extends JavaPackageDeclarationBuilder<EditableJavaPackageDeclaration>{
		
		/** Not allowed to instantiate outside {@link EditableJavaPackageDeclaration} */
		private EditableJavaPackageDeclarationBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaPackageDeclaration constructPackageDeclaration(){
			return new EditableJavaPackageDeclaration(packageName);
		}
	}
	
	/**
	 * Constructs a new {@link JavaPackageDeclaration package declaration} using the given parameters
	 *
	 * @param packageName The name of the package in the declaration
	 */
	private EditableJavaPackageDeclaration(String packageName){
		super(true, packageName);
	}
	
	/**
	 * @return A new {@link EditableJavaPackageDeclarationBuilder builder} to use to build a {@link EditableJavaPackageDeclaration}
	 */
	public static EditableJavaPackageDeclarationBuilder builder(){
		return new EditableJavaPackageDeclarationBuilder();
	}
	
	/**
	 * @param packageName The name of the package in the declaration
	 */
	public void setPackageName(String packageName){
		this.packageName = packageName;
	}
}
