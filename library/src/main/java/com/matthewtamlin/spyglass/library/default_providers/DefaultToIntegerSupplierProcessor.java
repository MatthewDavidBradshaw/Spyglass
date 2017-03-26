package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToIntegerSupplierProcessor
		implements DefaultProcessor<Integer, DefaultToIntegerSupplier> {

	@Override
	public Integer process(final DefaultToIntegerSupplier annotation, final Context context) {
		return instantiateSupplier(annotation.value()).get();
	}
}