package com.matthewtamlin.spyglass.library.handler_annotations;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;
import com.matthewtamlin.spyglass.library.meta_annotations.Handles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.PT;

@Handles
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface HandlesDimension {
	int annotationId();

	DimensionUnit dimensionUnit() default PT;
}