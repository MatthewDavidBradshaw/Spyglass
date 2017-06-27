package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.integration_tests.string_handler.StringHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.string_handler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integration_tests.string_handler.WithDefaultToString;
import com.matthewtamlin.spyglass.integration_tests.string_handler.WithDefaultToStringResource;
import com.matthewtamlin.spyglass.integration_tests.string_handler.WithoutDefault;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestStringHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEqualsHello() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.string_handler_with_attr_equals_hello);

		final StringHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of("hello")));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.string_handler_without_attr);

		final StringHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<String>none()));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToStringPresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.string_handler_without_attr);

		final StringHandlerTestTargetBase target = new WithDefaultToString(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(WithDefaultToString.DEFAULT_VALUE)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToStringResourcePresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.string_handler_without_attr);

		final StringHandlerTestTargetBase target = new WithDefaultToStringResource(context, attrs);

		final String expectedValue = context.getResources().getString(R.string.stringForTesting);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.string_handler_without_attr);

		final StringHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<String>of(null)));
	}
}