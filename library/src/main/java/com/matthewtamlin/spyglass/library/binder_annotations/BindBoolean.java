package com.matthewtamlin.spyglass.library.binder_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindBoolean {
	int annotationId();

	boolean ignoreIfAttributeMissing() default true;

	int defaultResourceId() default 0;

	boolean defaultValue() default true;
}