package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.integer_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.annotations.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class WithDefaultToInteger extends IntegerHandlerTestTargetBase {
	public static final int DEFAULT_VALUE = 60;

	public WithDefaultToInteger(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToInteger(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToInteger(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToInteger(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@IntegerHandler(attributeId = R.styleable.IntegerHandlerTestTargetBase_integerHandlerAttr)
	@DefaultToInteger(DEFAULT_VALUE)
	public void handlerMethod(final int i) {
		setReceivedValue(ReceivedValue.of(i));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithDefaultToInteger.class)
				.withStyleableResource(R.styleable.IntegerHandlerTestTargetBase)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}