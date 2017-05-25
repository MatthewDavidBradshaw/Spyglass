package com.matthewtamlin.spyglass.processors.annotation_utils.annotation_mirror_util;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.java_compiler_utilities.element_supplier.CompilerMissingException;
import com.matthewtamlin.java_compiler_utilities.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
@SuppressWarnings("unchecked") // Not relevant when mocking
public class TestAnnotationMirrorUtil {
	@Rule
	public CompilationRule compilationRule = new CompilationRule();

	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/annotation_utils/annotation_mirror_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationMirror_nullElement() {
		AnnotationMirrorUtil.getAnnotationMirror(null, Annotation.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationMirror_nullAnnotationClass() {
		AnnotationMirrorUtil.getAnnotationMirror(mock(Element.class), null);
	}

	@Test
	public void testGetAnnotationMirror_annotationMissing() throws CompilerMissingException {
		final Element element = elementSupplier.getUniqueElementWithId("get annotation mirror: without annotation");

		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, AnnotationWithValues.class);

		assertThat(mirror, is(nullValue()));
	}

	@Test
	public void testGetAnnotationMirror_annotationPresent() throws CompilerMissingException {
		final Element element = elementSupplier.getUniqueElementWithId("get annotation mirror: with annotation");

		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, AnnotationWithValues.class);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(AnnotationWithValues.class.getName()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationValueWithDefaults_nullAnnotationMirrorSupplied() {
		AnnotationMirrorUtil.getAnnotationValueWithDefaults(null, "", compilationRule.getElements());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationValueWithDefaults_nullValueKeySupplied() {
		AnnotationMirrorUtil.getAnnotationValueWithDefaults(mock(AnnotationMirror.class),
				null,
				compilationRule.getElements());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationValueWithDefaults_nullElementUtilSupplied() {
		AnnotationMirrorUtil.getAnnotationValueWithDefaults(mock(AnnotationMirror.class), "", null);
	}

	@Test
	public void testGetAnnotationValueIgnoringDefaults_invalidKey() throws CompilerMissingException {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value ignoring defaults: with value");

		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(e, AnnotationWithValues.class);

		final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueIgnoringDefaults(mirror, "invalidKey");

		assertThat(value, is(nullValue()));
	}

	@Test
	public void testGetAnnotationValueIgnoreDefaults_noValueProvided() throws CompilerMissingException {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value ignoring defaults: no value");
		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(e, AnnotationWithValues.class);

		final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueIgnoringDefaults(mirror, "value");

		assertThat(value, is(nullValue()));
	}

	@Test
	public void testGetAnnotationValueIgnoringDefaults_valueProvided() throws CompilerMissingException {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value ignoring defaults: with value");
		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(e, AnnotationWithValues.class);

		final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueIgnoringDefaults(mirror, "value");

		assertThat(value, is(notNullValue()));
		assertThat((String) value.getValue(), is(Data.SPECIFIED_VALUE));
	}

	@Test
	public void testGetAnnotationValueWithDefaults_invalidKey() throws CompilerMissingException {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value with defaults: with value");
		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(e, AnnotationWithValues.class);

		final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
				mirror,
				"invalidKey",
				compilationRule.getElements());

		assertThat(value, is(nullValue()));
	}

	@Test
	public void testGetAnnotationValueWithDefaults_noValueProvided() throws CompilerMissingException {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value with defaults: no value");
		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(e, AnnotationWithValues.class);

		final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
				mirror,
				"value",
				compilationRule.getElements());

		assertThat(value, is(notNullValue()));
		assertThat((String) value.getValue(), is(AnnotationWithValues.DEFAULT_VALUE));
	}

	@Test
	public void testGetAnnotationValueWithDefaults_valueProvided() throws CompilerMissingException {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value with defaults: with value");
		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(e, AnnotationWithValues.class);

		final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
				mirror,
				"value",
				compilationRule.getElements());

		assertThat(value, is(notNullValue()));
		assertThat((String) value.getValue(), is(Data.SPECIFIED_VALUE));
	}
}