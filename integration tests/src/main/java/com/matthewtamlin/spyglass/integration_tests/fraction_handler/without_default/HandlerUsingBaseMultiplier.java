package com.matthewtamlin.spyglass.integration_tests.fraction_handler.without_default;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.fraction_handler.FractionHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class HandlerUsingBaseMultiplier extends FractionHandlerTestTargetBase {
	public static final int MULTIPLIER = 10;

	public HandlerUsingBaseMultiplier(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public HandlerUsingBaseMultiplier(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public HandlerUsingBaseMultiplier(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public HandlerUsingBaseMultiplier(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@FractionHandler(attributeId = R.styleable.FloatHandlerTestTargetBase_floatHandlerAttr, baseMultiplier = MULTIPLIER)
	public void handlerMethod(final float f) {
		setReceivedValue(ReceivedValue.of(f));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(HandlerUsingBaseMultiplier.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.FractionHandlerTestTargetBase)
				.build()
				.passDataToMethods();
	}
}