package com.github.tadukoo.java.parsing.classtypes;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.JavaClassBuilder;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.java.parsing.codetypes.JavaAnnotationParser;
import com.github.tadukoo.java.parsing.codetypes.JavaFieldParser;
import com.github.tadukoo.java.parsing.codetypes.JavaMethodParser;
import com.github.tadukoo.java.parsing.codetypes.JavaTypeWithModifiersParser;
import com.github.tadukoo.java.parsing.comment.JavaMultiLineCommentParser;
import com.github.tadukoo.java.parsing.comment.JavaSingleLineCommentParser;
import com.github.tadukoo.java.parsing.comment.JavadocParser;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction2;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser used for parsing {@link JavaClass classes in Java}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaClassParser extends AbstractJavaParser{
	
	/** Not allowed to instantiate {@link JavaClassParser} */
	private JavaClassParser(){ }
	
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
				
				// Skip whitespace
				currentToken++;
				while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
					currentToken++;
				}
				
				// Class has a super class
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
				.className(className)
				.superClassName(superClassName);
		Javadoc doc = null;
		List<JavaAnnotation> annotations = new ArrayList<>();
		for(JavaCodeType type: itemsInClass){
			if(type instanceof JavaSingleLineComment singleLineComment){
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
