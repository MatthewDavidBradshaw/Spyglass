package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToBooleanSupplierAdapter
		implements DefaultAdapter<Boolean, DefaultToBooleanSupplier> {

	@Override
	public Boolean process(final DefaultToBooleanSupplier annotation, final Context context) {
		return instantiateSupplier(annotation.value()).get();
	}
}