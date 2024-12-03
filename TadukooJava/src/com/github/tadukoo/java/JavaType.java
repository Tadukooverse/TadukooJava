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
public class JavaType implements JavaTokens{
	
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
	 *         <td>typeParameters</td>
	 *         <td>The type parameters of the Java Type (e.g. {@code String} in {@code List<String>})</td>
	 *         <td>Defaults to an empty List</td>
	 *     </tr>
	 *     <tr>
	 *         <td>canonicalName</td>
	 *         <td>The canonical name (package.name.ClassName) of the base type</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 */
	public static class JavaTypeBuilder{
		/** The base type of the Java Type (e.g. {@code List} in {@code List<String>}) */
		private String baseType = null;
		/** The type parameters of the Java Type (e.g. {@code String} in {@code List<String>}) */
		private List<JavaTypeParameter> typeParameters = new ArrayList<>();
		/** The canonical name (package.name.ClassName) of the base type */
		private String canonicalName = null;
		
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
		 * @param canonicalName The canonical name (package.name.ClassName) of the base type
		 * @return this, to continue building
		 */
		public JavaTypeBuilder canonicalName(String canonicalName){
			this.canonicalName = canonicalName;
			return this;
		}
		
		/**
		 * @return A newly built {@link JavaType} using the given parameters
		 */
		public JavaType build(){
			if(StringUtil.isBlank(baseType)){
				throw new IllegalArgumentException("baseType can't be empty!");
			}
			return new JavaType(baseType, typeParameters, canonicalName);
		}
	}
	
	/** The base type of the Java Type (e.g. {@code List} in {@code List<String>}) */
	private final String baseType;
	/** The type parameters of the Java Type (e.g. {@code String} in {@code List<String>}) */
	private final List<JavaTypeParameter> typeParameters;
	/** The canonical name (package.name.ClassName) of the base type */
	private String canonicalName;
	
	/**
	 * Constructs a new {@link JavaType} using the given parameters
	 *
	 * @param baseType The base type of the Java Type (e.g. {@code List} in {@code List<String>})
	 * @param typeParameters The type parameters of the Java Type (e.g. {@code String} in {@code List<String>})
	 * @param canonicalName The canonical name (package.name.ClassName) of the base type
	 */
	private JavaType(String baseType, List<JavaTypeParameter> typeParameters, String canonicalName){
		this.baseType = baseType;
		this.typeParameters = typeParameters;
		this.canonicalName = canonicalName;
	}
	
	/**
	 * @return A new {@link JavaTypeBuilder builder} to use to build a {@link JavaType}
	 */
	public static JavaTypeBuilder builder(){
		return new JavaTypeBuilder();
	}
	
	/**
	 * @return The base type of the Java Type (e.g. {@code List} in {@code List<String>})
	 */
	public String getBaseType(){
		return baseType;
	}
	
	/**
	 * @return The type parameters of the Java Type (e.g. {@code String} in {@code List<String>}), may be null/empty List
	 */
	public List<JavaTypeParameter> getTypeParameters(){
		return typeParameters;
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
}
