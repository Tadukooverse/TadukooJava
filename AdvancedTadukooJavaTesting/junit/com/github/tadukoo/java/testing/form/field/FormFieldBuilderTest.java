package com.github.tadukoo.java.testing.form.field;

import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.testing.JavaClassParsingTest;
import org.junit.jupiter.api.Disabled;

@Disabled
public class FormFieldBuilderTest extends JavaClassParsingTest{
	
	protected FormFieldBuilderTest(){
		super("""
				package com.github.tadukoo.view.form.field.annotation;
				
				import com.github.tadukoo.java.JavaCodeTypes;
				import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
				import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
				import com.github.tadukoo.java.field.JavaField;
				import com.github.tadukoo.java.javaclass.EditableJavaClass;
				import com.github.tadukoo.java.javaclass.JavaClass;
				import com.github.tadukoo.java.Visibility;
				import com.github.tadukoo.java.javaclass.JavaClassBuilder;
				import com.github.tadukoo.java.javadoc.EditableJavadoc;
				import com.github.tadukoo.java.javadoc.Javadoc;
				import com.github.tadukoo.java.javadoc.JavadocBuilder;
				import com.github.tadukoo.java.method.EditableJavaMethod;
				import com.github.tadukoo.java.method.JavaMethod;
				import com.github.tadukoo.java.method.JavaMethodBuilder;
				import com.github.tadukoo.ultimatepower.UltimatePowerProcessor;
				import com.github.tadukoo.util.ListUtil;
				import com.github.tadukoo.util.StringUtil;
				import com.github.tadukoo.util.logger.EasyLogger;
				import com.github.tadukoo.util.map.HashMultiMap;
				import com.github.tadukoo.util.map.MultiMap;
				import com.github.tadukoo.util.tuple.Pair;
				import com.github.tadukoo.util.tuple.Triple;
				import com.github.tadukoo.view.font.FontFamily;
				import com.github.tadukoo.view.font.FontResourceLoader;
				import com.github.tadukoo.view.form.field.FieldType;
				import com.github.tadukoo.view.form.field.LabelType;
				import com.github.tadukoo.view.paint.SizablePaint;
				import com.github.tadukoo.view.shapes.ShapeInfo;
				
				import javax.swing.border.Border;
				import java.awt.GraphicsEnvironment;
				import java.lang.annotation.Annotation;
				import java.util.ArrayList;
				import java.util.List;
				
				public class FormFieldBuilderProcessor implements UltimatePowerProcessor{
				\t
					/** {@inheritDoc} */
					@Override
					public List<Class<? extends Annotation>> getAnnotations(){
						return ListUtil.createList(FormFieldBuilder.class);
					}
				\t
					public record Parameter(
							String name, String description, String defaultValueOrRequired, String type,
							List<Parameter> params){ }
				\t
					private FormFieldBuilder verifyAnnotation(List<? extends Annotation> annotations){
						if(annotations.size() > 1){
							throw new IllegalArgumentException("Expecting a single annotation!");
						}
						if(!(annotations.get(0) instanceof FormFieldBuilder annotation)){
							Class<?> annotationClass = annotations.get(0).getClass();
							throw new IllegalArgumentException("Expecting the FormFieldBuilder (" +
									FormFieldBuilder.class.getCanonicalName() + ") annotation, not " + annotationClass.getName() +
									"(" + annotationClass.getCanonicalName() + ")");
						}
						return annotation;
					}
				\t
					@Override
					public List<JavaClass> generateInnerClasses(List<? extends Annotation> annotations, JavaClass javaClass){
						// Grab the pieces of the annotation
						FormFieldBuilder annotation = verifyAnnotation(annotations);
						Class<?> type = annotation.type();
						String defaultDefaultValue = annotation.defaultDefaultValue();
						LabelType defaultLabelType = annotation.defaultLabelType();
				\t\t
						// Grab values needed for the builder class
						String className = javaClass.getClassName();
						String builderClassName = className + "Builder";
						String valueType = type.getSimpleName();
				\t\t
						// Grab author and version from the Javadoc of the class or use defaults
						String author = "FormFieldBuilderProcessor";
						String version = "Placeholder";
						if(javaClass.getJavadoc() != null){
							Javadoc javadoc = javaClass.getJavadoc();
							if(StringUtil.isNotBlank(javadoc.getAuthor())){
								author = javadoc.getAuthor();
							}
							if(StringUtil.isNotBlank(javadoc.getVersion())){
								version = javadoc.getVersion();
							}
						}
				\t\t
						// Grab the parameters we need
						Triple<List<String>, List<Parameter>, MultiMap<String, Parameter>> parameterInfo = getParameters(valueType);
						List<String> parameterCategories = parameterInfo.getLeft();
						List<Parameter> parametersInOrder = parameterInfo.getMiddle();
						MultiMap<String, Parameter> parameterMap = parameterInfo.getRight();
				\t\t
						// Start building builder class
						JavaClassBuilder<EditableJavaClass> builderClass = EditableJavaClass.builder()
								.innerClass()
								// Javadoc
								.javadoc(EditableJavadoc.builder()
										.content(createBuilderJavadocContent(className, parametersInOrder))
										.author(author)
										.version(version)
										.build())
								// Class Declaration
								.visibility(Visibility.PUBLIC)
								.isStatic().className(builderClassName).superClassName("FormFieldBuilder<" + valueType + ">");
				\t\t
						// Start the Constructor
						JavaMethodBuilder<EditableJavaMethod> constructorMethod = EditableJavaMethod.builder()
										.javadoc(EditableJavadoc.builder()
												.condensed()
												.content("Can't create " + builderClassName + " outside " + className)
												.build())
										.visibility(Visibility.PRIVATE)
										.returnType(builderClassName)
										.line("super();")
										.line("defaultValue = " + defaultDefaultValue +";");
						if(defaultLabelType != FormFieldBuilder.DEFAULT_defaultLabelType){
							constructorMethod.line("labelType = LabelType." + defaultLabelType.name() + ";");
						}
						// Add the constructor to the class
						builderClass.method(constructorMethod.build());
				\t\t
						// Handle parameters
						for(String parameterCategory: parameterCategories){
							// Add section comment
							builderClass.multiLineComment(EditableJavaMultiLineComment.builder()
									.content(parameterCategory)
									.build());
				\t\t\t
							// Grab parameters and add them
							for(Parameter parameter: parameterMap.get(parameterCategory)){
								JavaMethodBuilder<EditableJavaMethod> parameterMethod = EditableJavaMethod.builder()
										.javadoc(EditableJavadoc.builder()
												.condensed()
												.content("{@inheritDoc}")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Override")
												.build())
										.visibility(Visibility.PUBLIC)
										.returnType(builderClassName).name(parameter.name());
				\t\t\t\t
								// Parameters can vary
								if(ListUtil.isNotBlank(parameter.params())){
									StringBuilder superMethodCallParams = new StringBuilder();
									for(Parameter param: parameter.params()){
										parameterMethod.parameter(param.type(), param.name());
										superMethodCallParams.append(param.name()).append(", ");
									}
									superMethodCallParams.delete(superMethodCallParams.length()-2, superMethodCallParams.length());
									parameterMethod.line("super." + parameter.name() + "(" + superMethodCallParams + ");");
								}else{
									parameterMethod.parameter(parameter.type(), parameter.name());
									parameterMethod.line("super." + parameter.name() + "(" + parameter.name() + ");");
								}
				\t\t\t\t
								// Finish the method and add to builder class
								builderClass.method(parameterMethod.line("return this;")
										.build());
							}
						}
				\t\t
						// Create the build method
						JavaMethodBuilder<EditableJavaMethod> buildMethod = EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.condensed()
										.content("{@inheritDoc}")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Override")
										.build())
								.visibility(Visibility.PUBLIC).returnType(className).name("build");
						for(int i = 0; i < parameterCategories.size(); i++){
							String parameterCategory = parameterCategories.get(i);
							StringBuilder start;
							if(i == 0){
								start = new StringBuilder("return new " + className + "(");
							}else{
								start = new StringBuilder("\\t\\t");
							}
							for(Parameter parameter: parameterMap.get(parameterCategory)){
								if(ListUtil.isBlank(parameter.params())){
									start.append(parameter.name()).append(", ");
								}else{
									for(Parameter param: parameter.params()){
										start.append(param.name()).append(", ");
									}
								}
							}
				\t\t\t
							if(i == parameterCategories.size() - 1){
								start.delete(start.length()-2, start.length());
								start.append(");");
							}else{
								start.delete(start.length()-1, start.length());
							}
				\t\t\t
							buildMethod.line(start.toString());
						}
						builderClass.method(buildMethod.build());
				\t\t
						return ListUtil.createList(builderClass.build());
					}
				\t
					private Triple<List<String>, List<Parameter>, MultiMap<String, Parameter>> getParameters(String valueType){
						List<String> parameterCategories = new ArrayList<>();
						List<Parameter> parametersInOrder = new ArrayList<>();
						MultiMap<String, Parameter> parameterMap = new HashMultiMap<>();
				\t\t
						/*
						 * The Basics
						 */
						String theBasicsCategory = "The Basics";
						parameterCategories.add(theBasicsCategory);
						Parameter key = new Parameter("key", "The name of the field (used as a key in forms)",
								"Required", "String", null);
						parametersInOrder.add(key);
						parameterMap.put(theBasicsCategory, key);
				\t\t
						Parameter defaultValue = new Parameter("defaultValue", "The starting value of the field",
								"Defaults to the empty String", valueType, null);
						parametersInOrder.add(defaultValue);
						parameterMap.put(theBasicsCategory, defaultValue);
				\t\t
						/*
						 * Label Settings
						 */
						String labelSettingsCategory = "Label Settings";
						parameterCategories.add(labelSettingsCategory);
						Parameter labelType = new Parameter("labelType", "The {@link LabelType} to use for the field",
								"Defaults to {@link LabelType#NONE}", "LabelType", null);
						parametersInOrder.add(labelType);
						parameterMap.put(labelSettingsCategory, labelType);
				\t\t
						Parameter labelForegroundPaint = new Parameter("labelForegroundPaint",
								"The {@link SizablePaint} for the foreground of the Label",
								"Defaults to null (to use the Look &amp; Feel's default Label foreground paint)",
								"SizablePaint", null);
						parametersInOrder.add(labelForegroundPaint);
						parameterMap.put(labelSettingsCategory, labelForegroundPaint);
				\t\t
						Parameter labelBackgroundPaint = new Parameter("labelBackgroundPaint",
								"The {@link SizablePaint} for the background of the Label",
								"Defaults to null (to use the Look &amp; Feel's default Label background paint)",
								"SizablePaint", null);
						parametersInOrder.add(labelBackgroundPaint);
						parameterMap.put(labelSettingsCategory, labelBackgroundPaint);
				\t\t
						Parameter labelFont = new Parameter("labelFont",
								"The font to use for the Label - specified as a {@link FontFamily}, style, and size",
								"Defaults to null (to use the Look &amp; Feel's default Label font)",
								null, ListUtil.createList(
										new Parameter("labelFontFamily", "The {@link FontFamily} for the Label's font",
												null, "FontFamily", null),
										new Parameter("labelFontStyle", "The font style for the Label",
												null, "int", null),
										new Parameter("labelFontSize", "The font size for the Label",
												null, "int", null)));
						parametersInOrder.add(labelFont);
						parameterMap.put(labelSettingsCategory, labelFont);
				\t\t
						Parameter labelShape = new Parameter("labelShape",
								"The {@link ShapeInfo} to use for the Label",
								"Defaults to null (to use the Look &amp; Feel's default Label shape)",
								"ShapeInfo", null);
						parametersInOrder.add(labelShape);
						parameterMap.put(labelSettingsCategory, labelShape);
				\t\t
						Parameter labelBorder = new Parameter("labelBorder",
								"The {@link Border} to use for the Label",
								"Defaults to null (to use the Look &amp; Feel's default Label border)",
								"Border", null);
						parametersInOrder.add(labelBorder);
						parameterMap.put(labelSettingsCategory, labelBorder);
				\t\t
						/*
						 * Positioning
						 */
						String positioningCategory = "Positioning";
						parameterCategories.add(positioningCategory);
						Parameter rowPos = new Parameter("rowPos",
								"The row position of the field", "Required",
								"int", null);
						parametersInOrder.add(rowPos);
						parameterMap.put(positioningCategory, rowPos);
				\t\t
						Parameter colPos = new Parameter("colPos",
								"The column position of the field", "Required",
								"int", null);
						parametersInOrder.add(colPos);
						parameterMap.put(positioningCategory, colPos);
				\t\t
						Parameter rowSpan = new Parameter("rowSpan",
								"The row span of the field", "Defaults to 1",
								"int", null);
						parametersInOrder.add(rowSpan);
						parameterMap.put(positioningCategory, rowSpan);
				\t\t
						Parameter colSpan = new Parameter("colSpan",
								"The column span of the field", "Defaults to 1",
								"int", null);
						parametersInOrder.add(colSpan);
						parameterMap.put(positioningCategory, colSpan);
				\t\t
						/*
						 * Font Resource Loading
						 */
						String fontResourceLoadingCategory = "Font Resource Loading";
						parameterCategories.add(fontResourceLoadingCategory);
						Parameter logFontResourceLoaderWarnings = new Parameter("logFontResourceLoaderWarnings",
								"Whether to log warnings generated by the FontResourceLoader\\n" +
										"- can be ignored if you specify your own FontResourceLoader",
								"false", "boolean", null);
						parametersInOrder.add(logFontResourceLoaderWarnings);
						parameterMap.put(fontResourceLoadingCategory, logFontResourceLoaderWarnings);
				\t\t
						Parameter logger = new Parameter("logger",
								"An {@link EasyLogger} that will be sent to the FontResourceLoader by default\\n" +
										"- can be ignored if you specify your own FontResourceLoader",
								"null (since logging warnings is set to false by default)",
								"EasyLogger", null);
						parametersInOrder.add(logger);
						parameterMap.put(fontResourceLoadingCategory, logger);
				\t\t
						Parameter graphEnv = new Parameter("graphEnv",
								"The {@link GraphicsEnvironment} to load fonts to in the FontResourceLoader\\n" +
										"- can be ignored if you specify your own FontResourceLoader",
								"{@link GraphicsEnvironment#getLocalGraphicsEnvironment()}",
								"GraphicsEnvironment", null);
						parametersInOrder.add(graphEnv);
						parameterMap.put(fontResourceLoadingCategory, graphEnv);
				\t\t
						Parameter fontFolder = new Parameter("fontFolder",
								"The path to the fonts folder to find font files in if needed in the FontResourceLoader\\n" +
										"- can be ignored if you specify your own FontResourceLoader",
								"\\"fonts/\\"",
								"String", null);
						parametersInOrder.add(fontFolder);
						parameterMap.put(fontResourceLoadingCategory, fontFolder);
				\t\t
						Parameter fontResourceLoader = new Parameter("fontResourceLoader",
								"The {@link FontResourceLoader} to use in loading fonts and/or ensuring they're in the system",
								"a new FontResourceLoader with the specified values for {@link #logFontResourceLoaderWarnings},\\n" +
										"{@link #logger}, {@link #graphEnv}, and {@link #fontFolder}",
								"FontResourceLoader", null);
						parametersInOrder.add(fontResourceLoader);
						parameterMap.put(fontResourceLoadingCategory, fontResourceLoader);
				\t\t
						return Triple.of(parameterCategories, parametersInOrder, parameterMap);
					}
				\t
					private List<String> createBuilderJavadocContent(String className, List<Parameter> parameters){
						List<String> content = ListUtil.createList(
								"Builder to be used to create a {@link " + className + "}. " +
										"It has the following parameters:",
								"",
								"<table>",
								"\\t<caption>" + className + " Parameters</caption>",
								"\\t<tr>",
								"\\t\\t<th>Name</th>",
								"\\t\\t<th>Description</th>",
								"\\t\\t<th>Default Value or Required</th>",
								"\\t</tr>"
						);
				\t\t
						for(Parameter parameter: parameters){
							content.add("\\t<tr>");
							content.add("\\t\\t<td>" + parameter.name() + "</td>");
							content.add("\\t\\t<td>" + parameter.description().replace("\\n", "\\n * \\t\\t") + "</td>");
							content.add("\\t\\t<td>" + parameter.defaultValueOrRequired().replace("\\n", "\\n * \\t\\t") + "</td>");
							content.add("\\t</tr>");
						}
				\t\t
						content.add("</table>");
						return content;
					}
				\t
					@Override
					public List<JavaField> generateFields(List<? extends Annotation> annotations, JavaClass javaClass){
						return new ArrayList<>();
					}
				\t
					@Override
					public List<JavaMethod> generateMethods(List<? extends Annotation> annotations, JavaClass javaClass){
						// Grab the pieces of the annotation
						FormFieldBuilder annotation = verifyAnnotation(annotations);
						Class<?> type = annotation.type();
				\t\t
						// Grab class name from the class
						String className = javaClass.getClassName();
						String builderClassName = className + "Builder";
				\t\t
						// Grab parameters
						Triple<List<String>, List<Parameter>, MultiMap<String, Parameter>> parameterInfo = getParameters(type.getSimpleName());
						List<String> parameterCategories = parameterInfo.getLeft();
						List<Parameter> parametersInOrder = parameterInfo.getMiddle();
						MultiMap<String, Parameter> parameterMap = parameterInfo.getRight();
				\t\t
						// Create start of the constructor Javadoc and constructor method
						JavadocBuilder<EditableJavadoc> constructorJavadoc = EditableJavadoc.builder()
								.content("Creates a new " + className + " with the given parameters.");
						JavaMethodBuilder<EditableJavaMethod> constructor = EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE).returnType(className);
				\t\t
						// Handle parameters
						for(Parameter parameter: parametersInOrder){
							if(ListUtil.isBlank(parameter.params())){
								constructorJavadoc.param(parameter.name(), parameter.description().replace("\\n", "\\n * "));
								constructor.parameter(parameter.type(), parameter.name());
							}else{
								for(Parameter param: parameter.params()){
									constructorJavadoc.param(param.name(), param.description().replace("\\n", "\\n * "));
									constructor.parameter(param.type(), param.name());
								}
							}
						}
						for(int i = 0; i < parameterCategories.size(); i++){
							String parameterCategory = parameterCategories.get(i);
							StringBuilder start;
							if(i == 0){
								start = new StringBuilder("super(FieldType.LABEL, ");
							}else{
								start = new StringBuilder("\\t\\t");
							}
							for(Parameter parameter: parameterMap.get(parameterCategory)){
								if(ListUtil.isBlank(parameter.params())){
									start.append(parameter.name()).append(", ");
								}else{
									for(Parameter param: parameter.params()){
										start.append(param.name()).append(", ");
									}
								}
							}
				\t\t\t
							if(i == parameterCategories.size() - 1){
								start.delete(start.length()-2, start.length());
								start.append(");");
							}else{
								start.delete(start.length()-1, start.length());
							}
				\t\t\t
							constructor.line(start.toString());
						}
				\t\t
						// Complete the constructor
						JavaMethod constructorMethod = constructor.javadoc(constructorJavadoc.build())
								.build();
				\t\t
						// Create builder method
						JavaMethod builderMethod = EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.returnVal("A {@link " + builderClassName + " builder} to use to build a {@link " + className + "}")
										.build())
								.visibility(Visibility.PUBLIC).isStatic()
								.returnType(builderClassName).name("builder")
								.line("return new " + builderClassName + "();")
								.build();
				\t\t
						return ListUtil.createList(constructorMethod, builderMethod);
					}
				\t
					@Override
					public JavaClass makeOtherChanges(List<? extends Annotation> annotations, JavaClass javaClass){
						// Grab the pieces of the annotation
						FormFieldBuilder annotation = verifyAnnotation(annotations);
						Class<?> type = annotation.type();
				\t\t
						// Ensure we have an EditableJavaClass
						if(!(javaClass instanceof EditableJavaClass clazz)){
							throw new IllegalArgumentException("Java Class should be EditableJavaClass!");
						}
				\t\t
						// Add needed imports
						clazz.addImportNames(ListUtil.createList(
								EasyLogger.class.getCanonicalName(),
								FontFamily.class.getCanonicalName(),
								FontResourceLoader.class.getCanonicalName(),
								FieldType.class.getCanonicalName(),
								LabelType.class.getCanonicalName(),
								SizablePaint.class.getCanonicalName(),
								ShapeInfo.class.getCanonicalName(),
								Border.class.getCanonicalName(),
								GraphicsEnvironment.class.getCanonicalName()),
								false);
				\t\t
						// Grab parameters and determine constructor method signature
						List<Parameter> parametersInOrder = getParameters(type.getSimpleName()).getMiddle();
						StringBuilder constructorSignature = new StringBuilder("init(");
						for(Parameter parameter: parametersInOrder){
							if(ListUtil.isBlank(parameter.params())){
								constructorSignature.append(parameter.type()).append(' ').append(parameter.name()).append(", ");
							}else{
								for(Parameter param: parameter.params()){
									constructorSignature.append(param.type()).append(' ').append(param.name()).append(", ");
								}
							}
						}
						constructorSignature.delete(constructorSignature.length()-2, constructorSignature.length());
						constructorSignature.append(')');
				\t\t
						// Change the order of methods
						List<Pair<JavaCodeTypes, String>> innerElementsOrder = clazz.getInnerElementsOrder();
						String builderClassName = javaClass.getClassName() + "Builder";
						Pair<JavaCodeTypes, String> builderClass = null, constructorMethod = null, builderMethod = null;
						for(Pair<JavaCodeTypes, String> elementInfo: innerElementsOrder){
							if(elementInfo.getLeft() == JavaCodeTypes.CLASS &&
									StringUtil.equalsIgnoreCase(elementInfo.getRight(), builderClassName)){
								builderClass = elementInfo;
							}else if(elementInfo.getLeft() == JavaCodeTypes.METHOD){
								if(StringUtil.equalsIgnoreCase(elementInfo.getRight(), constructorSignature.toString())){
									constructorMethod = elementInfo;
								}else if(StringUtil.equalsIgnoreCase(elementInfo.getRight(), "builder()")){
									builderMethod = elementInfo;
								}
							}
						}
						// Create new order
						List<Pair<JavaCodeTypes, String>> newElementsOrder = new ArrayList<>();
						if(builderClass != null){
							newElementsOrder.add(builderClass);
							innerElementsOrder.remove(builderClass);
						}
						if(constructorMethod != null){
							newElementsOrder.add(constructorMethod);
							innerElementsOrder.remove(constructorMethod);
						}
						if(builderMethod != null){
							newElementsOrder.add(builderMethod);
							innerElementsOrder.remove(builderMethod);
						}
						newElementsOrder.addAll(innerElementsOrder);
						clazz.setInnerElementsOrder(newElementsOrder);
				\t\t
						return clazz;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.view.form.field.annotation")
				.importName("com.github.tadukoo.java.JavaCodeTypes", false)
				.importName("import com.github.tadukoo.java.annotation.EditableJavaAnnotation", false)
				.importName("com.github.tadukoo.java.comment.EditableJavaMultiLineComment", false)
				.importName("com.github.tadukoo.java.field.JavaField", false)
				.importName("com.github.tadukoo.java.javaclass.EditableJavaClass", false)
				.importName("com.github.tadukoo.java.javaclass.JavaClass", false)
				.importName("com.github.tadukoo.java.Visibility", false)
				.importName("com.github.tadukoo.java.javaclass.JavaClassBuilder", false)
				.importName("com.github.tadukoo.java.javadoc.EditableJavadoc", false)
				.importName("com.github.tadukoo.java.javadoc.Javadoc", false)
				.importName("com.github.tadukoo.java.javadoc.JavadocBuilder", false)
				.importName("com.github.tadukoo.java.method.EditableJavaMethod", false)
				.importName("com.github.tadukoo.java.method.JavaMethod", false)
				.importName("com.github.tadukoo.java.method.JavaMethodBuilder", false)
				.importName("com.github.tadukoo.ultimatepower.UltimatePowerProcessor", false)
				.importName("com.github.tadukoo.util.ListUtil", false)
				.importName("com.github.tadukoo.util.StringUtil", false)
				.importName("com.github.tadukoo.util.logger.EasyLogger", false)
				.importName("com.github.tadukoo.util.map.HashMultiMap", false)
				.importName("com.github.tadukoo.util.map.MultiMap", false)
				.importName("com.github.tadukoo.util.tuple.Pair", false)
				.importName("com.github.tadukoo.util.tuple.Triple", false)
				.importName("com.github.tadukoo.view.font.FontFamily", false)
				.importName("com.github.tadukoo.view.font.FontResourceLoader", false)
				.importName("com.github.tadukoo.view.form.field.FieldType", false)
				.importName("com.github.tadukoo.view.form.field.LabelType", false)
				.importName("com.github.tadukoo.view.paint.SizablePaint", false)
				.importName("com.github.tadukoo.view.shapes.ShapeInfo", false)
				.importName("javax.swing.border.Border", false)
				.importName("java.awt.GraphicsEnvironment", false)
				.importName("java.lang.annotation.Annotation", false)
				.importName("java.util.ArrayList", false)
				.importName("java.util.List", false)
				.build());
	}
}
