package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.code.staticcodeblock.EditableJavaStaticCodeBlock;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.java.parsing.classtypes.JavaClassParser;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A parser used for parsing Java types that have modifiers
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaTypeWithModifiersParser extends AbstractJavaParser{
	
	/** Not allowed to instantiate {@link JavaTypeWithModifiersParser} */
	private JavaTypeWithModifiersParser(){ }
	
	/**
	 * Parses a {@link JavaCodeTypes#TYPE_WITH_MODIFIERS type with modifiers} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaCodeTypes#TYPE_WITH_MODIFIERS type with modifiers}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseTypeWithModifiers(List<String> tokens, int startToken) throws JavaParsingException{
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
				ParsingPojo result = JavaClassParser.parseClass(tokens, currentToken);
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
						case ABSTRACT_MODIFIER -> clazz.setAbstract(true);
						case STATIC_MODIFIER -> clazz.setStatic(true);
						case FINAL_MODIFIER -> clazz.setFinal(true);
					}
				}
				
				break;
			}else if(StringUtil.equals(token, BLOCK_OPEN_TOKEN)){
				// Parse as a static code block
				
				// Check we only have 1 modifier and it's static
				if(modifiers.size() != 1 || StringUtil.notEquals(modifiers.get(0), STATIC_MODIFIER)){
					errors.add("Static Code Block can only have 'static' as a modifier");
				}
				
				// Go through every line until we get to the block close token
				int openBlocks = 1;
				StringBuilder content = new StringBuilder();
				currentToken++;
				
				// Skip leading whitespace
				while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
					currentToken++;
				}
				
				while(openBlocks > 0){
					// Grab next token, keep track of blocks we're in
					String currentTokenContent = tokens.get(currentToken);
					if(StringUtil.equals(currentTokenContent, BLOCK_OPEN_TOKEN)){
						openBlocks++;
					}else if(StringUtil.equals(currentTokenContent, BLOCK_CLOSE_TOKEN)){
						openBlocks--;
					}
					
					// Advance token count, end if we finished the original block
					currentToken++;
					if(openBlocks == 0){
						continue;
					}
					
					// Add to content the next token
					content.append(currentTokenContent);
				}
				
				// Last minute modifications to remove extra spacing from content
				String endContent = content.toString().replaceAll("\n\t\t", "\n")
						.replaceAll("\\s+$", "");
				
				// Create the static code block and set it as the result
				EditableJavaStaticCodeBlock staticCodeBlock = EditableJavaStaticCodeBlock.builder()
						.lines(StringUtil.parseListFromStringWithSeparator(endContent, "\n", false))
						.build();
				type = JavaCodeTypes.STATIC_CODE_BLOCK;
				resultType = staticCodeBlock;
				
				break;
			}else if(!WHITESPACE_MATCHER.reset(token).matches()){
				// Skip whitespace
				type = determineFieldOrMethod(tokens, currentToken);
				if(type == JavaCodeTypes.METHOD){
					// Parse the method and handle modifiers on it
					ParsingPojo result = JavaMethodParser.parseMethod(tokens, currentToken);
					EditableJavaMethod method = (EditableJavaMethod) result.parsedType();
					for(String modifier: modifiers){
						switch(modifier){
							case PRIVATE_MODIFIER -> method.setVisibility(Visibility.PRIVATE);
							case PROTECTED_MODIFIER -> method.setVisibility(Visibility.PROTECTED);
							case PUBLIC_MODIFIER -> method.setVisibility(Visibility.PUBLIC);
							case ABSTRACT_MODIFIER -> method.setAbstract(true);
							case STATIC_MODIFIER -> method.setStatic(true);
							case FINAL_MODIFIER -> method.setFinal(true);
						}
					}
					resultType = method;
					currentToken = result.nextTokenIndex();
					break;
				}else if(type == JavaCodeTypes.FIELD){
					// Parse the field and handle modifiers on it
					ParsingPojo result = JavaFieldParser.parseField(tokens, currentToken);
					EditableJavaField field = (EditableJavaField) result.parsedType();
					for(String modifier: modifiers){
						switch(modifier){
							case PRIVATE_MODIFIER -> field.setVisibility(Visibility.PRIVATE);
							case PROTECTED_MODIFIER -> field.setVisibility(Visibility.PROTECTED);
							case PUBLIC_MODIFIER -> field.setVisibility(Visibility.PUBLIC);
							case ABSTRACT_MODIFIER -> throw new JavaParsingException(JavaCodeTypes.FIELD,
									"'" + ABSTRACT_MODIFIER + "' is not a valid modifier on field!");
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
}
