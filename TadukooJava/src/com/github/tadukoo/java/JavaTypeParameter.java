package com.github.tadukoo.java;

import com.github.tadukoo.util.StringUtil;

/**
 * Represents a Type Parameter in Java (e.g. {@code String} in {@code List<String>})
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 */
public class JavaTypeParameter implements JavaCodeType{
	
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
	 *         <td>The base {@link JavaType type} of the type parameter (e.g.
	 *         {@code ?} in {@code List<? extends String>} or
	 *         {@code String} in {@code List<String>} or
	 *         {@code Map<String,Object>} in {@code List<Map<String,Object>})</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>extendsType</td>
	 *         <td>The {@link JavaType type} being extended in the type parameter (e.g.
	 *         {@code String} in {@code List<? extends String>} or
	 *         {@code List<String>} in {@code List<? extends List<String>})</td>
	 *         <td>Defaults to null (for no type being extended)</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 */
	public static class JavaTypeParameterBuilder{
		/** The base {@link JavaType type} of the type parameter (e.g.
		 * {@code ?} in {@code List<? extends String>} or
		 * {@code String} in {@code List<String>} or
		 * {@code Map<String,Object>} in {@code List<Map<String,Object>}) */
		private JavaType baseType = null;
		/** The {@link JavaType type} being extended in the type parameter (e.g.
		 * {@code String} in {@code List<? extends String>} or
		 * {@code List<String>} in {@code List<? extends List<String>}) */
		private JavaType extendsType = null;
		
		/** Not allowed to instantiate outside of {@link JavaTypeParameter} */
		private JavaTypeParameterBuilder(){ }
		
		/**
		 * @param baseType The base {@link JavaType type} of the type parameter (e.g.
		 * {@code ?} in {@code List<? extends String>} or
		 * {@code String} in {@code List<String>} or
		 * {@code Map<String,Object>} in {@code List<Map<String,Object>})
		 * @return this, to continue building
		 */
		public JavaTypeParameterBuilder baseType(JavaType baseType){
			this.baseType = baseType;
			return this;
		}
		
		/**
		 * @param extendsType The {@link JavaType type} being extended in the type parameter (e.g.
		 * {@code String} in {@code List<? extends String>} or
		 * {@code List<String>} in {@code List<? extends List<String>})
		 * @return this, to continue building
		 */
		public JavaTypeParameterBuilder extendsType(JavaType extendsType){
			this.extendsType = extendsType;
			return this;
		}
		
		/**
		 * @return A newly built {@link JavaTypeParameter} using the given parameters
		 */
		public JavaTypeParameter build(){
			if(baseType == null){
				throw new IllegalArgumentException("baseType can't be empty!");
			}
			return new JavaTypeParameter(baseType, extendsType);
		}
	}
	
	/** The base {@link JavaType type} of the type parameter (e.g.
	 * {@code ?} in {@code List<? extends String>} or
	 * {@code String} in {@code List<String>} or
	 * {@code Map<String,Object>} in {@code List<Map<String,Object>}) */
	private final JavaType baseType;
	/** The {@link JavaType type} being extended in the type parameter (e.g.
	 * {@code String} in {@code List<? extends String>} or
	 * {@code List<String>} in {@code List<? extends List<String>}) */
	private final JavaType extendsType;
	
	/**
	 * Constructs a new {@link JavaTypeParameter} with the given parameters
	 *
	 * @param baseType The base {@link JavaType type} of the type parameter (e.g.
	 * {@code ?} in {@code List<? extends String>} or
	 * {@code String} in {@code List<String>} or
	 * {@code Map<String,Object>} in {@code List<Map<String,Object>})
	 * @param extendsType The {@link JavaType type} being extended in the type parameter (e.g.
	 * {@code String} in {@code List<? extends String>} or
	 * {@code List<String>} in {@code List<? extends List<String>})
	 */
	private JavaTypeParameter(JavaType baseType, JavaType extendsType){
		this.baseType = baseType;
		this.extendsType = extendsType;
	}
	
	/**
	 * @return A {@link JavaTypeParameterBuilder builder} used to create a {@link JavaTypeParameter}
	 */
	public static JavaTypeParameterBuilder builder(){
		return new JavaTypeParameterBuilder();
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.TYPE_PARAMETER;
	}
	
	/**
	 * @return The base {@link JavaType type} of the type parameter (e.g.
	 * {@code ?} in {@code List<? extends String>} or
	 * {@code String} in {@code List<String>} or
	 * {@code Map<String,Object>} in {@code List<Map<String,Object>})
	 */
	public JavaType getBaseType(){
		return baseType;
	}
	
	/**
	 * @return The {@link JavaType type} being extended in the type parameter (e.g.
	 * {@code String} in {@code List<? extends String>} or
	 * {@code List<String>} in {@code List<? extends List<String>})
	 */
	public JavaType getExtendsType(){
		return extendsType;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherTypeParam){
		if(otherTypeParam instanceof JavaTypeParameter typeParam){
			return StringUtil.equals(this.toString(), typeParam.toString());
		}else{
			return false;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		// Start with baseType
		StringBuilder text = new StringBuilder(baseType.toString());
		
		// Add extendsType if we have it
		if(extendsType != null){
			text.append(" extends ").append(extendsType);
		}
		
		return text.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		// Start the building
		StringBuilder codeString = new StringBuilder(this.getClass().getSimpleName()).append(".builder()");
		
		// Add baseType
		codeString.append(NEWLINE_WITH_2_TABS).append(".baseType(")
				.append(baseType.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
				.append(')');
		
		// Add extends type if we have it
		if(extendsType != null){
			codeString.append(NEWLINE_WITH_2_TABS).append(".extendsType(")
					.append(extendsType.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
					.append(')');
		}
		
		// Finish the building
		codeString.append(NEWLINE_WITH_2_TABS).append(".build()");
		return codeString.toString();
	}
}
