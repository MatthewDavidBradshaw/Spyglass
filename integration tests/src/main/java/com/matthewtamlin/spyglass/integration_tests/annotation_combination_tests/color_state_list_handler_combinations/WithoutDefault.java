package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.color_state_list_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.ColorStateListHandler;

public class WithoutDefault extends ColorStateListHandlerTestTargetBase {
	public WithoutDefault(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithoutDefault(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithoutDefault(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithoutDefault(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@ColorStateListHandler(attributeId = R.styleable.ColorStateListHandlerTestTargetBase_colorStateListHandlerAttr)
	public void handlerMethod(final ColorStateList csl) {
		setReceivedValue(ReceivedValue.of(csl));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithoutDefault_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.ColorStateListHandlerTestTargetBase)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}