package com.matthewtamlin.spyglass.processor.code_generation.do_invocation_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.code_generation.DoInvocationGenerator;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.framework.CompileChecker;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.ExecutableElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Unit tests for the {@link DoInvocationGenerator} class.
 * <p>
 * Cases covered by tests:
 * - Null case
 * - Call handler
 * -- With no args
 * -- With one arg
 * -- With multiple args
 * - Value handler
 * -- With one primitive number arg
 * -- With one primitive non-number arg
 * -- With one object number arg
 * -- With one object non-number arg
 * -- With only recipient
 * -- With recipient and one other arg
 * -- With recipient and multiple other args
 */
@RunWith(JUnit4.class)
public class TestDoInvocationGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"code_generation/do_invocation_generator/Data.java");

	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private DoInvocationGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));

		final CoreHelpers coreHelpers = new CoreHelpers(compilationRule.getElements(), compilationRule.getTypes());
		generator = new DoInvocationGenerator(coreHelpers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullCoreHelpers() {
		new DoInvocationGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullElementSupplied() {
		generator.generateFor(null);
	}

	@Test
	public void testGenerateFor_callHandler_noArgs() {
		doTestForElementWithId("call handler, no args");
	}

	@Test
	public void testGenerateFor_callHandler_oneArg() {
		doTestForElementWithId("call handler, one arg");
	}

	@Test
	public void testGenerateFor_callHandler_multipleArgs() {
		doTestForElementWithId("call handler, multiple args");
	}

	@Test
	public void testGenerateFor_valueHandler_primitiveNumberArg() {
		doTestForElementWithId("value handler, primitive number arg");
	}

	@Test
	public void testGenerateFor_valueHandler_primitiveNonNumberArg() {
		doTestForElementWithId("value handler, primitive non-number arg");
	}

	@Test
	public void testGenerateFor_valueHandler_primitiveCharArg() {
		doTestForElementWithId("value handler, primitive char arg");
	}

	@Test
	public void testGenerateFor_valueHandler_objectNumberArg() {
		doTestForElementWithId("value handler, object number arg");
	}

	@Test
	public void testGenerateFor_valueHandler_objectNonNumberArg() {
		doTestForElementWithId("value handler, object non-number arg");
	}

	@Test
	public void testGenerateFor_valueHandler_objectCharacterArg() {
		doTestForElementWithId("value handler, object character arg");
	}

	@Test
	public void testGenerateFor_valueHandler_multipleArgs() {
		doTestForElementWithId("value handler, multiple args");
	}

	private void doTestForElementWithId(final String id) {
		final ExecutableElement element = getExecutableElementWithId(id);

		final MethodSpec generatedMethod = generator.generateFor(element);

		assertThat(generatedMethod, is(notNullValue()));
		assertThat(generatedMethod.returnType, is(TypeName.VOID));
		assertThat(generatedMethod.parameters, hasSize(0));

		checkCompiles(generatedMethod);
	}

	private ExecutableElement getExecutableElementWithId(final String id) {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}

	private void checkCompiles(final MethodSpec methodSpec) {
		// Create a type to contain the method
		final TypeSpec wrapperTypeSpec = TypeSpec
				.classBuilder("Wrapper")
				.addMethod(methodSpec)
				.build();

		final JavaFile wrapperJavaFile = JavaFile
				.builder("", wrapperTypeSpec)
				.build();

		CompileChecker.checkCompiles(wrapperJavaFile);
	}
}