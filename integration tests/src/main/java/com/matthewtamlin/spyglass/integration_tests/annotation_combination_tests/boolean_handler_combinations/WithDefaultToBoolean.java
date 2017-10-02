package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.boolean_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.BooleanHandler;

public class WithDefaultToBoolean extends BooleanHandlerTestTargetBase {
	public static final boolean DEFAULT_VALUE = true;

	public WithDefaultToBoolean(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToBoolean(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToBoolean(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToBoolean(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@BooleanHandler(attributeId = R.styleable.BooleanHandlerTestTargetBase_booleanHandlerAttr)
	@DefaultToBoolean(DEFAULT_VALUE)
	public void handlerMethod(final boolean b) {
		setReceivedValue(ReceivedValue.of(b));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithDefaultToBoolean_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.BooleanHandlerTestTargetBase)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}