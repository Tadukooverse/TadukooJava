package com.github.tadukoo.java.parsing.classtypes;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.JavaClassBuilder;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.java.parsing.codetypes.JavaAnnotationParser;
import com.github.tadukoo.java.parsing.codetypes.JavaFieldParser;
import com.github.tadukoo.java.parsing.codetypes.JavaImportStatementParser;
import com.github.tadukoo.java.parsing.codetypes.JavaMethodParser;
import com.github.tadukoo.java.parsing.codetypes.JavaPackageDeclarationParser;
import com.github.tadukoo.java.parsing.codetypes.JavaTypeWithModifiersParser;
import com.github.tadukoo.java.parsing.comment.JavaMultiLineCommentParser;
import com.github.tadukoo.java.parsing.comment.JavaSingleLineCommentParser;
import com.github.tadukoo.java.parsing.comment.JavadocParser;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction2;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser used for parsing {@link JavaClass classes in Java}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 * @since Beta v.0.5
 */
public class JavaClassParser extends AbstractJavaParser{
	
	/** Not allowed to instantiate {@link JavaClassParser} */
	private JavaClassParser(){ }
	
	/**
	 * Parses a {@link JavaClass class} from the given content String
	 *
	 * @param content The String of content to parse into a {@link JavaClass class}
	 * @return The {@link JavaClass class} parsed from the given String
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static JavaClass parseClass(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = splitContentIntoTokens(content);
		
		// Iterate over the tokens to parse stuff - we could get Package Declarations, Import Statements,
		// Javadocs, Annotations, and the class itself
		int currentToken = 0;
		List<JavaCodeType> types = new ArrayList<>();
		while(currentToken < tokens.size()){
			String token = tokens.get(currentToken);
			
			ThrowingFunction2<List<String>, Integer, ParsingPojo, JavaParsingException> parseMethod;
			
			if(WHITESPACE_MATCHER.reset(token).matches()){
				// Skip whitespace
				currentToken++;
				continue;
			}else if(token.startsWith(PACKAGE_TOKEN)){
				// Parse a package declaration
				parseMethod = JavaPackageDeclarationParser::parsePackageDeclaration;
			}else if(token.startsWith(IMPORT_TOKEN)){
				// Parse an import statement
				parseMethod = JavaImportStatementParser::parseImportStatement;
			}else if(token.startsWith(JAVADOC_START_TOKEN)){
				// Parse a javadoc
				parseMethod = JavadocParser::parseJavadoc;
			}else if(token.startsWith(ANNOTATION_START_TOKEN)){
				// Parse an annotation
				parseMethod = JavaAnnotationParser::parseAnnotation;
			}else if(MODIFIERS.contains(token)){
				// If it's modifiers, send it to the type with modifiers parser
				parseMethod = JavaTypeWithModifiersParser::parseTypeWithModifiers;
			}else{
				// Assume it's a class
				parseMethod = JavaClassParser::parseClass;
			}
			
			// Use the parse method and handle its results
			ParsingPojo pojo = parseMethod.apply(tokens, currentToken);
			types.add(pojo.parsedType());
			currentToken = pojo.nextTokenIndex();
		}
		
		// Combine the types
		JavaPackageDeclaration packageDeclaration = null;
		List<JavaImportStatement> importStatements = new ArrayList<>();
		Javadoc doc = null;
		List<JavaAnnotation> annotations = new ArrayList<>();
		JavaClass clazz = null;
		for(JavaCodeType type: types){
			if(type instanceof JavaPackageDeclaration javaPackageDeclaration){
				if(packageDeclaration != null){
					// Can't have multiple package declarations
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Only one package declaration allowed on a class!");
				}else if(clazz != null){
					// Can't have package declaration after the class
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Encountered package declaration after class!");
				}
				packageDeclaration = javaPackageDeclaration;
			}else if(type instanceof JavaImportStatement importStatement){
				// Can't have import statements after the class
				if(clazz != null){
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Encountered import statement after class!");
				}
				importStatements.add(importStatement);
			}else if(type instanceof Javadoc javadoc){
				if(doc != null){
					// Can't have multiple Javadocs
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Only one Javadoc allowed on a class!");
				}else if(clazz != null){
					// Can't have Javadoc after the class
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Encountered Javadoc after class!");
				}
				doc = javadoc;
			}else if(type instanceof JavaAnnotation annotation){
				// Can't have annotations after the class
				if(clazz != null){
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Encountered annotation after class!");
				}
				annotations.add(annotation);
			}else if(type instanceof EditableJavaClass javaClass){
				// Can't have multiple classes
				if(clazz != null){
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Encountered multiple classes!");
				}
				// Set package declaration if we have it
				if(packageDeclaration != null){
					javaClass.setPackageDeclaration(packageDeclaration);
				}
				// Set import statements if we have them
				if(ListUtil.isNotBlank(importStatements)){
					javaClass.setImportStatements(importStatements);
				}
				// Set Javadoc if we have it
				if(doc != null){
					javaClass.setJavadoc(doc);
				}
				// Set annotations if we have them
				if(ListUtil.isNotBlank(annotations)){
					javaClass.setAnnotations(annotations);
				}
				clazz = javaClass;
			}
		}
		
		// Error if we didn't find a class
		if(clazz == null){
			throw new JavaParsingException(JavaCodeTypes.CLASS, "Failed to parse an actual class!");
		}
		
		return clazz;
	}
	
	/**
	 * Parses a {@link JavaClass class} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaClass class}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseClass(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// First token of class must be "class"
		if(StringUtil.notEquals(tokens.get(startToken), CLASS_TOKEN)){
			errors.add("The first token of a class must be '" + CLASS_TOKEN + "'");
		}
		
		// Start parsing tokens after "class"
		int currentToken = startToken+1;
		
		// Skip any whitespace
		while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
			currentToken++;
		}
		
		// Next token is class name
		Pair<String, Integer> typeAndNextToken = parseOutType(tokens, currentToken);
		if(currentToken >= tokens.size() || typeAndNextToken == null){
			throw new JavaParsingException(JavaCodeTypes.CLASS, "Failed to find class name!");
		}
		String className = typeAndNextToken.getLeft();
		currentToken = typeAndNextToken.getRight();
		
		// Parse the rest for items within the class
		boolean hitBlockOpenToken = false;
		boolean endReached = false;
		String superClassName = null;
		List<String> implementsInterfaces = new ArrayList<>();
		List<JavaCodeType> itemsInClass = new ArrayList<>();
		while(currentToken < tokens.size() && !endReached){
			String token = tokens.get(currentToken);
			
			ThrowingFunction2<List<String>, Integer, ParsingPojo, JavaParsingException> parseMethod;
			if(StringUtil.equals(token, EXTENDS_TOKEN)){
				// If we already hit the block open token, there's a problem
				if(hitBlockOpenToken){
					throw new JavaParsingException(JavaCodeTypes.CLASS,
							"found '" + EXTENDS_TOKEN + "' after hitting the block open token!");
				}
				
				// Skip whitespace
				currentToken++;
				while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
					currentToken++;
				}
				
				// Class has a super class
				Pair<String, Integer> extendsTypeAndNextToken = parseOutType(tokens, currentToken);
				if(currentToken >= tokens.size() || extendsTypeAndNextToken == null){
					throw new JavaParsingException(JavaCodeTypes.CLASS, "Failed to find super class name after '" +
							EXTENDS_TOKEN + "'!");
				}
				superClassName = extendsTypeAndNextToken.getLeft();
				currentToken = extendsTypeAndNextToken.getRight();
				continue;
			}else if(StringUtil.equals(token, IMPLEMENTS_TOKEN)){
				// If we already hit the block open token, there's a problem
				if(hitBlockOpenToken){
					throw new JavaParsingException(JavaCodeTypes.CLASS,
							"found '" + IMPLEMENTS_TOKEN + "' after hitting the block open token!");
				}
				
				// Skip whitespace
				currentToken++;
				while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
					currentToken++;
				}
				
				boolean continueInterfaces = true;
				while(continueInterfaces){
					// Class has an interface it implements
					Pair<String, Integer> interfaceTypeAndNextToken = parseOutType(tokens, currentToken);
					if(currentToken >= tokens.size() || interfaceTypeAndNextToken == null){
						throw new JavaParsingException(JavaCodeTypes.CLASS, "Failed to find implements interface name " +
								"after '" + IMPLEMENTS_TOKEN + "' or '" + LIST_SEPARATOR_TOKEN + "'!");
					}
					String interfaceName = interfaceTypeAndNextToken.getLeft();
					currentToken = interfaceTypeAndNextToken.getRight();
					// Remove starting comma if it has it (unless first one, in which case it's an error)
					if(interfaceName.startsWith(LIST_SEPARATOR_TOKEN)){
						if(implementsInterfaces.isEmpty()){
							throw new JavaParsingException(JavaCodeTypes.CLASS, "Encountered '" + LIST_SEPARATOR_TOKEN +
									"' before any interface names!");
						}else{
							interfaceName = interfaceName.substring(LIST_SEPARATOR_TOKEN.length());
						}
					}
					
					// Check if interface name has an ending comma
					continueInterfaces = false;
					if(interfaceName.endsWith(LIST_SEPARATOR_TOKEN)){
						continueInterfaces = true;
						interfaceName = interfaceName.substring(0, interfaceName.length() - LIST_SEPARATOR_TOKEN.length());
					}
					// Actually add the interface to the list
					implementsInterfaces.add(interfaceName);
					
					// Proceed through whitespace to check for next token being comma
					while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
						currentToken++;
					}
					if(currentToken < tokens.size()){
						if(StringUtil.equals(tokens.get(currentToken), LIST_SEPARATOR_TOKEN)){
							continueInterfaces = true;
							currentToken++;
							// Skip any whitespace
							while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
								currentToken++;
							}
						}else if(tokens.get(currentToken).startsWith(LIST_SEPARATOR_TOKEN)){
							continueInterfaces = true;
						}
					}
				}
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
				parseMethod = JavaClassParser::parseClass;
			}else if(token.startsWith(JAVADOC_START_TOKEN)){
				// Parse a javadoc
				parseMethod = JavadocParser::parseJavadoc;
			}else if(token.startsWith(MULTI_LINE_COMMENT_START_TOKEN)){
				// Parse a multi-line comment
				parseMethod = JavaMultiLineCommentParser::parseMultiLineComment;
			}else if(token.startsWith(SINGLE_LINE_COMMENT_TOKEN)){
				// Parse a single-line comment
				parseMethod = JavaSingleLineCommentParser::parseSingleLineComment;
			}else if(token.startsWith(ANNOTATION_START_TOKEN)){
				// Parse an annotation
				parseMethod = JavaAnnotationParser::parseAnnotation;
			}else if(MODIFIERS.contains(token)){
				// Parse a type with modifiers (could be field, method, class, etc.)
				parseMethod = JavaTypeWithModifiersParser::parseTypeWithModifiers;
			}else if(WHITESPACE_MATCHER.reset(token).matches()){
				// Skip whitespace
				currentToken++;
				continue;
			}else{
				JavaCodeTypes type = determineFieldOrMethod(tokens, currentToken);
				if(type == JavaCodeTypes.FIELD){
					parseMethod = JavaFieldParser::parseField;
				}else if(type == JavaCodeTypes.METHOD){
					parseMethod = JavaMethodParser::parseMethod;
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
				.className(className);
		// Only add extends and implements if we have them
		if(StringUtil.isNotBlank(superClassName)){
			builder.superClassName(superClassName);
		}
		if(ListUtil.isNotBlank(implementsInterfaces)){
			builder.implementsInterfaceNameTexts(implementsInterfaces);
		}
		Javadoc doc = null;
		List<JavaAnnotation> annotations = new ArrayList<>();
		for(JavaCodeType type: itemsInClass){
			if(type instanceof JavaStaticCodeBlock staticCodeBlock){
				// Static Code Block goes on the class
				builder.staticCodeBlock(staticCodeBlock);
			}else if(type instanceof JavaSingleLineComment singleLineComment){
				// Single-Line comment goes on the class
				builder.singleLineComment(singleLineComment);
			}else if(type instanceof JavaMultiLineComment multiLineComment){
				// Multi-Line comment goes on the class
				builder.multiLineComment(multiLineComment);
			}else if(type instanceof Javadoc javadoc){
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
