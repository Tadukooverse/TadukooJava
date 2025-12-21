package com.github.tadukoo.java.javainterface;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.classtype.JavaClassType;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaannotationclass.JavaAnnotationClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javaenum.JavaEnum;
import com.github.tadukoo.java.javarecord.JavaRecord;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.util.tuple.Pair;

import java.util.List;

public abstract class JavaInterface extends JavaClassType{
	
	protected JavaInterface(
			boolean editable, boolean isInnerType,
			JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal, JavaType name,
			List<JavaType> extendsTypes, List<JavaType> implementsTypes,
			List<JavaStaticCodeBlock> staticCodeBlocks,
			List<JavaSingleLineComment> singleLineComments, List<JavaMultiLineComment> multiLineComments,
			List<JavaClass> innerClasses, List<JavaEnum> innerEnums, List<JavaInterface> innerInterfaces,
			List<JavaRecord> innerRecords, List<JavaAnnotationClass> innerAnnotationClasses,
			List<JavaField> fields, List<JavaMethod> methods,
			List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		super(editable, isInnerType,
				packageDeclaration, importStatements,
				javadoc, annotations,
				visibility, isAbstract, isStatic, isFinal, JavaCodeTypes.INTERFACE, name,
				extendsTypes, implementsTypes,
				staticCodeBlocks,
				singleLineComments, multiLineComments,
				innerClasses, innerEnums, innerInterfaces,
				innerRecords, innerAnnotationClasses,
				fields, methods,
				innerElementsOrder);
	}
}
