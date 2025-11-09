package com.github.tadukoo.java.testing.util.logger;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class EasyLoggerTest extends JavaClassParsingTest{
	
	public EasyLoggerTest(){
		super("""
				package com.github.tadukoo.util.logger;
				
				import com.github.tadukoo.util.stack.StackUtil;
				
				import java.util.logging.Level;
				import java.util.logging.Logger;
				
				/**
				 * EasyLogger is a wrapper around {@link Logger} that provides methods to simplify logging operations
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.6
				 * @since Alpha v.0.2
				 */
				public class EasyLogger{
				\t
					/** The {@link Logger} wrapped in this EasyLogger */
					private final Logger logger;
				\t
					/**
					 * Wraps the given {@link Logger} as an EasyLogger.
					 *\s
					 * @param logger The {@link Logger} to be wrapped
					 */
					public EasyLogger(Logger logger){
						this.logger = logger;
					}
				\t
					/**
					 * @return The {@link Logger} wrapped in this class if you want to call other methods
					 */
					public Logger getLogger(){
						return logger;
					}
				\t
					/**
					 * Logs the given {@link Level#INFO info} message to the {@link Logger}
					 *\s
					 * @param info The message to be logged
					 */
					public void logInfo(String info){
						logger.logp(Level.INFO, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), info);
					}
				\t
					/**
					 * Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as
					 * the message on the log entry. This entry is logged as {@link Level#INFO info}
					 *\s
					 * @param t The {@link Throwable} to be logged
					 */
					public void logInfo(Throwable t){
						logger.logp(Level.INFO, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);
					}
				\t
					/**
					 * Logs the given {@link Level#INFO info} message with the given {@link Throwable} to the {@link Logger}
					 *\s
					 * @param info The message to be logged
					 * @param t The {@link Throwable} to be logged
					 */
					public void logInfo(String info, Throwable t){
						logger.logp(Level.INFO, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), info, t);
					}
				\t
					/**
					 * Logs the given {@link Level#WARNING warning} message to the {@link Logger}
					 *\s
					 * @param warning The message to be logged
					 */
					public void logWarning(String warning){
						logger.logp(Level.WARNING, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), warning);
					}
				\t
					/**
					 * Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as
					 * the message on the log entry. This entry is logged as a {@link Level#WARNING warning}
					 *\s
					 * @param t The {@link Throwable} to be logged
					 */
					public void logWarning(Throwable t){
						logger.logp(Level.WARNING, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);
					}
				\t
					/**
					 * Logs the given {@link Level#WARNING warning} message with the given {@link Throwable} to the {@link Logger}
					 *\s
					 * @param warning The message to be logged
					 * @param t The {@link Throwable} to be logged
					 */
					public void logWarning(String warning, Throwable t){
						logger.logp(Level.WARNING, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), warning, t);
					}
				\t
					/**
					 * Logs the given {@link Level#SEVERE error} message to the {@link Logger}
					 *\s
					 * @param error The message to be logged
					 */
					public void logError(String error){
						logger.logp(Level.SEVERE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), error);
					}
				\t
					/**
					 * Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as
					 * the message on the log entry. This entry is logged as an {@link Level#SEVERE error}
					 *\s
					 * @param t The {@link Throwable} to be logged
					 */
					public void logError(Throwable t){
						logger.logp(Level.SEVERE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);
					}
				\t
					/**
					 * Logs the given {@link Level#SEVERE error} message with the given {@link Throwable} to the {@link Logger}
					 *\s
					 * @param error The message to be logged
					 * @param t The {@link Throwable} to be logged
					 */
					public void logError(String error, Throwable t){
						logger.logp(Level.SEVERE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), error, t);
					}
				\t
					/**
					 * Logs the given {@link Level#CONFIG config} message to the {@link Logger}
					 *\s
					 * @param config The message to be logged
					 */
					public void logConfig(String config){
						logger.logp(Level.CONFIG, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), config);
					}
				\t
					/**
					 * Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as
					 * the message on the log entry. This entry is logged as a {@link Level#CONFIG config} message
					 *\s
					 * @param t The {@link Throwable} to be logged
					 */
					public void logConfig(Throwable t){
						logger.logp(Level.CONFIG, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);
					}
				\t
					/**
					 * Logs the given {@link Level#CONFIG config} message with the given {@link Throwable} to the {@link Logger}
					 *\s
					 * @param config The message to be logged
					 * @param t The {@link Throwable} to be logged
					 */
					public void logConfig(String config, Throwable t){
						logger.logp(Level.CONFIG, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), config, t);
					}
				\t
					/**
					 * Logs the given debug message to the {@link Logger} at {@link Level#FINE}
					 *\s
					 * @param debug The message to be logged
					 */
					public void logDebugFine(String debug){
						logger.logp(Level.FINE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug);
					}
				\t
					/**
					 * Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as
					 * the message on the log entry. This entry is logged at {@link Level#FINE}
					 *\s
					 * @param t The {@link Throwable} to be logged
					 */
					public void logDebugFine(Throwable t){
						logger.logp(Level.FINE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);
					}
				\t
					/**
					 * Logs the given debug message with the given {@link Throwable} to the {@link Logger} at {@link Level#FINE}
					 *\s
					 * @param debug The message to be logged
					 * @param t The {@link Throwable} to be logged
					 */
					public void logDebugFine(String debug, Throwable t){
						logger.logp(Level.FINE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug, t);
					}
				\t
					/**
					 * Logs the given debug message to the {@link Logger} at {@link Level#FINER}
					 *\s
					 * @param debug The message to be logged
					 */
					public void logDebugFiner(String debug){
						logger.logp(Level.FINER, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug);
					}
				\t
					/**
					 * Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as
					 * the message on the log entry. This entry is logged at {@link Level#FINER}
					 *\s
					 * @param t The {@link Throwable} to be logged
					 */
					public void logDebugFiner(Throwable t){
						logger.logp(Level.FINER, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);
					}
				\t
					/**
					 * Logs the given debug message with the given {@link Throwable} to the {@link Logger} at {@link Level#FINER}
					 *\s
					 * @param debug The message to be logged
					 * @param t The {@link Throwable} to be logged
					 */
					public void logDebugFiner(String debug, Throwable t){
						logger.logp(Level.FINER, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug, t);
					}
				\t
					/**
					 * Logs the given debug message to the {@link Logger} at {@link Level#FINEST}
					 *\s
					 * @param debug The message to be logged
					 */
					public void logDebugFinest(String debug){
						logger.logp(Level.FINEST, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug);
					}
				\t
					/**
					 * Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as
					 * the message on the log entry. This entry is logged at {@link Level#FINEST}
					 *\s
					 * @param t The {@link Throwable} to be logged
					 */
					public void logDebugFinest(Throwable t){
						logger.logp(Level.FINEST, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);
					}
				\t
					/**
					 * Logs the given debug message with the given {@link Throwable} to the {@link Logger} at {@link Level#FINEST}
					 *\s
					 * @param debug The message to be logged
					 * @param t The {@link Throwable} to be logged
					 */
					public void logDebugFinest(String debug, Throwable t){
						logger.logp(Level.FINEST, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug, t);
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.logger")
				.importName("com.github.tadukoo.util.stack.StackUtil", false)
				.importName("java.util.logging.Level", false)
				.importName("java.util.logging.Logger", false)
				.javadoc(EditableJavadoc.builder()
						.content("EasyLogger is a wrapper around {@link Logger} that provides methods to simplify logging operations")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.6")
						.since("Alpha v.0.2")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("EasyLogger")
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The {@link Logger} wrapped in this EasyLogger")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("Logger").name("logger")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Wraps the given {@link Logger} as an EasyLogger.")
								.param("logger", "The {@link Logger} to be wrapped")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("EasyLogger")
						.parameter("Logger logger")
						.line("this.logger = logger;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.returnVal("The {@link Logger} wrapped in this class if you want to call other methods")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("Logger")
						.name("getLogger")
						.line("return logger;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Level#INFO info} message to the {@link Logger}")
								.param("info", "The message to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logInfo")
						.parameter("String info")
						.line("logger.logp(Level.INFO, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), info);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as")
								.content("the message on the log entry. This entry is logged as {@link Level#INFO info}")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logInfo")
						.parameter("Throwable t")
						.line("logger.logp(Level.INFO, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Level#INFO info} message with the given {@link Throwable} to the {@link Logger}")
								.param("info", "The message to be logged")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logInfo")
						.parameter("String info")
						.parameter("Throwable t")
						.line("logger.logp(Level.INFO, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), info, t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Level#WARNING warning} message to the {@link Logger}")
								.param("warning", "The message to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logWarning")
						.parameter("String warning")
						.line("logger.logp(Level.WARNING, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), warning);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as")
								.content("the message on the log entry. This entry is logged as a {@link Level#WARNING warning}")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logWarning")
						.parameter("Throwable t")
						.line("logger.logp(Level.WARNING, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Level#WARNING warning} message with the given {@link Throwable} to the {@link Logger}")
								.param("warning", "The message to be logged")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logWarning")
						.parameter("String warning")
						.parameter("Throwable t")
						.line("logger.logp(Level.WARNING, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), warning, t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Level#SEVERE error} message to the {@link Logger}")
								.param("error", "The message to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logError")
						.parameter("String error")
						.line("logger.logp(Level.SEVERE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), error);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as")
								.content("the message on the log entry. This entry is logged as an {@link Level#SEVERE error}")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logError")
						.parameter("Throwable t")
						.line("logger.logp(Level.SEVERE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Level#SEVERE error} message with the given {@link Throwable} to the {@link Logger}")
								.param("error", "The message to be logged")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logError")
						.parameter("String error")
						.parameter("Throwable t")
						.line("logger.logp(Level.SEVERE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), error, t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Level#CONFIG config} message to the {@link Logger}")
								.param("config", "The message to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logConfig")
						.parameter("String config")
						.line("logger.logp(Level.CONFIG, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), config);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as")
								.content("the message on the log entry. This entry is logged as a {@link Level#CONFIG config} message")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logConfig")
						.parameter("Throwable t")
						.line("logger.logp(Level.CONFIG, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Level#CONFIG config} message with the given {@link Throwable} to the {@link Logger}")
								.param("config", "The message to be logged")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logConfig")
						.parameter("String config")
						.parameter("Throwable t")
						.line("logger.logp(Level.CONFIG, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), config, t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given debug message to the {@link Logger} at {@link Level#FINE}")
								.param("debug", "The message to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFine")
						.parameter("String debug")
						.line("logger.logp(Level.FINE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as")
								.content("the message on the log entry. This entry is logged at {@link Level#FINE}")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFine")
						.parameter("Throwable t")
						.line("logger.logp(Level.FINE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given debug message with the given {@link Throwable} to the {@link Logger} at {@link Level#FINE}")
								.param("debug", "The message to be logged")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFine")
						.parameter("String debug")
						.parameter("Throwable t")
						.line("logger.logp(Level.FINE, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug, t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given debug message to the {@link Logger} at {@link Level#FINER}")
								.param("debug", "The message to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFiner")
						.parameter("String debug")
						.line("logger.logp(Level.FINER, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as")
								.content("the message on the log entry. This entry is logged at {@link Level#FINER}")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFiner")
						.parameter("Throwable t")
						.line("logger.logp(Level.FINER, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given debug message with the given {@link Throwable} to the {@link Logger} at {@link Level#FINER}")
								.param("debug", "The message to be logged")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFiner")
						.parameter("String debug")
						.parameter("Throwable t")
						.line("logger.logp(Level.FINER, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug, t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given debug message to the {@link Logger} at {@link Level#FINEST}")
								.param("debug", "The message to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFinest")
						.parameter("String debug")
						.line("logger.logp(Level.FINEST, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given {@link Throwable} to the {@link Logger}. Uses {@link Throwable#getMessage()} as")
								.content("the message on the log entry. This entry is logged at {@link Level#FINEST}")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFinest")
						.parameter("Throwable t")
						.line("logger.logp(Level.FINEST, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), t.getMessage(), t);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Logs the given debug message with the given {@link Throwable} to the {@link Logger} at {@link Level#FINEST}")
								.param("debug", "The message to be logged")
								.param("t", "The {@link Throwable} to be logged")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("logDebugFinest")
						.parameter("String debug")
						.parameter("Throwable t")
						.line("logger.logp(Level.FINEST, StackUtil.getCallingClassName(), StackUtil.getCallingMethodName(), debug, t);")
						.build())
				.build());
	}
}
