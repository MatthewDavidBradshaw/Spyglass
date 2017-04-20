package com.matthewtamlin.spyglass.library_tests.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.TextHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.TextHandler;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestTextHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return TextHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return TextHandlerAdapter.class;
	}
}