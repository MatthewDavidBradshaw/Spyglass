package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.core.Supplier;

public @interface DefaultToIntegerSupplier {
	Class<? extends Supplier<Integer>> value();
}