package com.matthewtamlin.spyglass.library_tests.default_annotations;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFractionResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionResource;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestDefaultToFractionResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToFractionResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToFractionResourceAdapter.class;
	}
}