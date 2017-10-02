package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.dimension_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.DimensionHandler;

public class WithDefaultToPtDimensionResource extends DimensionHandlerTestTargetBase {
	public WithDefaultToPtDimensionResource(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToPtDimensionResource(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToPtDimensionResource(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToPtDimensionResource(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@DimensionHandler(attributeId = R.styleable.DimensionHandlerTestTargetBase_dimensionHandlerAttr)
	@DefaultToDimensionResource(resId = R.dimen.DimensionForTestingPt)
	public void handlerMethod(final int i) {
		setReceivedValue(ReceivedValue.of(i));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithDefaultToPtDimensionResource_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.DimensionHandlerTestTargetBase)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}