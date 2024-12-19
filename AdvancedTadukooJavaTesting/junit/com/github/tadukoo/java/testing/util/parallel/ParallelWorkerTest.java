package com.github.tadukoo.java.testing.util.parallel;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class ParallelWorkerTest extends JavaClassParsingTest{
	
	public ParallelWorkerTest(){
		super("""
				package com.github.tadukoo.util.parallel;
				
				import com.github.tadukoo.util.logger.EasyLogger;
				
				/**
				 * Parallel Worker is the class involved in parallel programming that actually does the work involved in the
				 * parallel operation.
				 * <br><br>
				 * Subclasses you make of this class should have a constructor matching this class's constructor, because of
				 * how {@link ParallelRunner} works for instantiating an instance of this class.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.6
				 *\s
				 * @param <E> The type of the work object
				 */
				public abstract class ParallelWorker<E> implements Runnable{
				\t
					/** The {@link EasyLogger logger} to use for logging */
					protected final EasyLogger logger;
					/** The {@link Queue} containing work that needs to be executed yet */
					private final Queue<E> todoQueue;
					/** The {@link Queue} to put finished work into */
					private final Queue<E> doneQueue;
				\t
					/**
					 * Constructs a new {@link ParallelWorker} with the given parameters
					 *\s
					 * @param logger The {@link EasyLogger logger} to use for logging
					 * @param todoQueue The {@link Queue} containing work that needs to be executed yet
					 * @param doneQueue The {@link Queue} to put finished work into
					 */
					public ParallelWorker(EasyLogger logger, Queue<E> todoQueue, Queue<E> doneQueue){
						this.logger = logger;
						this.todoQueue = todoQueue;
						this.doneQueue = doneQueue;
					}
				\t
					/**
					 * Used to run the actual worker. Runs forever until {@link #checkToContinueWork(Object)} returns false.
					 * Each iteration it grabs work off the {@link #todoQueue}, checks if it is an object to terminate work,
					 * and if not will call {@link #doWork(Object)} with the work object, then add that work to the {@link #doneQueue}
					 */
					@Override
					public void run(){
						boolean cont = true;
						while(cont){
							try{
								E work = todoQueue.dequeue();
								if(checkToContinueWork(work)){
									// Do the actual work
									doWork(work);
									doneQueue.enqueue(work);
								}else{
									cont = false;
								}
							}catch(InterruptedException e){
								logger.logError(e);
							}
						}
					}
				\t
					/**
					 * Checks if the passed in work object is a terminate work object and returns {@code false} if it is
					 *\s
					 * @param work The work object to check
					 * @return {@code true} if the object is a valid work object, {@code false} if it is a terminate work object
					 */
					protected abstract boolean checkToContinueWork(E work);
				\t
					/**
					 * Do the work involved with the given work object
					 *\s
					 * @param work The work object to use to do the work
					 */
					protected abstract void doWork(E work);
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.parallel")
				.importName("com.github.tadukoo.util.logger.EasyLogger", false)
				.javadoc(EditableJavadoc.builder()
						.content("Parallel Worker is the class involved in parallel programming that actually does the work involved in the")
						.content("parallel operation.")
						.content("<br><br>")
						.content("Subclasses you make of this class should have a constructor matching this class's constructor, because of")
						.content("how {@link ParallelRunner} works for instantiating an instance of this class.")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.6")
						.param("<E>", "The type of the work object")
						.build())
				.visibility(Visibility.PUBLIC)
				.isAbstract()
				.className("ParallelWorker<E>")
				.implementsInterfaceName("Runnable")
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The {@link EasyLogger logger} to use for logging")
								.build())
						.visibility(Visibility.PROTECTED)
						.isFinal()
						.type("EasyLogger").name("logger")
						.build())
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The {@link Queue} containing work that needs to be executed yet")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("Queue<E>").name("todoQueue")
						.build())
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The {@link Queue} to put finished work into")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("Queue<E>").name("doneQueue")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Constructs a new {@link ParallelWorker} with the given parameters")
								.param("logger", "The {@link EasyLogger logger} to use for logging")
								.param("todoQueue", "The {@link Queue} containing work that needs to be executed yet")
								.param("doneQueue", "The {@link Queue} to put finished work into")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("ParallelWorker")
						.parameter("EasyLogger logger")
						.parameter("Queue<E> todoQueue")
						.parameter("Queue<E> doneQueue")
						.line("this.logger = logger;")
						.line("this.todoQueue = todoQueue;")
						.line("this.doneQueue = doneQueue;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Used to run the actual worker. Runs forever until {@link #checkToContinueWork(Object)} returns false.")
								.content("Each iteration it grabs work off the {@link #todoQueue}, checks if it is an object to terminate work,")
								.content("and if not will call {@link #doWork(Object)} with the work object, then add that work to the {@link #doneQueue}")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Override")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("run")
						.line("boolean cont = true;")
						.line("while(cont){")
						.line("\ttry{")
						.line("\t\tE work = todoQueue.dequeue();")
						.line("\t\tif(checkToContinueWork(work)){")
						.line("\t\t\t// Do the actual work")
						.line("\t\t\tdoWork(work);")
						.line("\t\t\tdoneQueue.enqueue(work);")
						.line("\t\t}else{")
						.line("\t\t\tcont = false;")
						.line("\t\t}")
						.line("\t}catch(InterruptedException e){")
						.line("\t\tlogger.logError(e);")
						.line("\t}")
						.line("}")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the passed in work object is a terminate work object and returns {@code false} if it is")
								.param("work", "The work object to check")
								.returnVal("{@code true} if the object is a valid work object, {@code false} if it is a terminate work object")
								.build())
						.visibility(Visibility.PROTECTED)
						.isAbstract()
						.returnType("boolean")
						.name("checkToContinueWork")
						.parameter("E work")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Do the work involved with the given work object")
								.param("work", "The work object to use to do the work")
								.build())
						.visibility(Visibility.PROTECTED)
						.isAbstract()
						.returnType("void")
						.name("doWork")
						.parameter("E work")
						.build())
				.build());
	}
}
