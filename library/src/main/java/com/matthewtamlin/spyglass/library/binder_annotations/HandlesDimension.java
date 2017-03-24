package com.matthewtamlin.spyglass.library.binder_annotations;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.PT;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface HandlesDimension {
	int annotationId();

	DimensionUnit dimensionUnit() default PT;
}