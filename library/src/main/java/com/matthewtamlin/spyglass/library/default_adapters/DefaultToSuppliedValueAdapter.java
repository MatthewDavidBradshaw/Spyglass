package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToSuppliedValue;
import com.matthewtamlin.spyglass.library.supplier.SupplierInstantiator;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToSuppliedValue annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToSuppliedValueAdapter
		implements DefaultAdapter<Object, DefaultToSuppliedValue> {

	@Override
	public Object getDefault(final DefaultToSuppliedValue annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return SupplierInstantiator.instantiateWildcardSupplier(annotation.value()).get();
	}
}