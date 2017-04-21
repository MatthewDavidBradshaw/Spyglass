package com.matthewtamlin.spyglass.library_tests.core;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.matthewtamlin.spyglass.library.core.InvalidBuilderStateException;
import com.matthewtamlin.spyglass.library.core.Spyglass;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestSpyglassBuilder {
	@Test(expected = InvalidBuilderStateException.class)
	public void testBuild_noViewEverSupplied() {
		Spyglass.builder()
				.withContext(mock(Context.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testBuild_nullViewSupplied() {
		Spyglass.builder()
				.withTarget(null)
				.withContext(mock(Context.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testBuild_noContextEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testBuild_nullContextSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(null)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testBuild_noStyleableResourceEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(mock(Context.class))
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testBuild_nullStyleableResourceSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(mock(Context.class))
				.withStyleableResource(null)
				.build();
	}

	@Test
	public void testBuild_allMandatoryValuesSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(getContext())
				.withStyleableResource(new int[0])
				.build();
	}
}