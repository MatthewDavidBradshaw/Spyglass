package com.matthewtamlin.spyglass.library.binder_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindFraction {
	int annotationId();

	int baseMultiplier() default 1;

	int parentMultiplier() default 1;
}