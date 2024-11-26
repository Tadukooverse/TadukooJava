package com.github.tadukoo.java.importstatement;

/**
 * Represents a {@link JavaImportStatement} that can't be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class UneditableJavaImportStatement extends JavaImportStatement{
	
	/**
	 * A builder used to build an {@link UneditableJavaImportStatement}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @see JavaImportStatementBuilder
	 */
	public static class UneditableJavaImportStatementBuilder extends JavaImportStatementBuilder<UneditableJavaImportStatement>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaImportStatement} */
		private UneditableJavaImportStatementBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected UneditableJavaImportStatement constructImportStatement(){
			return new UneditableJavaImportStatement(isStatic, importName);
		}
	}
	
	/**
	 * Constructs a new {@link JavaImportStatement} using the given parameters
	 *
	 * @param isStatic Whether this import is static or not
	 * @param importName The name for the import
	 */
	private UneditableJavaImportStatement(boolean isStatic, String importName){
		super(false, isStatic, importName);
	}
	
	/**
	 * @return A {@link UneditableJavaImportStatementBuilder builder} to use to build a new {@link UneditableJavaImportStatement}
	 */
	public static UneditableJavaImportStatementBuilder builder(){
		return new UneditableJavaImportStatementBuilder();
	}
}
