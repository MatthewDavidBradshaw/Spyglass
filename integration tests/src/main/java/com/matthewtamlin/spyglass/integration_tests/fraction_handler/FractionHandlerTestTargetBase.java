package com.matthewtamlin.spyglass.integration_tests.fraction_handler;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class FractionHandlerTestTargetBase extends View {
	private ReceivedValue<Float> receivedValue = ReceivedValue.none();

	public FractionHandlerTestTargetBase(final Context context) {
		super(context);
	}

	public FractionHandlerTestTargetBase(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public FractionHandlerTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public FractionHandlerTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ReceivedValue<Float> getReceivedValue() {
		return receivedValue;
	}

	protected void setReceivedValue(final ReceivedValue<Float> receivedValue) {
		this.receivedValue = receivedValue;
	}
}