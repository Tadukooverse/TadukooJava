package com.github.tadukoo.java.testing.util.pojo;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class AbstractOrderedMappedPojoTest extends JavaClassParsingTest{
	
	public AbstractOrderedMappedPojoTest(){
		super("""
				package com.github.tadukoo.util.pojo;
				
				/**
				 * Abstract Ordered Mapped Pojo is a simple implementation of {@link OrderedMappedPojo}. It extends
				 * {@link AbstractMappedPojo}, and requires its subclasses to implement the {@link #getKeyOrder()} method.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.2.1
				 * @since Alpha v.0.2
				 */
				public abstract class AbstractOrderedMappedPojo extends AbstractMappedPojo implements OrderedMappedPojo{
				\t
					/** {@inheritDoc} */
					protected AbstractOrderedMappedPojo(){
						super();
					}
				\t
					/** {@inheritDoc} */
					protected AbstractOrderedMappedPojo(MappedPojo pojo){
						super(pojo);
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.pojo")
				.javadoc(EditableJavadoc.builder()
						.content("Abstract Ordered Mapped Pojo is a simple implementation of {@link OrderedMappedPojo}. It extends")
						.content("{@link AbstractMappedPojo}, and requires its subclasses to implement the {@link #getKeyOrder()} method.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.2.1")
						.since("Alpha v.0.2")
						.build())
				.visibility(Visibility.PUBLIC)
				.isAbstract()
				.className("AbstractOrderedMappedPojo")
				.superClassName("AbstractMappedPojo")
				.implementsInterfaceName("OrderedMappedPojo")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("{@inheritDoc}")
								.build())
						.visibility(Visibility.PROTECTED)
						.returnType("AbstractOrderedMappedPojo")
						.line("super();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("{@inheritDoc}")
								.build())
						.visibility(Visibility.PROTECTED)
						.returnType("AbstractOrderedMappedPojo")
						.parameter("MappedPojo pojo")
						.line("super(pojo);")
						.build())
				.build());
	}
}
