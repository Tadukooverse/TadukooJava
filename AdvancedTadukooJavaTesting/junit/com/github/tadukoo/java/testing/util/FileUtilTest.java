package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class FileUtilTest extends JavaClassParsingTest{
	
	public FileUtilTest(){
		super("""
				package com.github.tadukoo.util;
				
				import java.io.BufferedReader;
				import java.io.BufferedWriter;
				import java.io.File;
				import java.io.FileInputStream;
				import java.io.FileNotFoundException;
				import java.io.FileOutputStream;
				import java.io.FileReader;
				import java.io.FileWriter;
				import java.io.IOException;
				import java.io.Reader;
				import java.io.Writer;
				import java.nio.file.Files;
				import java.nio.file.Path;
				import java.nio.file.Paths;
				import java.util.ArrayList;
				import java.util.Collection;
				import java.util.Comparator;
				import java.util.List;
				import java.util.stream.Collectors;
				import java.util.stream.Stream;
				import java.util.zip.ZipEntry;
				import java.util.zip.ZipInputStream;
				import java.util.zip.ZipOutputStream;
				
				/**
				 * Util functions for dealing with Files.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.5.2
				 * @since Pre-Alpha
				 */
				public final class FileUtil{
				\t
					/** Not allowed to create a FileUtil */
					private FileUtil(){ }
				\t
					/**
					 * Finds the file extension for the given filepath, if one exists.
					 *\s
					 * @param filepath The filepath to grab the extension for
					 * @return The found file extension, or null
					 */
					public static String getFileExtension(String filepath){
						// Find the last dot in the filepath
						int lastDotIndex = filepath.lastIndexOf('.');
					\t
						// If one isn't found, return null
						if(lastDotIndex == -1){
							return null;
						}else{
							// If we found a dot, return whatever's after it
							return filepath.substring(lastDotIndex + 1);
						}
					}
				\t
					/**
					 * Creates a List of all Files in the given directory and any of
					 * its subdirectories.
					 *\s
					 * @param directoryPath The path to the directory to check
					 * @return A List of all Files in the directory and its subdirectories
					 * @throws IOException If something goes wrong in listing the files
					 */
					public static List<File> listAllFiles(Path directoryPath) throws IOException{
						try(Stream<Path> pathStream = Files.walk(directoryPath)){
							return pathStream
									.filter(Files::isRegularFile)
									.map(Path::toFile)
									.collect(Collectors.toList());
						}
					}
				\t
					/**
					 * Creates a List of all Files in the given directory and any of its
					 * subdirectories.
					 *\s
					 * @param directory The directory (as a File) to check
					 * @return A List of all Files in the directory and its subdirectories
					 * @throws IOException If something goes wrong in listing the files
					 */
					public static List<File> listAllFiles(File directory) throws IOException{
						return listAllFiles(directory.toPath());
					}
				\t
					/**
					 * Creates a List of all Files in the given directory and any of its
					 * subdirectories.
					 *\s
					 * @param directory The directory path (as a String) to check
					 * @return A List of all Files in the directory and its subdirectories
					 * @throws IOException If something goes wrong in listing the files
					 */
					public static List<File> listAllFiles(String directory) throws IOException{
						return listAllFiles(Paths.get(directory));
					}
				\t
					/**
					 * Creates a List of all files in the given directory and any of
					 * its subdirectories. The files are returned as Paths.
					 *\s
					 * @param directory The path to the directory to check
					 * @return A List of all files in the directory and its subdirectories as Paths
					 * @throws IOException If something goes wrong in listing the files
					 */
					public static List<Path> listAllFilesAsPaths(Path directory) throws IOException{
						try(Stream<Path> pathStream = Files.walk(directory)){
							return pathStream
									.filter(Files::isRegularFile)
									.collect(Collectors.toList());
						}
					}
				\t
					/**
					 * Creates a List of all files in the given directory and any of its
					 * subdirectories. The files are returned as Paths.
					 *\s
					 * @param directory The directory (as a File) to check
					 * @return A List of all files in the directory and its subdirectories as Paths
					 * @throws IOException If something goes wrong in listing the files
					 */
					public static List<Path> listAllFilesAsPaths(File directory) throws IOException{
						return listAllFilesAsPaths(directory.toPath());
					}
				\t
					/**
					 * Creates a List of all files in the given directory and any of its
					 * subdirectories. The files are returned as Paths.
					 *\s
					 * @param directory The directory path (as a String) to check
					 * @return A List of all files in the directory and its subdirectories as Paths
					 * @throws IOException If something goes wrong in listing the files
					 */
					public static List<Path> listAllFilesAsPaths(String directory) throws IOException{
						return listAllFilesAsPaths(Paths.get(directory));
					}
				\t
					/**
					 * Creates a file at the given filepath, including any directories necessary,
					 * and returns the {@link File} object to be used.
					 *\s
					 * @param filepath The path for the File to be created
					 * @return The newly created File
					 * @throws IOException If something goes wrong in creating the file
					 */
					@SuppressWarnings("ResultOfMethodCallIgnored")
					public static File createFile(String filepath) throws IOException{
						// Create a File object from the given filepath
						File file = new File(filepath);
					\t
						// If a directory is specified, create it if it doesn't exist
						File parentFile = file.getParentFile();
						if(parentFile != null && !parentFile.exists()){
							file.getParentFile().mkdirs();
						}
					\t
						// If the file doesn't exist, create it
						if(!file.exists()){
							file.createNewFile();
						}
					\t
						return file;
					}
				\t
					/**
					 * Deletes the file at the given filepath
					 *\s
					 * @param filepath The path for the File to be deleted
					 * @return If the file was deleted or not
					 */
					public static boolean deleteFile(String filepath){
						// Create the File object from the given filepath
						File file = new File(filepath);
					\t
						return file.delete();
					}
				\t
					/**
					 * Creates a directory at the given directoryPath, including any parent directories
					 * necessary, and returns the {@link File} object to be used.
					 *\s
					 * @param directoryPath The path for the directory to be created
					 * @return The newly created directory
					 */
					public static File createDirectory(String directoryPath){
						// Create a File object from the given filepath
						File directory = new File(directoryPath);
					\t
						// Make the directory and its parent folders
						if(!directory.mkdirs() && !directory.exists()){
							throw new IllegalStateException("Failed to create folder: " + directoryPath + "!");
						}
					\t
						return directory;
					}
				\t
					/**
					 * Deletes the directory at the given directoryPath, including any files contained
					 * within it.
					 *\s
					 * @param directoryPath The path to the directory to be deleted
					 * @throws IOException If anything goes wrong
					 */
					public static void deleteDirectory(String directoryPath) throws IOException{
						try(Stream<Path> pathStream = Files.walk(Path.of(directoryPath))){
							//noinspection ResultOfMethodCallIgnored
							pathStream
									.sorted(Comparator.reverseOrder())
									.map(Path::toFile)
									.forEach(File::delete);
						}
					}
				\t
					/**
					 * Creates a new {@link BufferedReader} for the file at the given filepath.
					 *\s
					 * @param filepath The path of the file to be read
					 * @return A {@link BufferedReader} for the given file
					 * @throws FileNotFoundException If the file can't be found
					 */
					public static BufferedReader setupFileReader(String filepath) throws FileNotFoundException{
						return new BufferedReader(new FileReader(filepath));
					}
				\t
					/**
					 * Creates a new {@link BufferedReader} for the given {@link File}.
					 *\s
					 * @param file The {@link File} to be read
					 * @return A {@link BufferedReader} for the given {@link File}
					 * @throws FileNotFoundException If the file can't be found
					 */
					public static BufferedReader setupFileReader(File file) throws FileNotFoundException{
						return new BufferedReader(new FileReader(file));
					}
				\t
					/**
					 * Checks if the file at the given filepath exists.
					 *\s
					 * @param filepath The path of the file to be checked
					 * @return true if the file exists, false if it doesn't
					 */
					public static boolean exists(String filepath){
						return Files.exists(Paths.get(filepath));
					}
				\t
					/**
					 * Checks if the given {@link File} exists.
					 *\s
					 * @param file The {@link File} to be checked
					 * @return true if the file exists, false if it doesn't
					 */
					public static boolean exists(File file){
						return Files.exists(file.toPath());
					}
				\t
					/**
					 * Checks if the file at the given filepath does not exist.
					 *\s
					 * @param filepath The path of the file to be checked
					 * @return false if the file exists, true if it doesn't
					 */
					public static boolean notExists(String filepath){
						return Files.notExists(Paths.get(filepath));
					}
				\t
					/**
					 * Checks if the given {@link File} does not exist.
					 *\s
					 * @param file The {@link File} to be checked
					 * @return false if the file exists, true if it doesn't
					 */
					public static boolean notExists(File file){
						return Files.notExists(file.toPath());
					}
				\t
					/**
					 * Reads the file at the given filepath as a String
					 *\s
					 * @param filepath The path of the file to be read
					 * @return A String representing the contents of the file
					 * @throws IOException If something goes wrong in reading the file
					 */
					public static String readAsString(String filepath) throws IOException{
						return Files.readString(Paths.get(filepath));
					}
				\t
					/**
					 * Reads the given {@link File} as a String
					 *\s
					 * @param file The {@link File} to be read
					 * @return A String representing the contents of the file
					 * @throws IOException If something goes wrong in reading the file
					 */
					public static String readAsString(File file) throws IOException{
						return Files.readString(file.toPath());
					}
				\t
					/**
					 * Creates a List of Strings for each line in the file being read from the given filepath.
					 *\s
					 * @param filepath The path to the file to be read
					 * @return A List of lines in the file
					 * @throws IOException If something goes wrong in reading the file
					 */
					public static List<String> readLinesAsList(String filepath) throws IOException{
						return Files.readAllLines(Paths.get(filepath));
					}
				\t
					/**
					 * Creates a List of Strings for each line in the file being read.
					 *\s
					 * @param file The {@link File} to read
					 * @return A List of lines in the file
					 * @throws IOException If something goes wrong in reading the file
					 */
					public static List<String> readLinesAsList(File file) throws IOException{
						return Files.readAllLines(file.toPath());
					}
				\t
					/**
					 * Creates a List of Strings for each line in the file being read in the
					 * given {@link Reader}.
					 *\s
					 * @param reader The Reader to use in reading
					 * @return A List of lines in the file
					 * @throws IOException If something goes wrong in reading the file
					 */
					public static List<String> readLinesAsList(Reader reader) throws IOException{
						// Make a BufferedReader out of the given Reader
						BufferedReader buffReader = new BufferedReader(reader);
						// Create a List of Strings to store the lines
						List<String> lines = new ArrayList<>();
					\t
						// Read the first line of the file
						String line = buffReader.readLine();
					\t
						// Continue to read lines until there are no more
						while(line != null){
							// Add the line to the list
							lines.add(line);
							// Grab the next line
							line = buffReader.readLine();
						}
						return lines;
					}
				\t
					/**
					 * Reads the file at the given filepath into a byte array.
					 *\s
					 * @param filepath The path of the file to be read
					 * @return The byte array of the given file
					 * @throws IOException If something goes wrong in reading the file
					 */
					public static byte[] readAsBytes(String filepath) throws IOException{
						return Files.readAllBytes(Paths.get(filepath));
					}
				\t
					/**
					 * Reads the given file into a byte array.
					 *\s
					 * @param file The {@link File} to be read
					 * @return The byte array of the given file
					 * @throws IOException If something goes wrong in reading the file
					 */
					public static byte[] readAsBytes(File file) throws IOException{
						return Files.readAllBytes(file.toPath());
					}
				\t
					/**
					 * Writes the given string to the file given by the filepath.
					 * Will create the file and its directories if they don't exist.
					 *\s
					 * @param filepath The path to save the file to
					 * @param content The content of the file to be written
					 * @throws IOException If something goes wrong in writing the file
					 */
					public static void writeFile(String filepath, String content) throws IOException{
						// Create the File
						File file = createFile(filepath);
					\t
						// Actually write to the file using a FileWriter
						writeFile(new FileWriter(file), content);
					}
				\t
					/**
					 * Writes the given string to the file given in the {@link Writer}.
					 *\s
					 * @param writer The Writer to use in writing
					 * @param content The content of the file to be written
					 * @throws IOException If something goes wrong in writing the file
					 */
					public static void writeFile(Writer writer, String content) throws IOException{
						// Make a BufferedWriter out of the given Writer
						BufferedWriter buffWriter = new BufferedWriter(writer);
					\t
						// Write the content to the file
						buffWriter.write(content);
					\t
						// flush and close the writer
						buffWriter.flush();
						buffWriter.close();
					}
				\t
					/**
					 * Writes the given lines to the file given in the {@link Writer}.
					 *\s
					 * @param writer The Writer to use in writing
					 * @param lines The content of the file to be written
					 * @throws IOException If something goes wrong in writing the file
					 */
					public static void writeFile(Writer writer, Collection<String> lines) throws IOException{
						writeFile(writer, StringUtil.buildStringWithNewLines(lines));
					}
				\t
					/**
					 * Writes the given byte array to the file path given
					 *\s
					 * @param filePath The path to write the file at
					 * @param bytes The byte array contents of the file
					 * @throws IOException If something goes wrong in writing the file
					 */
					public static void writeFile(String filePath, byte[] bytes) throws IOException{
						// Create the File
						File file = createFile(filePath);
					\t
						// Actually write to the file using a FileOutputStream
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(bytes);
						fos.close();
					}
				\t
					/**
					 * Creates a zip file using the file or directory at the given path.
					 *\s
					 * @param pathToZip The path to the file or directory to be zipped
					 * @param zipPath The path (and name) of the zip file to be created
					 * @throws IOException If something goes wrong in zipping the files
					 */
					public static void zipFile(String pathToZip, String zipPath) throws IOException{
						zipFile(new File(pathToZip), zipPath);
					}
				\t
					/**
					 * Creates a zip file using the given File (can be a file or directory).
					 *\s
					 * @param fileToZip The File to be zipped (can be file or directory)
					 * @param zipPath The path (and name) of the zip file to be created
					 * @throws IOException If something goes wrong in zipping the file
					 */
					public static void zipFile(File fileToZip, String zipPath) throws IOException{
						// Setup
						FileOutputStream fos = new FileOutputStream(zipPath);
						ZipOutputStream zos = new ZipOutputStream(fos);
					\t
						// Call helper method (which handles if it's a directory)
						zipFile(fileToZip, fileToZip.getName(), zos);
						zos.close();
						fos.close();
					}
				\t
					/**
					 * Creates a zip file using the given File (can be a file or directory), fileName for the file to be zipped,
					 * and a ZipOutputStream to be used. Each call to this method creates a single entry in the zip file, and
					 * if it's a directory, it will make recursive calls for all children of the directory.
					 *\s
					 * @param fileToZip The File to be zipped (can be file or directory)
					 * @param fileName The name of the file to be zipped
					 * @param zos The ZipOutputStream to be used in zipping
					 * @throws IOException If something goes wrong in zipping the file
					 */
					private static void zipFile(File fileToZip, String fileName, ZipOutputStream zos) throws IOException{
						if(fileToZip.isHidden()){
							return;
						}
					\t
						// Handle directories differently
						if(fileToZip.isDirectory()){
							// If we're missing the ending /, add it
							if(!fileName.endsWith("/")){
								fileName += "/";
							}
						\t
							// Add the directory as a zip entry
							zos.putNextEntry(new ZipEntry(fileName));
							zos.closeEntry();
						\t
							// Grab the children of the directory
							File[] children = fileToZip.listFiles();
							if(children == null){
								return;
							}
						\t
							// Add all the children to the zip
							for(File childFile: children){
								zipFile(childFile, fileName + "/" + childFile.getName(), zos);
							}
							return;
						}
					\t
						// If it's not a directory, just zip the single file
						FileInputStream fis = new FileInputStream(fileToZip);
						ZipEntry zipEntry = new ZipEntry(fileName);
						zos.putNextEntry(zipEntry);
						byte[] bytes = new byte[1024];
						int length;
						while((length = fis.read(bytes)) >= 0){
							zos.write(bytes, 0, length);
						}
						fis.close();
					}
				\t
					/**
					 * Unzips a zip file into the given destination path.
					 *\s
					 * @param zipPath The path to the zip file
					 * @param destinationPath The path to extract the zip file contents to
					 * @throws IOException If something goes wrong in unzipping the file
					 */
					public static void unzipFile(String zipPath, String destinationPath) throws IOException{
						unzipFile(zipPath, new File(destinationPath));
					}
				\t
					/**
					 * Unzips a zip file into the given destination directory.
					 *\s
					 * @param zipPath The path to the zip file
					 * @param destDirectory The directory to extract the zip file contents to
					 * @throws IOException If something goes wrong in unzipping the file
					 */
					public static void unzipFile(String zipPath, File destDirectory) throws IOException{
						// Setup
						byte[] buffer = new byte[1024];
						ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
						ZipEntry zipEntry = zis.getNextEntry();
					\t
						// Extract each file
						while(zipEntry != null){
							File newFile = new File(destDirectory, zipEntry.getName());
						\t
							// Check destination path to prevent zip slip
							String destDirPath = destDirectory.getCanonicalPath();
							String destFilePath = newFile.getCanonicalPath();
							if(!destFilePath.startsWith(destDirPath + File.separator)){
								throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
							}
						\t
							// If it's a directory, make the directory instead of a file
							if(zipEntry.isDirectory()){
								if(!newFile.mkdirs() && !newFile.exists()){
									throw new IOException("Failed to create new directory at " + newFile.getAbsolutePath());
								}
							}else{
								// Write the new file
								FileOutputStream fos = new FileOutputStream(newFile);
								int length;
								while((length = zis.read(buffer)) > 0){
									fos.write(buffer, 0, length);
								}
							\t
								// Close the new file and prepare for next one
								fos.close();
							}
							zipEntry = zis.getNextEntry();
						}
						zis.closeEntry();
						zis.close();
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util")
				.importName("java.io.BufferedReader", false)
				.importName("java.io.BufferedWriter", false)
				.importName("java.io.File", false)
				.importName("java.io.FileInputStream", false)
				.importName("java.io.FileNotFoundException", false)
				.importName("java.io.FileOutputStream", false)
				.importName("java.io.FileReader", false)
				.importName("java.io.FileWriter", false)
				.importName("java.io.IOException", false)
				.importName("java.io.Reader", false)
				.importName("java.io.Writer", false)
				.importName("java.nio.file.Files", false)
				.importName("java.nio.file.Path", false)
				.importName("java.nio.file.Paths", false)
				.importName("java.util.ArrayList", false)
				.importName("java.util.Collection", false)
				.importName("java.util.Comparator", false)
				.importName("java.util.List", false)
				.importName("java.util.stream.Collectors", false)
				.importName("java.util.stream.Stream", false)
				.importName("java.util.zip.ZipEntry", false)
				.importName("java.util.zip.ZipInputStream", false)
				.importName("java.util.zip.ZipOutputStream", false)
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with Files.")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.5.2")
						.since("Pre-Alpha")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("FileUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to create a FileUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("FileUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Finds the file extension for the given filepath, if one exists.")
								.param("filepath", "The filepath to grab the extension for")
								.returnVal("The found file extension, or null")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String")
						.name("getFileExtension")
						.parameter("String filepath")
						.line("// Find the last dot in the filepath")
						.line("int lastDotIndex = filepath.lastIndexOf('.');")
						.line("")
						.line("// If one isn't found, return null")
						.line("if(lastDotIndex == -1){")
						.line("\treturn null;")
						.line("}else{")
						.line("\t// If we found a dot, return whatever's after it")
						.line("\treturn filepath.substring(lastDotIndex + 1);")
						.line("}")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of all Files in the given directory and any of")
								.content("its subdirectories.")
								.param("directoryPath", "The path to the directory to check")
								.returnVal("A List of all Files in the directory and its subdirectories")
								.throwsInfo("IOException", "If something goes wrong in listing the files")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<File>")
						.name("listAllFiles")
						.parameter("Path directoryPath")
						.throwType("IOException")
						.line("try(Stream<Path> pathStream = Files.walk(directoryPath)){")
						.line("\treturn pathStream")
						.line("\t\t\t.filter(Files::isRegularFile)")
						.line("\t\t\t.map(Path::toFile)")
						.line("\t\t\t.collect(Collectors.toList());")
						.line("}")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of all Files in the given directory and any of its")
								.content("subdirectories.")
								.param("directory", "The directory (as a File) to check")
								.returnVal("A List of all Files in the directory and its subdirectories")
								.throwsInfo("IOException", "If something goes wrong in listing the files")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<File>")
						.name("listAllFiles")
						.parameter("File directory")
						.throwType("IOException")
						.line("return listAllFiles(directory.toPath());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of all Files in the given directory and any of its")
								.content("subdirectories.")
								.param("directory", "The directory path (as a String) to check")
								.returnVal("A List of all Files in the directory and its subdirectories")
								.throwsInfo("IOException", "If something goes wrong in listing the files")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<File>")
						.name("listAllFiles")
						.parameter("String directory")
						.throwType("IOException")
						.line("return listAllFiles(Paths.get(directory));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of all files in the given directory and any of")
								.content("its subdirectories. The files are returned as Paths.")
								.param("directory", "The path to the directory to check")
								.returnVal("A List of all files in the directory and its subdirectories as Paths")
								.throwsInfo("IOException", "If something goes wrong in listing the files")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<Path>")
						.name("listAllFilesAsPaths")
						.parameter("Path directory")
						.throwType("IOException")
						.line("try(Stream<Path> pathStream = Files.walk(directory)){")
						.line("\treturn pathStream")
						.line("\t\t\t.filter(Files::isRegularFile)")
						.line("\t\t\t.collect(Collectors.toList());")
						.line("}")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of all files in the given directory and any of its")
								.content("subdirectories. The files are returned as Paths.")
								.param("directory", "The directory (as a File) to check")
								.returnVal("A List of all files in the directory and its subdirectories as Paths")
								.throwsInfo("IOException", "If something goes wrong in listing the files")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<Path>")
						.name("listAllFilesAsPaths")
						.parameter("File directory")
						.throwType("IOException")
						.line("return listAllFilesAsPaths(directory.toPath());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of all files in the given directory and any of its")
								.content("subdirectories. The files are returned as Paths.")
								.param("directory", "The directory path (as a String) to check")
								.returnVal("A List of all files in the directory and its subdirectories as Paths")
								.throwsInfo("IOException", "If something goes wrong in listing the files")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<Path>")
						.name("listAllFilesAsPaths")
						.parameter("String directory")
						.throwType("IOException")
						.line("return listAllFilesAsPaths(Paths.get(directory));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a file at the given filepath, including any directories necessary,")
								.content("and returns the {@link File} object to be used.")
								.param("filepath", "The path for the File to be created")
								.returnVal("The newly created File")
								.throwsInfo("IOException", "If something goes wrong in creating the file")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("SuppressWarnings")
								.parameter("value", "\"ResultOfMethodCallIgnored\"")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("File")
						.name("createFile")
						.parameter("String filepath")
						.throwType("IOException")
						.line("// Create a File object from the given filepath")
						.line("File file = new File(filepath);")
						.line("")
						.line("// If a directory is specified, create it if it doesn't exist")
						.line("File parentFile = file.getParentFile();")
						.line("if(parentFile != null && !parentFile.exists()){")
						.line("\tfile.getParentFile().mkdirs();")
						.line("}")
						.line("")
						.line("// If the file doesn't exist, create it")
						.line("if(!file.exists()){")
						.line("\tfile.createNewFile();")
						.line("}")
						.line("")
						.line("return file;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Deletes the file at the given filepath")
								.param("filepath", "The path for the File to be deleted")
								.returnVal("If the file was deleted or not")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("deleteFile")
						.parameter("String filepath")
						.line("// Create the File object from the given filepath")
						.line("File file = new File(filepath);")
						.line("")
						.line("return file.delete();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a directory at the given directoryPath, including any parent directories")
								.content("necessary, and returns the {@link File} object to be used.")
								.param("directoryPath", "The path for the directory to be created")
								.returnVal("The newly created directory")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("File")
						.name("createDirectory")
						.parameter("String directoryPath")
						.line("// Create a File object from the given filepath")
						.line("File directory = new File(directoryPath);")
						.line("")
						.line("// Make the directory and its parent folders")
						.line("if(!directory.mkdirs() && !directory.exists()){")
						.line("\tthrow new IllegalStateException(\"Failed to create folder: \" + directoryPath + \"!\");")
						.line("}")
						.line("")
						.line("return directory;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Deletes the directory at the given directoryPath, including any files contained")
								.content("within it.")
								.param("directoryPath", "The path to the directory to be deleted")
								.throwsInfo("IOException", "If anything goes wrong")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("deleteDirectory")
						.parameter("String directoryPath")
						.throwType("IOException")
						.line("try(Stream<Path> pathStream = Files.walk(Path.of(directoryPath))){")
						.line("\t//noinspection ResultOfMethodCallIgnored")
						.line("\tpathStream")
						.line("\t\t\t.sorted(Comparator.reverseOrder())")
						.line("\t\t\t.map(Path::toFile)")
						.line("\t\t\t.forEach(File::delete);")
						.line("}")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a new {@link BufferedReader} for the file at the given filepath.")
								.param("filepath", "The path of the file to be read")
								.returnVal("A {@link BufferedReader} for the given file")
								.throwsInfo("FileNotFoundException", "If the file can't be found")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("BufferedReader")
						.name("setupFileReader")
						.parameter("String filepath")
						.throwType("FileNotFoundException")
						.line("return new BufferedReader(new FileReader(filepath));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a new {@link BufferedReader} for the given {@link File}.")
								.param("file", "The {@link File} to be read")
								.returnVal("A {@link BufferedReader} for the given {@link File}")
								.throwsInfo("FileNotFoundException", "If the file can't be found")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("BufferedReader")
						.name("setupFileReader")
						.parameter("File file")
						.throwType("FileNotFoundException")
						.line("return new BufferedReader(new FileReader(file));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the file at the given filepath exists.")
								.param("filepath", "The path of the file to be checked")
								.returnVal("true if the file exists, false if it doesn't")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("exists")
						.parameter("String filepath")
						.line("return Files.exists(Paths.get(filepath));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the given {@link File} exists.")
								.param("file", "The {@link File} to be checked")
								.returnVal("true if the file exists, false if it doesn't")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("exists")
						.parameter("File file")
						.line("return Files.exists(file.toPath());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the file at the given filepath does not exist.")
								.param("filepath", "The path of the file to be checked")
								.returnVal("false if the file exists, true if it doesn't")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("notExists")
						.parameter("String filepath")
						.line("return Files.notExists(Paths.get(filepath));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the given {@link File} does not exist.")
								.param("file", "The {@link File} to be checked")
								.returnVal("false if the file exists, true if it doesn't")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("notExists")
						.parameter("File file")
						.line("return Files.notExists(file.toPath());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Reads the file at the given filepath as a String")
								.param("filepath", "The path of the file to be read")
								.returnVal("A String representing the contents of the file")
								.throwsInfo("IOException", "If something goes wrong in reading the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String")
						.name("readAsString")
						.parameter("String filepath")
						.throwType("IOException")
						.line("return Files.readString(Paths.get(filepath));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Reads the given {@link File} as a String")
								.param("file", "The {@link File} to be read")
								.returnVal("A String representing the contents of the file")
								.throwsInfo("IOException", "If something goes wrong in reading the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String")
						.name("readAsString")
						.parameter("File file")
						.throwType("IOException")
						.line("return Files.readString(file.toPath());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of Strings for each line in the file being read from the given filepath.")
								.param("filepath", "The path to the file to be read")
								.returnVal("A List of lines in the file")
								.throwsInfo("IOException", "If something goes wrong in reading the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<String>")
						.name("readLinesAsList")
						.parameter("String filepath")
						.throwType("IOException")
						.line("return Files.readAllLines(Paths.get(filepath));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of Strings for each line in the file being read.")
								.param("file", "The {@link File} to read")
								.returnVal("A List of lines in the file")
								.throwsInfo("IOException", "If something goes wrong in reading the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<String>")
						.name("readLinesAsList")
						.parameter("File file")
						.throwType("IOException")
						.line("return Files.readAllLines(file.toPath());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a List of Strings for each line in the file being read in the")
								.content("given {@link Reader}.")
								.param("reader", "The Reader to use in reading")
								.returnVal("A List of lines in the file")
								.throwsInfo("IOException", "If something goes wrong in reading the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("List<String>")
						.name("readLinesAsList")
						.parameter("Reader reader")
						.throwType("IOException")
						.line("// Make a BufferedReader out of the given Reader")
						.line("BufferedReader buffReader = new BufferedReader(reader);")
						.line("// Create a List of Strings to store the lines")
						.line("List<String> lines = new ArrayList<>();")
						.line("")
						.line("// Read the first line of the file")
						.line("String line = buffReader.readLine();")
						.line("")
						.line("// Continue to read lines until there are no more")
						.line("while(line != null){")
						.line("\t// Add the line to the list")
						.line("\tlines.add(line);")
						.line("\t// Grab the next line")
						.line("\tline = buffReader.readLine();")
						.line("}")
						.line("return lines;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Reads the file at the given filepath into a byte array.")
								.param("filepath", "The path of the file to be read")
								.returnVal("The byte array of the given file")
								.throwsInfo("IOException", "If something goes wrong in reading the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("byte[]")
						.name("readAsBytes")
						.parameter("String filepath")
						.throwType("IOException")
						.line("return Files.readAllBytes(Paths.get(filepath));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Reads the given file into a byte array.")
								.param("file", "The {@link File} to be read")
								.returnVal("The byte array of the given file")
								.throwsInfo("IOException", "If something goes wrong in reading the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("byte[]")
						.name("readAsBytes")
						.parameter("File file")
						.throwType("IOException")
						.line("return Files.readAllBytes(file.toPath());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Writes the given string to the file given by the filepath.")
								.content("Will create the file and its directories if they don't exist.")
								.param("filepath", "The path to save the file to")
								.param("content", "The content of the file to be written")
								.throwsInfo("IOException", "If something goes wrong in writing the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("writeFile")
						.parameter("String filepath")
						.parameter("String content")
						.throwType("IOException")
						.line("// Create the File")
						.line("File file = createFile(filepath);")
						.line("")
						.line("// Actually write to the file using a FileWriter")
						.line("writeFile(new FileWriter(file), content);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Writes the given string to the file given in the {@link Writer}.")
								.param("writer", "The Writer to use in writing")
								.param("content", "The content of the file to be written")
								.throwsInfo("IOException", "If something goes wrong in writing the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("writeFile")
						.parameter("Writer writer")
						.parameter("String content")
						.throwType("IOException")
						.line("// Make a BufferedWriter out of the given Writer")
						.line("BufferedWriter buffWriter = new BufferedWriter(writer);")
						.line("")
						.line("// Write the content to the file")
						.line("buffWriter.write(content);")
						.line("")
						.line("// flush and close the writer")
						.line("buffWriter.flush();")
						.line("buffWriter.close();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Writes the given lines to the file given in the {@link Writer}.")
								.param("writer", "The Writer to use in writing")
								.param("lines", "The content of the file to be written")
								.throwsInfo("IOException", "If something goes wrong in writing the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("writeFile")
						.parameter("Writer writer")
						.parameter("Collection<String> lines")
						.throwType("IOException")
						.line("writeFile(writer, StringUtil.buildStringWithNewLines(lines));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Writes the given byte array to the file path given")
								.param("filePath", "The path to write the file at")
								.param("bytes", "The byte array contents of the file")
								.throwsInfo("IOException", "If something goes wrong in writing the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("writeFile")
						.parameter("String filePath")
						.parameter("byte[] bytes")
						.throwType("IOException")
						.line("// Create the File")
						.line("File file = createFile(filePath);")
						.line("")
						.line("// Actually write to the file using a FileOutputStream")
						.line("FileOutputStream fos = new FileOutputStream(file);")
						.line("fos.write(bytes);")
						.line("fos.close();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a zip file using the file or directory at the given path.")
								.param("pathToZip", "The path to the file or directory to be zipped")
								.param("zipPath", "The path (and name) of the zip file to be created")
								.throwsInfo("IOException", "If something goes wrong in zipping the files")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("zipFile")
						.parameter("String pathToZip")
						.parameter("String zipPath")
						.throwType("IOException")
						.line("zipFile(new File(pathToZip), zipPath);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a zip file using the given File (can be a file or directory).")
								.param("fileToZip", "The File to be zipped (can be file or directory)")
								.param("zipPath", "The path (and name) of the zip file to be created")
								.throwsInfo("IOException", "If something goes wrong in zipping the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("zipFile")
						.parameter("File fileToZip")
						.parameter("String zipPath")
						.throwType("IOException")
						.line("// Setup")
						.line("FileOutputStream fos = new FileOutputStream(zipPath);")
						.line("ZipOutputStream zos = new ZipOutputStream(fos);")
						.line("")
						.line("// Call helper method (which handles if it's a directory)")
						.line("zipFile(fileToZip, fileToZip.getName(), zos);")
						.line("zos.close();")
						.line("fos.close();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Creates a zip file using the given File (can be a file or directory), fileName for the file to be zipped,")
								.content("and a ZipOutputStream to be used. Each call to this method creates a single entry in the zip file, and")
								.content("if it's a directory, it will make recursive calls for all children of the directory.")
								.param("fileToZip", "The File to be zipped (can be file or directory)")
								.param("fileName", "The name of the file to be zipped")
								.param("zos", "The ZipOutputStream to be used in zipping")
								.throwsInfo("IOException", "If something goes wrong in zipping the file")
								.build())
						.visibility(Visibility.PRIVATE)
						.isStatic()
						.returnType("void")
						.name("zipFile")
						.parameter("File fileToZip")
						.parameter("String fileName")
						.parameter("ZipOutputStream zos")
						.throwType("IOException")
						.line("if(fileToZip.isHidden()){")
						.line("\treturn;")
						.line("}")
						.line("")
						.line("// Handle directories differently")
						.line("if(fileToZip.isDirectory()){")
						.line("\t// If we're missing the ending /, add it")
						.line("\tif(!fileName.endsWith(\"/\")){")
						.line("\t\tfileName += \"/\";")
						.line("\t}")
						.line("\t")
						.line("\t// Add the directory as a zip entry")
						.line("\tzos.putNextEntry(new ZipEntry(fileName));")
						.line("\tzos.closeEntry();")
						.line("\t")
						.line("\t// Grab the children of the directory")
						.line("\tFile[] children = fileToZip.listFiles();")
						.line("\tif(children == null){")
						.line("\t\treturn;")
						.line("\t}")
						.line("\t")
						.line("\t// Add all the children to the zip")
						.line("\tfor(File childFile: children){")
						.line("\t\tzipFile(childFile, fileName + \"/\" + childFile.getName(), zos);")
						.line("\t}")
						.line("\treturn;")
						.line("}")
						.line("")
						.line("// If it's not a directory, just zip the single file")
						.line("FileInputStream fis = new FileInputStream(fileToZip);")
						.line("ZipEntry zipEntry = new ZipEntry(fileName);")
						.line("zos.putNextEntry(zipEntry);")
						.line("byte[] bytes = new byte[1024];")
						.line("int length;")
						.line("while((length = fis.read(bytes)) >= 0){")
						.line("\tzos.write(bytes, 0, length);")
						.line("}")
						.line("fis.close();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Unzips a zip file into the given destination path.")
								.param("zipPath", "The path to the zip file")
								.param("destinationPath", "The path to extract the zip file contents to")
								.throwsInfo("IOException", "If something goes wrong in unzipping the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("unzipFile")
						.parameter("String zipPath")
						.parameter("String destinationPath")
						.throwType("IOException")
						.line("unzipFile(zipPath, new File(destinationPath));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Unzips a zip file into the given destination directory.")
								.param("zipPath", "The path to the zip file")
								.param("destDirectory", "The directory to extract the zip file contents to")
								.throwsInfo("IOException", "If something goes wrong in unzipping the file")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("unzipFile")
						.parameter("String zipPath")
						.parameter("File destDirectory")
						.throwType("IOException")
						.line("// Setup")
						.line("byte[] buffer = new byte[1024];")
						.line("ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));")
						.line("ZipEntry zipEntry = zis.getNextEntry();")
						.line("")
						.line("// Extract each file")
						.line("while(zipEntry != null){")
						.line("\tFile newFile = new File(destDirectory, zipEntry.getName());")
						.line("\t")
						.line("\t// Check destination path to prevent zip slip")
						.line("\tString destDirPath = destDirectory.getCanonicalPath();")
						.line("\tString destFilePath = newFile.getCanonicalPath();")
						.line("\tif(!destFilePath.startsWith(destDirPath + File.separator)){")
						.line("\t\tthrow new IOException(\"Entry is outside of the target dir: \" + zipEntry.getName());")
						.line("\t}")
						.line("\t")
						.line("\t// If it's a directory, make the directory instead of a file")
						.line("\tif(zipEntry.isDirectory()){")
						.line("\t\tif(!newFile.mkdirs() && !newFile.exists()){")
						.line("\t\t\tthrow new IOException(\"Failed to create new directory at \" + newFile.getAbsolutePath());")
						.line("\t\t}")
						.line("\t}else{")
						.line("\t\t// Write the new file")
						.line("\t\tFileOutputStream fos = new FileOutputStream(newFile);")
						.line("\t\tint length;")
						.line("\t\twhile((length = zis.read(buffer)) > 0){")
						.line("\t\t\tfos.write(buffer, 0, length);")
						.line("\t\t}")
						.line("\t\t")
						.line("\t\t// Close the new file and prepare for next one")
						.line("\t\tfos.close();")
						.line("\t}")
						.line("\tzipEntry = zis.getNextEntry();")
						.line("}")
						.line("zis.closeEntry();")
						.line("zis.close();")
						.build())
				.build());
	}
}
