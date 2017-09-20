package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.use_annotation_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import java.util.ArrayList;
import java.util.List;

public class WithUseNumberOnMatchingBoxedTypes extends UseAnnotationsTestTargetBase {
	public WithUseNumberOnMatchingBoxedTypes(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithUseNumberOnMatchingBoxedTypes(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithUseNumberOnMatchingBoxedTypes(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public WithUseNumberOnMatchingBoxedTypes(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@StringHandler(attributeId = R.styleable.UseAnnotationsTestTargetBase_useAnnotationsAttr)
	@DefaultToString("default value")
	public void handlerMethod(
			final String arg0,
			@UseBoolean(true) final Boolean arg1,
			@UseByte(30) final Byte arg2,
			@UseChar('\u1003') final Character arg3,
			@UseDouble(10.5) final Double arg4,
			@UseFloat(40.8F) final Float arg5,
			@UseInt(9) final Integer arg6,
			@UseLong(123456789123456789L) final Long arg7,
			@UseShort(2) final Short arg8) {

		final List<Object> invocationArgs = new ArrayList<>();

		invocationArgs.add(arg0);
		invocationArgs.add(arg1);
		invocationArgs.add(arg2);
		invocationArgs.add(arg3);
		invocationArgs.add(arg4);
		invocationArgs.add(arg5);
		invocationArgs.add(arg6);
		invocationArgs.add(arg7);
		invocationArgs.add(arg8);

		setReceivedValue(ReceivedValue.of(invocationArgs));
	}

	@Override
	public ReceivedValue<List<Object>> getExpectedReceivedValues() {
		final List<Object> expectedArgs = new ArrayList<>();

		expectedArgs.add("default value");
		expectedArgs.add(true);
		expectedArgs.add(30);
		expectedArgs.add('\u1003');
		expectedArgs.add(10.5);
		expectedArgs.add(40.8F);
		expectedArgs.add(9);
		expectedArgs.add(123456789123456789L);
		expectedArgs.add(2);

		return ReceivedValue.of(expectedArgs);
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithUseNumberOnMatchingBoxedTypes.class)
				.withStyleableResource(R.styleable.UseAnnotationsTestTargetBase)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}