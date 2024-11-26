package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.parsing.FullJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.functional.function.ThrowingFunction2;
import com.github.tadukoo.util.functional.predicate.ThrowingPredicate2;
import com.github.tadukoo.util.tuple.Pair;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;

import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class JavaCodeTypeTest{
	
	/**
	 * Checks if the boolean value is different for the given {@link JavaCodeType} objects. If it's different between
	 * them, "{@code valueName} is different!" will be added to the differences List.
	 *
	 * @param expectedObject The expected object
	 * @param actualObject The actual object
	 * @param differences The List of differences so far (to be potentially added to)
	 * @param valueName The name of the value being compared (for use in the differences List message)
	 * @param booleanMethod The method to retrieve the boolean value from the objects
	 * @param <Type> The {@link JavaCodeType} of the objects being compared
	 */
	public static <Type extends JavaCodeType> void checkBoolean(
			Type expectedObject, Type actualObject, List<String> differences, String valueName,
			ThrowingFunction<Type, Boolean, NoException> booleanMethod){
		if(booleanMethod.apply(expectedObject) != booleanMethod.apply(actualObject)){
			differences.add(valueName + " is different!");
		}
	}
	
	/**
	 * Checks if the String value is different for the given {@link JavaCodeType} objects. If it's different between
	 * them, "{@code valueName} is different!" will be added to the differences List.
	 *
	 * @param expectedObject The expected object
	 * @param actualObject The actual object
	 * @param differences The List of differences so far (to be potentially added to)
	 * @param valueName The name of the value being compared (for use in the differences List message)
	 * @param stringMethod The method to retrieve the String value from the objects
	 * @param <Type> The {@link JavaCodeType} of the objects being compared
	 */
	public static <Type extends JavaCodeType> void checkString(
			Type expectedObject, Type actualObject, List<String> differences, String valueName,
			ThrowingFunction<Type, String, NoException> stringMethod){
		if(StringUtil.notEquals(stringMethod.apply(expectedObject), stringMethod.apply(actualObject)) &&
				!StringUtil.allBlank(stringMethod.apply(expectedObject), stringMethod.apply(actualObject))){
			differences.add(valueName + " is different!");
		}
	}
	
	/**
	 * Checks if the List value is different for the given {@link JavaCodeType} objects. If it's different between
	 * them, "{@code valueName} is different!" will be added to the differences List.
	 *
	 * @param expectedObject The expected object
	 * @param actualObject The actual object
	 * @param differences The List of differences so far (to be potentially added to)
	 * @param valueName The name of the value being compared (for use in the differences List message)
	 * @param listMethod The method to retrieve the List value from the objects
	 * @param listItemCompareMethod The method to compare items in the List (returning true = they're the same)
	 * @param <Type> The {@link JavaCodeType} of the objects being compared
	 * @param <ListType> The type of the items in the List being compared
	 */
	public static <Type extends JavaCodeType, ListType> void checkList(
			Type expectedObject, Type actualObject, List<String> differences, String valueName,
			ThrowingFunction<Type, List<ListType>, NoException> listMethod,
			ThrowingPredicate2<ListType, ListType, NoException> listItemCompareMethod){
		List<ListType> list1 = listMethod.apply(expectedObject), list2 = listMethod.apply(actualObject);
		if(list1.size() != list2.size()){
			differences.add(valueName + " length is different!");
		}
		for(int i = 0; i < list1.size(); i++){
			if(list2.size() <= i || !listItemCompareMethod.test(list1.get(i), list2.get(i))){
				differences.add(valueName + " differs on #" + (i+1) + "!");
			}
		}
	}
	
	/**
	 * Checks if the Enum value is different for the given {@link JavaCodeType} objects. If it's different between
	 * them, "{@code valueName} is different!" will be added to the differences List.
	 *
	 * @param expectedObject The expected object
	 * @param actualObject The actual object
	 * @param differences The List of differences so far (to be potentially added to)
	 * @param valueName The name of the value being compared (for use in the differences List message)
	 * @param enumMethod The method to retrieve the Enum value from the objects
	 * @param <Type> The {@link JavaCodeType} of the objects being compared
	 * @param <E> The type of the Enums being compared
	 */
	public static <Type extends JavaCodeType, E extends Enum<?>> void checkEnum(
			Type expectedObject, Type actualObject, List<String> differences, String valueName,
			ThrowingFunction<Type, E, NoException> enumMethod){
		if(enumMethod.apply(expectedObject) != enumMethod.apply(actualObject)){
			differences.add(valueName + " is different!");
		}
	}
	
	/**
	 * Checks if the subtype is different for the given {@link JavaCodeType} objects. If it's different between
	 * them, "{@code subTypeName} differs:" along with its differences will be added to the differences List.
	 *
	 * @param expectedObject The expected object
	 * @param actualObject The actual object
	 * @param differences The List of differences so far (to be potentially added to)
	 * @param subtypeName The name of the subtype being compared (for use in the differences List message)
	 * @param subtypeMethod The method to retrieve the subtype from the objects
	 * @param subtypeDifferencesMethod The method used to get differences for the subtype objects
	 * @param <Type> The {@link JavaCodeType} of the objects being compared
	 * @param <Subtype> The {@link JavaCodeType} of the subtype objects being compared
	 */
	public static <Type extends JavaCodeType, Subtype extends JavaCodeType> void checkSingleSubtype(
			Type expectedObject, Type actualObject, List<String> differences, String subtypeName,
			ThrowingFunction<Type, Subtype, NoException> subtypeMethod,
			ThrowingFunction2<Subtype, Subtype, List<String>, NoException> subtypeDifferencesMethod){
		Subtype subtype1 = subtypeMethod.apply(expectedObject), subtype2 = subtypeMethod.apply(actualObject);
		List<String> subtypeDifferences = subtypeDifferencesMethod.apply(subtype1, subtype2);
		if(ListUtil.isNotBlank(subtypeDifferences)){
			differences.add(subtypeName + " differs:\n\t" +
					StringUtil.buildStringWithSeparator(subtypeDifferences, "\n\t"));
		}
	}
	
	/**
	 * Checks if the subtypes are different for the given {@link JavaCodeType} objects. If they're different between
	 * them, "{@code subTypeName} differs:" along with its differences will be added to the differences List.
	 *
	 * @param expectedObject The expected object
	 * @param actualObject The actual object
	 * @param differences The List of differences so far (to be potentially added to)
	 * @param subtypeName The name of the subtype being compared (for use in the differences List message)
	 * @param subtypeListMethod The method to retrieve the subtypes from the objects
	 * @param subtypeDifferencesMethod The method used to get differences for the subtype objects
	 * @param <Type> The {@link JavaCodeType} of the objects being compared
	 * @param <Subtype> The {@link JavaCodeType} of the subtype objects being compared
	 */
	public static <Type extends JavaCodeType, Subtype extends JavaCodeType> void checkListSubtype(
			Type expectedObject, Type actualObject, List<String> differences, String subtypeName,
			ThrowingFunction<Type, List<Subtype>, NoException> subtypeListMethod,
			ThrowingFunction2<Subtype, Subtype, List<String>, NoException> subtypeDifferencesMethod){
		List<Subtype> subtypeList1 = subtypeListMethod.apply(expectedObject),
				subtypeList2 = subtypeListMethod.apply(actualObject);
		if(subtypeList1.size() != subtypeList2.size()){
			differences.add(subtypeName + " length is different!");
		}
		for(int i = 0; i < subtypeList1.size(); i++){
			if(subtypeList2.size() <= i){
				differences.add(subtypeName + " differs on #" + (i+1) + "!");
			}else{
				List<String> subtypeDifferences = subtypeDifferencesMethod.apply(subtypeList1.get(i), subtypeList2.get(i));
				if(ListUtil.isNotBlank(subtypeDifferences)){
					differences.add(subtypeName + " differs on #" + (i + 1) + ":\n\t" +
							StringUtil.buildStringWithSeparator(subtypeDifferences, "\n\t"));
				}
			}
		}
	}
	
	/**
	 * Runs the {@link FullJavaParser} for the given content String and verifies it is a {@link JavaCodeTypes#CLASS},
	 * specifically an instance of {@link EditableJavaClass}. It will throw an {@link AssertionFailedError} if it is
	 * not a class result as expected.
	 *
	 * @param content The content String to be parsed into a {@link JavaClass class}
	 * @return The {@link EditableJavaClass} parsed from the given content String
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static EditableJavaClass runParserForClass(String content) throws JavaParsingException{
		JavaCodeType result = FullJavaParser.parseType(content);
		assertEquals(JavaCodeTypes.CLASS, result.getJavaCodeType());
		assertInstanceOf(EditableJavaClass.class, result);
		return (EditableJavaClass) result;
	}
	
	/**
	 * The base method for doing an assertEquals on {@link JavaCodeType} objects.
	 *
	 * @param expectedObject The expected object
	 * @param actualObject The actual object
	 * @param differencesMethod The differences method to use to find differences
	 * @param <Type> The {@link JavaCodeType} of the objects being compared
	 */
	public static <Type extends JavaCodeType> void baseAssertEquals(
			Type expectedObject, Type actualObject,
			ThrowingFunction2<Type, Type, List<String>, NoException> differencesMethod){
		// Find any differences
		List<String> differences = differencesMethod.apply(expectedObject, actualObject);
		// If there are differences, throw an assertion error with the differences
		if(ListUtil.isNotBlank(differences)){
			throw new AssertionFailedError(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedObject, actualObject)));
		}
	}
	
	/**
	 * Finds any differences between the two given {@link JavaPackageDeclaration package declarations}
	 *
	 * @param expectedPackageDeclaration The "expected" {@link JavaPackageDeclaration package declaration}
	 * @param actualPackageDeclaration The "actual" {@link JavaPackageDeclaration package declaration}
	 * @return The List of differences between the {@link JavaPackageDeclaration package declarations}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findPackageDeclarationDifferences(
			JavaPackageDeclaration expectedPackageDeclaration, JavaPackageDeclaration actualPackageDeclaration){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		// Check if both are null
		if(expectedPackageDeclaration == null && actualPackageDeclaration == null){
			return differences;
		}else if(expectedPackageDeclaration == null || actualPackageDeclaration == null){
			differences.add("One of the package declarations is null, and the other isn't!");
			return differences;
		}
		checkBoolean(expectedPackageDeclaration, actualPackageDeclaration, differences,
				"Editable", JavaPackageDeclaration::isEditable);
		checkString(expectedPackageDeclaration, actualPackageDeclaration, differences,
				"Package Name", JavaPackageDeclaration::getPackageName);
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link JavaPackageDeclaration package declarations} are equivalent. It uses
	 * {@link #findPackageDeclarationDifferences(JavaPackageDeclaration, JavaPackageDeclaration)} to find any
	 * differences between the two {@link JavaPackageDeclaration package declarations} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedPackageDeclaration The "expected" {@link JavaPackageDeclaration package declaration}
	 * @param actualPackageDeclaration The "actual" {@link JavaPackageDeclaration package declaration}
	 */
	public static void assertPackageDeclarationEquals(
			JavaPackageDeclaration expectedPackageDeclaration, JavaPackageDeclaration actualPackageDeclaration){
		baseAssertEquals(expectedPackageDeclaration, actualPackageDeclaration,
				JavaCodeTypeTest::findPackageDeclarationDifferences);
	}
	
	/**
	 * Finds any differences between the two given {@link JavaImportStatement import statements}
	 *
	 * @param expectedImportStatement The "expected" {@link JavaImportStatement import statement}
	 * @param actualImportStatement The "actual" {@link JavaImportStatement import statement}
	 * @return The List of differences found between the given {@link JavaImportStatement import statements}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findImportStatementDifferences(
			JavaImportStatement expectedImportStatement, JavaImportStatement actualImportStatement){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		checkBoolean(expectedImportStatement, actualImportStatement, differences,
				"Editable", JavaImportStatement::isEditable);
		checkBoolean(expectedImportStatement, actualImportStatement, differences,
				"Static", JavaImportStatement::isStatic);
		checkString(expectedImportStatement, actualImportStatement, differences,
				"Import Name", JavaImportStatement::getImportName);
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link JavaImportStatement import statements} are equivalent. It uses
	 * {@link #findImportStatementDifferences(JavaImportStatement, JavaImportStatement)} to find any
	 * differences between the two {@link JavaImportStatement import statements} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedImportStatement The "expected" {@link JavaImportStatement import statement}
	 * @param actualImportStatement The "actual" {@link JavaImportStatement import statement}
	 */
	public static void assertImportStatementEquals(
			JavaImportStatement expectedImportStatement, JavaImportStatement actualImportStatement){
		baseAssertEquals(expectedImportStatement, actualImportStatement,
				JavaCodeTypeTest::findImportStatementDifferences);
	}
	
	/**
	 * Finds any differences between the two given {@link Javadoc javadocs}
	 *
	 * @param expectedDoc The "expected" {@link Javadoc}
	 * @param actualDoc The "actual" {@link Javadoc}
	 * @return The List of differences found between the given {@link Javadoc javadocs}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findJavadocDifferences(Javadoc expectedDoc, Javadoc actualDoc){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		// Check if both are null
		if(expectedDoc == null && actualDoc == null){
			return differences;
		}else if(expectedDoc == null || actualDoc == null){
			differences.add("One of the Javadocs is null, and the other isn't!");
			return differences;
		}
		
		// Editable and Condensed
		checkBoolean(expectedDoc, actualDoc, differences, "Editable", Javadoc::isEditable);
		checkBoolean(expectedDoc, actualDoc, differences, "Condensed", Javadoc::isCondensed);
		
		// Content
		checkList(expectedDoc, actualDoc, differences, "Content", Javadoc::getContent, StringUtil::equals);
		
		// Annotations
		checkString(expectedDoc, actualDoc, differences, "Author", Javadoc::getAuthor);
		checkString(expectedDoc, actualDoc, differences, "Version", Javadoc::getVersion);
		checkString(expectedDoc, actualDoc, differences, "Since", Javadoc::getSince);
		checkList(expectedDoc, actualDoc, differences, "Params", Javadoc::getParams, Pair::equals);
		checkString(expectedDoc, actualDoc, differences, "Return", Javadoc::getReturnVal);
		checkList(expectedDoc, actualDoc, differences, "Throws", Javadoc::getThrowsInfos, Pair::equals);
		
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link Javadoc javadocs} are equivalent. It uses
	 * {@link #findJavadocDifferences(Javadoc, Javadoc)} to find any
	 * differences between the two {@link Javadoc javadocs} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedDoc The "expected" {@link Javadoc}
	 * @param actualDoc The "actual" {@link Javadoc}
	 */
	public static void assertJavadocEquals(Javadoc expectedDoc, Javadoc actualDoc){
		baseAssertEquals(expectedDoc, actualDoc, JavaCodeTypeTest::findJavadocDifferences);
	}
	
	/**
	 * Finds any differences between the two given {@link JavaAnnotation annotations}
	 *
	 * @param expectedAnnotation The "expected" {@link JavaAnnotation annotation}
	 * @param actualAnnotation The "actual" {@link JavaAnnotation annotation}
	 * @return The List of differences found between the given {@link JavaAnnotation annotations}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findAnnotationDifferences(
			JavaAnnotation expectedAnnotation, JavaAnnotation actualAnnotation){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		// Check if both are null
		if(expectedAnnotation == null && actualAnnotation == null){
			return differences;
		}else if(expectedAnnotation == null || actualAnnotation == null){
			differences.add("One of the annotations is null, and the other isn't!");
			return differences;
		}
		
		// Editable and Names
		checkBoolean(expectedAnnotation, actualAnnotation, differences, "Editable",
				JavaAnnotation::isEditable);
		checkString(expectedAnnotation, actualAnnotation, differences, "Name", JavaAnnotation::getName);
		checkString(expectedAnnotation, actualAnnotation, differences, "Canonical Name",
				JavaAnnotation::getCanonicalName);
		
		// Parameters
		checkList(expectedAnnotation, actualAnnotation, differences,
				"Parameters", JavaAnnotation::getParameters, Pair::equals);
		
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link JavaAnnotation annotation} are equivalent. It uses
	 * {@link #findAnnotationDifferences(JavaAnnotation, JavaAnnotation)} to find any
	 * differences between the two {@link JavaAnnotation annotations} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedAnnotation The "expected" {@link JavaAnnotation annotation}
	 * @param actualAnnotation The "actual" {@link JavaAnnotation annotation}
	 */
	public static void assertAnnotationEquals(
			JavaAnnotation expectedAnnotation, JavaAnnotation actualAnnotation){
		baseAssertEquals(expectedAnnotation, actualAnnotation, JavaCodeTypeTest::findAnnotationDifferences);
	}
	
	/**
	 * Finds any differences between the two given {@link JavaSingleLineComment single-line comments}
	 *
	 * @param expectedComment The "expected" {@link JavaSingleLineComment single-line comment}
	 * @param actualComment The "actual" {@link JavaSingleLineComment single-line comment}
	 * @return The List of differences found between the given {@link JavaSingleLineComment single-line comments}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findSingleLineCommentDifferences(
			JavaSingleLineComment expectedComment, JavaSingleLineComment actualComment){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		// Check if both are null
		if(expectedComment == null && actualComment == null){
			return differences;
		}else if(expectedComment == null || actualComment == null){
			differences.add("One of the single-line comments is null, and the other isn't!");
			return differences;
		}
		
		// Editable and Content
		checkBoolean(expectedComment, actualComment, differences, "Editable", JavaSingleLineComment::isEditable);
		checkString(expectedComment, actualComment, differences, "Content", JavaSingleLineComment::getContent);
		
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link JavaSingleLineComment single-line comments} are equivalent. It uses
	 * {@link #findSingleLineCommentDifferences(JavaSingleLineComment, JavaSingleLineComment)} to find any
	 * differences between the two {@link JavaSingleLineComment single-line comments} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedComment The "expected" {@link JavaSingleLineComment single-line comment}
	 * @param actualComment The "actual" {@link JavaSingleLineComment single-line comment}
	 */
	public static void assertSingleLineCommentEquals(
			JavaSingleLineComment expectedComment, JavaSingleLineComment actualComment){
		baseAssertEquals(expectedComment, actualComment, JavaCodeTypeTest::findSingleLineCommentDifferences);
	}
	
	/**
	 * Finds any differences between the two given {@link JavaMultiLineComment multi-line comments}
	 *
	 * @param expectedComment The "expected" {@link JavaMultiLineComment multi-line comment}
	 * @param actualComment The "actual" {@link JavaMultiLineComment multi-line comment}
	 * @return The List of differences found between the given {@link JavaMultiLineComment multi-line comments}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findMultiLineCommentDifferences(
			JavaMultiLineComment expectedComment, JavaMultiLineComment actualComment){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		// Check if both are null
		if(expectedComment == null && actualComment == null){
			return differences;
		}else if(expectedComment == null || actualComment == null){
			differences.add("One of the multi-line comments is null, and the other isn't!");
			return differences;
		}
		
		// Editable and Content
		checkBoolean(expectedComment, actualComment, differences, "Editable", JavaMultiLineComment::isEditable);
		checkList(expectedComment, actualComment, differences, "Content", JavaMultiLineComment::getContent, StringUtil::equals);
		
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link JavaMultiLineComment multi-line comments} are equivalent. It uses
	 * {@link #findMultiLineCommentDifferences(JavaMultiLineComment, JavaMultiLineComment)} to find any
	 * differences between the two {@link JavaMultiLineComment multi-line comments} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedComment The "expected" {@link JavaMultiLineComment multi-line comment}
	 * @param actualComment The "actual" {@link JavaMultiLineComment multi-line comment}
	 */
	public static void assertMultiLineCommentEquals(
			JavaMultiLineComment expectedComment, JavaMultiLineComment actualComment){
		baseAssertEquals(expectedComment, actualComment, JavaCodeTypeTest::findMultiLineCommentDifferences);
	}
	
	/**
	 * Finds any differences between the two given {@link JavaField fields}
	 *
	 * @param expectedField The "expected" {@link JavaField field}
	 * @param actualField The "actual" {@link JavaField field}
	 * @return The List of differences found between the given {@link JavaField fields}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findFieldDifferences(JavaField expectedField, JavaField actualField){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		// Check if both are null
		if(expectedField == null && actualField == null){
			return differences;
		}else if(expectedField == null || actualField == null){
			differences.add("One of the fields is null, and the other isn't!");
			return differences;
		}
		
		// Editable
		checkBoolean(expectedField, actualField, differences, "Editable", JavaField::isEditable);
		
		// Javadoc and Annotations
		checkSingleSubtype(expectedField, actualField, differences, "Javadoc",
				JavaField::getJavadoc, JavaCodeTypeTest::findJavadocDifferences);
		checkListSubtype(expectedField, actualField, differences, "Annotations",
				JavaField::getAnnotations, JavaCodeTypeTest::findAnnotationDifferences);
		
		// Modifiers
		checkEnum(expectedField, actualField, differences, "Visibility", JavaField::getVisibility);
		checkBoolean(expectedField, actualField, differences, "Static", JavaField::isStatic);
		checkBoolean(expectedField, actualField, differences, "Final", JavaField::isFinal);
		// Other Info
		checkString(expectedField, actualField, differences, "Type", JavaField::getType);
		checkString(expectedField, actualField, differences, "Name", JavaField::getName);
		checkString(expectedField, actualField, differences, "Value", JavaField::getValue);
		
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link JavaField fields} are equivalent. It uses
	 * {@link #findFieldDifferences(JavaField, JavaField)}  to find any
	 * differences between the two {@link JavaField fields} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedField The "expected" {@link JavaField field}
	 * @param actualField The "actual" {@link JavaField field}
	 */
	public static void assertFieldEquals(JavaField expectedField, JavaField actualField){
		baseAssertEquals(expectedField, actualField, JavaCodeTypeTest::findFieldDifferences);
	}
	
	/**
	 * Finds any differences between the two given {@link JavaMethod methods}
	 *
	 * @param expectedMethod The "expected" {@link JavaMethod method}
	 * @param actualMethod The "actual" {@link JavaMethod method}
	 * @return The List of differences found between the given {@link JavaMethod methods}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findMethodDifferences(JavaMethod expectedMethod, JavaMethod actualMethod){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		// Check if both are null
		if(expectedMethod == null && actualMethod == null){
			return differences;
		}else if(expectedMethod == null || actualMethod == null){
			differences.add("One of the methods is null, and the other isn't!");
			return differences;
		}
		
		// Editable
		checkBoolean(expectedMethod, actualMethod, differences, "Editable", JavaMethod::isEditable);
		
		// Javadoc and Annotations
		checkSingleSubtype(expectedMethod, actualMethod, differences, "Javadoc",
				JavaMethod::getJavadoc, JavaCodeTypeTest::findJavadocDifferences);
		checkListSubtype(expectedMethod, actualMethod, differences, "Annotations",
				JavaMethod::getAnnotations, JavaCodeTypeTest::findAnnotationDifferences);
		
		// Modifiers
		checkEnum(expectedMethod, actualMethod, differences, "Visibility", JavaMethod::getVisibility);
		checkBoolean(expectedMethod, actualMethod, differences, "Abstract", JavaMethod::isAbstract);
		checkBoolean(expectedMethod, actualMethod, differences, "Static", JavaMethod::isStatic);
		checkBoolean(expectedMethod, actualMethod, differences, "Final", JavaMethod::isFinal);
		
		// Type and Name
		checkString(expectedMethod, actualMethod, differences, "Return Type", JavaMethod::getReturnType);
		checkString(expectedMethod, actualMethod, differences, "Name", JavaMethod::getName);
		
		// Parameters, Throw Types, and Content
		checkList(expectedMethod, actualMethod, differences, "Parameters", JavaMethod::getParameters, Pair::equals);
		checkList(expectedMethod, actualMethod, differences, "Throw Types", JavaMethod::getThrowTypes, StringUtil::equals);
		checkList(expectedMethod, actualMethod, differences, "Content", JavaMethod::getLines, StringUtil::equals);
		
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link JavaMethod methods} are equivalent. It uses
	 * {@link #findMethodDifferences(JavaMethod, JavaMethod)} to find any
	 * differences between the two {@link JavaMethod methods} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedMethod The "expected" {@link JavaMethod method}
	 * @param actualMethod The "actual" {@link JavaMethod method}
	 */
	public static void assertMethodEquals(JavaMethod expectedMethod, JavaMethod actualMethod){
		baseAssertEquals(expectedMethod, actualMethod, JavaCodeTypeTest::findMethodDifferences);
	}
	
	/**
	 * Finds any differences between the two given {@link JavaClass classes}
	 *
	 * @param expectedClass The "expected" {@link JavaClass class}
	 * @param actualClass The "actual" {@link JavaClass class}
	 * @return The List of differences found between the given {@link JavaClass classes}
	 * - will be an empty List if there are no differences
	 */
	public static List<String> findClassDifferences(JavaClass expectedClass, JavaClass actualClass){
		// Keep track of differences
		List<String> differences = new ArrayList<>();
		// Check if both are null
		if(expectedClass == null && actualClass == null){
			return differences;
		}else if(expectedClass == null || actualClass == null){
			differences.add("One of the classes is null, and the other isn't!");
			return differences;
		}
		
		// Editable and Inner Class
		checkBoolean(expectedClass, actualClass, differences, "Editable", JavaClass::isEditable);
		checkBoolean(expectedClass, actualClass, differences, "Inner Class", JavaClass::isInnerClass);
		
		// Package Declaration
		checkSingleSubtype(expectedClass, actualClass, differences, "Package Declaration",
				JavaClass::getPackageDeclaration, JavaCodeTypeTest::findPackageDeclarationDifferences);
		
		// Import Statements
		checkListSubtype(expectedClass, actualClass, differences, "Import Statements",
				JavaClass::getImportStatements, JavaCodeTypeTest::findImportStatementDifferences);
		
		// Javadoc
		checkSingleSubtype(expectedClass, actualClass, differences, "Javadoc",
				JavaClass::getJavadoc, JavaCodeTypeTest::findJavadocDifferences);
		
		// Annotations
		checkListSubtype(expectedClass, actualClass, differences, "Annotations",
				JavaClass::getAnnotations, JavaCodeTypeTest::findAnnotationDifferences);
		
		// Modifiers
		checkEnum(expectedClass, actualClass, differences, "Visibility", JavaClass::getVisibility);
		checkBoolean(expectedClass, actualClass, differences, "Abstract", JavaClass::isAbstract);
		checkBoolean(expectedClass, actualClass, differences, "Static", JavaClass::isStatic);
		checkBoolean(expectedClass, actualClass, differences, "Final", JavaClass::isFinal);
		
		// Class Name, Extends, Implements
		checkString(expectedClass, actualClass, differences, "Class Name", JavaClass::getClassName);
		checkString(expectedClass, actualClass, differences, "Super Class Name", JavaClass::getSuperClassName);
		checkList(expectedClass, actualClass, differences, "Implements Interface Names",
				JavaClass::getImplementsInterfaceNames, StringUtil::equals);
		
		// Single Line Comments
		checkListSubtype(expectedClass, actualClass, differences, "Single Line Comments",
				JavaClass::getSingleLineComments, JavaCodeTypeTest::findSingleLineCommentDifferences);
		
		// Multi Line Comments
		checkListSubtype(expectedClass, actualClass, differences, "Multi Line Comments",
				JavaClass::getMultiLineComments, JavaCodeTypeTest::findMultiLineCommentDifferences);
		
		// Inner Classes
		checkListSubtype(expectedClass, actualClass, differences, "Inner Classes",
				JavaClass::getInnerClasses, JavaCodeTypeTest::findClassDifferences);
		
		// Fields
		checkListSubtype(expectedClass, actualClass, differences, "Fields",
				JavaClass::getFields, JavaCodeTypeTest::findFieldDifferences);
		
		// Methods
		checkListSubtype(expectedClass, actualClass, differences, "Methods",
				JavaClass::getMethods, JavaCodeTypeTest::findMethodDifferences);
		
		// Inner Elements Order
		checkList(expectedClass, actualClass, differences, "Inner Elements Order",
				JavaClass::getInnerElementsOrder, Pair::equals);
		
		return differences;
	}
	
	/**
	 * Asserts that the two given {@link JavaClass classes} are equivalent. It uses
	 * {@link #findClassDifferences(JavaClass, JavaClass)}  to find any
	 * differences between the two {@link JavaClass classes} and will throw an
	 * {@link AssertionFailedError} if any differences are found
	 *
	 * @param expectedClass The "expected" {@link JavaClass class}
	 * @param actualClass The "actual" {@link JavaClass class}
	 */
	public static void assertClassEquals(JavaClass expectedClass, JavaClass actualClass){
		baseAssertEquals(expectedClass, actualClass, JavaCodeTypeTest::findClassDifferences);
	}
}
