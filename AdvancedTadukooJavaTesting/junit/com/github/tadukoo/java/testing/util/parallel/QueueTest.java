package com.github.tadukoo.java.testing.util.parallel;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class QueueTest extends JavaClassParsingTest{
	
	public QueueTest(){
		super("""
				package com.github.tadukoo.util.parallel;
				
				import java.util.LinkedList;
				
				/**
				 * This Queue is used to run parallel code with thread-safe methods for grabbing items off the queue and
				 * putting items on the queue.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.6
				 *\s
				 * @param <E> The type of item stored in this {@link Queue}
				 */
				public class Queue<E>{
				\t
					/** An object to use as a lock for synchronizing while running in parallel */
					private final Object lock = new Object();
					/** The actual data of the {@link Queue} */
					private final LinkedList<E> data;
					/** The maximum number of items that can be in the {@link Queue} */
					private final int maxItems;
				\t
					/**
					 * Creates a new {@link Queue}
					 *\s
					 * @param maxItems The maximum number of items that can be in the {@link Queue}
					 */
					public Queue(int maxItems){
						data = new LinkedList<>();
						this.maxItems = maxItems;
					}
				\t
					/**
					 * Adds the given item to the {@link Queue}
					 *\s
					 * @param item The item to add to the {@link Queue}
					 * @throws InterruptedException If something goes wrong in waiting for the lock
					 */
					public void enqueue(E item) throws InterruptedException{
						synchronized(lock){
							// Wait until we can add more to the queue
							while(data.size() == maxItems){
								lock.wait();
							}
							// Add the item to the queue and release the lock
							data.addLast(item);
							lock.notifyAll();
						}
					}
				\t
					/**
					 * Takes an item off the {@link Queue}
					 *\s
					 * @return The item taken off the {@link Queue}
					 * @throws InterruptedException If something goes wrong in waiting for the lock
					 */
					public E dequeue() throws InterruptedException{
						synchronized(lock){
							// Wait until there's something in the queue to take out
							while(data.size() == 0){
								lock.wait();
							}
							// Grab an item off the queue, release the lock, and return the grabbed item
							E item = data.removeFirst();
							lock.notifyAll();
							return item;
						}
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.parallel")
				.importName("java.util.LinkedList", false)
				.javadoc(EditableJavadoc.builder()
						.content("This Queue is used to run parallel code with thread-safe methods for grabbing items off the queue and")
						.content("putting items on the queue.")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.6")
						.param("<E>", "The type of item stored in this {@link Queue}")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("Queue<E>")
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("An object to use as a lock for synchronizing while running in parallel")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("Object").name("lock")
						.value("new Object()")
						.build())
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The actual data of the {@link Queue}")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("LinkedList<E>").name("data")
						.build())
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The maximum number of items that can be in the {@link Queue}")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("int").name("maxItems")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a new {@link Queue}")
								.param("maxItems", "The maximum number of items that can be in the {@link Queue}")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("Queue")
						.parameter("int", "maxItems")
						.line("data = new LinkedList<>();")
						.line("this.maxItems = maxItems;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Adds the given item to the {@link Queue}")
								.param("item", "The item to add to the {@link Queue}")
								.throwsInfo("InterruptedException", "If something goes wrong in waiting for the lock")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("enqueue")
						.parameter("E", "item")
						.throwType("InterruptedException")
						.line("synchronized(lock){")
						.line("\t// Wait until we can add more to the queue")
						.line("\twhile(data.size() == maxItems){")
						.line("\t\tlock.wait();")
						.line("\t}")
						.line("\t// Add the item to the queue and release the lock")
						.line("\tdata.addLast(item);")
						.line("\tlock.notifyAll();")
						.line("}")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Takes an item off the {@link Queue}")
								.returnVal("The item taken off the {@link Queue}")
								.throwsInfo("InterruptedException", "If something goes wrong in waiting for the lock")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("E")
						.name("dequeue")
						.throwType("InterruptedException")
						.line("synchronized(lock){")
						.line("\t// Wait until there's something in the queue to take out")
						.line("\twhile(data.size() == 0){")
						.line("\t\tlock.wait();")
						.line("\t}")
						.line("\t// Grab an item off the queue, release the lock, and return the grabbed item")
						.line("\tE item = data.removeFirst();")
						.line("\tlock.notifyAll();")
						.line("\treturn item;")
						.line("}")
						.build())
				.build());
	}
}
