package com.github.tadukoo.java.packagedeclaration;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class DefaultJavaPackageDeclarationTest<PackageDeclarationType extends JavaPackageDeclaration>{
	
	private final Class<PackageDeclarationType> clazz;
	private final ThrowingSupplier<JavaPackageDeclarationBuilder<PackageDeclarationType>, NoException> builder;
	protected String packageName;
	protected PackageDeclarationType packageDeclaration;
	
	protected DefaultJavaPackageDeclarationTest(
			Class<PackageDeclarationType> clazz,
			ThrowingSupplier<JavaPackageDeclarationBuilder<PackageDeclarationType>, NoException> builder){
		this.clazz = clazz;
		this.builder = builder;
	}
	
	@BeforeEach
	public void setup(){
		packageName = "com.example";
		packageDeclaration = builder.get()
				.packageName(packageName)
				.build();
	}
	
	@Test
	public void testGetType(){
		Assertions.assertEquals(JavaCodeTypes.PACKAGE_DECLARATION, packageDeclaration.getJavaCodeType());
	}
	
	@Test
	public void testBuilderCopy(){
		JavaPackageDeclaration otherPackageDeclaration = builder.get()
				.packageName(packageName)
				.build();
		packageDeclaration = builder.get()
				.copy(otherPackageDeclaration)
				.build();
		assertEquals(otherPackageDeclaration, packageDeclaration);
	}
	
	@Test
	public void testBuilderPackageName(){
		assertEquals(packageName, packageDeclaration.getPackageName());
	}
	
	@Test
	public void testBuilderErrorNoPackageName(){
		try{
			packageDeclaration = builder.get()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("packageName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("package com.example;", packageDeclaration.toString());
	}
	
	@Test
	public void testEquals(){
		JavaPackageDeclaration otherPackageDeclaration = builder.get()
				.packageName(packageName)
				.build();
		assertEquals(packageDeclaration, otherPackageDeclaration);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaPackageDeclaration otherPackageDeclaration = builder.get()
				.packageName("com.github.tadukoo")
				.build();
		assertNotEquals(packageDeclaration, otherPackageDeclaration);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(packageDeclaration, "testing");
	}
	
	@Test
	public void testToBuilderCode(){
		packageDeclaration = builder.get()
				.packageName(packageName)
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.packageName(\"" + packageName + "\")\n" +
				"\t\t.build()", packageDeclaration.toBuilderCode());
	}
}
