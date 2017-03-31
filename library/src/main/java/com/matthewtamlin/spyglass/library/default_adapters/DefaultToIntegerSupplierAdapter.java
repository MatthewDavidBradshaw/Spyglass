package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerSupplier;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.library.core.supplier.SupplierInstantiator.instantiateSupplier;

public class DefaultToIntegerSupplierAdapter
		implements DefaultAdapter<Integer, DefaultToIntegerSupplier> {

	@Override
	public Integer getDefault(final DefaultToIntegerSupplier annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return instantiateSupplier(annotation.value()).get();
	}
}