package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_ordinal_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.EnumOrdinalHandler;

public class WithDefaultToIntegerResource extends EnumOrdinalHandlerTestTargetBase {
	public WithDefaultToIntegerResource(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToIntegerResource(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToIntegerResource(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToIntegerResource(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@EnumOrdinalHandler(attributeId = R.styleable.EnumOrdinalHandlerTestTargetBase_language)
	@DefaultToIntegerResource(resId = R.integer.IntegerForTesting)
	public void handlerMethod(final int i) {
		setReceivedValue(ReceivedValue.of(i));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithDefaultToIntegerResource_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.EnumOrdinalHandlerTestTargetBase)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}