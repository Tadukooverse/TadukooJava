package com.github.tadukoo.java;

import com.github.tadukoo.util.StringUtil;

/**
 * Represents a Type Parameter in Java (e.g. {@code String} in {@code List<String>})
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 */
public class JavaTypeParameter{
	
	/**
	 * A Builder used to make a {@link JavaTypeParameter}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Java Type Parameter Parameters</caption>
	 *     <tr>
	 *         <th>Parameter Name</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>baseType</td>
	 *         <td>The base type of the type parameter (e.g. {@code ?} in {@code List<? extends String>} or
	 *         {@code String} in {@code List<String>})</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>extendsType</td>
	 *         <td>The type being extended in the type parameter (e.g. {@code String} in {@code List<? extends String>})</td>
	 *         <td>Defaults to null (for no type being extended)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>canonicalName</td>
	 *         <td>The canonical name (package.name.ClassName) of the type in the type parameter (e.g. {@code String}
	 *         in {@code List<String>} or in {@code List<? extends String>})</td>
	 *         <td>Defaults to null (to not specify it, can be specified in some cases for clarity,
	 *         but is not necessary in others)</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 */
	public static class JavaTypeParameterBuilder{
		/** The base type of the type parameter (e.g. {@code ?} in {@code List<? extends String>} or
		 * {@code String} in {@code List<String>}) */
		private String baseType = null;
		/** The type being extended in the type parameter (e.g. {@code String} in {@code List<? extends String>}) */
		private String extendsType = null;
		/** The canonical name (package.name.ClassName) of the type in the type parameter (e.g. {@code String})
		 * in {@code List<String>} or in {@code List<? extends String>} */
		private String canonicalName = null;
		
		/** Not allowed to instantiate outside of {@link JavaTypeParameter} */
		private JavaTypeParameterBuilder(){ }
		
		/**
		 * @param baseType The base type of the type parameter (e.g. {@code ?} in {@code List<? extends String>} or
		 * {@code String} in {@code List<String>})
		 * @return this, to continue building
		 */
		public JavaTypeParameterBuilder baseType(String baseType){
			this.baseType = baseType;
			return this;
		}
		
		/**
		 * @param extendsType The type being extended in the type parameter (e.g. {@code String} in {@code List<? extends String>})
		 * @return this, to continue building
		 */
		public JavaTypeParameterBuilder extendsType(String extendsType){
			this.extendsType = extendsType;
			return this;
		}
		
		/**
		 * @param canonicalName The canonical name (package.name.ClassName) of the type in the type parameter
		 * (e.g. {@code String} in {@code List<String>} or in {@code List<? extends String>})
		 * @return this, to continue building
		 */
		public JavaTypeParameterBuilder canonicalName(String canonicalName){
			this.canonicalName = canonicalName;
			return this;
		}
		
		/**
		 * @return A newly built {@link JavaTypeParameter} using the given parameters
		 */
		public JavaTypeParameter build(){
			if(StringUtil.isBlank(baseType)){
				throw new IllegalArgumentException("baseType can't be empty!");
			}
			return new JavaTypeParameter(baseType, extendsType, canonicalName);
		}
	}
	
	/** The base type of the type parameter (e.g. {@code ?} in {@code List<? extends String>} or
	 * {@code String} in {@code List<String>}) */
	private final String baseType;
	/** The type being extended in the type parameter (e.g. {@code String} in {@code List<? extends String>}) */
	private final String extendsType;
	/** The canonical name (package.name.ClassName) of the type in the type parameter (e.g. {@code String}
	 * in {@code List<String>} or in {@code List<? extends String>}) */
	private String canonicalName;
	
	/**
	 * Constructs a new {@link JavaTypeParameter} with the given parameters
	 *
	 * @param baseType The base type of the type parameter (e.g. {@code ?} in {@code List<? extends String>} or
	 * @code String} in {@code List<String>})
	 * @param extendsType The type being extended in the type parameter (e.g. {@code String} in {@code List<? extends String>})
	 * @param canonicalName The canonical name (package.name.ClassName) of the type in the type parameter (e.g. {@code String}
	 * in {@code List<String>} or in {@code List<? extends String>})
	 */
	private JavaTypeParameter(String baseType, String extendsType, String canonicalName){
		this.baseType = baseType;
		this.extendsType = extendsType;
		this.canonicalName = canonicalName;
	}
	
	/**
	 * @return A {@link JavaTypeParameterBuilder builder} used to create a {@link JavaTypeParameter}
	 */
	public static JavaTypeParameterBuilder builder(){
		return new JavaTypeParameterBuilder();
	}
	
	/**
	 * @return The base type of the type parameter (e.g. {@code ?} in {@code List<? extends String>} or
	 * {@code String} in {@code List<String>})
	 */
	public String getBaseType(){
		return baseType;
	}
	
	/**
	 * @return The type being extended in the type parameter (e.g. {@code String} in {@code List<? extends String>}),
	 * may be null
	 */
	public String getExtendsType(){
		return extendsType;
	}
	
	/**
	 * @return The canonical name (package.name.ClassName) of the type in the type parameter (e.g. {@code String}
	 * in {@code List<String>} or in {@code List<? extends String>}), may be null
	 */
	public String getCanonicalName(){
		return canonicalName;
	}
	
	/**
	 * @param canonicalName The canonical name (package.name.ClassName) of the type in the type parameter
	 * (e.g. {@code String} in {@code List<String>} or in {@code List<? extends String>})
	 */
	public void setCanonicalName(String canonicalName){
		this.canonicalName = canonicalName;
	}
}
