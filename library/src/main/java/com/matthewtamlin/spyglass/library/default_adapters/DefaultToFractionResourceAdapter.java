package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToFractionResource annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToFractionResourceAdapter
		implements DefaultAdapter<Float, DefaultToFractionResource> {

	@Override
	public Float getDefault(final DefaultToFractionResource annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return context.getResources().getFraction(
				annotation.resId(),
				annotation.baseMultiplier(),
				annotation.parentMultiplier());
	}
}