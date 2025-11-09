package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.code.staticcodeblock.EditableJavaStaticCodeBlock;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.code.staticcodeblock.UneditableJavaStaticCodeBlock;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertStaticCodeBlockEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findStaticCodeBlockDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestStaticCodeBlockTest{
	
	@ParameterizedTest
	@MethodSource("getStaticCodeBlockDifferences")
	public void testFindStaticCodeBlockDifferences(
			JavaStaticCodeBlock expectedStaticCodeBlock, JavaStaticCodeBlock actualStaticCodeBlock,
			List<String> differences){
		assertEquals(differences, findStaticCodeBlockDifferences(expectedStaticCodeBlock, actualStaticCodeBlock));
	}
	
	@ParameterizedTest
	@MethodSource("getStaticCodeBlockDifferences")
	public void testAssertStaticCodeBlockEquals(
			JavaStaticCodeBlock expectedStaticCodeBlock, JavaStaticCodeBlock actualStaticCodeBlock,
			List<String> differences){
		try{
			assertStaticCodeBlockEquals(expectedStaticCodeBlock, actualStaticCodeBlock);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedStaticCodeBlock, actualStaticCodeBlock)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getStaticCodeBlockDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						EditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						new ArrayList<>()
				),
				// Both Null
				Arguments.of(
						null, null,
						new ArrayList<>()
				),
				// 1 Null 2 Not
				Arguments.of(
						null,
						EditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						ListUtil.createList("One of the static code blocks is null, and the other isn't!")
				),
				// 2 Null 1 Not
				Arguments.of(
						EditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						null,
						ListUtil.createList("One of the static code blocks is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						UneditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						EditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Lines
				Arguments.of(
						EditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						EditableJavaStaticCodeBlock.builder()
								.line("doSomethingElse();")
								.build(),
						ListUtil.createList("Lines differs on #1!")
				),
				// Lines Length
				Arguments.of(
						EditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.line("doA3rdThing();")
								.build(),
						EditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						ListUtil.createList("Lines length is different!",
								"Lines differs on #2!")
				),
				// All
				Arguments.of(
						UneditableJavaStaticCodeBlock.builder()
								.line("doSomething();")
								.build(),
						EditableJavaStaticCodeBlock.builder()
								.line("doSomethingElse();")
								.line("doA3rdThing();")
								.build(),
						ListUtil.createList("Editable is different!",
								"Lines length is different!", "Lines differs on #1!")
				)
		);
	}
}
