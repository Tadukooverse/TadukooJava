package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class ByteUtilTest extends JavaClassParsingTest{
	
	public ByteUtilTest(){
		super("""
			package com.github.tadukoo.util;
			
			/**
			 * Util functions for dealing with bytes.
			 *\s
			 * @author Logan Ferree (Tadukoo)
			 * @version Beta v.0.5
			 */
			public final class ByteUtil{
			\t
				/** An array containing 0-9 and then A-F, used for converting to hex */
				public static final char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
			\t
				/** Not allowed to create a ByteUtil */
				private ByteUtil(){ }
			\t
				/**
				 * Gets the bit at the given position
				 *\s
				 * @param bite The byte to grab a bit from
				 * @param position The position of the bit to be grabbed
				 * @return The value of the bit at the given position
				 */
				public static int getBit(byte bite, int position){
					return (bite >>> position) & 1;
				}
			\t
				/**
				 * Sets the bit at the given position (to 1/true)
				 *\s
				 * @param bite The byte to set the bit on
				 * @param position The position of the bit to be set
				 * @return A byte with the given bit set
				 */
				public static byte setBit(byte bite, int position){
					return (byte) (bite | (1 << position));
				}
			\t
				/**
				 * Clears the bit at the given position (sets to 0/false)
				 *\s
				 * @param bite The byte to clear the bit on
				 * @param position The position of the bit to be cleared
				 * @return A byte with the given bit cleared
				 */
				public static byte clearBit(byte bite, int position){
					return (byte) (bite & ~(1 << position));
				}
			\t
				/**
				 * Toggles the bit at the given position
				 *\s
				 * @param bite The byte to toggle the bit on
				 * @param position The position of the bit to be toggled
				 * @return A byte with the given bit toggled
				 */
				public static byte toggleBit(byte bite, int position){
					return (byte) (bite ^ (1 << position));
				}
			\t
				/**
				 * Checks if the bit at the given position is set or not
				 *\s
				 * @param bite The byte to check a bit from
				 * @param position The position of the bit to be checked
				 * @return true if the bit is set (equal to 1) or false if not set (equal to 0)
				 */
				public static boolean checkBit(byte bite, int position){
					return getBit(bite, position) == 1;
				}
			\t
				/**
				 * Parse the given byte into a binary string
				 *\s
				 * @param bite The byte to be converted to a binary string
				 * @return The binary string representation of the byte
				 */
				public static String toBinaryString(byte bite){
					return String.format("%8s", Integer.toBinaryString(bite & 0xFF)).replace(' ', '0');
				}
			\t
				/**
				 * Parse the given String into a byte. String should be a binary representation of a byte
				 *\s
				 * @param byteString The binary representation of a byte
				 * @return A Byte parsed from the given binary String
				 */
				public static Byte parseByte(String byteString){
					return (byte) Integer.parseInt(byteString, 2);
				}
			\t
				/**
				 * Returns the byte as an int, signed (-128 to 127)
				 *\s
				 * @param bite The byte to convert to an int
				 * @return The int value of the given byte
				 */
				public static int toSignedInt(byte bite){
					return bite;
				}
			\t
				/**
				 * Returns the byte as an unsigned int (0 to 255)
				 *\s
				 * @param bite The byte to convert to an int
				 * @return The int value of the given byte
				 */
				public static int toUnsignedInt(byte bite){
					return Byte.toUnsignedInt(bite);
				}
			\t
				/**
				 * Converts the given byte to a 2 digit hex string
				 *\s
				 * @param bite The byte to convert to hex
				 * @return The hex representation of the given byte
				 */
				public static String toHex(byte bite){
					return String.valueOf(hexChars[(bite >>> 4) & 0xF]) + hexChars[bite & 0xF];
				}
			\t
				/**
				 * Converts the given byte array into a hex string
				 *\s
				 * @param bytes The bytes to convert to hex
				 * @return The hex representation of the given bytes
				 */
				public static String toHex(byte[] bytes){
					StringBuilder hex = new StringBuilder();
					for(byte bite: bytes){
						hex.append(toHex(bite));
					}
					return hex.toString();
				}
			\t
				/**
				 * Converts the given hex character to an int
				 *\s
				 * @param hexChar The hex character to convert
				 * @return The int value of the hex character, or -1 if invalid
				 */
				public static int hexToInt(char hexChar){
					if('0' <= hexChar && hexChar <= '9'){
						return hexChar - '0';
					}else if('A' <= hexChar && hexChar <= 'F'){
						return hexChar - 'A' + 10;
					}else if('a' <= hexChar && hexChar <= 'f'){
						return hexChar - 'a' + 10;
					}else{
						return -1;
					}
				}
			\t
				/**
				 * Convert the given hex string into a byte array
				 *\s
				 * @param hex The hex string to convert
				 * @return A byte array representing the given hex string
				 */
				public static byte[] fromHex(String hex){
					int size = hex.length();
			\t\t
					// Check that the size is even
					if(size % 2 != 0){
						throw new IllegalArgumentException("hex string must be an even length: " + hex);
					}
			\t\t
					// Create byte array to store the bytes in
					byte[] bites = new byte[size/2];
			\t\t
					// Iterate over the string, 2 characters at a time
					for(int i = 0; i < size; i+=2){
						int highNibble = hexToInt(hex.charAt(i));
						int lowNibble = hexToInt(hex.charAt(i+1));
						// If either nibble came out -1, we have an illegal hex character
						if(highNibble == -1 || lowNibble == -1){
							throw new IllegalArgumentException("hex string contains an illegal hex character: " + hex);
						}
			\t\t\t
						bites[i/2] = (byte) (highNibble*16 + lowNibble);
					}
			\t\t
					return bites;
				}
			}
			""",
	EditableJavaClass.builder()
				.packageDeclaration(EditableJavaPackageDeclaration.builder()
						.packageName("com.github.tadukoo.util")
						.build())
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with bytes.")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.5")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("ByteUtil")
				.field(EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("An array containing 0-9 and then A-F, used for converting to hex")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic().isFinal()
						.type("char[]").name("hexChars")
						.value("new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to create a ByteUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("ByteUtil").name("")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Gets the bit at the given position")
								.param("bite", "The byte to grab a bit from")
								.param("position", "The position of the bit to be grabbed")
								.returnVal("The value of the bit at the given position")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("int").name("getBit")
						.parameter("byte", "bite")
						.parameter("int", "position")
						.line("return (bite >>> position) & 1;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Sets the bit at the given position (to 1/true)")
								.param("bite", "The byte to set the bit on")
								.param("position", "The position of the bit to be set")
								.returnVal("A byte with the given bit set")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("byte").name("setBit")
						.parameter("byte", "bite")
						.parameter("int", "position")
						.line("return (byte) (bite | (1 << position));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Clears the bit at the given position (sets to 0/false)")
								.param("bite", "The byte to clear the bit on")
								.param("position", "The position of the bit to be cleared")
								.returnVal("A byte with the given bit cleared")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("byte").name("clearBit")
						.parameter("byte", "bite")
						.parameter("int", "position")
						.line("return (byte) (bite & ~(1 << position));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Toggles the bit at the given position")
								.param("bite", "The byte to toggle the bit on")
								.param("position", "The position of the bit to be toggled")
								.returnVal("A byte with the given bit toggled")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("byte").name("toggleBit")
						.parameter("byte", "bite")
						.parameter("int", "position")
						.line("return (byte) (bite ^ (1 << position));")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Checks if the bit at the given position is set or not")
								.param("bite", "The byte to check a bit from")
								.param("position", "The position of the bit to be checked")
								.returnVal("true if the bit is set (equal to 1) or false if not set (equal to 0)")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean").name("checkBit")
						.parameter("byte", "bite")
						.parameter("int", "position")
						.line("return getBit(bite, position) == 1;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Parse the given byte into a binary string")
								.param("bite", "The byte to be converted to a binary string")
								.returnVal("The binary string representation of the byte")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String").name("toBinaryString")
						.parameter("byte", "bite")
						.line("return String.format(\"%8s\", Integer.toBinaryString(bite & 0xFF)).replace(' ', '0');")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Parse the given String into a byte. String should be a binary representation of a byte")
								.param("byteString", "The binary representation of a byte")
								.returnVal("A Byte parsed from the given binary String")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("Byte").name("parseByte")
						.parameter("String", "byteString")
						.line("return (byte) Integer.parseInt(byteString, 2);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Returns the byte as an int, signed (-128 to 127)")
								.param("bite", "The byte to convert to an int")
								.returnVal("The int value of the given byte")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("int").name("toSignedInt")
						.parameter("byte", "bite")
						.line("return bite;")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Returns the byte as an unsigned int (0 to 255)")
								.param("bite", "The byte to convert to an int")
								.returnVal("The int value of the given byte")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("int").name("toUnsignedInt")
						.parameter("byte", "bite")
						.line("return Byte.toUnsignedInt(bite);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Converts the given byte to a 2 digit hex string")
								.param("bite", "The byte to convert to hex")
								.returnVal("The hex representation of the given byte")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String").name("toHex")
						.parameter("byte", "bite")
						.line("return String.valueOf(hexChars[(bite >>> 4) & 0xF]) + hexChars[bite & 0xF];")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Converts the given byte array into a hex string")
								.param("bytes", "The bytes to convert to hex")
								.returnVal("The hex representation of the given bytes")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String").name("toHex")
						.parameter("byte[]", "bytes")
						.line("StringBuilder hex = new StringBuilder();")
						.line("for(byte bite: bytes){")
						.line("\thex.append(toHex(bite));")
						.line("}")
						.line("return hex.toString();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Converts the given hex character to an int")
								.param("hexChar", "The hex character to convert")
								.returnVal("The int value of the hex character, or -1 if invalid")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("int").name("hexToInt")
						.parameter("char", "hexChar")
						.line("if('0' <= hexChar && hexChar <= '9'){")
						.line("\treturn hexChar - '0';")
						.line("}else if('A' <= hexChar && hexChar <= 'F'){")
						.line("\treturn hexChar - 'A' + 10;")
						.line("}else if('a' <= hexChar && hexChar <= 'f'){")
						.line("\treturn hexChar - 'a' + 10;")
						.line("}else{")
						.line("\treturn -1;")
						.line("}")
						.build())
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.content("Convert the given hex string into a byte array")
										.param("hex", "The hex string to convert")
										.returnVal("A byte array representing the given hex string")
										.build())
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("byte[]").name("fromHex")
								.parameter("String", "hex")
								.line("int size = hex.length();")
								.line("")
								.line("// Check that the size is even")
								.line("if(size % 2 != 0){")
								.line("\tthrow new IllegalArgumentException(\"hex string must be an even length: \" + hex);")
								.line("}")
								.line("")
								.line("// Create byte array to store the bytes in")
								.line("byte[] bites = new byte[size/2];")
								.line("")
								.line("// Iterate over the string, 2 characters at a time")
								.line("for(int i = 0; i < size; i+=2){")
								.line("\tint highNibble = hexToInt(hex.charAt(i));")
								.line("\tint lowNibble = hexToInt(hex.charAt(i+1));")
								.line("\t// If either nibble came out -1, we have an illegal hex character")
								.line("\tif(highNibble == -1 || lowNibble == -1){")
								.line("\t\tthrow new IllegalArgumentException(\"hex string contains an illegal hex character: \" + hex);")
								.line("\t}")
								.line("\t")
								.line("\tbites[i/2] = (byte) (highNibble*16 + lowNibble);")
								.line("}")
								.line("")
								.line("return bites;")
								.build())
				.build());
	}
}
