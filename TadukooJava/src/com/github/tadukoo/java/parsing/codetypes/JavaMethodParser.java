package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaParameter;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.java.parsing.comment.JavadocParser;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser used for parsing {@link JavaMethod methods in Java}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 * @since Beta v.0.5
 */
public class JavaMethodParser extends AbstractJavaParser{
	
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
	private static final Pattern METHOD_PATTERN = Pattern.compile(
			"\\s*" + MODIFIERS_REGEX +
					"(" + TYPE_REGEX + ")(\\s*[^\\s(]*)?\\s*" +
					"\\(\\s*((" + PARAMETER_REGEX + ",?)*)\\s*\\)(?:\\s*throws ([^{]*))?" +
					"\\s*(?:\\{\\s*(.*)\\s*}|;\\s*)",
			Pattern.DOTALL);
	
	/** Not allowed to instantiate {@link JavaMethodParser} */
	private JavaMethodParser(){ }
	
	/**
	 * Parses a {@link JavaMethod method} from the given content String
	 *
	 * @param content The String of content to parse into a {@link JavaMethod method}
	 * @return The {@link JavaMethod method} parsed from the given String
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static JavaMethod parseMethod(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = splitContentIntoTokens(content);
		
		// Iterate over the tokens to parse stuff - we could get Javadocs, Annotations, and the method itself
		int currentToken = 0;
		List<JavaCodeType> types = new ArrayList<>();
		while(currentToken < tokens.size()){
			String token = tokens.get(currentToken);
			
			ThrowingFunction2<List<String>, Integer, ParsingPojo, JavaParsingException> parseMethod;
			
			if(WHITESPACE_MATCHER.reset(token).matches()){
				// Skip whitespace
				currentToken++;
				continue;
			}else if(token.startsWith(JAVADOC_START_TOKEN)){
				// Parse a javadoc
				parseMethod = JavadocParser::parseJavadoc;
			}else if(token.startsWith(ANNOTATION_START_TOKEN)){
				// Parse an annotation
				parseMethod = JavaAnnotationParser::parseAnnotation;
			}else{
				// Assume it's a method
				parseMethod = JavaMethodParser::parseMethod;
			}
			
			// Use the parse method and handle its results
			ParsingPojo pojo = parseMethod.apply(tokens, currentToken);
			types.add(pojo.parsedType());
			currentToken = pojo.nextTokenIndex();
		}
		
		// Combine the types
		Javadoc doc = null;
		List<JavaAnnotation> annotations = new ArrayList<>();
		JavaMethod method = null;
		for(JavaCodeType type: types){
			if(type instanceof Javadoc javadoc){
				if(doc != null){
					// Can't have multiple Javadocs
					throw new JavaParsingException(JavaCodeTypes.METHOD, "Only one Javadoc allowed on a method!");
				}else if(method != null){
					// Can't have Javadoc after the method
					throw new JavaParsingException(JavaCodeTypes.METHOD, "Encountered Javadoc after method!");
				}
				doc = javadoc;
			}else if(type instanceof JavaAnnotation annotation){
				// Can't have annotations after the method
				if(method != null){
					throw new JavaParsingException(JavaCodeTypes.METHOD, "Encountered annotation after method!");
				}
				annotations.add(annotation);
			}else if(type instanceof EditableJavaMethod javaMethod){
				// Can't have multiple methods
				if(method != null){
					throw new JavaParsingException(JavaCodeTypes.METHOD, "Encountered multiple methods!");
				}
				// Set Javadoc if we have it
				if(doc != null){
					javaMethod.setJavadoc(doc);
				}
				// Set annotations if we have them
				if(ListUtil.isNotBlank(annotations)){
					javaMethod.setAnnotations(annotations);
				}
				method = javaMethod;
			}
		}
		
		// Error if we didn't find a method
		if(method == null){
			throw new JavaParsingException(JavaCodeTypes.METHOD, "Failed to parse an actual method!");
		}
		
		return method;
	}
	
	/**
	 * Parses a {@link JavaMethod method} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaMethod method}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseMethod(List<String> tokens, int startToken) throws JavaParsingException{
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
			
			// If parameters are done, check for block open or semicolon
			if(parametersDone && token.contains(BLOCK_OPEN_TOKEN)){
				insideMethod = true;
			}else if(parametersDone && !insideMethod && token.endsWith(SEMICOLON)){
				methodDone = true;
			}
			
			// If inside the method, update the number of open blocks
			if(insideMethod){
				// Count open and close block tokens
				if(StringUtil.equals(token, BLOCK_OPEN_TOKEN)){
					openBlocks++;
				}
				if(StringUtil.equals(token, BLOCK_CLOSE_TOKEN)){
					openBlocks--;
				}
				
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
		return new ParsingPojo(currentToken, parseJustMethod(methodString.toString()));
	}
	
	/**
	 * Parses a Java Method (not counting any Javadoc before it, just the method itself)
	 *
	 * @param content The text to be parsed into a {@link JavaMethod method)}
	 * @return The parsed {@link JavaMethod method}, or null if we don't have a method
	 */
	public static JavaMethod parseJustMethod(String content){
		String methodString = StringUtil.trim(content);
		
		// Use regex to parse the method
		Matcher matcher = METHOD_PATTERN.matcher(methodString);
		if(matcher.matches()){
			Visibility visibility = Visibility.NONE;
			boolean isAbstract = false, isStatic = false, isFinal = false;
			for(int i = 1; i <= 3; i++){
				String token = matcher.group(i);
				if(StringUtil.isNotBlank(token)){
					switch(token){
						case PUBLIC_MODIFIER -> visibility = Visibility.PUBLIC;
						case PROTECTED_MODIFIER -> visibility = Visibility.PROTECTED;
						case PRIVATE_MODIFIER -> visibility = Visibility.PRIVATE;
						case ABSTRACT_MODIFIER -> isAbstract = true;
						case STATIC_MODIFIER -> isStatic = true;
						case FINAL_MODIFIER -> isFinal = true;
					}
				}
			}
			String returnType = StringUtil.trim(matcher.group(4));
			String name = StringUtil.trim(matcher.group(11));
			String parameterString = StringUtil.trim(matcher.group(12));
			String throwsString = StringUtil.trim(matcher.group(23));
			String contentString = StringUtil.trim(matcher.group(24));
			
			// Parse parameters
			List<JavaParameter> parameters = new ArrayList<>();
			if(StringUtil.isNotBlank(parameterString)){
				if(parameterString.contains(LIST_SEPARATOR_TOKEN)){
					String[] parameterList = parameterString.split(LIST_SEPARATOR_TOKEN);
					String aParameter = null;
					for(String parameterPiece: parameterList){
						if(StringUtil.isBlank(aParameter)){
							aParameter = parameterPiece;
						}else{
							aParameter += "," + parameterPiece;
						}
						if(PARAMETER_PATTERN.matcher(aParameter).matches()){
							parameters.add(parseJavaParameter(aParameter));
							aParameter = null;
						}
					}
				}else{
					parameters.add(parseJavaParameter(parameterString));
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
			int insideBlocks = 0;
			if(StringUtil.isNotBlank(contentString)){
				if(contentString.contains("\n")){
					for(String line: contentString.split("\n")){
						line = StringUtil.trim(line);
						if(StringUtil.equals(line, SEMICOLON)){
							String lastLine = lines.remove(lines.size()-1);
							lines.add(lastLine + SEMICOLON);
						}
						if(line.contains(SEMICOLON) && !line.endsWith(BLOCK_OPEN_TOKEN)){
							for(String subLine: line.split(SEMICOLON)){
								subLine = StringUtil.trim(subLine);
								if(StringUtil.isNotBlank(subLine)){
									if(subLine.startsWith(".")){
										subLine = "\t\t" + subLine;
									}
									lines.add("\t".repeat(insideBlocks) + subLine + SEMICOLON);
								}
							}
						}else{
							if(line.startsWith(BLOCK_CLOSE_TOKEN) || line.endsWith(BLOCK_CLOSE_TOKEN)){
								insideBlocks--;
							}
							lines.add("\t".repeat(insideBlocks) + (line.startsWith(".")?"\t\t":"") + StringUtil.trim(line));
							if(line.endsWith(BLOCK_OPEN_TOKEN)){
								insideBlocks++;
							}
						}
					}
				}else{
					for(String line: contentString.split(SEMICOLON)){
						lines.add("\t".repeat(insideBlocks) + (line.startsWith(".")?"\t\t":"") +
								StringUtil.trim(line) + SEMICOLON);
					}
				}
			}
			
			return EditableJavaMethod.builder()
					.visibility(visibility)
					.isAbstract(isAbstract)
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
}
