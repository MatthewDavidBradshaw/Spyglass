package com.matthewtamlin.spyglass.processor.code_generation.value_is_available_method_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.processor.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processor.code_generation.ValueIsAvailableMethodGenerator;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.framework.CompileChecker;
import com.matthewtamlin.spyglass.processor.mirror_helpers.AnnotationMirrorHelper;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import static javax.lang.model.element.Modifier.STATIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestValueIsAvailableMethodGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"code_generation/value_is_available_method_generator/Data.java");

	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private ValueIsAvailableMethodGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));

		final CoreHelpers coreHelpers = new CoreHelpers(compilationRule.getElements(), compilationRule.getTypes());
		generator = new ValueIsAvailableMethodGenerator(coreHelpers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullCoreHelpers() {
		new ValueIsAvailableMethodGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullSupplied() {
		generator.generateFor(null);
	}

	@Test
	public void testGenerateFor_booleanHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("boolean");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, BooleanHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_colorHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, ColorHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_colorStateListHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color state list");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				ColorStateListHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_dimensionHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("dimension");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DimensionHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_drawableHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("drawable");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DrawableHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_enumConstantHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("enum constant");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, EnumConstantHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_EnumOrdinalHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("enum ordinal");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, EnumOrdinalHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_floatHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("float");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, FloatHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_fractionHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("fraction");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, FractionHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_integerHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("integer");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, IntegerHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_stringHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("string");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, StringHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	private void checkMethodSignature(final MethodSpec generatedMethod) {
		assertThat("Generated method must not be null.", generatedMethod, is(notNullValue()));
		assertThat("Generated method has wrong return type.", generatedMethod.returnType, is(TypeName.BOOLEAN.box()));
		assertThat("Generated method has wrong number of parameters.", generatedMethod.parameters.size(), is(0));
		assertThat("Generated method must not be static.", generatedMethod.modifiers.contains(STATIC), is(false));
	}

	private void checkCompiles(final MethodSpec method) {
		final TypeSpec wrapperTypeSpec = CallerDef
				.getNewCallerSubclassPrototype("Wrapper", TypeName.OBJECT)
				.addMethod(CallerDef.getNewCallMethodPrototype().build())
				.addMethod(CallerDef.getNewConstructorPrototype(TypeName.OBJECT).build())
				.addMethod(method)
				.build();

		final JavaFile wrapperJavaFile = JavaFile
				.builder("", wrapperTypeSpec)
				.build();

		final Set<JavaFile> filesToCompile = new HashSet<>();
		filesToCompile.add(wrapperJavaFile);
		filesToCompile.add(CallerDef.SRC_FILE);

		CompileChecker.checkCompiles(filesToCompile);
	}
}