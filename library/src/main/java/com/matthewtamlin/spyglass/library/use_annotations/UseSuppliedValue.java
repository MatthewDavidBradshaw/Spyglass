package com.matthewtamlin.spyglass.library.use_annotations;

import com.matthewtamlin.spyglass.library.core.supplier.Supplier;
import com.matthewtamlin.spyglass.library.meta_annotations.Use;
import com.matthewtamlin.spyglass.library.use_adapters.UseSupplierAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Use(adapterClass = UseSupplierAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface UseSuppliedValue {
	Class<? extends Supplier<?>> value();
}