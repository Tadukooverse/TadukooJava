package com.github.tadukoo.java.testing.util.download;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class ProgressReadableByteChannelWrapperTest extends JavaClassParsingTest{
	
	public ProgressReadableByteChannelWrapperTest(){
		super("""
				package com.github.tadukoo.util.download;
				
				import java.io.IOException;
				import java.nio.ByteBuffer;
				import java.nio.channels.ReadableByteChannel;
				
				/**
				 * Progress Readable Byte Channel Wrapper is a wrapper around {@link ReadableByteChannel} that will send
				 * progress updates to a {@link ProgressRBCWrapperListener}, which is keeping track of progress.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.5
				 */
				public class ProgressReadableByteChannelWrapper implements ReadableByteChannel{
				\t
					/** The underlying {@link ReadableByteChannel} to use for reading */
					private final ReadableByteChannel byteChannel;
					/** The {@link ProgressRBCWrapperListener} that is keeping track of progress */
					private final ProgressRBCWrapperListener listener;
					/** The expected total size in bytes to be read */
					private final long expectedSize;
					/** The amount of bytes read so far */
					private long readSoFar;
				\t
					/**
					 * Wraps the given {@link ReadableByteChannel} so we can send progress updates to the given
					 * {@link ProgressRBCWrapperListener}, which is keeping track of progress
					 *\s
					 * @param byteChannel The underlying {@link ReadableByteChannel} to be wrapped
					 * @param listener The {@link ProgressRBCWrapperListener} which will track progress
					 * @param expectedSize The expected total size in bytes to be read
					 */
					public ProgressReadableByteChannelWrapper(
							ReadableByteChannel byteChannel, ProgressRBCWrapperListener listener, long expectedSize){
						this.byteChannel = byteChannel;
						this.listener = listener;
						this.expectedSize = expectedSize;
					}
				\t
					/**
					 * Closes the underlying {@link #byteChannel}
					 *\s
					 * @throws IOException If anything goes wrong in closing the byte channel
					 */
					@Override
					public void close() throws IOException{
						byteChannel.close();
					}
				\t
					/**
					 * @return Whether the underlying {@link #byteChannel} is open or not
					 */
					@Override
					public boolean isOpen(){
						return byteChannel.isOpen();
					}
				\t
					/**
					 * Reads bytes from the underlying {@link #byteChannel} into the given {@link ByteBuffer} and
					 * will send a progress update to the {@link #listener} that is keeping track of progress
					 *\s
					 * @param bb The {@link ByteBuffer} to be read into
					 * @return The number of bytes read, possibly 0 or -1 if we're at the end of stream
					 * @throws IOException If anything goes wrong in reading bytes
					 */
					@Override
					public int read(ByteBuffer bb) throws IOException{
						int n;
						double progress;
					\t
						if((n = byteChannel.read(bb)) > 0 && listener != null){
							readSoFar += n;
							progress = expectedSize > 0 ? (double) readSoFar/(double) expectedSize * 100.0:-1.0;
							listener.progressUpdate(progress, readSoFar, expectedSize);
						}
					\t
						return n;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.download")
				.importName("java.io.IOException", false)
				.importName("java.nio.ByteBuffer", false)
				.importName("java.nio.channels.ReadableByteChannel", false)
				.javadoc(EditableJavadoc.builder()
						.content("Progress Readable Byte Channel Wrapper is a wrapper around {@link ReadableByteChannel} that will send")
						.content("progress updates to a {@link ProgressRBCWrapperListener}, which is keeping track of progress.")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.5")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("ProgressReadableByteChannelWrapper")
				.implementsInterfaceName("ReadableByteChannel")
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The underlying {@link ReadableByteChannel} to use for reading")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("ReadableByteChannel").name("byteChannel")
						.build())
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The {@link ProgressRBCWrapperListener} that is keeping track of progress")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("ProgressRBCWrapperListener").name("listener")
						.build())
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The expected total size in bytes to be read")
								.build())
						.visibility(Visibility.PRIVATE)
						.isFinal()
						.type("long").name("expectedSize")
						.build())
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("The amount of bytes read so far")
								.build())
						.visibility(Visibility.PRIVATE)
						.type("long").name("readSoFar")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Wraps the given {@link ReadableByteChannel} so we can send progress updates to the given")
								.content("{@link ProgressRBCWrapperListener}, which is keeping track of progress")
								.param("byteChannel", "The underlying {@link ReadableByteChannel} to be wrapped")
								.param("listener", "The {@link ProgressRBCWrapperListener} which will track progress")
								.param("expectedSize", "The expected total size in bytes to be read")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("ProgressReadableByteChannelWrapper")
						.parameter("ReadableByteChannel byteChannel")
						.parameter("ProgressRBCWrapperListener listener")
						.parameter("long expectedSize")
						.line("this.byteChannel = byteChannel;")
						.line("this.listener = listener;")
						.line("this.expectedSize = expectedSize;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Closes the underlying {@link #byteChannel}")
								.throwsInfo("IOException", "If anything goes wrong in closing the byte channel")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Override")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("void")
						.name("close")
						.throwType("IOException")
						.line("byteChannel.close();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.returnVal("Whether the underlying {@link #byteChannel} is open or not")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Override")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("boolean")
						.name("isOpen")
						.line("return byteChannel.isOpen();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Reads bytes from the underlying {@link #byteChannel} into the given {@link ByteBuffer} and")
								.content("will send a progress update to the {@link #listener} that is keeping track of progress")
								.param("bb", "The {@link ByteBuffer} to be read into")
								.returnVal("The number of bytes read, possibly 0 or -1 if we're at the end of stream")
								.throwsInfo("IOException", "If anything goes wrong in reading bytes")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Override")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("int")
						.name("read")
						.parameter("ByteBuffer bb")
						.throwType("IOException")
						.line("int n;")
						.line("double progress;")
						.line("")
						.line("if((n = byteChannel.read(bb)) > 0 && listener != null){")
						.line("\treadSoFar += n;")
						.line("\tprogress = expectedSize > 0 ? (double) readSoFar/(double) expectedSize * 100.0:-1.0;")
						.line("\tlistener.progressUpdate(progress, readSoFar, expectedSize);")
						.line("}")
						.line("")
						.line("return n;")
						.build())
				.build());
	}
}
