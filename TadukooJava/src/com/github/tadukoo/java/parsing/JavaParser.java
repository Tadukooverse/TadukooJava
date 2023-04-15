package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaClass;
import com.github.tadukoo.java.JavaClassBuilder;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.editable.EditableJavaClass;
import com.github.tadukoo.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaParser{
	private static final Pattern packageLinePattern = Pattern.compile("\\s*package\\s*(\\S*)\\s*;\\s*");
	private static final Pattern importLinePattern = Pattern.compile("\\s*import\\s*(static\\s*)?(\\S*)\\s*;\\s*");
	private static final Pattern classLinePattern = Pattern.compile(
			"(public |protected |private )?(static )?(class |interface |enum )([^\\s.]*?)(?: extends (.*))?\\{");
	
	public JavaType parseJavaType(String content){
		// Use class line pattern to determine what Java type this is
		Matcher classLineMatcher = classLinePattern.matcher(content);
		if(classLineMatcher.find()){
			String type = StringUtil.trim(classLineMatcher.group(3));
			if(StringUtil.equalsIgnoreCase(type, "class")){
				return parseClass(content);
			}else{
				throw new IllegalArgumentException("Type '" + type + "' is not currently supported for parsing");
			}
		}else{
			throw new IllegalStateException("Failed to determine Java type");
		}
	}
	
	private JavaClass parseClass(String content){
		// Split the class into lines to more easily work with it
		List<String> lines = StringUtil.parseListFromStringWithSeparator(content, "\n", true);
		
		// Start building the class
		JavaClassBuilder<EditableJavaClass> builder = EditableJavaClass.builder();
		
		// Keep track of what we found / where we are in parsing
		boolean foundPackage = false;
		boolean inImports = false, inStaticImports = false;
		boolean lastLineBlank = false;
		boolean gotBasicClassInfo = false;
		
		// Actually handle the parsing
		for(String line: lines){
			// If we have a blank line, note it and move on (it matters in some cases and not others)
			if(StringUtil.isBlank(line)){
				lastLineBlank = true;
				continue;
			}
			
			// Check for the package if we haven't already found it
			if(!foundPackage){
				Matcher packageMatcher = packageLinePattern.matcher(line);
				if(packageMatcher.matches()){
					builder.packageName(packageMatcher.group(1));
					foundPackage = true;
					continue;
				}
			}
			
			// Check for import statements
			Matcher importMatcher = importLinePattern.matcher(line);
			if(importMatcher.matches()){
				// Check if it's a static import or not
				boolean isStatic = StringUtil.isNotBlank(importMatcher.group(1));
				
				// Handle static import
				if(isStatic){
					// If we were in regular imports, we're not anymore
					if(inImports){
						inImports = false;
					}
					
					// If the last line was blank, add an empty static import if we were in static imports
					if(lastLineBlank){
						if(inStaticImports){
							builder.staticImport("");
						}
						lastLineBlank = false;
					}
					
					// Add the static import and mark that we're doing static imports now
					builder.staticImport(importMatcher.group(2));
					inStaticImports = true;
				}else{
					// Handle non-static import
					
					// If we were in static imports, we're not anymore
					if(inStaticImports){
						inStaticImports = false;
					}
					
					// If the last line was blank, add an empty import if we were in imports
					if(lastLineBlank){
						if(inImports){
							builder.singleImport("");
						}
						lastLineBlank = false;
					}
					
					// Add the import and mark that we're doing imports now
					builder.singleImport(importMatcher.group(2));
					inImports = true;
				}
				continue;
			}
			
			// If we don't already have the basic class info, check for it now
			if(!gotBasicClassInfo){
				Matcher classMatcher = classLinePattern.matcher(line);
				if(classMatcher.matches()){
					// If we were in imports or static imports, we're not anymore
					if(inImports){
						inImports = false;
					}
					if(inStaticImports){
						inStaticImports = false;
					}
					
					// Parse the visibility string into the Visibility type (we need to trim off leading/trailing whitespace)
					Visibility visibility = Visibility.fromText(StringUtil.trim(classMatcher.group(1)));
					
					// Check if we're a static class or not
					boolean isStatic = StringUtil.isNotBlank(classMatcher.group(2));
					
					// Grab the class name and trim off leading/trailing whitespace
					String className = StringUtil.trim(classMatcher.group(4));
					
					// Grab the super class name (and trim off leading/trailing whitespace)
					String superClassName = StringUtil.trim(classMatcher.group(5));
					
					// Send the info we just parsed to the builder and mark that we got the basic class info
					builder.visibility(visibility)
							.isStatic(isStatic)
							.className(className)
							.superClassName(superClassName);
					gotBasicClassInfo = true;
					continue;
				}
			}
		}
		
		return builder.build();
	}
}
