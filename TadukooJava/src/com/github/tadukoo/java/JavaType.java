package com.github.tadukoo.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a type in Java (e.g. {@code String} or {@code List<String>} or
 * {@code Map<? extends String, ? extends Object>})
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 */
public class JavaType implements JavaCodeType{
	
	/**
	 * A builder used to build a {@link JavaType}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Java Type Parameters</caption>
	 *     <tr>
	 *         <th>Parameter Name</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>baseType</td>
	 *         <td>The base type of the Java Type (e.g. {@code List} in {@code List<String>})</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>canonicalName</td>
	 *         <td>The canonical name (package.name.ClassName) of the base type</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>typeParameters</td>
	 *         <td>The type parameters of the Java Type (e.g. {@code String} in {@code List<String>})</td>
	 *         <td>Defaults to an empty List</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 */
	public static class JavaTypeBuilder{
		/** The base type of the Java Type (e.g. {@code List} in {@code List<String>}) */
		private String baseType = null;
		/** The canonical name (package.name.ClassName) of the base type */
		private String canonicalName = null;
		/** The type parameters of the Java Type (e.g. {@code String} in {@code List<String>}) */
		private List<JavaTypeParameter> typeParameters = new ArrayList<>();
		
		/** Not allowed to instantiate outside of {@link JavaType} */
		private JavaTypeBuilder(){ }
		
		/**
		 * @param baseType The base type of the Java Type (e.g. {@code List} in {@code List<String>})
		 * @return this, to continue building
		 */
		public JavaTypeBuilder baseType(String baseType){
			this.baseType = baseType;
			return this;
		}
		
		/**
		 * @param canonicalName The canonical name (package.name.ClassName) of the base type
		 * @return this, to continue building
		 */
		public JavaTypeBuilder canonicalName(String canonicalName){
			this.canonicalName = canonicalName;
			return this;
		}
		
		/**
		 * @param typeParameter A type parameter of the Java Type (e.g. {@code String} in {@code List<String>})
		 * @return this, to continue building
		 */
		public JavaTypeBuilder typeParameter(JavaTypeParameter typeParameter){
			typeParameters.add(typeParameter);
			return this;
		}
		
		/**
		 * @param typeParameters The type parameters of the Java Type (e.g. {@code String} in {@code List<String>})
		 * @return this, to continue building
		 */
		public JavaTypeBuilder typeParameters(List<JavaTypeParameter> typeParameters){
			this.typeParameters = typeParameters;
			return this;
		}
		
		/**
		 * @return A newly built {@link JavaType} using the given parameters
		 */
		public JavaType build(){
			if(StringUtil.isBlank(baseType)){
				throw new IllegalArgumentException("baseType can't be empty!");
			}
			return new JavaType(baseType, canonicalName, typeParameters);
		}
	}
	
	/** The base type of the Java Type (e.g. {@code List} in {@code List<String>}) */
	private final String baseType;
	/** The canonical name (package.name.ClassName) of the base type */
	private String canonicalName;
	/** The type parameters of the Java Type (e.g. {@code String} in {@code List<String>}) */
	private final List<JavaTypeParameter> typeParameters;
	
	/**
	 * Constructs a new {@link JavaType} using the given parameters
	 *
	 * @param baseType The base type of the Java Type (e.g. {@code List} in {@code List<String>})
	 * @param canonicalName The canonical name (package.name.ClassName) of the base type
	 * @param typeParameters The type parameters of the Java Type (e.g. {@code String} in {@code List<String>})
	 */
	private JavaType(String baseType, String canonicalName, List<JavaTypeParameter> typeParameters){
		this.baseType = baseType;
		this.canonicalName = canonicalName;
		this.typeParameters = typeParameters;
	}
	
	/**
	 * @return A new {@link JavaTypeBuilder builder} to use to build a {@link JavaType}
	 */
	public static JavaTypeBuilder builder(){
		return new JavaTypeBuilder();
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.TYPE;
	}
	
	/**
	 * @return The base type of the Java Type (e.g. {@code List} in {@code List<String>})
	 */
	public String getBaseType(){
		return baseType;
	}
	
	/**
	 * @return The canonical name (package.name.ClassName) of the base type, may be null
	 */
	public String getCanonicalName(){
		return canonicalName;
	}
	
	/**
	 * @param canonicalName The canonical name (package.name.ClassName) of the base type
	 */
	public void setCanonicalName(String canonicalName){
		this.canonicalName = canonicalName;
	}
	
	/**
	 * @return The type parameters of the Java Type (e.g. {@code String} in {@code List<String>}), may be null/empty List
	 */
	public List<JavaTypeParameter> getTypeParameters(){
		return typeParameters;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherType){
		if(otherType instanceof JavaType type){
			return StringUtil.equals(this.toString(), type.toString()) &&
					StringUtil.equals(this.getCanonicalName(), type.getCanonicalName());
		}else{
			return false;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		// Start with baseType
		StringBuilder typeText = new StringBuilder(baseType);
		
		// Add type parameters if we have them
		if(ListUtil.isNotBlank(typeParameters)){
			typeText.append(TYPE_PARAMETER_OPEN_TOKEN);
			for(JavaTypeParameter typeParam: typeParameters){
				typeText.append(typeParam).append(LIST_SEPARATOR_TOKEN).append(" ");
			}
			// Remove the last comma and space
			typeText.delete(typeText.length()-2, typeText.length());
			typeText.append(TYPE_PARAMETER_CLOSE_TOKEN);
		}
		
		return typeText.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		// Start the building
		StringBuilder codeString = new StringBuilder(this.getClass().getSimpleName()).append(".builder()");
		
		// Add baseType
		codeString.append(NEWLINE_WITH_2_TABS).append(".baseType(\"").append(baseType).append("\")");
		
		// Add canonical name if we have it
		if(StringUtil.isNotBlank(canonicalName)){
			codeString.append(NEWLINE_WITH_2_TABS).append(".canonicalName(\"").append(canonicalName).append("\")");
		}
		
		// Add type parameters if we have them
		if(ListUtil.isNotBlank(typeParameters)){
			for(JavaTypeParameter typeParameter: typeParameters){
				codeString.append(NEWLINE_WITH_2_TABS).append(".typeParameter(")
						.append(typeParameter.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
						.append(')');
			}
		}
		
		// Finish the building
		codeString.append(NEWLINE_WITH_2_TABS).append(".build()");
		return codeString.toString();
	}
}
