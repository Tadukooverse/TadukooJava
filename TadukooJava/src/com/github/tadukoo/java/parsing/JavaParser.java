package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.JavaClassBuilder;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.JavaTokens;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction2;

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
	private static final Pattern annotationPattern = Pattern.compile("\\s*@\\s*([^\\s(]*)(?:\\s*\\((.*)\\)\\s*)?\\s*");
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
			"(public |protected |private )?(static )?(final )?(\\S*)\\s*(\\S*?)\\s*;?");
	
	/**
	 * Used as a pojo for a return type of the various parsing sub-methods
	 *
	 * @param nextTokenIndex The index of the next token to be parsed
	 * @param parsedType A {@link JavaType} that was parsed by the method
	 */
	private record ParsingPojo(int nextTokenIndex, JavaType parsedType){ }
	
	/**
	 * Parses the given text as Java code and returns it as the proper {@link JavaType}
	 *
	 * @param content The text to be parsed as Java code
	 * @return The parsed {@link JavaType} from the given text
	 * @throws JavaParsingException If anything goes wrong while parsing
	 */
	public JavaType parseType(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = StringUtil.parseListFromStringWithRegex(content, "\\s+", true).stream()
				.filter(StringUtil::isNotBlank)
				.toList();
		
		// The Java types we've collected in order while parsing
		List<JavaType> types = new ArrayList<>();
		
		// Iterate over the tokens
		int i = 0;
		while(i < tokens.size()){
			// Grab the current token
			String token = tokens.get(i);
			
			ThrowingFunction2<List<String>, Integer, ParsingPojo, JavaParsingException> parseMethod;
			
			if(StringUtil.equals(token, PACKAGE_TOKEN)){
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
			}else if(token.startsWith(MULTI_LINE_COMMENT_START_TOKEN) || token.startsWith(SINGLE_LINE_COMMENT_TOKEN)){
				// Parse a comment
				parseMethod = this::parseComment;
			}else if(token.startsWith(ANNOTATION_START_TOKEN)){
				// Parse an annotation
				parseMethod = this::parseAnnotation;
			}else if(MODIFIERS.contains(token)){
				// Parse a type with modifiers (could be field, method, class, etc.)
				parseMethod = this::parseTypeWithModifiers;
			}else{
				throw new JavaParsingException(JavaTypes.UNKNOWN, "Unable to determine token: '" + token + "'");
			}
			
			// Use the parse method and handle its results
			ParsingPojo pojo = parseMethod.apply(tokens, i);
			types.add(pojo.parsedType());
			i = pojo.nextTokenIndex();
		}
		
		// Build the final type to be returned based on our collected types
		if(types.size() == 1){
			return types.get(0);
		}else{
			JavaPackageDeclaration packageDeclaration = null;
			List<JavaImportStatement> importStatements = new ArrayList<>();
			JavaClass javaClass = null;
			for(JavaType type: types){
				if(type instanceof JavaPackageDeclaration declaration){
					if(packageDeclaration != null){
						throw new JavaParsingException(JavaTypes.UNKNOWN, "Encountered two package declarations!");
					}
					if(javaClass != null){
						throw new JavaParsingException(JavaTypes.UNKNOWN, "Encountered package declaration after class!");
					}
					packageDeclaration = declaration;
				}else if(type instanceof JavaImportStatement importStmt){
					if(javaClass != null){
						throw new JavaParsingException(JavaTypes.UNKNOWN, "Encountered import statement after class!");
					}
					importStatements.add(importStmt);
				}else if(type instanceof EditableJavaClass clazz){
					if(javaClass != null){
						throw new JavaParsingException(JavaTypes.UNKNOWN, "Encountered two outer level classes!");
					}
					if(packageDeclaration != null){
						clazz.setPackageDeclaration(packageDeclaration);
					}
					if(!importStatements.isEmpty()){
						clazz.setImportStatements(importStatements);
					}
					javaClass = clazz;
				}else{
					throw new JavaParsingException(JavaTypes.UNKNOWN, "Unknown how to handle putting '" +
							type.getJavaType() + "' together with other types!");
				}
			}
			
			// Return class if we got it
			if(javaClass != null){
				return javaClass;
			}
			
			throw new JavaParsingException(JavaTypes.UNKNOWN, "Unable to collect unknown types together");
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
			}else{
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
			throw new JavaParsingException(JavaTypes.PACKAGE_DECLARATION, StringUtil.buildStringWithNewLines(errors));
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
		
		// Check if the next token is "static" (for a static import statement)
		boolean isStatic = false;
		if(tokens.size() > startToken+1){
			isStatic = StringUtil.equalsIgnoreCase(tokens.get(startToken+1), STATIC_MODIFIER);
		}
		
		// Set up current token
		int currentToken = startToken + (isStatic?2:1);
		
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
			}else{
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
			throw new JavaParsingException(JavaTypes.IMPORT_STATEMENT, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build the import statement and return
		return new ParsingPojo(currentToken, EditableJavaImportStatement.builder()
				.isStatic(isStatic)
				.importName(importName.toString())
				.build());
	}
	
	/**
	 * Parses an {@link Javadoc} from the given tokens and starting index
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
			throw new JavaParsingException(JavaTypes.JAVADOC, StringUtil.buildStringWithNewLines(errors));
		}
		
		// TODO: Return a proper pojo
		return new ParsingPojo(startToken, null);
	}
	
	private ParsingPojo parseComment(List<String> tokens, int startToken) throws JavaParsingException{
		// TODO: Handle actual parsing
		
		// TODO: Return a proper pojo
		return new ParsingPojo(startToken, null);
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
		
		// TODO: Handle actual parsing
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaTypes.ANNOTATION, StringUtil.buildStringWithNewLines(errors));
		}
		
		// TODO: Return a proper pojo
		return new ParsingPojo(startToken, null);
	}
	
	/**
	 * Parses a {@link JavaTypes#TYPE_WITH_MODIFIERS type with modifiers} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaTypes#TYPE_WITH_MODIFIERS type with modifiers}
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
		JavaTypes type = JavaTypes.TYPE_WITH_MODIFIERS;
		JavaType resultType = null;
		
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
				type = JavaTypes.CLASS;
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
						// TODO: Final on class
					}
				}
				
				break;
			}else{
				// TODO: Handle fields and methods here
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
		// TODO: Handle actual parsing
		
		// TODO: Return a proper pojo
		return new ParsingPojo(startToken, null);
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
		// TODO: Handle actual parsing
		
		// TODO: Return a proper pojo
		return new ParsingPojo(startToken, null);
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
		List<JavaType> itemsInClass = new ArrayList<>();
		for(; currentToken < tokens.size() && !endReached; currentToken++){
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
				continue;
			}else if(StringUtil.equals(token, BLOCK_OPEN_TOKEN)){
				if(hitBlockOpenToken){
					errors.add("We hit the block open token twice for the same class!");
				}
				hitBlockOpenToken = true;
				continue;
			}else if(StringUtil.equals(token, BLOCK_CLOSE_TOKEN)){
				endReached = true;
				continue;
			}else if(StringUtil.equals(token, CLASS_TOKEN)){
				// Parse a class
				parseMethod = this::parseClass;
			}else if(token.startsWith(JAVADOC_START_TOKEN)){
				// Parse a javadoc
				parseMethod = this::parseJavadoc;
			}else if(token.startsWith(MULTI_LINE_COMMENT_START_TOKEN) || token.startsWith(SINGLE_LINE_COMMENT_TOKEN)){
				// Parse a comment
				parseMethod = this::parseComment;
			}else if(token.startsWith(ANNOTATION_START_TOKEN)){
				// Parse an annotation
				parseMethod = this::parseAnnotation;
			}else if(MODIFIERS.contains(token)){
				// Parse a type with modifiers (could be field, method, class, etc.)
				parseMethod = this::parseTypeWithModifiers;
			}else{
				throw new JavaParsingException(JavaTypes.CLASS, "Unable to determine token: '" + token + "'");
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
		for(JavaType type: itemsInClass){
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
				errors.add("Don't know how to add '" + type.getJavaType() + "' to a class");
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
			throw new JavaParsingException(JavaTypes.CLASS, StringUtil.buildStringWithNewLines(errors));
		}
		
		return new ParsingPojo(currentToken, builder.build());
	}
	
	/**
	 * Parses the given text into an {@link JavaAnnotation annotation} if possible, or returns null
	 *
	 * @param content The text to be parsed into a {@link JavaAnnotation annotation}
	 * @return The {@link JavaAnnotation annotation} parsed from the text, or {@code null} if it can't be parsed
	 */
	// TODO: Remove old method
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
				while(parameterMatcher.find()){
					String parameterName = StringUtil.trim(parameterMatcher.group(1));
					String parameterValue = StringUtil.trim(parameterMatcher.group(2));
					builder.parameter(parameterName, parameterValue);
				}
			}
			
			return builder.build();
		}else{
			return null;
		}
	}
	
	/**
	 * Parses a Java Field (not counting any Javadoc before it, just the field itself)
	 *
	 * @param content The text to be parsed into a {@link JavaField field)}
	 * @return The parsed {@link JavaField field}, or null if we don't have a field
	 */
	// TODO: Remove old method
	public JavaField parseField(String content){
		// Find the equals signs in the field
		int equalsIndex = content.indexOf('=');
		
		String value = null;
		String firstPart = content;
		
		// Anything after equals is the value (if we even have equals)
		if(equalsIndex != -1){
			value = StringUtil.trim(content.substring(equalsIndex + 1));
			
			// remove semicolon from value if it exists
			if(value.endsWith(";")){
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
}
