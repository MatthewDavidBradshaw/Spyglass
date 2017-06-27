package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.float_handler.FloatHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.float_handler.WithDefaultToFractionUsingBaseFractionAndBaseMultiplier;
import com.matthewtamlin.spyglass.integration_tests.float_handler.WithDefaultToFractionUsingBaseFractionAndNoMultiplier;
import com.matthewtamlin.spyglass.integration_tests.float_handler.WithDefaultToFractionUsingBaseFractionAndParentMultiplier;
import com.matthewtamlin.spyglass.integration_tests.float_handler.WithDefaultToFractionUsingParentFractionAndBaseMultiplier;
import com.matthewtamlin.spyglass.integration_tests.float_handler.WithDefaultToFractionUsingParentFractionAndNoMultiplier;
import com.matthewtamlin.spyglass.integration_tests.float_handler.WithDefaultToFractionUsingParentFractionAndParentMultiplier;
import com.matthewtamlin.spyglass.integration_tests.float_handler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integration_tests.float_handler.WithoutDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestFloatHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEquals14PointFive() {
		final AttributeSet attrs = fromXml(context, R.xml.float_handler_with_attr_equals_14_point_5);

		final FloatHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(14.5F)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefault() {
		final AttributeSet attrs = fromXml(context, R.xml.float_handler_without_attr);

		final FloatHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<Float>none()));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingBaseFractionAndBaseMultiplier() {
		final AttributeSet attrs = fromXml(context, R.xml.float_handler_without_attr);

		final FloatHandlerTestTargetBase target = new WithDefaultToFractionUsingBaseFractionAndBaseMultiplier(
				context,
				attrs);

		final int multiplier = WithDefaultToFractionUsingBaseFractionAndBaseMultiplier.MULTIPLIER;
		final float expectedValue = getBaseFractionMultipliedBy(multiplier);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingBaseFractionAndPrntMultiplier() {
		final AttributeSet attrs = fromXml(context, R.xml.float_handler_without_attr);

		final FloatHandlerTestTargetBase target = new WithDefaultToFractionUsingBaseFractionAndParentMultiplier(
				context,
				attrs);

		final float expectedValue = getBaseFractionMultipliedBy(1);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingBaseFractionAndNoMultiplier() {
		final AttributeSet attrs = fromXml(context, R.xml.float_handler_without_attr);

		final FloatHandlerTestTargetBase target = new WithDefaultToFractionUsingBaseFractionAndNoMultiplier(
				context,
				attrs);

		final float expectedValue = getBaseFractionMultipliedBy(1);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingPrntFractionAndBaseMultiplier() {
		final AttributeSet attrs = fromXml(context, R.xml.float_handler_without_attr);

		final FloatHandlerTestTargetBase target = new WithDefaultToFractionUsingParentFractionAndBaseMultiplier(
				context,
				attrs);

		final float expectedValue = getParentFractionMultipliedBy(1);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingPrntFractionAndPrntMultiplier() {
		final AttributeSet attrs = fromXml(context, R.xml.float_handler_without_attr);

		final FloatHandlerTestTargetBase target = new WithDefaultToFractionUsingParentFractionAndParentMultiplier(
				context,
				attrs);

		final int multiplier = WithDefaultToFractionUsingParentFractionAndParentMultiplier.MULTIPLIER;
		final float expectedValue = getParentFractionMultipliedBy(multiplier);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingPrntFractionAndNoMultiplier() {
		final AttributeSet attrs = fromXml(context, R.xml.float_handler_without_attr);

		final FloatHandlerTestTargetBase target = new WithDefaultToFractionUsingParentFractionAndNoMultiplier(
				context,
				attrs);

		final float expectedValue = getParentFractionMultipliedBy(1);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.float_handler_without_attr);

		final FloatHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<Float>of(null)));
	}

	private float getBaseFractionMultipliedBy(final int i) {
		return context.getResources().getFraction(R.fraction.base_fraction_for_testing, i, 0);
	}

	private float getParentFractionMultipliedBy(final int i) {
		return context.getResources().getFraction(R.fraction.parent_fraction_for_testing, 0, i);
	}
}