package com.github.tadukoo.java;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Java parameter (e.g. {@code Map<String, Object> theMap} or {@code String ... strings})
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 */
public class JavaParameter implements JavaCodeType{
	
	/**
	 * A builder used to build a {@link JavaParameter}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Java Parameter Parameters</caption>
	 *     <tr>
	 *         <th>Parameter Name</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>type</td>
	 *         <td>The {@link JavaType} of the parameter</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>name</td>
	 *         <td>The name of the parameter</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>vararg</td>
	 *         <td>Whether the parameter is a variable argument or not</td>
	 *         <td>Defaults to {@code false}</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 */
	public static class JavaParameterBuilder{
		/** The {@link JavaType} of the parameter */
		private JavaType type = null;
		/** The name of the parameter */
		private String name = null;
		/** Whether the parameter is a variable argument or not */
		private boolean vararg = false;
		
		/** Not allowed to instantiate outside of {@link JavaParameter} */
		private JavaParameterBuilder(){ }
		
		/**
		 * @param type The {@link JavaType} of the parameter
		 * @return this, to continue building
		 */
		public JavaParameterBuilder type(JavaType type){
			this.type = type;
			return this;
		}
		
		/**
		 * @param name The name of the parameter
		 * @return this, to continue building
		 */
		public JavaParameterBuilder name(String name){
			this.name = name;
			return this;
		}
		
		/**
		 * Sets the parameter as a variable argument
		 *
		 * @return this, to continue building
		 */
		public JavaParameterBuilder vararg(){
			vararg = true;
			return this;
		}
		
		/**
		 * @param vararg Whether the parameter is a variable argument or not
		 * @return this, to continue building
		 */
		public JavaParameterBuilder vararg(boolean vararg){
			this.vararg = vararg;
			return this;
		}
		
		/**
		 * @return A newly built {@link JavaParameter} using the given parameters
		 */
		public JavaParameter build(){
			List<String> errors = new ArrayList<>();
			if(type == null){
				errors.add("type can't be null!");
			}
			if(StringUtil.isBlank(name)){
				errors.add("name can't be empty!");
			}
			if(!errors.isEmpty()){
				throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
			}
			return new JavaParameter(type, name, vararg);
		}
	}
	
	/** The {@link JavaType} of the parameter */
	private final JavaType type;
	/** The name of the parameter */
	private final String name;
	/** Whether the parameter is a variable argument or not */
	private final boolean vararg;
	
	/**
	 * Constructs a new {@link JavaParameter} using the following parameters
	 *
	 * @param type The {@link JavaType} of the parameter
	 * @param name The name of the parameter
	 * @param vararg Whether the parameter is a variable argument or not
	 */
	private JavaParameter(JavaType type, String name, boolean vararg){
		this.type = type;
		this.name = name;
		this.vararg = vararg;
	}
	
	/**
	 * @return A {@link JavaParameterBuilder builder} to use to build a {@link JavaParameter}
	 */
	public static JavaParameterBuilder builder(){
		return new JavaParameterBuilder();
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.PARAMETER;
	}
	
	/**
	 * @return The {@link JavaType} of the parameter
	 */
	public JavaType getType(){
		return type;
	}
	
	/**
	 * @return The name of the parameter
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return Whether the parameter is a variable argument or not
	 */
	public boolean isVararg(){
		return vararg;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherParameter){
		if(otherParameter instanceof JavaParameter parameter){
			return StringUtil.equals(this.toString(), parameter.toString());
		}else{
			return false;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		// Start with the type
		StringBuilder parameterText = new StringBuilder(type.toString());
		
		// Add varargs if we have it
		if(vararg){
			parameterText.append(" ").append(VARARGS_TOKEN);
		}
		
		// Finish with the name
		parameterText.append(" ").append(name);
		
		return parameterText.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		// Start the building
		StringBuilder codeString = new StringBuilder(this.getClass().getSimpleName()).append(".builder()");
		
		// Add type
		codeString.append(NEWLINE_WITH_2_TABS).append(".type(")
				.append(type.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
				.append(')');
		
		// Add vararg if we have it
		if(vararg){
			codeString.append(NEWLINE_WITH_2_TABS).append(".vararg()");
		}
		
		// Add name
		codeString.append(NEWLINE_WITH_2_TABS).append(".name(\"").append(name).append("\")");
		
		// Finish the building
		codeString.append(NEWLINE_WITH_2_TABS).append(".build()");
		return codeString.toString();
	}
}
