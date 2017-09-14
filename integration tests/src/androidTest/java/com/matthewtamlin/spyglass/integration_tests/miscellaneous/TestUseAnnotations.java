package com.matthewtamlin.spyglass.integration_tests.miscellaneous;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.integration_tests.miscellaneous.UseAnnotationsTestTarget;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestUseAnnotations {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testUseAnnotationsPassCorrectValues() {
		final UseAnnotationsTestTarget target = new UseAnnotationsTestTarget(context);

		assertThat(target.getReceivedValue(), is(target.getUseAnnotationValues()));
	}
}