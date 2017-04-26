package com.matthewtamlin.spyglass.annotations.call_handler_annotations;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestSpecificEnumHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return SpecificEnumHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return SpecificEnumHandlerAdapter.class;
	}
}