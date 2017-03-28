package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class DefaultToDimensionAdapter implements DefaultAdapter<Float, DefaultToDimension> {
	@Override
	public Float getDefault(final DefaultToDimension annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return annotation.unit().convertToPx(context, annotation.value());
	}
}