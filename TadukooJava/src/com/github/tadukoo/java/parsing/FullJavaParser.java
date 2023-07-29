package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.parsing.classtypes.JavaClassParser;
import com.github.tadukoo.java.parsing.codetypes.JavaAnnotationParser;
import com.github.tadukoo.java.parsing.codetypes.JavaFieldParser;
import com.github.tadukoo.java.parsing.codetypes.JavaImportStatementParser;
import com.github.tadukoo.java.parsing.codetypes.JavaMethodParser;
import com.github.tadukoo.java.parsing.codetypes.JavaPackageDeclarationParser;
import com.github.tadukoo.java.parsing.codetypes.JavaTypeWithModifiersParser;
import com.github.tadukoo.java.parsing.comment.JavaMultiLineCommentParser;
import com.github.tadukoo.java.parsing.comment.JavaSingleLineCommentParser;
import com.github.tadukoo.java.parsing.comment.JavadocParser;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction2;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Parser is used to parse Java code
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class FullJavaParser extends AbstractJavaParser{
	
	private FullJavaParser(){
	
	}
	
	/**
	 * Parses the given text as Java code and returns it as the proper {@link JavaCodeType}
	 *
	 * @param content The text to be parsed as Java code
	 * @return The parsed {@link JavaCodeType} from the given text
	 * @throws JavaParsingException If anything goes wrong while parsing
	 */
	public static JavaCodeType parseType(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = StringUtil.parseListFromStringWithPattern(content, TOKEN_REGEX, false).stream()
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
				parseMethod = JavaPackageDeclarationParser::parsePackageDeclaration;
			}else if(StringUtil.equals(token, IMPORT_TOKEN)){
				// Parse an import statement
				parseMethod = JavaImportStatementParser::parseImportStatement;
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
			}else{
				JavaCodeTypes typeToParse = determineFieldOrMethod(tokens, currentToken);
				if(typeToParse == JavaCodeTypes.FIELD){
					parseMethod = JavaFieldParser::parseField;
				}else if(typeToParse == JavaCodeTypes.METHOD){
					parseMethod = JavaMethodParser::parseMethod;
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
		
		return combineTypes(types);
	}
	
	/**
	 * Handles combining the given types into one type if possible (e.g. if type 0 is a Javadoc and type 1
	 * is a field, we can put the Javadoc on the field)
	 *
	 * @param types The {@link JavaCodeType types} to be combined
	 * @return The resulting {@link JavaCodeType type}
	 * @throws JavaParsingException If anything goes wrong in combining types
	 */
	private static JavaCodeType combineTypes(List<JavaCodeType> types) throws JavaParsingException{
		// Build the final type to be returned based on our collected types
		if(types.size() == 1){
			return types.get(0);
		}else{
			JavaPackageDeclaration packageDeclaration = null;
			List<JavaImportStatement> importStatements = new ArrayList<>();
			Javadoc javadoc = null;
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
				}else if(type instanceof Javadoc doc){
					// Can't have multiple javadocs
					if(javadoc != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered two Javadocs!");
					}
					
					// Can't have javadoc after a field
					if(javaField != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered a Javadoc after field!");
					}
					
					// Can't have javadoc after a method
					if(javaMethod != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered a Javadoc after method!");
					}
					
					// Can't have javadoc after a class
					if(javaClass != null){
						throw new JavaParsingException(JavaCodeTypes.UNKNOWN, "Encountered a Javadoc after class!");
					}
					
					// Set the javadoc to potentially be added to something else
					javadoc = doc;
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
					
					// Add javadoc to the field
					if(javadoc != null){
						field.setJavadoc(javadoc);
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
					
					// Add javadoc to the method
					if(javadoc != null){
						method.setJavadoc(javadoc);
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
					
					// Add javadoc to the class
					if(javadoc != null){
						clazz.setJavadoc(javadoc);
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
}
