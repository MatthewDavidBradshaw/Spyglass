package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.enum_ordinal_handler.EnumOrdinalHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.enum_ordinal_handler.WithDefaultToInteger;
import com.matthewtamlin.spyglass.integration_tests.enum_ordinal_handler.WithDefaultToIntegerResource;
import com.matthewtamlin.spyglass.integration_tests.enum_ordinal_handler.WithoutDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.matthewtamlin.spyglass.integration_tests.testing_utilities.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestEnumOrdinalHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEqualsC() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_ordinal_handler_with_attr_equals_c);

		final EnumOrdinalHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(1)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_ordinal_handler_without_attr);

		final EnumOrdinalHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<Integer>none()));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToIntegerPresent() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_ordinal_handler_without_attr);

		final EnumOrdinalHandlerTestTargetBase target = new WithDefaultToInteger(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(WithDefaultToInteger.DEFAULT_VALUE)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToIntegerResourcePresent() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_ordinal_handler_without_attr);

		final EnumOrdinalHandlerTestTargetBase target = new WithDefaultToIntegerResource(context, attrs);

		final int expectedValue = context.getResources().getInteger(R.integer.IntegerForTesting);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}
}