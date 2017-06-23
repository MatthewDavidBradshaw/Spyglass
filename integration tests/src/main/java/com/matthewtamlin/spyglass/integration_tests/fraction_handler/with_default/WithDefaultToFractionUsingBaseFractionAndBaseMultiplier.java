package com.matthewtamlin.spyglass.integration_tests.fraction_handler.with_default;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.fraction_handler.FractionHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class WithDefaultToFractionUsingBaseFractionAndBaseMultiplier extends FractionHandlerTestTargetBase {
	public static final int MULTIPLIER = -7;

	public WithDefaultToFractionUsingBaseFractionAndBaseMultiplier(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToFractionUsingBaseFractionAndBaseMultiplier(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToFractionUsingBaseFractionAndBaseMultiplier(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToFractionUsingBaseFractionAndBaseMultiplier(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@FloatHandler(attributeId = R.styleable.FloatHandlerTestTargetBase_floatHandlerAttr)
	@DefaultToFractionResource(resId = R.fraction.base_fraction_for_testing, baseMultiplier = MULTIPLIER)
	public void handlerMethod(final float f) {
		setReceivedValue(ReceivedValue.of(f));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithDefaultToFractionUsingBaseFractionAndBaseMultiplier.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.FractionHandlerTestTargetBase)
				.build()
				.passDataToMethods();
	}
}