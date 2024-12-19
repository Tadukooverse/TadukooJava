package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class LoggerUtilTest extends JavaClassParsingTest{
	
	public LoggerUtilTest(){
		super("""
				package com.github.tadukoo.util;
				
				import java.io.IOException;
				import java.util.logging.FileHandler;
				import java.util.logging.Level;
				import java.util.logging.Logger;
				import java.util.logging.SimpleFormatter;
				
				/**
				 * Util functions for dealing with Loggers.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version 0.1-Alpha-SNAPSHOT
				 */
				public final class LoggerUtil{
				\t
					/** Not allowed to create a LoggerUtil */
					private LoggerUtil(){ }
				\t
					/**
					 * Creates a new {@link Logger} for the given file with the given {@link Level}
					 * of logging.
					 *\s
					 * @param filepath The path to the File to be used as a log
					 * @param level The Level to log messages at
					 * @return The created Logger
					 * @throws IOException If something goes wrong in creating the file logger
					 */
					public static Logger createFileLogger(String filepath, Level level) throws IOException{
						// Create the file
						FileUtil.createFile(filepath);
					\t
						// Setup a FileHandler for the File with a SimpleFormatter
						FileHandler fh = new FileHandler(filepath, true);
						SimpleFormatter formatter = new SimpleFormatter();
						fh.setFormatter(formatter);
					\t
						// Create the Logger with the given level and the created FileHandler
						Logger logger = Logger.getLogger(filepath);
						logger.setLevel(level);
						logger.addHandler(fh);
					\t
						// Return the newly created Logger
						return logger;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util")
				.importName("java.io.IOException", false)
				.importName("java.util.logging.FileHandler", false)
				.importName("java.util.logging.Level", false)
				.importName("java.util.logging.Logger", false)
				.importName("java.util.logging.SimpleFormatter", false)
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with Loggers.")
						.author("Logan Ferree (Tadukoo)")
						.version("0.1-Alpha-SNAPSHOT")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("LoggerUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to create a LoggerUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("LoggerUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a new {@link Logger} for the given file with the given {@link Level}")
								.content("of logging.")
								.param("filepath", "The path to the File to be used as a log")
								.param("level", "The Level to log messages at")
								.returnVal("The created Logger")
								.throwsInfo("IOException", "If something goes wrong in creating the file logger")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("Logger")
						.name("createFileLogger")
						.parameter("String filepath")
						.parameter("Level level")
						.throwType("IOException")
						.line("// Create the file")
						.line("FileUtil.createFile(filepath);")
						.line("")
						.line("// Setup a FileHandler for the File with a SimpleFormatter")
						.line("FileHandler fh = new FileHandler(filepath, true);")
						.line("SimpleFormatter formatter = new SimpleFormatter();")
						.line("fh.setFormatter(formatter);")
						.line("")
						.line("// Create the Logger with the given level and the created FileHandler")
						.line("Logger logger = Logger.getLogger(filepath);")
						.line("logger.setLevel(level);")
						.line("logger.addHandler(fh);")
						.line("")
						.line("// Return the newly created Logger")
						.line("return logger;")
						.build())
				.build());
	}
}
