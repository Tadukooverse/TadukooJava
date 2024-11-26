package com.github.tadukoo.java.testing.util.event;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class EventTest extends JavaClassParsingTest{
	
	public EventTest(){
		super("""
				package com.github.tadukoo.util.event;
				
				/**
				 * Represents a generic Event that has happened and may need to be handled
				 * by various {@link EventHandler EventHandlers}.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version 0.1-Alpha-SNAPSHOT
				 */
				public abstract class Event{
				\t
					/** The type of Event */
					private final String type;
					/** The message for the Event */
					private final String message;
				\t
					/**
					 * Creates an {@link Event} for the given type with the given message.
					 *\s
					 * @param type The type of Event
					 * @param message The message for the Event
					 */
					public Event(String type, String message){
						this.type = type;
						this.message = message;
					}
				\t
					/**
					 * Grabs the type of {@link Event} this represents.
					 *\s
					 * @return The type of Event this is
					 */
					public String getType(){
						return type;
					}
				\t
					/**
					 * Grabs the message describing this {@link Event}.
					 *\s
					 * @return The message for this Event
					 */
					public String getMessage(){
						return message;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.event")
				.javadoc(EditableJavadoc.builder()
						.content("Represents a generic Event that has happened and may need to be handled")
						.content("by various {@link EventHandler EventHandlers}.")
						.author("Logan Ferree (Tadukoo)")
						.version("0.1-Alpha-SNAPSHOT")
						.build())
				.visibility(Visibility.PUBLIC)
				.isAbstract()
				.className("Event")
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The type of Event")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("String").name("type")
						.build())
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The message for the Event")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("String").name("message")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates an {@link Event} for the given type with the given message.")
								.param("type", "The type of Event")
								.param("message", "The message for the Event")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("Event")
						.parameter("String", "type")
						.parameter("String", "message")
						.line("this.type = type;")
						.line("this.message = message;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Grabs the type of {@link Event} this represents.")
								.returnVal("The type of Event this is")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("String")
						.name("getType")
						.line("return type;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Grabs the message describing this {@link Event}.")
								.returnVal("The message for this Event")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("String")
						.name("getMessage")
						.line("return message;")
						.build())
				.build());
	}
}
