package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.JavaClassBuilder;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.JavaTokens;
import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction2;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java Parser is used to parse Java code
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaParser implements JavaTokens{
	
	/**
	 * A {@link Pattern} used to detect an {@link JavaAnnotation annotation}
	 * <table>
	 *     <caption>Groups</caption>
	 *     <tr>
	 *         <th>#</th>
	 *         <th>Description</th>
	 *         <th>Example</th>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>Annotation Name</td>
	 *         <td>"{@code Test}" from "{@code @Test(type = String.class)}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>Annotation Parameters (parentheses excluded)</td>
	 *         <td>"{@code type = String.class}" from "{@code @Test(type = String.class)}"</td>
	 *     </tr>
	 * </table>
	 */
	private static final Pattern annotationPattern = Pattern.compile(
			"\\s*@\\s*([^\\s(]*)(?:\\s*\\((.*)\\)\\s*)?\\s*", Pattern.DOTALL);
	/**
	 * A {@link Pattern} used to detect the parameters of an {@link JavaAnnotation annotation}.
	 * This is used for the parameters inside the annotation (in a loop using {@link Matcher#find()})
	 * once the parentheses are removed
	 * <table>
	 *     <caption>Groups</caption>
	 *     <tr>
	 *         <th>#</th>
	 *         <th>Description</th>
	 *         <th>Example</th>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>Parameter Name</td>
	 *         <td>"{@code type}" and "{@code defaultValue}" from "{@code type = String.class, defaultValue = ""}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>Parameter Value</td>
	 *         <td>"{@code String.class}" and "{@code ""}" from "{@code type = String.class, defaultValue = ""}"</td>
	 *     </tr>
	 * </table>
	 */
	private static final Pattern annotationParameterPattern = Pattern.compile("\\s*([^=,]*)\\s*=\\s*([^,]*),?\\s*");
	
	/**
	 * A regular expression used for {@link Visibility}
	 */
	private static final String VISIBILITY_REGEX = "(?:(" + PUBLIC_MODIFIER + "|" + PROTECTED_MODIFIER + "|" +
			PRIVATE_MODIFIER + ")\\s*)?";
	/**
	 * A regular expression used for the static modifier
	 */
	private static final String STATIC_REGEX = "(" + STATIC_MODIFIER + "\\s*)?";
	/**
	 * A regular expression used for the final modifier
	 */
	private static final String FINAL_REGEX = "(" + FINAL_MODIFIER + "\\s*)?";
	/**
	 * A regular expression used for all the modifiers
	 */
	private static final String MODIFIERS_REGEX = VISIBILITY_REGEX + STATIC_REGEX + FINAL_REGEX;
	
	/**
	 * A {@link Pattern} used for parsing a {@link JavaField field}.
	 * This is used for the field preceding the equals sign in a field
	 * <table>
	 *     <caption>Groups</caption>
	 *     <tr>
	 *         <th>#</th>
	 *         <th>Group</th>
	 *         <th>Example</th>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>Field {@link Visibility}</td>
	 *         <td>"{@code public}" from "{@code public String test}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>Field Static Modifier (or empty)</td>
	 *         <td>"{@code static}" from "{@code public static String test}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>3</td>
	 *         <td>Field Final Modifier (or empty)</td>
	 *         <td>"{@code final}" from "{@code public final String test}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>4</td>
	 *         <td>Field Type</td>
	 *         <td>"{@code String}" from "{@code public String test}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>5</td>
	 *         <td>Field Name</td>
	 *         <td>"{@code test}" from "{@code public String test}"</td>
	 *     </tr>
	 * </table>
	 */
	private static final Pattern fieldStartPattern = Pattern.compile(
			"\\s*" + MODIFIERS_REGEX + "(\\S*)\\s*(\\S*?)\\s*;?\\s*");
	
	/**
	 * A {@link Pattern} used for parsing a {@link JavaMethod method}.
	 * <table>
	 *     <caption>Groups</caption>
	 *     <tr>
	 *         <th>#</th>
	 *         <th>Group</th>
	 *         <th>Example</th>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>Method {@link Visibility}</td>
	 *         <td>"{@code public}" from "{@code public String name(){}}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>Method Static Modifier (or empty)</td>
	 *         <td>"{@code static}" from "{@code public static String name(){}}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>3</td>
	 *         <td>Method Final Modifier (or empty)</td>
	 *         <td>"{@code final}" from "{@code public final String name(){}}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>4</td>
	 *         <td>Method Return Type</td>
	 *         <td>"{@code String}" from "{@code public String name(){}}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>5</td>
	 *         <td>Method Name (or blank if a constructor)</td>
	 *         <td>"{@code name}" from "{@code public String name(){}}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>6</td>
	 *         <td>Method Parameters (or empty)</td>
	 *         <td>"{@code String blah, int derp}" from "{@code public String name(String blah, int derp){}}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>7</td>
	 *         <td>Method Throw Types (or empty)</td>
	 *         <td>"{@code Exception, Throwable}" from "{@code public String name() throws Exception, Throwable{}}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>8</td>
	 *         <td>Method Content (or empty)</td>
	 *         <td>"{@code doSomething();}" from "{@code public String name(){ doSomething(); }}"</td>
	 *     </tr>
	 * </table>
	 */
	private static final Pattern methodPattern = Pattern.compile(
			"\\s*" + MODIFIERS_REGEX +
					"([^\\s(]*)(\\s*[^\\s(]*)?\\s*" +
					"\\(\\s*(.*)\\s*\\)(?:\\s*throws (.*))?" +
					"\\s*\\{\\s*(.*)\\s*}",
			Pattern.DOTALL);
	
	/**
	 * Used as a pojo for a return type of the various parsing sub-methods
	 *
	 * @param nextTokenIndex The index of the next token to be parsed
	 * @param parsedType A {@link JavaCodeType} that was parsed by the method
	 */
	private record ParsingPojo(int nextTokenIndex, JavaCodeType parsedType){ }
	
	/**
	 * Parses the given text as Java code and returns it as the proper {@link JavaCodeType}
	 *
	 * @param content The text to be parsed as Java code
	 * @return The parsed {@link JavaCodeType} from the given text
	 * @throws JavaParsingException If anything goes wrong while parsing
	 */
	public JavaCodeType parseType(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = StringUtil.parseListFromStringWithPattern(content, "\\S+|\n", false).stream()
				.filter(StringUtil::isNotBlank)
				.toList();
		
		// The Java types we've collected in order while parsing
		List<JavaCodeType> types = new ArrayList<>();
		
		// Iterate over the tokens
		int currentToken = 0;
		while(currentToken < tokens.size()){
			// Grab the current token
			String token = tokens.get(currentToken);
			
			ThrowingFunction2<List<String>, Integer, ParsingPojo, JavaParsingException> parseMethod;
			
			if(StringUtil.equals(token, "\n")){
				// Skip newline tokens
				currentToken++;
				continue;
			}else if(StringUtil.equals(token, PACKAGE_TOKEN)){
				// Parse a package declaration
				parseMethod = this::parsePackageDeclaration;
			}else if(StringUtil.equals(token, IMPORT_TOKEN)){
				// Parse an import statement
				parseMethod = this::parseImportStatement;
			}else if(StringUtil.equals(token, CLASS_TOKEN)){
				// Parse a class
				parseMethod = this::parseClass;
			}else if(token.startsWith(JAVADOC_START_TOKEN)){
				// Parse a javadoc
				parseMethod = this::parseJavadoc;
			}else if(token.startsWith(MULTI_LINE_COMMENT_START_TOKEN)){
				// Parse a multi-line comment
				parseMethod = this::parseMultiLineComment;
			}else if(token.startsWith(SINGLE_LINE_COMMENT_TOKEN)){
				// Parse a single-line comment
				parseMethod = this::parseSingleLineComment;
			}else if(token.startsWith(ANNOTATION_START_TOKEN)){
				// Parse an annotation
				parseMethod = this::parseAnnotation;
			}else if(MODIFIERS.contains(token)){
				// Parse a type with modifiers (could be field, method, class, etc.)
				parseMethod = this::parseTypeWithModifiers;
			}else{
				JavaCodeTypes typeToParse = determineFieldOrMethod(tokens, currentToken);
				if(typeToParse == JavaCodeTypes.FIELD){
					parseMethod = this::parseField;
				}else if(typeToParse == JavaCodeTypes.METHOD){
					parseMethod = this::parseMethod;
				}else{
					throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Failed to determine type from token '" +
							token + "'");
				}
			}
			
			// Use the parse method and handle its results
			ParsingPojo pojo = parseMethod.apply(tokens, currentToken);
			types.add(pojo.parsedType());
			currentToken = pojo.nextTokenIndex();
		}
		
		// Build the final type to be returned based on our collected types
		if(types.size() == 1){
			return types.get(0);
		}else{
			JavaPackageDeclaration packageDeclaration = null;
			List<JavaImportStatement> importStatements = new ArrayList<>();
			List<JavaAnnotation> annotations = new ArrayList<>();
			JavaField javaField = null;
			JavaMethod javaMethod = null;
			JavaClass javaClass = null;
			for(JavaCodeType type: types){
				if(type instanceof JavaPackageDeclaration declaration){
					// Can't have multiple package declarations
					if(packageDeclaration != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered two package declarations!");
					}
					
					// Can't have package declaration after the class
					if(javaClass != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered package declaration after class!");
					}
					
					// Set the package declaration to be potentially added to something else
					packageDeclaration = declaration;
				}else if(type instanceof JavaImportStatement importStmt){
					// Can't have import statements after the class
					if(javaClass != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered import statement after class!");
					}
					
					// Add the import statement to the list to be potentially added to something else
					importStatements.add(importStmt);
				}else if(type instanceof JavaAnnotation annotation){
					// Can't have annotations after the class
					if(javaClass != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered annotation after class!");
					}
					
					// Add the annotation to the list to be potentially added to something else
					annotations.add(annotation);
				}else if(type instanceof EditableJavaField field){
					// Can't have multiple fields - having a field at top level is for field to be the main type
					if(javaField != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered two fields!");
					}
					
					// Can't have class randomly before field
					if(javaClass != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered field outside a class!");
					}
					
					// Can't have package declarations
					if(packageDeclaration != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered package declaration before field!");
					}
					
					// Can't have import statements
					if(!importStatements.isEmpty()){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered import statements before field!");
					}
					
					// Add annotations to the field
					if(!annotations.isEmpty()){
						field.setAnnotations(annotations);
					}
					
					// Set the field to be returned
					javaField = field;
				}else if(type instanceof EditableJavaMethod method){
					// Can't have multiple methods - having a method at top level is for method to be the main type
					if(javaMethod != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered two methods!");
					}
					
					// Can't have class randomly before method
					if(javaClass != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered method outside a class!");
					}
					
					// Can't have package declarations
					if(packageDeclaration != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered package declaration before method!");
					}
					
					// Can't have import statements
					if(!importStatements.isEmpty()){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered import statements before method!");
					}
					
					// Add annotations to the method
					if(!annotations.isEmpty()){
						method.setAnnotations(annotations);
					}
					
					// Set the method to be returned
					javaMethod = method;
				}else if(type instanceof EditableJavaClass clazz){
					// Can't have multiple outer level classes
					if(javaClass != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered two outer level classes!");
					}
					
					// Can't have fields before the class outside of it
					if(javaField != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered fields outside a class!");
					}
					
					// Can't have methods before the class outside of it
					if(javaMethod != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered methods outside a class!");
					}
					
					// Add package declaration if we have it
					if(packageDeclaration != null){
						clazz.setPackageDeclaration(packageDeclaration);
					}
					
					// Add any import statements if we have them
					if(!importStatements.isEmpty()){
						clazz.setImportStatements(importStatements);
					}
					
					// Add any annotations if we have them
					if(!annotations.isEmpty()){
						clazz.setAnnotations(annotations);
					}
					
					// Set the class to be returned
					javaClass = clazz;
				}else{
					throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Unknown how to handle putting '" +
							type.getJavaCodeType() + "' together with other types!");
				}
			}
			
			// Return field if we got it
			if(javaField != null){
				return javaField;
			}
			
			// Return method if we got it
			if(javaMethod != null){
				return javaMethod;
			}
			
			// Return class if we got it
			if(javaClass != null){
				return javaClass;
			}
			
			throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Unable to collect unknown types together");
		}
	}
	
	/**
	 * Used to determine if we have a field or method based on the token we're looking at in parsing.
	 * This is used in several places to determine which we're looking at, hence the need for a method to
	 * reduce repeated code :P
	 *
	 * @param tokens The tokens we're parsing
	 * @param currentToken The current token being looked at
	 * @return Either {@link JavaCodeTypes#FIELD}, {@link JavaCodeTypes#METHOD}, or {@link JavaCodeTypes#UNKNOWN}
	 */
	private JavaCodeTypes determineFieldOrMethod(List<String> tokens, int currentToken){
		int thisToken = currentToken;
		// Skip any newlines
		while(thisToken < tokens.size() && StringUtil.equals(tokens.get(thisToken), "\n")){
			thisToken++;
		}
		// Check we didn't reach the end of tokens
		if(thisToken >= tokens.size()){
			return JavaCodeTypes.UNKNOWN;
		}
		String token = tokens.get(thisToken);
		// First token is a type (possibly with start of parameters if a method)
		if(token.contains(PARAMETER_OPEN_TOKEN)){
			// We got a method
			return JavaCodeTypes.METHOD;
		}else if(token.contains(ASSIGNMENT_OPERATOR_TOKEN)){
			// We got a field
			return JavaCodeTypes.FIELD;
		}else{
			// Move to next token to check
			thisToken++;
			// Skip any newlines
			while(thisToken < tokens.size() && StringUtil.equals(tokens.get(thisToken), "\n")){
				thisToken++;
			}
			// Check we didn't reach the end of tokens
			if(thisToken >= tokens.size()){
				return JavaCodeTypes.UNKNOWN;
			}
			String nextToken = tokens.get(thisToken);
			if(nextToken.contains(PARAMETER_OPEN_TOKEN)){
				// We got a method
				return JavaCodeTypes.METHOD;
			}else if(nextToken.contains(SEMICOLON)){
				// We got a field
				return JavaCodeTypes.FIELD;
			}else{
				// Move to next token to check
				thisToken++;
				// Skip any newlines
				while(thisToken < tokens.size() && StringUtil.equals(tokens.get(thisToken), "\n")){
					thisToken++;
				}
				// Check we didn't reach the end of tokens
				if(thisToken >= tokens.size()){
					return JavaCodeTypes.UNKNOWN;
				}
				String nextNextToken = tokens.get(thisToken);
				if(nextNextToken.startsWith(PARAMETER_OPEN_TOKEN)){
					// We got a method
					return JavaCodeTypes.METHOD;
				}else{
					// We got a field
					return JavaCodeTypes.FIELD;
				}
			}
		}
	}
	
	/**
	 * Parses a {@link JavaPackageDeclaration package declaration} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaPackageDeclaration package declaration}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parsePackageDeclaration(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Ensure first token is "package"
		if(StringUtil.notEquals(tokens.get(startToken), PACKAGE_TOKEN)){
			errors.add("First token of package declaration must be '" + PACKAGE_TOKEN + "'");
		}
		
		// We will start iterating at startToken+1 (since startToken is the "package" token)
		// We have it here because we'll be returning this
		int currentToken = startToken+1;
		
		// Get the actual package name
		boolean gotSemicolon = false;
		StringBuilder packageName = new StringBuilder();
		for(; currentToken < tokens.size() && !gotSemicolon; currentToken++){
			String token = tokens.get(currentToken);
			
			// If we got a single semicolon, we're done
			if(StringUtil.equals(token, SEMICOLON)){
				gotSemicolon = true;
			}else if(token.endsWith(SEMICOLON)){
				packageName.append(token, 0, token.length()-1);
				gotSemicolon = true;
			}else if(StringUtil.notEquals(token, "\n")){
				// If we got a newline, skip it
				packageName.append(token);
			}
		}
		
		// If we got no package name, there's a problem
		if(packageName.isEmpty()){
			errors.add("Failed to find package name in package declaration!");
		}
		
		// If we got no semicolon, we got a problem
		if(!gotSemicolon){
			errors.add("Failed to find semicolon ending package declaration!");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.PACKAGE_DECLARATION, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build the package declaration and return
		return new ParsingPojo(currentToken, EditableJavaPackageDeclaration.builder()
				.packageName(packageName.toString())
				.build());
	}
	
	/**
	 * Parses an {@link JavaImportStatement import statement} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaImportStatement import statement}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parseImportStatement(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Ensure the first token is "import"
		if(StringUtil.notEquals(tokens.get(startToken), IMPORT_TOKEN)){
			errors.add("First token of import statement must be '" + IMPORT_TOKEN + "'");
		}
		
		// Skip any newline tokens here
		int currentToken = startToken + 1;
		while(currentToken < tokens.size() && StringUtil.equals(tokens.get(currentToken), "\n")){
			currentToken++;
		}
		
		// Check if the next token is "static" (for a static import statement)
		boolean isStatic = false;
		if(currentToken < tokens.size()){
			isStatic = StringUtil.equalsIgnoreCase(tokens.get(currentToken), STATIC_MODIFIER);
			if(isStatic){
				currentToken++;
			}
		}
		
		// Build the import name from the remaining tokens
		boolean gotSemicolon = false;
		StringBuilder importName = new StringBuilder();
		for(; currentToken < tokens.size() && !gotSemicolon; currentToken++){
			String token = tokens.get(currentToken);
			
			// If we got a single semicolon, we're done
			if(StringUtil.equalsIgnoreCase(token, SEMICOLON)){
				gotSemicolon = true;
			}else if(token.endsWith(SEMICOLON)){
				importName.append(token, 0, token.length()-1);
				gotSemicolon = true;
			}else if(StringUtil.notEquals(token, "\n")){
				// Skip newlines
				importName.append(token);
			}
		}
		
		// If we got no import name, there's a problem
		if(importName.isEmpty()){
			errors.add("Failed to find import name in import statement!");
		}
		
		// If we got no semicolon, we got a problem
		if(!gotSemicolon){
			errors.add("Failed to find semicolon ending import statement!");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.IMPORT_STATEMENT, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build the import statement and return
		return new ParsingPojo(currentToken, EditableJavaImportStatement.builder()
				.isStatic(isStatic)
				.importName(importName.toString())
				.build());
	}
	
	/**
	 * Parses a {@link Javadoc} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link Javadoc}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parseJavadoc(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Ensure the first token starts with /**
		if(!tokens.get(startToken).startsWith(JAVADOC_START_TOKEN)){
			errors.add("First token of Javadoc must start with '" + JAVADOC_START_TOKEN + "'");
		}
		
		// TODO: Handle actual parsing
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.JAVADOC, StringUtil.buildStringWithNewLines(errors));
		}
		
		// TODO: Return a proper pojo
		return new ParsingPojo(startToken, null);
	}
	
	private ParsingPojo parseMultiLineComment(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// First token must start with /*
		if(!tokens.get(startToken).startsWith(MULTI_LINE_COMMENT_START_TOKEN)){
			errors.add("First token of multi-line comment must start with '" + MULTI_LINE_COMMENT_START_TOKEN + "'");
		}
		
		// TODO: Handle actual parsing
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.MULTI_LINE_COMMENT, StringUtil.buildStringWithNewLines(errors));
		}
		
		// TODO: Return a proper pojo
		return new ParsingPojo(startToken, null);
	}
	
	/**
	 * Parses a {@link JavaSingleLineComment single-line comment} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaSingleLineComment comment}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parseSingleLineComment(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// First token must start with //
		String firstToken = tokens.get(startToken);
		if(!firstToken.startsWith(SINGLE_LINE_COMMENT_TOKEN)){
			errors.add("First token of single-line comment must start with '" + SINGLE_LINE_COMMENT_TOKEN + "'");
		}
		
		// Add the first token to the content if it has anything other than the start token
		StringBuilder content = new StringBuilder();
		if(StringUtil.notEquals(firstToken, SINGLE_LINE_COMMENT_TOKEN)){
			content.append(firstToken, SINGLE_LINE_COMMENT_TOKEN.length(), firstToken.length());
		}
		
		// Continue adding to content
		int currentToken = startToken + 1;
		while(currentToken < tokens.size()){
			String token = tokens.get(currentToken);
			currentToken++;
			// We're done when we hit a newline
			if(StringUtil.equals(token, "\n")){
				break;
			}
			if(!content.isEmpty()){
				content.append(' ');
			}
			content.append(token);
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.SINGLE_LINE_COMMENT, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build and return the single-line comment
		return new ParsingPojo(currentToken, EditableJavaSingleLineComment.builder()
				.content(content.toString())
				.build());
	}
	
	/**
	 * Parses an {@link JavaAnnotation annotation} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaAnnotation annotation}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parseAnnotation(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Ensure the first token starts with an @
		if(!tokens.get(startToken).startsWith(ANNOTATION_START_TOKEN)){
			errors.add("First token of annotation must start with '" + ANNOTATION_START_TOKEN + "'");
		}
		
		// Start building the full annotation String - we're using regexes here
		String firstToken = tokens.get(startToken);
		StringBuilder fullAnnotation = new StringBuilder(firstToken);
		
		// Add the next token if we were missing the annotation name
		int currentToken = startToken+1;
		if(StringUtil.equals(firstToken, ANNOTATION_START_TOKEN)){
			String nextToken = tokens.get(currentToken);
			fullAnnotation.append(nextToken);
			currentToken++;
		}
		
		// Now check if we started/ended parameters
		boolean parametersOpen = false, parametersDone = false;
		if(fullAnnotation.toString().contains(PARAMETER_OPEN_TOKEN)){
			parametersOpen = true;
			if(fullAnnotation.toString().contains(PARAMETER_CLOSE_TOKEN)){
				parametersDone = true;
			}
		}
		
		// If we did not start parameters, the only way to continue is if the next token starts parameters
		if(!parametersOpen && currentToken < tokens.size()){
			String nextToken = tokens.get(currentToken);
			if(nextToken.startsWith(PARAMETER_OPEN_TOKEN)){
				currentToken++;
				parametersOpen = true;
				fullAnnotation.append(nextToken);
				if(nextToken.endsWith(PARAMETER_CLOSE_TOKEN)){
					parametersDone = true;
				}
			}
		}
		
		// Keep going until we complete the parameters
		while(parametersOpen && !parametersDone && currentToken < tokens.size()){
			String token = tokens.get(currentToken);
			fullAnnotation.append(token);
			if(token.endsWith(PARAMETER_CLOSE_TOKEN)){
				parametersDone = true;
			}
			currentToken++;
		}
		
		// If we opened and never finished parameters, that's an issue
		if(parametersOpen && !parametersDone){
			errors.add("Didn't find end of parameters");
		}
		
		// Parse the annotation using the regex method
		JavaAnnotation annotation = parseAnnotation(fullAnnotation.toString());
		
		// If annotation is null, we got problems
		if(annotation == null){
			errors.add("Failed to parse annotation");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.ANNOTATION, StringUtil.buildStringWithNewLines(errors));
		}
		
		return new ParsingPojo(currentToken, annotation);
	}
	
	/**
	 * Parses the given text into an {@link JavaAnnotation annotation} if possible, or returns null
	 *
	 * @param content The text to be parsed into a {@link JavaAnnotation annotation}
	 * @return The {@link JavaAnnotation annotation} parsed from the text, or {@code null} if it can't be parsed
	 */
	public JavaAnnotation parseAnnotation(String content){
		Matcher annotationMatcher = annotationPattern.matcher(content);
		if(annotationMatcher.matches()){
			JavaAnnotationBuilder<EditableJavaAnnotation> builder = EditableJavaAnnotation.builder();
			
			// Grab the name and add it to the builder
			String annotationName = StringUtil.trim(annotationMatcher.group(1));
			builder.name(annotationName);
			
			// Grab and parse the parameters
			String annotationParameters = StringUtil.trim(annotationMatcher.group(2));
			if(StringUtil.isNotBlank(annotationParameters)){
				Matcher parameterMatcher = annotationParameterPattern.matcher(annotationParameters);
				boolean firstFind = parameterMatcher.find();
				if(!firstFind){
					builder.parameter("value", annotationParameters);
				}else{
					do{
						String parameterName = StringUtil.trim(parameterMatcher.group(1));
						String parameterValue = StringUtil.trim(parameterMatcher.group(2));
						builder.parameter(parameterName, parameterValue);
					}while(parameterMatcher.find());
				}
			}
			
			return builder.build();
		}else{
			return null;
		}
	}
	
	/**
	 * Parses a {@link JavaCodeTypes#TYPE_WITH_MODIFIERS type with modifiers} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaCodeTypes#TYPE_WITH_MODIFIERS type with modifiers}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parseTypeWithModifiers(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Ensure the first token is a modifier
		if(!MODIFIERS.contains(tokens.get(startToken))){
			errors.add("First token of type with modifiers must be a modifier");
		}
		
		// We may discover a more specific type later on
		JavaCodeTypes type = JavaCodeTypes.TYPE_WITH_MODIFIERS;
		JavaCodeType resultType = null;
		
		// Keep track of modifiers we find
		List<String> modifiers = new ArrayList<>();
		int currentToken;
		for(currentToken = startToken; currentToken < tokens.size(); currentToken++){
			String token = tokens.get(currentToken);
			
			// If we got a modifier, just add it to the list and continue
			if(MODIFIERS.contains(token)){
				modifiers.add(token);
			}else if(StringUtil.equals(token, CLASS_TOKEN)){
				// Parse it as a class
				ParsingPojo result = parseClass(tokens, currentToken);
				type = JavaCodeTypes.CLASS;
				resultType = result.parsedType();
				EditableJavaClass clazz = (EditableJavaClass) resultType;
				currentToken = result.nextTokenIndex();
				
				// Handle the modifiers to be attached to the class
				for(String modifier: modifiers){
					switch(modifier){
						case PRIVATE_MODIFIER -> clazz.setVisibility(Visibility.PRIVATE);
						case PROTECTED_MODIFIER -> clazz.setVisibility(Visibility.PROTECTED);
						case PUBLIC_MODIFIER -> clazz.setVisibility(Visibility.PUBLIC);
						case STATIC_MODIFIER -> clazz.setStatic(true);
						case FINAL_MODIFIER -> clazz.setFinal(true);
					}
				}
				
				break;
			}else if(StringUtil.notEquals(token, "\n")){
				// Skip newlines
				type = determineFieldOrMethod(tokens, currentToken);
				if(type == JavaCodeTypes.METHOD){
					// Parse the method and handle modifiers on it
					ParsingPojo result = parseMethod(tokens, currentToken);
					EditableJavaMethod method = (EditableJavaMethod) result.parsedType();
					for(String modifier: modifiers){
						switch(modifier){
							case PRIVATE_MODIFIER -> method.setVisibility(Visibility.PRIVATE);
							case PROTECTED_MODIFIER -> method.setVisibility(Visibility.PROTECTED);
							case PUBLIC_MODIFIER -> method.setVisibility(Visibility.PUBLIC);
							case STATIC_MODIFIER -> method.setStatic(true);
							case FINAL_MODIFIER -> method.setFinal(true);
						}
					}
					resultType = method;
					currentToken = result.nextTokenIndex();
					break;
				}else if(type == JavaCodeTypes.FIELD){
					// Parse the field and handle modifiers on it
					ParsingPojo result = parseField(tokens, currentToken);
					EditableJavaField field = (EditableJavaField) result.parsedType();
					for(String modifier: modifiers){
						switch(modifier){
							case PRIVATE_MODIFIER -> field.setVisibility(Visibility.PRIVATE);
							case PROTECTED_MODIFIER -> field.setVisibility(Visibility.PROTECTED);
							case PUBLIC_MODIFIER -> field.setVisibility(Visibility.PUBLIC);
							case STATIC_MODIFIER -> field.setStatic(true);
							case FINAL_MODIFIER -> field.setFinal(true);
						}
					}
					resultType = field;
					currentToken = result.nextTokenIndex();
					break;
				}else{
					errors.add("Failed to determine type");
				}
			}
		}
		
		// Check we had no duplicate modifiers (and not more than 1 visibility modifier)
		Set<String> foundModifiers = new HashSet<>();
		int visibilityModifiers = 0;
		for(String modifier: modifiers){
			if(!foundModifiers.add(modifier)){
				errors.add("Found duplicate modifier: '" + modifier + "'");
			}
			if(VISIBILITY_MODIFIERS.contains(modifier)){
				visibilityModifiers++;
			}
		}
		
		// If we had more than 1 visibility modifier, it's a problem
		if(visibilityModifiers > 1){
			errors.add("Found multiple visibility modifiers");
		}
		
		// If we have no resultType, there's a problem
		if(resultType == null){
			errors.add("Failed to determine result type");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(type, StringUtil.buildStringWithNewLines(errors));
		}
		
		return new ParsingPojo(currentToken, resultType);
	}
	
	/**
	 * Parses a {@link JavaField field} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaField field}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parseField(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of any errors
		List<String> errors = new ArrayList<>();
		
		StringBuilder field = new StringBuilder();
		// Iterate over tokens until we find the semicolon
		int currentToken = startToken;
		boolean foundSemicolon = false;
		for(; currentToken < tokens.size() && !foundSemicolon; currentToken++){
			String token = tokens.get(currentToken);
			if(!field.isEmpty()){
				field.append(' ');
			}
			field.append(token);
			
			// Check if we got the semicolon
			if(token.endsWith(SEMICOLON)){
				foundSemicolon = true;
			}
		}
		
		// If we don't have a semicolon, it's a problem
		if(!foundSemicolon){
			errors.add("Failed to find semicolon at end of field");
		}
		
		// If we don't get a field, it's a problem
		JavaField javaField = parseField(field.toString());
		if(javaField == null){
			errors.add("Failed to parse a field");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.FIELD, StringUtil.buildStringWithNewLines(errors));
		}
		
		return new ParsingPojo(currentToken, javaField);
	}
	
	/**
	 * Parses a Java Field (not counting any Javadoc before it, just the field itself)
	 *
	 * @param content The text to be parsed into a {@link JavaField field)}
	 * @return The parsed {@link JavaField field}, or null if we don't have a field
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public JavaField parseField(String content) throws JavaParsingException{
		// Check if field ends with semicolon for failure
		if(!StringUtil.trim(content).endsWith(SEMICOLON)){
			throw new JavaParsingException(JavaCodeTypes.FIELD, "Failed to find semicolon at end of field");
		}
		
		// Find the equals signs in the field
		int equalsIndex = content.indexOf(ASSIGNMENT_OPERATOR_TOKEN);
		
		String value = null;
		String firstPart = content;
		
		// Anything after equals is the value (if we even have equals)
		if(equalsIndex != -1){
			value = StringUtil.trim(content.substring(equalsIndex + 1));
			
			// remove semicolon from value if it exists
			if(value.endsWith(SEMICOLON)){
				value = StringUtil.trim(value.substring(0, value.length()-1));
			}
			
			// Before equals is the modifiers, type, and name
			firstPart = content.substring(0, equalsIndex);
		}
		
		// Handle matching for the first part of the string (modifiers, type, name)
		Matcher matcher = fieldStartPattern.matcher(firstPart);
		if(matcher.matches()){
			Visibility visibility = Visibility.fromToken(StringUtil.trim(matcher.group(1)));
			boolean isStatic = StringUtil.isNotBlank(matcher.group(2));
			boolean isFinal = StringUtil.isNotBlank(matcher.group(3));
			String type = StringUtil.trim(matcher.group(4));
			String name = StringUtil.trim(matcher.group(5));
			
			return EditableJavaField.builder()
					.visibility(visibility)
					.isStatic(isStatic).isFinal(isFinal)
					.type(type).name(name)
					.value(value)
					.build();
		}
		
		return null;
	}
	
	/**
	 * Parses a {@link JavaMethod method} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaMethod method}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parseMethod(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Build the method string
		StringBuilder methodString = new StringBuilder();
		boolean inParameters = false;
		boolean parametersDone = false;
		boolean insideMethod = false;
		int openBlocks = 0;
		boolean methodDone = false;
		int currentToken;
		for(currentToken = startToken; currentToken < tokens.size() && !methodDone; currentToken++){
			String token = tokens.get(currentToken);
			if(!methodString.isEmpty()){
				methodString.append(' ');
			}
			methodString.append(token);
			
			// Check for parameter start token if we're not yet in parameters
			if(!parametersDone && !inParameters && token.contains(PARAMETER_OPEN_TOKEN)){
				inParameters = true;
			}
			
			// If we're in parameters and not yet done, check for parameter end token
			if(!parametersDone && inParameters && token.contains(PARAMETER_CLOSE_TOKEN)){
				inParameters = false;
				parametersDone = true;
			}
			
			// If parameters are done, check for block open
			if(parametersDone && token.contains(BLOCK_OPEN_TOKEN)){
				insideMethod = true;
			}
			
			// If inside the method, update the number of open blocks
			if(insideMethod){
				// Count open and close block tokens
				int openTokens = 0, closeTokens = 0;
				if(token.contains(BLOCK_OPEN_TOKEN)){
					openTokens = 1;
					String[] openSplit = token.split("\\" + BLOCK_OPEN_TOKEN);
					if(openSplit.length > 2){
						openTokens = openSplit.length - 1;
					}
				}
				if(token.contains(BLOCK_CLOSE_TOKEN)){
					closeTokens = 1;
					String[] closeSplit = token.split(BLOCK_CLOSE_TOKEN);
					if(closeSplit.length > 2){
						closeTokens = closeSplit.length - 1;
					}
				}
				// Figure out current number of open blocks (update previous count)
				openBlocks += openTokens - closeTokens;
				
				// If open blocks = 0, we're done
				if(openBlocks == 0){
					methodDone = true;
				}
			}
		}
		
		// If we didn't do parameters, it's a problem
		if(!parametersDone){
			errors.add("Didn't complete parameters in method");
		}
		
		// If we didn't finish the method, it's a problem
		if(!methodDone){
			errors.add("Didn't complete the method");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.METHOD, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build the method
		return new ParsingPojo(currentToken, parseMethod(methodString.toString()));
	}
	
	/**
	 * Parses a Java Method (not counting any Javadoc before it, just the method itself)
	 *
	 * @param content The text to be parsed into a {@link JavaMethod method)}
	 * @return The parsed {@link JavaMethod method}, or null if we don't have a method
	 */
	public JavaMethod parseMethod(String content){
		String methodString = StringUtil.trim(content);
		
		// Use regex to parse the method
		Matcher matcher = methodPattern.matcher(methodString);
		if(matcher.matches()){
			Visibility visibility = Visibility.fromToken(StringUtil.trim(matcher.group(1)));
			boolean isStatic = StringUtil.isNotBlank(matcher.group(2));
			boolean isFinal = StringUtil.isNotBlank(matcher.group(3));
			String returnType = StringUtil.trim(matcher.group(4));
			String name = StringUtil.trim(matcher.group(5));
			String parameterString = StringUtil.trim(matcher.group(6));
			String throwsString = StringUtil.trim(matcher.group(7));
			String contentString = StringUtil.trim(matcher.group(8));
			
			// Parse parameters
			List<Pair<String, String>> parameters = new ArrayList<>();
			if(StringUtil.isNotBlank(parameterString)){
				if(parameterString.contains(LIST_SEPARATOR_TOKEN)){
					String[] parameterList = parameterString.split(LIST_SEPARATOR_TOKEN);
					for(String aParameter: parameterList){
						String[] parameterSplit = StringUtil.trim(aParameter).split("\\s+");
						String parameterType = StringUtil.trim(parameterSplit[0]);
						String parameterName = StringUtil.trim(parameterSplit[1]);
						parameters.add(Pair.of(parameterType, parameterName));
					}
				}else{
					String[] parameterSplit = parameterString.split("\\s+");
					String parameterType = StringUtil.trim(parameterSplit[0]);
					String parameterName = StringUtil.trim(parameterSplit[1]);
					parameters.add(Pair.of(parameterType, parameterName));
				}
			}
			
			// Parse throws
			List<String> throwTypes = new ArrayList<>();
			if(StringUtil.isNotBlank(throwsString)){
				if(throwsString.contains(LIST_SEPARATOR_TOKEN)){
					for(String throwType: throwsString.split(LIST_SEPARATOR_TOKEN)){
						throwTypes.add(StringUtil.trim(throwType));
					}
				}else{
					throwTypes.add(throwsString);
				}
			}
			
			// Parse content
			List<String> lines = new ArrayList<>();
			if(StringUtil.isNotBlank(contentString)){
				if(contentString.contains(SEMICOLON)){
					for(String line: contentString.split(SEMICOLON)){
						lines.add(StringUtil.trim(line) + SEMICOLON);
					}
				}else{
					lines.add(contentString);
				}
			}
			
			return EditableJavaMethod.builder()
					.visibility(visibility)
					.isStatic(isStatic)
					.isFinal(isFinal)
					.returnType(returnType).name(name)
					.parameters(parameters)
					.throwTypes(throwTypes)
					.lines(lines)
					.build();
		}else{
			return null;
		}
	}
	
	/**
	 * Parses a {@link JavaClass class} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaClass class}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private ParsingPojo parseClass(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// First token of class must be "class"
		if(StringUtil.notEquals(tokens.get(startToken), CLASS_TOKEN)){
			errors.add("The first token of a class must be '" + CLASS_TOKEN + "'");
		}
		
		// Start parsing tokens after "class"
		int currentToken = startToken+1;
		
		// Skip any newlines
		while(currentToken < tokens.size() && StringUtil.equals(tokens.get(currentToken), "\n")){
			currentToken++;
		}
		
		// Next token is class name
		String className = tokens.get(currentToken);
		currentToken++;
		
		// Check class name for closing block
		boolean hitBlockOpenToken = false;
		boolean endReached = false;
		if(className.endsWith(BLOCK_CLOSE_TOKEN)){
			className = className.substring(0, className.length()-1);
			endReached = true;
		}
		
		// Check class name for opening block
		if(className.endsWith(BLOCK_OPEN_TOKEN)){
			className = className.substring(0, className.length()-1);
			hitBlockOpenToken = true;
		}else if(endReached){
			errors.add("We reached the end of the class without finding the block open token");
		}
		
		// Parse the rest for items within the class
		String superClassName = null;
		List<JavaCodeType> itemsInClass = new ArrayList<>();
		while(currentToken < tokens.size() && !endReached){
			String token = tokens.get(currentToken);
			
			ThrowingFunction2<List<String>, Integer, ParsingPojo, JavaParsingException> parseMethod;
			if(StringUtil.equals(token, EXTENDS_TOKEN)){
				// If we already hit the block open token, there's a problem
				if(hitBlockOpenToken){
					errors.add("found '" + EXTENDS_TOKEN + "' after hitting the block open token!");
				}
				
				// Class has a super class
				currentToken++;
				superClassName = tokens.get(currentToken);
				
				// Check if we've hit the block closed/open tokens
				if(superClassName.endsWith(BLOCK_CLOSE_TOKEN)){
					superClassName = superClassName.substring(0, superClassName.length()-1);
					endReached = true;
				}
				if(superClassName.endsWith(BLOCK_OPEN_TOKEN)){
					superClassName = superClassName.substring(0, superClassName.length()-1);
					hitBlockOpenToken = true;
				}else if(endReached){
					errors.add("We reached the end of the class without finding the block open token");
				}
				currentToken++;
				continue;
			}else if(StringUtil.equals(token, BLOCK_OPEN_TOKEN)){
				if(hitBlockOpenToken){
					errors.add("We hit the block open token twice for the same class!");
				}
				hitBlockOpenToken = true;
				currentToken++;
				continue;
			}else if(StringUtil.equals(token, BLOCK_CLOSE_TOKEN)){
				endReached = true;
				currentToken++;
				continue;
			}else if(StringUtil.equals(token, CLASS_TOKEN)){
				// Parse a class
				parseMethod = this::parseClass;
			}else if(token.startsWith(JAVADOC_START_TOKEN)){
				// Parse a javadoc
				parseMethod = this::parseJavadoc;
			}else if(token.startsWith(MULTI_LINE_COMMENT_START_TOKEN)){
				// Parse a multi-line comment
				parseMethod = this::parseMultiLineComment;
			}else if(token.startsWith(SINGLE_LINE_COMMENT_TOKEN)){
				// Parse a single-line comment
				parseMethod = this::parseSingleLineComment;
			}else if(token.startsWith(ANNOTATION_START_TOKEN)){
				// Parse an annotation
				parseMethod = this::parseAnnotation;
			}else if(MODIFIERS.contains(token)){
				// Parse a type with modifiers (could be field, method, class, etc.)
				parseMethod = this::parseTypeWithModifiers;
			}else if(StringUtil.equals(token, "\n")){
				// Skip newlines
				currentToken++;
				continue;
			}else{
				JavaCodeTypes type = determineFieldOrMethod(tokens, currentToken);
				if(type == JavaCodeTypes.FIELD){
					parseMethod = this::parseField;
				}else if(type == JavaCodeTypes.METHOD){
					parseMethod = this::parseMethod;
				}else{
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Unable to determine token: '" + token + "'");
				}
			}
			
			// Run the parse method
			ParsingPojo result = parseMethod.apply(tokens, currentToken);
			itemsInClass.add(result.parsedType());
			currentToken = result.nextTokenIndex();
		}
		
		// Start building a JavaClass
		JavaClassBuilder<EditableJavaClass> builder = EditableJavaClass.builder()
				.className(className)
				.superClassName(superClassName);
		Javadoc doc = null;
		List<JavaAnnotation> annotations = new ArrayList<>();
		for(JavaCodeType type: itemsInClass){
			if(type instanceof Javadoc javadoc){
				// Javadoc needs to go on another type later
				doc = javadoc;
			}else if(type instanceof JavaAnnotation annotation){
				// Annotations need to go on another type later
				annotations.add(annotation);
			}else if(type instanceof EditableJavaField field){
				// Attach javadoc to field if we have it
				if(doc != null){
					field.setJavadoc(doc);
					doc = null;
				}
				// Attach annotations to field if we have them
				if(!annotations.isEmpty()){
					field.setAnnotations(annotations);
					annotations = new ArrayList<>();
				}
				builder.field(field);
			}else if(type instanceof EditableJavaMethod method){
				// Attach javadoc to method if we have it
				if(doc != null){
					method.setJavadoc(doc);
					doc = null;
				}
				// Attach annotations to method if we have them
				if(!annotations.isEmpty()){
					method.setAnnotations(annotations);
					annotations = new ArrayList<>();
				}
				builder.method(method);
			}else if(type instanceof EditableJavaClass clazz){
				// Attach javadoc to inner class if we have it
				if(doc != null){
					clazz.setJavadoc(doc);
					doc = null;
				}
				// Attach annotations to inner class if we have them
				if(!annotations.isEmpty()){
					clazz.setAnnotations(annotations);
					annotations = new ArrayList<>();
				}
				clazz.setInnerClass(true);
				builder.innerClass(clazz);
			}else{
				errors.add("Don't know how to add '" + type.getJavaCodeType() + "' to a class");
			}
		}
		
		// Check for hanging doc/annotations
		if(doc != null){
			errors.add("Found Javadoc at end of class with nothing to attach it to!");
		}
		if(!annotations.isEmpty()){
			errors.add("Found annotations at end of class with nothing to attach them to!");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.CLASS, StringUtil.buildStringWithNewLines(errors));
		}
		
		return new ParsingPojo(currentToken, builder.build());
	}
}
