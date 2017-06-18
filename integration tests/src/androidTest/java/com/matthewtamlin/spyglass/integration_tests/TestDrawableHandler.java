package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.drawable_handler.DrawableHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.drawable_handler.WithDefaultToDrawable;
import com.matthewtamlin.spyglass.integration_tests.drawable_handler.WithoutDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.integration_tests.testing_utilities.AttributeSetSupplier.fromXml;

@RunWith(AndroidJUnit4.class)
public class TestDrawableHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent() {
		final AttributeSet attrs = fromXml(context, R.xml.drawable_handler_with_attr_equals_main_drawable);

		final DrawableHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		final Drawable expectedValue = ContextCompat.getDrawable(context, R.drawable.main_drawable_for_testing);
		checkReceivedDrawablesAreEqual(target.getReceivedValue(), ReceivedValue.of(expectedValue));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
		final AttributeSet attrs = fromXml(context, R.xml.drawable_handler_without_attr);

		final DrawableHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		checkReceivedDrawablesAreEqual(target.getReceivedValue(), ReceivedValue.<Drawable>none());
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDrawablePresent() {
		final AttributeSet attrs = fromXml(context, R.xml.drawable_handler_without_attr);

		final DrawableHandlerTestTargetBase target = new WithDefaultToDrawable(context, attrs);

		final Drawable expectedValue = ContextCompat.getDrawable(context, R.drawable.default_drawable_for_testing);
		checkReceivedDrawablesAreEqual(target.getReceivedValue(), ReceivedValue.of(expectedValue));
	}

	private boolean checkReceivedDrawablesAreEqual(
			final ReceivedValue<Drawable> arg1,
			final ReceivedValue<Drawable> arg2) {

		checkNotNull(arg1, "Argument \'arg1\' cannot be null.");
		checkNotNull(arg1, "Argument \'arg2\' cannot be null.");

		if (!arg1.exists() || !arg2.exists()) {
			return !arg1.exists() && !arg2.exists();

		} else if (arg1.get() == null || arg2.get() == null) {
			return arg1.get() == arg2.get();

		} else {
			return arg1.get().getConstantState().equals(arg2.get().getConstantState());
		}
	}
}