package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser used for parsing {@link JavaMethod methods in Java}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
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
					"([^\\s(]*)(\\s*[^\\s(]*)?\\s*" +
					"\\(\\s*([^)]*)\\s*\\)(?:\\s*throws (.*))?" +
					"\\s*\\{\\s*(.*)\\s*}",
			Pattern.DOTALL);
	
	/** Not allowed to instantiate {@link JavaMethodParser} */
	private JavaMethodParser(){ }
	
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
			boolean beforeOrAfterOpenParens = (token.startsWith(PARAMETER_OPEN_TOKEN) &&
					!(!methodString.isEmpty() &&
							(methodString.charAt(methodString.length()-1) == ASSIGNMENT_OPERATOR_TOKEN.charAt(0) ||
									methodString.charAt(methodString.length()-1) == PARAMETER_CLOSE_TOKEN.charAt(0)))) ||
					(!methodString.isEmpty() && methodString.charAt(methodString.length()-1) == PARAMETER_OPEN_TOKEN.charAt(0));
			boolean beforeOrAfterCloseParens = (token.startsWith(PARAMETER_CLOSE_TOKEN) &&
					!(!methodString.isEmpty() &&
							(methodString.charAt(methodString.length()-1) == ASSIGNMENT_OPERATOR_TOKEN.charAt(0) ||
									methodString.charAt(methodString.length()-1) == PARAMETER_OPEN_TOKEN.charAt(0))));
			boolean otherCloseParensCase = (!methodString.isEmpty() &&
					methodString.charAt(methodString.length()-1) == PARAMETER_CLOSE_TOKEN.charAt(0)) &&
					token.startsWith(".");
			boolean beforeOrAfterBraces = ((token.startsWith(BLOCK_OPEN_TOKEN) || token.startsWith(BLOCK_CLOSE_TOKEN))) ||
					(!methodString.isEmpty() && methodString.charAt(methodString.length()-1) == BLOCK_CLOSE_TOKEN.charAt(0));
			boolean beforeComma = token.startsWith(LIST_SEPARATOR_TOKEN);
			if(!methodString.isEmpty() && !beforeOrAfterOpenParens && !beforeOrAfterCloseParens &&
					!otherCloseParensCase && !beforeOrAfterBraces && !beforeComma){
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
	public static JavaMethod parseMethod(String content){
		String methodString = StringUtil.trim(content);
		
		// Use regex to parse the method
		Matcher matcher = METHOD_PATTERN.matcher(methodString);
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
			int insideBlocks = 0;
			if(StringUtil.isNotBlank(contentString)){
				if(contentString.contains("\n")){
					for(String line: contentString.split("\n")){
						line = StringUtil.trim(line);
						if(StringUtil.equals(line, SEMICOLON)){
							String lastLine = lines.get(lines.size()-1);
							if(!lastLine.endsWith(SEMICOLON)){
								lines.remove(lines.size()-1);
								lines.add(lastLine + SEMICOLON);
							}
						}
						if(line.contains(SEMICOLON) && !line.endsWith(BLOCK_OPEN_TOKEN)){
							for(String subLine: line.split(SEMICOLON)){
								subLine = StringUtil.trim(subLine);
								if(StringUtil.isNotBlank(subLine)){
									lines.add("\t".repeat(insideBlocks) + subLine + SEMICOLON);
								}
							}
						}else{
							if(line.startsWith(BLOCK_CLOSE_TOKEN) || line.endsWith(BLOCK_CLOSE_TOKEN)){
								insideBlocks--;
							}
							lines.add("\t".repeat(insideBlocks) + StringUtil.trim(line));
							if(line.endsWith(BLOCK_OPEN_TOKEN)){
								insideBlocks++;
							}
						}
					}
				}else if(contentString.contains(SEMICOLON)){
					for(String line: contentString.split(SEMICOLON)){
						lines.add("\t".repeat(insideBlocks) + StringUtil.trim(line) + SEMICOLON);
					}
				}else{
					lines.add("\t".repeat(insideBlocks) + contentString);
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
}
