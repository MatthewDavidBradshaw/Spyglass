package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.integration_tests.inheritance_behaviour_test_target.Subclass;
import com.matthewtamlin.spyglass.integration_tests.inheritance_behaviour_test_target.Superclass;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class TestInheritanceBehaviour {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSuperclassInstantiationTriggersSuperclassSpyglass() {
		final Superclass s = new Superclass(context);

		assertThat(s.getSuperclassActualInvocationArgs(), is(s.getSuperclassExpectedInvocationArgs()));
	}

	@Test
	@UiThreadTest
	public void testSubclassInstantiationTriggersSuperclassSpyglass() {
		final Subclass s = new Subclass(context);

		assertThat(s.getSuperclassActualInvocationArgs(), is(s.getSuperclassExpectedInvocationArgs()));
	}

	@Test
	@UiThreadTest
	public void testSubclassInstantiationTriggersSubclassSpyglass() {
		final Subclass s = new Subclass(context);

		assertThat(s.getSubclassActualInvocationArgs(), is(s.getSubclassExpectedInvocationArgs()));
	}
}