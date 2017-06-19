package com.matthewtamlin.spyglass.integration_tests.enum_ordinal_handler;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

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
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithDefaultToIntegerResource.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.EnumOrdinalHandlerTestTargetBase)
				.build()
				.passDataToMethods();
	}
}