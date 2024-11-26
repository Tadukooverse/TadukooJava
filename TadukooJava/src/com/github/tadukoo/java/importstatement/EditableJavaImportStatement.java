package com.github.tadukoo.java.importstatement;

/**
 * Represents a {@link JavaImportStatement} that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class EditableJavaImportStatement extends JavaImportStatement{
	
	/**
	 * A builder used to build a new {@link EditableJavaImportStatement}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @see JavaImportStatementBuilder
	 */
	public static class EditableJavaImportStatementBuilder extends JavaImportStatementBuilder<EditableJavaImportStatement>{
		
		/** Not allowed to instantiate outside {@link EditableJavaImportStatement} */
		private EditableJavaImportStatementBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaImportStatement constructImportStatement(){
			return new EditableJavaImportStatement(isStatic, importName);
		}
	}
	
	/**
	 * Constructs a new {@link JavaImportStatement} using the given parameters
	 *
	 * @param isStatic Whether this import is static or not
	 * @param importName The name for the import
	 */
	private EditableJavaImportStatement(boolean isStatic, String importName){
		super(true, isStatic, importName);
	}
	
	/**
	 * @return A new {@link EditableJavaImportStatementBuilder builder} to use to build a new {@link EditableJavaImportStatement}
	 */
	public static EditableJavaImportStatementBuilder builder(){
		return new EditableJavaImportStatementBuilder();
	}
	
	/**
	 * @param isStatic Whether the import statement is static or not
	 */
	public void setStatic(boolean isStatic){
		this.isStatic = isStatic;
	}
	
	/**
	 * @param importName The name of the import in the statement
	 */
	public void setImportName(String importName){
		this.importName = importName;
	}
}
