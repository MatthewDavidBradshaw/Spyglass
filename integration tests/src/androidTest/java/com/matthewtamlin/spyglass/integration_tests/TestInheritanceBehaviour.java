package com.matthewtamlin.spyglass.integration_tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.integration_tests.test_inheritance_behaviour.Subclass;
import com.matthewtamlin.spyglass.integration_tests.test_inheritance_behaviour.Superclass;
import com.matthewtamlin.spyglass.integration_tests.testing_utilities.SynchronousUiThreadExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class TestInheritanceBehaviour {
	@Rule
	public final ActivityTestRule<EmptyActivity> activityRule = new ActivityTestRule<>(EmptyActivity.class);

	private SynchronousUiThreadExecutor executor;

	@Before
	public void setup() {
		executor = new SynchronousUiThreadExecutor(activityRule.getActivity());
	}

	@Test
	public void testSubclassInstantiationTriggersSuperclassSpyglass() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Subclass s = new Subclass(activityRule.getActivity());

				assertThat("Spyglass didn't pass a value.", s.getSuperclassInvocationRecord(), is(notNullValue()));
				assertThat("Spyglass passed the wrong value.",
						s.getSuperclassInvocationRecord().get(0),
						is((Object) Superclass.DEFAULT_VALUE));
			}
		});
	}
}