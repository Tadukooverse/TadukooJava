package com.github.tadukoo.java.testing.util.download;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class DownloadUtilTest extends JavaClassParsingTest{
	
	public DownloadUtilTest(){
		super("""
				package com.github.tadukoo.util.download;
				
				import com.github.tadukoo.util.logger.EasyLogger;
				
				import java.io.File;
				import java.io.FileOutputStream;
				import java.io.IOException;
				import java.net.HttpURLConnection;
				import java.net.URL;
				import java.nio.channels.Channels;
				import java.nio.channels.FileChannel;
				import java.nio.channels.ReadableByteChannel;
				
				/**
				 * Download Util contains utilities used for downloading files
				 *\s
				 * @author Logan Ferree
				 * @version Beta v.0.5.2
				 */
				public class DownloadUtil{
				\t
					/** Not allowed to instantiate DownloadUtil */
					private DownloadUtil(){ }
				\t
					/**
					 * Attempts to grab the file size for the file at the given {@link URL}. If it fails, a warning
					 * will be logged, but it will not error out and return -1
					 *\s
					 * @param logger The {@link EasyLogger logger} to use for logging if we fail
					 * @param url The {@link URL} the file is located at
					 * @return The size of the file in bytes, or -1 if we fail to retrieve it
					 */
					public static int getFileSize(EasyLogger logger, URL url){
						// Set file size default to -1 (so we can at least return something if it fails)
						int fileLength = -1;
					\t
						try{
							// Follow redirects for grabbing file size (set in case something else set it to false)
							HttpURLConnection.setFollowRedirects(true);
						\t
							// Setup a connection to get the HEAD for the file
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setRequestMethod("HEAD");
						\t
							// Grab the file size from the connection
							fileLength = connection.getContentLength();
						}catch(Exception e){
							// Log warning that we couldn't get file size + notify user
							logger.logWarning("Failed to get file size at url: " + url.getPath(), e);
						}
					\t
						return fileLength;
					}
				\t
					/**
					 * If the file already exists at the given filepath, nothing happens. Otherwise, we download it from the
					 * given address, and progress will be updated by sending this as a {@link ProgressRBCWrapperListener} to
					 * the {@link ProgressReadableByteChannelWrapper} we use
					 *\s
					 * @param logger The {@link EasyLogger logger} to use for logging
					 * @param listener The {@link ProgressRBCWrapperListener progress listener} to send updates to
					 * @param address The URL for the file to be downloaded
					 * @param filepath The local filepath for the file
					 * @throws IOException If anything goes wrong in downloading the file
					 */
					public static void downloadFile(
							EasyLogger logger, ProgressRBCWrapperListener listener, String address, String filepath) throws IOException{
						// Check if file already exists so we don't need to download it
						File file = new File(filepath);
						if(file.exists()){
							return;
						}
					\t
						// Setup the download, including setting this as the listener for progress updates
						URL url = new URL(address);
						// Follow redirects for the file (set in case something else sets it to false)
						HttpURLConnection.setFollowRedirects(true);
						ReadableByteChannel fileDownload = new ProgressReadableByteChannelWrapper(
						Channels.newChannel(url.openStream()), listener, getFileSize(logger, url));
					\t
						// Perform the file transfer from the URL to our local filepath
						FileOutputStream fileOutputStream = new FileOutputStream(filepath);
						FileChannel fileChannel = fileOutputStream.getChannel();
						fileChannel.transferFrom(fileDownload, 0, Long.MAX_VALUE);
					\t
						// Close the file channels and output stream now that we're done
						fileDownload.close();
						fileChannel.close();
						fileOutputStream.close();
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.download")
				.importName("com.github.tadukoo.util.logger.EasyLogger", false)
				.importName("java.io.File", false)
				.importName("java.io.FileOutputStream", false)
				.importName("java.io.IOException", false)
				.importName("java.net.HttpURLConnection", false)
				.importName("java.net.URL", false)
				.importName("java.nio.channels.Channels", false)
				.importName("java.nio.channels.FileChannel", false)
				.importName("java.nio.channels.ReadableByteChannel", false)
				.javadoc(EditableJavadoc.builder()
						.content("Download Util contains utilities used for downloading files")
						.author("Logan Ferree")
						.version("Beta v.0.5.2")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("DownloadUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to instantiate DownloadUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("DownloadUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Attempts to grab the file size for the file at the given {@link URL}. If it fails, a warning")
								.content("will be logged, but it will not error out and return -1")
								.param("logger", "The {@link EasyLogger logger} to use for logging if we fail")
								.param("url", "The {@link URL} the file is located at")
								.returnVal("The size of the file in bytes, or -1 if we fail to retrieve it")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("int")
						.name("getFileSize")
						.parameter("EasyLogger", "logger")
						.parameter("URL", "url")
						.line("// Set file size default to -1 (so we can at least return something if it fails)")
						.line("int fileLength = -1;")
						.line("")
						.line("try{")
						.line("\t// Follow redirects for grabbing file size (set in case something else set it to false)")
						.line("\tHttpURLConnection.setFollowRedirects(true);")
						.line("\t")
						.line("\t// Setup a connection to get the HEAD for the file")
						.line("\tHttpURLConnection connection = (HttpURLConnection) url.openConnection();")
						.line("\tconnection.setRequestMethod(\"HEAD\");")
						.line("\t")
						.line("\t// Grab the file size from the connection")
						.line("\tfileLength = connection.getContentLength();")
						.line("}catch(Exception e){")
						.line("\t// Log warning that we couldn't get file size + notify user")
						.line("\tlogger.logWarning(\"Failed to get file size at url: \" + url.getPath(), e);")
						.line("}")
						.line("")
						.line("return fileLength;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("If the file already exists at the given filepath, nothing happens. Otherwise, we download it from the")
								.content("given address, and progress will be updated by sending this as a {@link ProgressRBCWrapperListener} to")
								.content("the {@link ProgressReadableByteChannelWrapper} we use")
								.param("logger", "The {@link EasyLogger logger} to use for logging")
								.param("listener", "The {@link ProgressRBCWrapperListener progress listener} to send updates to")
								.param("address", "The URL for the file to be downloaded")
								.param("filepath", "The local filepath for the file")
								.throwsInfo("IOException", "If anything goes wrong in downloading the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("downloadFile")
						.parameter("EasyLogger", "logger")
						.parameter("ProgressRBCWrapperListener", "listener")
						.parameter("String", "address")
						.parameter("String", "filepath")
						.throwType("IOException")
						.line("// Check if file already exists so we don't need to download it")
						.line("File file = new File(filepath);")
						.line("if(file.exists()){")
						.line("\treturn;")
						.line("}")
						.line("")
						.line("// Setup the download, including setting this as the listener for progress updates")
						.line("URL url = new URL(address);")
						.line("// Follow redirects for the file (set in case something else sets it to false)")
						.line("HttpURLConnection.setFollowRedirects(true);")
						.line("ReadableByteChannel fileDownload = new ProgressReadableByteChannelWrapper(")
						.line("Channels.newChannel(url.openStream()), listener, getFileSize(logger, url));")
						.line("")
						.line("// Perform the file transfer from the URL to our local filepath")
						.line("FileOutputStream fileOutputStream = new FileOutputStream(filepath);")
						.line("FileChannel fileChannel = fileOutputStream.getChannel();")
						.line("fileChannel.transferFrom(fileDownload, 0, Long.MAX_VALUE);")
						.line("")
						.line("// Close the file channels and output stream now that we're done")
						.line("fileDownload.close();")
						.line("fileChannel.close();")
						.line("fileOutputStream.close();")
						.build())
				.build());
	}
}
