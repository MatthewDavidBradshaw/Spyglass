package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.default_processors.DefaultToBooleanSupplierProcessor;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Default(adapterClass = DefaultToBooleanSupplierProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToBooleanSupplier {
	Class<? extends Supplier<Boolean>> value();
}