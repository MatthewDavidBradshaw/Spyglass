package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;
import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public abstract class TestValueHandler {
	public abstract Class<? extends Annotation> getAnnotationUnderTest();

	public abstract Class<? extends ValueHandlerAdapter> getExpectedAdapterClass();

	private ValueHandler metaAnnotation;

	@Before
	public void setup() {
		// Could be null
		metaAnnotation = getAnnotationUnderTest().getAnnotation(ValueHandler.class);
	}

	@Test
	public void testMetaAnnotationIsPresent() {
		assertThat("Meta-annotation is missing.", metaAnnotation, is(notNullValue()));
	}

	@Test
	public void testMetaAnnotationHasCorrectAdapter() {
		assertThat("Meta-annotation specifies wrong adapter.",
				metaAnnotation.adapterClass(),
				equalTo((Class) getExpectedAdapterClass()));
	}
}